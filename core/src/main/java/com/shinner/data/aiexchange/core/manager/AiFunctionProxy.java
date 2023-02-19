package com.shinner.data.aiexchange.core.manager;

import com.shinner.data.aiexchange.core.entity.AiFunctionDO;
import com.shinner.data.aiexchange.model.AiResult;

import javax.servlet.http.HttpServletResponse;

public interface AiFunctionProxy {

    AiResult doRequest(String arguments,  HttpServletResponse response, AiRequestManger.RequestCallBack callback);

    AiFunctionDO getAiFunction();
}
