package com.lenovo.research.se.aiexchange.web.controller;

import com.lenovo.research.se.aiexchange.common.auth.ClientAuth;
import com.lenovo.research.se.aiexchange.common.utils.JsonSerializer;
import com.lenovo.research.se.aiexchange.core.manager.AiRequestManger;
import com.lenovo.research.se.aiexchange.model.AiResult;
import com.lenovo.research.se.aiexchange.model.RestResponse;
import com.lenovo.research.se.aiexchange.model.enums.AiRequestStatus;
import com.lenovo.research.se.aiexchange.model.enums.ErrorCode;
import com.lenovo.research.se.aiexchange.model.vo.AiRequestVO;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.Objects;

@ClientAuth
@RestController
@RequestMapping(value = "/api")
public class AiRequestController {
    private final static Logger logger = LoggerFactory.getLogger(AiRequestController.class);

    @Autowired(required = false)
    private AiRequestManger aiRequestManger;

    @RequestMapping(value = "/request/{aiKey}/{functionId}", method = RequestMethod.POST)
    @ResponseBody
    @ApiOperation("AI request post")
    public ResponseEntity<?> doAiCompute(
            HttpServletResponse response,
            @PathVariable(value = "aiKey") String aiKey,
            @PathVariable(value = "functionId") String functionId,
            @RequestBody String argument,
            @RequestParam(value = "argMd5") String argMd5) {
        AiRequestVO aiRequest = new AiRequestVO()
                .setAiKey(aiKey)
                .setFunctionId(functionId)
                .setArguments(argument)
                .setArgumentMd5(argMd5);
        AiResult result = aiRequestManger.doAiRequest(aiRequest, response);
        if (result.getStatusCode() == AiRequestStatus.Error.getCode()) {
            return ResponseEntity.ok().body(RestResponse.failWith(ErrorCode.INTERNAL_ERROR).setData(result.getResult()));
        }
        String resultStr = result.getResult();
        if (Objects.nonNull(result.getData()) && result.getData() instanceof byte[]) {
            byte[] data = (byte[]) result.getData();
            return ResponseEntity.ok()
            .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + "kegang_"+ System.currentTimeMillis() +".xls")
                    .contentType(MediaType.APPLICATION_OCTET_STREAM)
                    .contentLength(data.length)
                    .body(data);
        }

        return ResponseEntity.ok().body(RestResponse.successWith(JsonSerializer.deserialize(resultStr, Object.class)));
    }

    @RequestMapping(value = "/request", method = RequestMethod.GET)
    @ResponseBody
    @ApiOperation("AI request get")
    public RestResponse<AiResult> checkAiCompute(
            @RequestParam(value = "uuid") String uuid) {
        logger.info("uuid:{}", uuid);
        AiRequestVO result = aiRequestManger.getAiRequest(uuid);
        return RestResponse.successWith(
                new AiResult().setStatusCode(result.getStatus())
                        .setResult(result.getResponse())
                        .setUuid(result.getUuid())
        );
    }
}
