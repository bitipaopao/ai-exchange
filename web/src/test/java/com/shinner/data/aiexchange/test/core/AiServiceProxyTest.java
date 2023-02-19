package com.shinner.data.aiexchange.test.core;

import com.shinner.data.aiexchange.core.manager.AiServiceProxy;
import com.shinner.data.aiexchange.test.AiExchangeApplicationTest;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = AiExchangeApplicationTest.class)
public class AiServiceProxyTest {
    private final Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private AiServiceProxy aiServiceProxy;

    @Test
    public void doRequestTest() {
        String aiKey = "ai_test";
        String functionId = "ai_label";
        String arguments = "Hello";
//        AiResult aiResult = aiServiceProxy.doRequest(aiKey, functionId, arguments);
//        logger.info("result:{}", aiResult);
    }
}
