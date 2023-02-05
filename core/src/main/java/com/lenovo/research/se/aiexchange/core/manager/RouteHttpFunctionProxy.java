package com.lenovo.research.se.aiexchange.core.manager;

import com.lenovo.research.se.aiexchange.core.entity.AiFunctionDO;
import com.lenovo.research.se.aiexchange.model.AiResult;

import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.function.Function;
import java.util.stream.Collectors;

public class RouteHttpFunctionProxy implements AiFunctionProxy{

    private final Map<String, AbstractAiFunctionProxy> routeMap;
    private final AiFunctionDO aiFunction;
    private final List<String> hosts;

    public RouteHttpFunctionProxy(AiFunctionDO aiFunction, List<String> hosts) {
        this.aiFunction = aiFunction;
        this.hosts = hosts;
        routeMap = hosts.stream()
                .collect(
                        Collectors.toMap(
                                Function.identity(),
                                host -> new HttpFunctionProxy(aiFunction, host)
                        )
                );
    }

    @Override
    public AiResult doRequest(String arguments, HttpServletResponse response, AiRequestManger.RequestCallBack callback) {
        AiFunctionProxy aiFunctionProxy = route();
        return aiFunctionProxy.doRequest(arguments, response, callback);
    }

    @Override
    public AiFunctionDO getAiFunction() {
        return this.aiFunction;
    }

    private AiFunctionProxy route() {

        Random ra = new Random();
        int index = ra.nextInt(routeMap.size());
        return routeMap.get(hosts.get(index));
    }

    public void resetLimiter(AiFunctionDO aiFunction) {
        routeMap.values().forEach(
                proxy -> proxy.resetLimiter(aiFunction.getFlowRate(), aiFunction.getFlowIntervalSecond())
        );
    }
}
