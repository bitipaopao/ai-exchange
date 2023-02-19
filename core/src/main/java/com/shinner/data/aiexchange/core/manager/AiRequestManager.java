package com.shinner.data.aiexchange.core.manager;

import com.shinner.data.aiexchange.core.dao.AiRequestDao;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

@Component
public class AiRequestManager {
    private final static Logger logger = LoggerFactory.getLogger(AiRequestManager.class);

    @Autowired
    private AiRequestDao aiRequestDao;

    @PostConstruct
    public void init() {
        ScheduledExecutorService scheduledExecutorService = Executors
                .newSingleThreadScheduledExecutor();
        scheduledExecutorService.scheduleAtFixedRate(() -> {
            try {
                clearHistory();
            } catch (Exception e) {
                logger.error("Reset function limiter error.", e);
            }
        },1, 1, TimeUnit.DAYS);
    }

    public void clearHistory() {
        aiRequestDao.clearHistory();
    }
}
