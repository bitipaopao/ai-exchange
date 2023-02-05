package com.lenovo.research.se.aiexchange.core.manager;

import com.lenovo.research.se.aiexchange.common.thread.NameThreadFactory;
import com.lenovo.research.se.aiexchange.common.utils.JsonSerializer;
import com.lenovo.research.se.aiexchange.core.dao.AiRequestDao;
import com.lenovo.research.se.aiexchange.core.entity.AiRequestDO;
import com.lenovo.research.se.aiexchange.model.AiResult;
import com.lenovo.research.se.aiexchange.model.RestResponse;
import com.lenovo.research.se.aiexchange.model.enums.ErrorCode;
import org.apache.logging.log4j.util.Strings;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import javax.annotation.PostConstruct;
import java.util.Objects;
import java.util.concurrent.*;

@Component
public class AiRequestCallback {
    private final Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired(required = false)
    private RestTemplate restTemplate;
    @Autowired(required = false)
    private AiRequestDao aiRequestDao;

    private final BlockingDeque<Runnable> blockingDeque = new LinkedBlockingDeque<>(Integer.MAX_VALUE);
    private ExecutorService executorService;

    @PostConstruct
    public void init() {
        executorService = new ThreadPoolExecutor(
                2,
                4,
                10,
                TimeUnit.MINUTES,
                blockingDeque,
                new NameThreadFactory("AiRequest-callback-pool",
                        (t, e) -> logger.error(t.getName() + "runtime error, ", e.getMessage())));
    }

    private RestResponse doRestfulCallback(String url, AiResult aiResult) {
        try {
            HttpHeaders requestHeaders = new HttpHeaders();
            requestHeaders.setContentType(MediaType.APPLICATION_JSON);
            HttpEntity<AiResult> requestEntity = new HttpEntity<>(aiResult, requestHeaders);
            ResponseEntity<RestResponse> responseEntity = restTemplate.exchange(url, HttpMethod.POST, requestEntity, RestResponse.class);
            if (responseEntity.hasBody()) {
                RestResponse restResponse = responseEntity.getBody();
                if (Objects.nonNull(restResponse)) {
                    return restResponse;
                }
            }
            return new RestResponse().setCode(ErrorCode.NotFound.getCode()).setMsg("Callback has no response body");
        } catch (Exception e) {
            logger.error("Callback error with result:" + JsonSerializer.serialize(aiResult), e);
            return new RestResponse().setCode(ErrorCode.INTERNAL_ERROR.getCode()).setMsg(e.getMessage());
        }
    }

    @FunctionalInterface
    public interface CallBackDone {
        void onCallback(String message);
    }
}
