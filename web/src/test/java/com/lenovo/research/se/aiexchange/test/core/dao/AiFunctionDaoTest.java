package com.lenovo.research.se.aiexchange.test.core.dao;

import com.lenovo.research.se.aiexchange.core.dao.AiFunctionDao;
import com.lenovo.research.se.aiexchange.core.entity.AiFunctionDO;
import com.lenovo.research.se.aiexchange.model.enums.AiFunctionBatch;
import com.lenovo.research.se.aiexchange.model.enums.AiFunctionResType;
import com.lenovo.research.se.aiexchange.model.enums.Valid;
import com.lenovo.research.se.aiexchange.test.AiExchangeApplicationTest;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = AiExchangeApplicationTest.class)
public class AiFunctionDaoTest {
    @Autowired
    private AiFunctionDao functionDao;

    private final AiFunctionDO toTest = new AiFunctionDO()
            .setAiKey("testAiKey")
            .setFunctionId("testFunctionId")
            .setFunctionBatch(AiFunctionBatch.Serial.getCode())
            .setFunctionResType(AiFunctionResType.Synchronous.getCode())
            .setFlowIntervalSecond(2)
            .setFlowRate(5)
            .setValid(Valid.ACTIVATE.getId())
            .setPath("/testAi/testFunction");

    @Before
    public void before() {
        functionDao.insertOnDup(toTest);
    }

    @After
    public void after() {
        functionDao.delete(toTest.getAiKey(), toTest.getFunctionId());
    }

    @Test
    public void selectTest() {
        AiFunctionDO function = functionDao.getAiFunctionInfo(toTest.getAiKey(), toTest.getFunctionId());
        Assert.assertNotNull(function);
        Assert.assertEquals(function.getFlowIntervalSecond(), toTest.getFlowIntervalSecond());
        Assert.assertEquals(function.getFlowRate(), toTest.getFlowRate());
        Assert.assertEquals(function.getFunctionBatch(), toTest.getFunctionBatch());
        Assert.assertEquals(function.getFunctionResType(), toTest.getFunctionResType());
        Assert.assertEquals(function.getValid(), toTest.getValid());
    }

    @Test
    public void updateTest() {
        AiFunctionDO function = functionDao.getAiFunctionInfo(toTest.getAiKey(), toTest.getFunctionId());
        Assert.assertNotNull(function);
        Assert.assertEquals(function.getFlowIntervalSecond(), toTest.getFlowIntervalSecond());
        Assert.assertEquals(function.getFlowRate(), toTest.getFlowRate());
        Assert.assertEquals(function.getFunctionBatch(), toTest.getFunctionBatch());
        Assert.assertEquals(function.getFunctionResType(), toTest.getFunctionResType());
        Assert.assertEquals(function.getPath(), toTest.getPath());
        Assert.assertEquals(function.getValid(), toTest.getValid());

        AiFunctionDO newFunction = new AiFunctionDO()
                .setAiKey(toTest.getAiKey())
                .setFunctionId(toTest.getFunctionId())
                .setPath("test2")
                .setFlowIntervalSecond(1)
                .setFlowRate(120);

        functionDao.updateSelective(newFunction);
        function = functionDao.getAiFunctionInfo(toTest.getAiKey(), toTest.getFunctionId());
        Assert.assertNotEquals(function.getFlowIntervalSecond(), toTest.getFlowIntervalSecond());
        Assert.assertNotEquals(function.getFlowRate(), toTest.getFlowRate());
        Assert.assertNotEquals(function.getPath(), toTest.getPath());

        Assert.assertEquals(function.getFlowIntervalSecond(), newFunction.getFlowIntervalSecond());
        Assert.assertEquals(function.getFlowRate(), newFunction.getFlowRate());
        Assert.assertEquals(function.getPath(), newFunction.getPath());
    }
}
