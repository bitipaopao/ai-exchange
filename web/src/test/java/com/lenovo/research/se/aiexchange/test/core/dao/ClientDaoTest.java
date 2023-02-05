package com.lenovo.research.se.aiexchange.test.core.dao;

import com.lenovo.research.se.aiexchange.common.utils.DatetimeUtils;
import com.lenovo.research.se.aiexchange.core.dao.ClientDao;
import com.lenovo.research.se.aiexchange.core.entity.ClientDO;
import com.lenovo.research.se.aiexchange.test.AiExchangeApplicationTest;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Objects;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = AiExchangeApplicationTest.class)
public class ClientDaoTest {

    @Autowired
    private ClientDao clientDao;

    private final ClientDO toTest = new ClientDO().setClientAccessKey("testAccessKey")
            .setAccessSecret("testAccessSecret")
            .setClientId("testClient01")
            .setEnv("dev")
            .setAppId("bjmx")
            .setProjectId(2022052001)
            .setExpireTime(DatetimeUtils.getLastDayDateOfThisMonth());

    @Before
    public void init() {
        clientDao.insertOnDup(toTest);
    }

    @After
    public void destroy() {
        clientDao.delete(toTest);
    }

    @Test
    public void selectTest() {
        ClientDO result = clientDao.selectByKey("testClient01", "dev");
        Assert.assertTrue(Objects.nonNull(result));
        Assert.assertEquals(result.getClientId(), toTest.getClientId());
        Assert.assertEquals(result.getEnv(), toTest.getEnv());
        Assert.assertEquals(result.getClientAccessKey(), toTest.getClientAccessKey());
        Assert.assertEquals(result.getAccessSecret(), toTest.getAccessSecret());

    }

    @Test
    public void updateTest() {
        ClientDO toUpdate = new ClientDO().setClientAccessKey("testAccessKey01")
                .setAccessSecret("testAccessSecret01")
                .setClientId("testClient01")
                .setEnv("dev")
                .setAppId("testClient")
                .setProjectId(2022052001)
                .setExpireTime(DatetimeUtils.getLastDayDateOfThisMonth());
        clientDao.updateSelective(toUpdate);
        ClientDO result = clientDao.selectByKey("testClient01", "dev");
        Assert.assertEquals(result.getAccessSecret(), toUpdate.getAccessSecret());
        Assert.assertNotEquals(result.getAccessSecret(), toTest.getAccessSecret());


        clientDao.updateSelective(toTest);
        result = clientDao.selectByKey("testClient01", "dev");
        Assert.assertEquals(result.getAccessSecret(), toTest.getAccessSecret());
        Assert.assertNotEquals(result.getAccessSecret(), toUpdate.getAccessSecret());
    }
}
