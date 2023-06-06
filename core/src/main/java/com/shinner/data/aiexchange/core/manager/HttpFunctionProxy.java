package com.shinner.data.aiexchange.core.manager;

import com.alibaba.fastjson.JSONObject;
import com.google.gson.reflect.TypeToken;
import com.shinner.data.aiexchange.common.thread.NameThreadFactory;
import com.shinner.data.aiexchange.core.entity.AiFunctionDO;
import com.shinner.data.aiexchange.model.AiResult;
import com.shinner.data.aiexchange.model.RestResponse;
import com.shinner.data.aiexchange.model.enums.AiRequestStatus;
import com.shinner.data.aiexchange.model.enums.ErrorCode;
import com.shinner.data.aiexchange.model.exception.AiServiceError;
import com.shinner.data.aiexchange.model.exception.TooFrequentException;
import com.shinner.data.aiexchange.common.utils.*;
import okhttp3.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;
import java.util.concurrent.*;
import java.util.concurrent.locks.ReentrantLock;

public class HttpFunctionProxy extends AbstractAiFunctionProxy {
    private final Logger logger = LoggerFactory.getLogger(getClass());

    private final String host;
    private final ExecutorService executorService;
    private ConcurrentLimiter limiter;
    private final ConcurrentCounter counter;

    public HttpFunctionProxy(AiFunctionDO aiFunction, String host) {
        super(aiFunction);
        AiFunctionDO function = getAiFunction();
        this.host = host;
        BlockingDeque<Runnable> blockingDeque = new LinkedBlockingDeque<>();
        executorService = new ThreadPoolExecutor(
                2,
                4,
                10,
                TimeUnit.MINUTES,
                blockingDeque,
                new NameThreadFactory("AiRequest-pool",
                        (t, e) -> logger.error(t.getName() + "runtime error, ", e.getMessage())),
                (r, e) -> {
                    throw new TooFrequentException("AiFunction:" + function.getAiKey() + ":" + function.getFunctionId() + "'s Request exceeds capacity");
                }
        );
        resetLimiter(aiFunction.getFlowRate(), aiFunction.getFlowIntervalSecond());
        counter = new ConcurrentCounter(new ReentrantLock(), 5 * 60 * 1000L);
    }

    public void resetLimiter(int flowRate, int intervalSecond) {
        synchronized (this) {
            ConcurrentLimiter newLimiter = new ConcurrentLimiter(
                    new ReentrantLock(),
                    flowRate,
                    intervalSecond * 1000L
            );
            this.limiter = newLimiter;
        }
    }

    @Override
    public long lastExecTime() {
        return counter.getLast();
    }

    @Override
    public int total() {
        return counter.getCount();
    }

    @Override
    public AiResult doRequest(String arguments, ResponseAssamble responseAssamble, HttpServletResponse response, AiRequestManger.RequestCallBack callback) {

        if (!limiter.countOne()) {
            throw new TooFrequentException();
        }
        counter.count();
        String url = host + getAiFunction().getPath();
        if (getAiFunction().getFunctionResType() == 1) {
            byte[] data = doStreamHttpRequest(url, arguments, response);
            AiResult result = new AiResult().setStatusCode(AiRequestStatus.Done.getCode())
                    .setResult("Download success");
            callback.requestCallBack(result);
            return result.setData(data);
        }
        Future<AiResult> future = executorService.submit(
                () -> {
                    AiResult result;
                    RestResponse restResponse = doHttpRequest(url, arguments, responseAssamble);
                    logger.info(restResponse.toString());
                    if (restResponse.success()) {
                        result = new AiResult().setStatusCode(AiRequestStatus.Done.getCode())
                                .setResult(JsonSerializer.serialize(restResponse.getData()));
                    } else {
                        result = new AiResult().setStatusCode(AiRequestStatus.Error.getCode())
                                .setResult(restResponse.getMsg());
                    }
                    if (getAiFunction().getFunctionBatch() == 0) {
                        Thread.sleep(5000L);
                    }
                    callback.requestCallBack(result);
                    return result;
                }
        );
        if (getAiFunction().getFunctionBatch() == 0) {
            return new AiResult().setStatusCode(AiRequestStatus.Doing.getCode());
        }

        try {
            return future.get(120, TimeUnit.SECONDS);
        } catch (Exception e) {
            logger.error("Do AI http reqesut error", e);
            throw new AiServiceError("Do AI http reqesut error", e);
        }
    }

    private byte[] doStreamHttpRequest(String url, String arguments, HttpServletResponse response) {
        OkHttpClient client = new OkHttpClient().newBuilder()
                .build();
        okhttp3.MediaType mediaType = okhttp3.MediaType.parse("text/plain");
        RequestBody body = RequestBody.create(mediaType, arguments);
        Request request = new Request.Builder()
                .url(url)
                .method("POST", body)
                .addHeader("Content-Type", "text/plain")
                .build();
        try {
            Response res = client.newCall(request).execute();
            return res.body().bytes();
        } catch (IOException e) {
            logger.error("Ai request error with url:" + JsonSerializer.serialize(url), e);
            throw new AiServiceError("Do AI http reqesut error", e);
        }
    }

    private RestResponse doHttpRequest(String url, String arguments, ResponseAssamble responseAssamble) {
        String httpMethod = getAiFunction().getFunctionMethod();
        if (httpMethod.equals("POST")) {
            return doPostHttpRequest(url, arguments, responseAssamble);
        } else if (httpMethod.equals("GET")) {
            return doGetHttpRequest(url, arguments, responseAssamble);
        }

        throw new UnsupportedOperationException("Unsupport http method:" + httpMethod);
    }

    public RestResponse doPostHttpRequest(String url, String requestBody, ResponseAssamble responseAssamble) {
        OkHttpClient client = new OkHttpClient().newBuilder()
                .build();
        MediaType mediaType = MediaType.parse("application/json");
        RequestBody body = RequestBody.create(mediaType, requestBody);
        Request request = new Request.Builder()
                .url(url)
                .method("POST", body)
                .addHeader("Content-Type", "application/json")
                .build();
        try {
            Response response = client.newCall(request).execute();
            String responseStr = response.body().string();
            return responseAssamble.assembleResponse(responseStr);
        } catch (IOException e) {
            logger.error("Ai request error with url:" + JsonSerializer.serialize(url), e);
            return new RestResponse().setMsg("Ai request error" + e.getMessage()).setCode(ErrorCode.INTERNAL_ERROR.getCode());
        }
    }

    private RestResponse doGetHttpRequest(String url, String arguments, ResponseAssamble responseAssamble) {
        OkHttpClient client = new OkHttpClient().newBuilder()
                .build();
        Map<String, String> params = JsonSerializer.deserialize(arguments, new TypeToken<Map<String, String>>() {
        });
        String paramStr = SignUtil.mapToStr(params);
        Request request = new Request.Builder()
                .get()
                .url(url + "?" + paramStr)
                .addHeader("Content-Type", "text/plain")
                .build();
        try {
            Response response = client.newCall(request).execute();
            String bodyStr = response.body().string();
            return responseAssamble.assembleResponse(bodyStr);
        } catch (IOException e) {
            logger.error("Ai request error with url:" + JsonSerializer.serialize(url), e);
            return new RestResponse().setMsg("Ai request error" + e.getMessage()).setCode(ErrorCode.INTERNAL_ERROR.getCode());
        }
    }

}
