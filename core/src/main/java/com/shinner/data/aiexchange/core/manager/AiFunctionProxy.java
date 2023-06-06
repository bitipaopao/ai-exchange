package com.shinner.data.aiexchange.core.manager;

import com.shinner.data.aiexchange.core.entity.AiFunctionDO;
import com.shinner.data.aiexchange.model.AiResult;
import com.shinner.data.aiexchange.model.RestResponse;

import javax.servlet.http.HttpServletResponse;

public interface AiFunctionProxy {

    AiResult doRequest(String arguments,  ResponseAssamble responseAssamble, HttpServletResponse response, AiRequestManger.RequestCallBack callback);

    AiFunctionDO getAiFunction();

    @FunctionalInterface
    interface ResponseAssamble {
        RestResponse assembleResponse(String responseStr);
    }
}
