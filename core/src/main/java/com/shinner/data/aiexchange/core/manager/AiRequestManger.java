package com.shinner.data.aiexchange.core.manager;

import com.shinner.data.aiexchange.common.utils.JsonSerializer;
import com.shinner.data.aiexchange.common.utils.MD5Util;
import com.shinner.data.aiexchange.common.utils.StringUtil;
import com.shinner.data.aiexchange.core.dao.AiRequestDao;
import com.shinner.data.aiexchange.core.entity.AiRequestDO;
import com.shinner.data.aiexchange.model.AiResult;
import com.shinner.data.aiexchange.model.enums.AiRequestStatus;
import com.shinner.data.aiexchange.model.exception.AiArgumentCheckException;
import com.shinner.data.aiexchange.model.vo.AiRequestVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletResponse;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.Map;
import java.util.Objects;

@Component
public class AiRequestManger {

    @Autowired(required = false)
    private AiRequestDao aiRequestDao;
    @Autowired(required = false)
    private AiServiceProxy aiServiceProxy;

    public AiRequestVO getAiRequest(String uuid) {
        AiRequestDO requestDO = aiRequestDao.selectByUuid(uuid);

        if (Objects.isNull(requestDO)) {
            throw new RuntimeException("No request for uuid:" + uuid);
        }

        return new AiRequestVO()
                .setUuid(requestDO.getUuid())
                .setAiKey(requestDO.getAiKey())
                .setArgumentMd5(requestDO.getArgumentMd5())
                .setArguments(JsonSerializer.deserialize(requestDO.getArgument(), Map.class))
                .setFunctionId(requestDO.getFunctionId())
                .setResponse(requestDO.getResponse())
                .setRequestTime(requestDO.getCreateTime())
                .setResponseTime(requestDO.getDoneTime())
                .setStatus(requestDO.getRequestStatus());
    }

    public AiResult doAiRequest(AiRequestVO aiRequest, HttpServletResponse response) {
        checkArguments(aiRequest);
        String uuid = saveRequest(aiRequest);
        AiResult aiResult = aiServiceProxy.doRequest(
                aiRequest,
                response,
                (re) -> {
                    re.setUuid(uuid);
                    doneRequest(re);
                });
        aiResult.setUuid(uuid);
        updateRequestStatus(aiResult);
        return aiResult;
    }

    private void checkArguments(AiRequestVO aiRequest) {
        String toMd5 = JsonSerializer.serialize(aiRequest.getArguments(), "");
        String md5 = aiRequest.getArgumentMd5();
        String toCheck = MD5Util.md5(toMd5, StandardCharsets.UTF_8);
        if (!md5.equals(toCheck)) {
            throw new AiArgumentCheckException("Argument MD5 Check Un Pass");
        }
    }

    private String saveRequest(AiRequestVO aiRequest) {
        String uuid = StringUtil.getUuid();
        aiRequest.setUuid(uuid);
        AiRequestDO aiRequestDO = new AiRequestDO()
                .setUuid(aiRequest.getUuid())
                .setAiKey(aiRequest.getAiKey())
                .setArgumentMd5(aiRequest.getArgumentMd5())
                .setArgument(JsonSerializer.serialize(aiRequest.getArguments()))
                .setFunctionId(aiRequest.getFunctionId())
                .setResponse(aiRequest.getResponse())
                .setRequestStatus(AiRequestStatus.Undo.getCode());
        aiRequestDao.insert(aiRequestDO);
        return uuid;
    }

    private void doneRequest(AiResult aiResult) {
        AiRequestDO aiRequestDO = new AiRequestDO()
                .setUuid(aiResult.getUuid())
                .setResponse(aiResult.getResult())
                .setDoneTime(new Date())
                .setRequestStatus(aiResult.getStatusCode());
        aiRequestDao.updateLimitByStatus(aiRequestDO);
    }

    private void updateRequestStatus(AiResult aiResult) {
        if (aiResult.getStatusCode() == AiRequestStatus.Done.getCode()
                || aiResult.getStatusCode() == AiRequestStatus.Error.getCode()) {
            return;
        }
        AiRequestDO aiRequestDO = new AiRequestDO()
                .setUuid(aiResult.getUuid())
                .setResponse(aiResult.getResult())
                .setRequestStatus(aiResult.getStatusCode());
        aiRequestDao.updateLimitByStatus(aiRequestDO);
    }

    public interface RequestCallBack {
        void requestCallBack(AiResult aiResult);
    }
}
