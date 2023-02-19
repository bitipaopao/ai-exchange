package com.shinner.data.aiexchange.test.core.dao;

import com.shinner.data.aiexchange.core.dao.AiRequestDao;
import com.shinner.data.aiexchange.core.entity.AiRequestDO;
import com.shinner.data.aiexchange.model.enums.AiRequestStatus;
import com.shinner.data.aiexchange.test.AiExchangeApplicationTest;
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
public class AiRequestDaoTest {

    @Autowired
    private AiRequestDao aiRequestDao;

    private final AiRequestDO toTest = new AiRequestDO().setAiKey("testAiKey")
            .setArgument("testArgument")
            .setArgumentMd5("testArgumentMd5")
            .setFunctionId("testFunctionId")
            .setUuid("testUuid")
            .setRequestStatus(AiRequestStatus.Undo.getCode());

    @Before
    public void init() {
        aiRequestDao.insert(toTest);
    }

    @After
    public void destroy() {
        AiRequestDO request = aiRequestDao.selectByUuid(toTest.getUuid());
        if (Objects.nonNull(request)) {
            aiRequestDao.delete(request.getRequestId());
        }
    }

    @Test
    public void selectTest() {
        AiRequestDO request = aiRequestDao.selectByUuid(toTest.getUuid());
        Assert.assertNotNull(request);
        Assert.assertEquals(request.getAiKey(), toTest.getAiKey());
        Assert.assertEquals(request.getUuid(), toTest.getUuid());
        Assert.assertEquals(request.getArgument(), toTest.getArgument());
        Assert.assertEquals(request.getArgumentMd5(), toTest.getArgumentMd5());
        Assert.assertEquals(request.getFunctionId(), toTest.getFunctionId());
    }

    @Test
    public void updateTest() {
        AiRequestDO request = aiRequestDao.selectByUuid(toTest.getUuid());
        AiRequestDO toUpdate = new AiRequestDO().setRequestId(request.getRequestId())
                .setUuid(request.getUuid())
                .setResponse("testResponse")
                .setRequestStatus(2);
        Assert.assertNotNull(request);
        Assert.assertNotEquals(request.getRequestStatus(), toUpdate.getRequestStatus());
        Assert.assertNull(request.getResponse());

        aiRequestDao.updateLimitByStatus(toUpdate);
        request = aiRequestDao.selectByUuid(toTest.getUuid());
        Assert.assertEquals(request.getRequestStatus(), toUpdate.getRequestStatus());
        Assert.assertNotNull(request.getResponse());
    }
}
