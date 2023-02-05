package com.lenovo.research.se.aiexchange.core.manager;

import com.lenovo.research.se.aiexchange.core.entity.AiFunctionDO;
import com.lenovo.research.se.aiexchange.model.AiResult;

import javax.servlet.http.HttpServletResponse;

public interface AiFunctionProxy {

    AiResult doRequest(String arguments,  HttpServletResponse response, AiRequestManger.RequestCallBack callback);

    AiFunctionDO getAiFunction();
}
