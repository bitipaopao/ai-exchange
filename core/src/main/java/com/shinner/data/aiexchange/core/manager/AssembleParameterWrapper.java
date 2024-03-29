package com.shinner.data.aiexchange.core.manager;

import com.google.gson.reflect.TypeToken;
import com.shinner.data.aiexchange.common.spring.SpringBootUtil;
import com.shinner.data.aiexchange.common.utils.JsonSerializer;
import com.shinner.data.aiexchange.core.entity.AiFunctionDO;
import com.shinner.data.aiexchange.core.entity.AiServiceDO;
import com.shinner.data.aiexchange.core.source.SourceTokenWrapper;
import com.shinner.data.aiexchange.model.AiResult;
import org.apache.commons.lang3.StringUtils;

import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class AssembleParameterWrapper implements AiFunctionProxy {

    private final AiFunctionDO aiFunction;
    private final AiServiceDO service;
    private final RouteHttpFunctionProxy proxy;

    AssembleParameterWrapper(List<AiServiceDO> services, AiFunctionDO aiFunction) {
        this.aiFunction = aiFunction;
        this.service = services.get(0);
        List<String> hostUrls = services.stream()
                .map(aiService -> aiService.getProtocol() + aiService.getHost() + ":" + aiService.getPort())
                .collect(Collectors.toList());
        this.proxy = new RouteHttpFunctionProxy(aiFunction, hostUrls);
    }


    @Override
    public AiResult doRequest(String arguments, ResponseAssamble responseAssamble, HttpServletResponse response, AiRequestManger.RequestCallBack callback) {
        SourceTokenWrapper tokenWrapper = (SourceTokenWrapper) SpringBootUtil.getBean(service.getVendor() + SourceTokenWrapper.suffix);
        if (!StringUtils.isEmpty(aiFunction.getFunctionInfo())) {
            Map<String, Object> params = JsonSerializer.deserialize(
                    arguments, new TypeToken<Map<String, Object>>() {
                    });
            Map<String, String> preParam = JsonSerializer.deserialize(
                    aiFunction.getFunctionInfo(), new TypeToken<Map<String, String>>() {
                    });
            params.putAll(preParam);
            params = tokenWrapper.assembleRequestToken(params);
            arguments = JsonSerializer.serialize(params);
        }
        arguments = tokenWrapper.assembleRequestBody(arguments);
        return proxy.doRequest(arguments, tokenWrapper::assembleResult, response, callback);
    }

    @Override
    public AiFunctionDO getAiFunction() {
        return this.aiFunction;
    }

    public void resetLimiter(AiFunctionDO aiFunction) {
        proxy.resetLimiter(aiFunction);
    }
}
