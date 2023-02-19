package com.shinner.data.aiexchange.core.manager;

import com.shinner.data.aiexchange.common.spring.OneTimeExecutionApplicationEventListener;
import com.shinner.data.aiexchange.common.utils.JsonSerializer;
import com.shinner.data.aiexchange.core.dao.AiFunctionDao;
import com.shinner.data.aiexchange.core.dao.AiServiceDao;
import com.shinner.data.aiexchange.core.entity.AiFunctionDO;
import com.shinner.data.aiexchange.core.entity.AiServiceDO;
import com.shinner.data.aiexchange.model.AiResult;
import com.shinner.data.aiexchange.model.exception.NoAvailableAiServiceException;
import com.shinner.data.aiexchange.model.vo.AiRequestVO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.ApplicationContextEvent;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.function.Function;
import java.util.stream.Collectors;

@Component
public class AiServiceProxy extends OneTimeExecutionApplicationEventListener {
    private final Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired(required = false)
    private AiFunctionDao aiFunctionDao;
    @Autowired(required = false)
    private AiServiceDao aiServiceDao;

    private Map<String, Map<String, AssembleParameterWrapper>> functionMap;

    @PostConstruct
    public void init() {
        List<AiServiceDO> services = aiServiceDao.all();
        if (CollectionUtils.isEmpty(services)) {
            return;
        }

        Map<String, List<AiServiceDO>> servicesMap = services.stream()
                .collect(Collectors.groupingBy(AiServiceDO::getAiKey));

        functionMap = servicesMap.keySet().stream()
                .filter(
                        aiKey -> {
                            List<AiServiceDO> hosts = servicesMap.get(aiKey);
                            return !CollectionUtils.isEmpty(hosts);
                        }
                )
                .filter(
                        aiKey -> {
                            List<AiFunctionDO> functions = aiFunctionDao.queryAiFunctionByAiKey(aiKey);
                            return !CollectionUtils.isEmpty(functions);
                        }
                )
                .collect(
                        Collectors.toMap(
                                Function.identity(),
                                aiKey -> {
                                    List<AiFunctionDO> functions = aiFunctionDao.queryAiFunctionByAiKey(aiKey);
                                    List<AiServiceDO> hosts = servicesMap.get(aiKey);

                                    return functions.stream()
                                            .collect(
                                                    Collectors.toMap(
                                                            AiFunctionDO::getFunctionId,
                                                            function -> new AssembleParameterWrapper(hosts,function)
                                                    )
                                            );
                                }
                        )
                );
    }


    public AiResult doRequest(AiRequestVO aiRequest, HttpServletResponse response, AiRequestManger.RequestCallBack callback) {
        if (functionMap.containsKey(aiRequest.getAiKey())) {
            AiFunctionProxy functionProxy = functionMap.get(aiRequest.getAiKey()).get(aiRequest.getFunctionId());
            if (Objects.nonNull(functionProxy)) {
                String arguments = aiRequest.getArguments().toString();
                return functionProxy.doRequest(arguments, response, callback);
            }
        }

        throw new NoAvailableAiServiceException("No function for aiRequest:"
                + JsonSerializer.serialize(aiRequest, "null"));
    }

    @Override
    protected void onApplicationContextEvent(ApplicationContextEvent event) {
        if (event instanceof ContextRefreshedEvent) {
            ScheduledExecutorService scheduledExecutorService = Executors
                    .newSingleThreadScheduledExecutor();
            scheduledExecutorService.scheduleAtFixedRate(() -> {
                try {
                    resetLimiter();
                } catch (Exception e) {
                    logger.error("Reset function limiter error.", e);
                }
            },5, 5, TimeUnit.MINUTES);
        }
    }

    private void resetLimiter() {
        List<AiServiceDO> services = aiServiceDao.all();
        services.forEach(
                service -> {
                    List<AiFunctionDO> functions = aiFunctionDao.queryAiFunctionByAiKey(service.getAiKey());
                    functions.forEach(
                            function -> {
                                AssembleParameterWrapper proxy = functionMap.get(function.getAiKey()).get(function.getFunctionId());
                                proxy.resetLimiter(function);
                            }
                    );
                }
        );
    }
}
