package com.lenovo.research.se.aiexchange.common.thread;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.*;

@Configuration
public class CommonExecutorConfig {
    private final Logger logger = LoggerFactory.getLogger(getClass());
    @Value("${mw.inward.executor.core:20}")
    private Integer coreNumber;
    @Value("${mw.inward.executor.maxAlive:64}")
    private Integer maxAliveNumber;
    @Value("${mw.inward.executor.aliveMinutes:10}")
    private Long keepAliveMinutes;
        @Value("${mw.inward.executor.poolName:common-exec-pool}")
    private String poolName;

    private final BlockingDeque<Runnable> blockingDeque = new LinkedBlockingDeque<>(Integer.MAX_VALUE);

    @Bean(name = "commonExecutor")
    public ExecutorService executor() {
        return new ThreadPoolExecutor(
                coreNumber,
                maxAliveNumber,
                keepAliveMinutes,
                TimeUnit.MINUTES,
                blockingDeque,
                new NameThreadFactory(poolName,
                (t, e) -> logger.error(t.getName() + "runtime error, ", e.getMessage())));
    }
}
