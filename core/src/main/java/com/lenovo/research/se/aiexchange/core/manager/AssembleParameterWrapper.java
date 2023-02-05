package com.lenovo.research.se.aiexchange.core.manager;

import com.google.gson.reflect.TypeToken;
import com.lenovo.research.se.aiexchange.common.spring.SpringBootUtil;
import com.lenovo.research.se.aiexchange.common.utils.JsonSerializer;
import com.lenovo.research.se.aiexchange.core.entity.AiFunctionDO;
import com.lenovo.research.se.aiexchange.core.entity.AiServiceDO;
import com.lenovo.research.se.aiexchange.core.source.SourceTokenWrapper;
import com.lenovo.research.se.aiexchange.model.AiResult;
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
    public AiResult doRequest(String arguments, HttpServletResponse response, AiRequestManger.RequestCallBack callback) {
        Map<String, String> params = JsonSerializer.deserialize(
                arguments, new TypeToken<Map<String, String>>() {
                });
        if (!StringUtils.isEmpty(aiFunction.getFunctionInfo())) {
            Map<String, String> preParam = JsonSerializer.deserialize(
                    aiFunction.getFunctionInfo(), new TypeToken<Map<String, String>>() {
                    });
            params.putAll(preParam);
        }

        SourceTokenWrapper tokenWrapper = (SourceTokenWrapper) SpringBootUtil.getBean(service.getVendor() + SourceTokenWrapper.suffix);
        params = tokenWrapper.assembleRequestToken(params);
        arguments = JsonSerializer.serialize(params);
        return proxy.doRequest(arguments, response, callback);
    }

    @Override
    public AiFunctionDO getAiFunction() {
        return this.aiFunction;
    }

    public void resetLimiter(AiFunctionDO aiFunction) {
        proxy.resetLimiter(aiFunction);
    }
}
