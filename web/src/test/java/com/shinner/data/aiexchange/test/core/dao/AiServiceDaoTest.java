package com.shinner.data.aiexchange.test.core.dao;

import com.shinner.data.aiexchange.core.dao.AiServiceDao;
import com.shinner.data.aiexchange.core.entity.AiServiceDO;
import com.shinner.data.aiexchange.test.AiExchangeApplicationTest;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = AiExchangeApplicationTest.class)
public class AiServiceDaoTest {

    @Autowired
    private AiServiceDao aiServiceDao;

    private final AiServiceDO toTest = new AiServiceDO()
            .setAiKey("test_ai_key")
            .setHost("127.0.0.1")
            .setPort("8080")
            .setProtocol("http://");

    @Before
    public void before() {
        aiServiceDao.insert(toTest);
    }

    @Test
    public void after() {
        aiServiceDao.delete("test_ai_key", "127.0.0.1");
    }

    @Test
    public void queryByAiKeyTest() {
        List<AiServiceDO> aiServices = aiServiceDao.queryByAiKey("test_ai_key");
        Assert.assertNotNull(aiServices);
        Assert.assertEquals(1, aiServices.size());
    }

    @Test
    public void queryAllTest() {
        List<AiServiceDO> aiServices = aiServiceDao.all();
        Assert.assertNotNull(aiServices);
    }
}
