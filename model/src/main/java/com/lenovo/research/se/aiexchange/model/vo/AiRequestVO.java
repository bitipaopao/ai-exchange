package com.lenovo.research.se.aiexchange.model.vo;

import java.util.Date;

public class AiRequestVO {

    private String uuid;
    private String aiKey;
    private String functionId;
    private Object arguments;
    private String argumentMd5;
    private Integer status;
    private String response;
    private Date requestTime;
    private Date responseTime;

    public String getUuid() {
        return uuid;
    }

    public AiRequestVO setUuid(String uuid) {
        this.uuid = uuid;
        return this;
    }

    public String getAiKey() {
        return aiKey;
    }

    public AiRequestVO setAiKey(String aiKey) {
        this.aiKey = aiKey;
        return this;
    }

    public String getFunctionId() {
        return functionId;
    }

    public AiRequestVO setFunctionId(String functionId) {
        this.functionId = functionId;
        return this;
    }

    public Object getArguments() {
        return arguments;
    }

    public AiRequestVO setArguments(Object arguments) {
        this.arguments = arguments;
        return this;
    }

    public String getArgumentMd5() {
        return argumentMd5;
    }

    public AiRequestVO setArgumentMd5(String argumentMd5) {
        this.argumentMd5 = argumentMd5;
        return this;
    }


    public Integer getStatus() {
        return status;
    }

    public AiRequestVO setStatus(Integer status) {
        this.status = status;
        return this;
    }

    public String getResponse() {
        return response;
    }

    public AiRequestVO setResponse(String response) {
        this.response = response;
        return this;
    }

    public Date getRequestTime() {
        return requestTime;
    }

    public AiRequestVO setRequestTime(Date requestTime) {
        this.requestTime = requestTime;
        return this;
    }

    public Date getResponseTime() {
        return responseTime;
    }

    public AiRequestVO setResponseTime(Date responseTime) {
        this.responseTime = responseTime;
        return this;
    }
}
