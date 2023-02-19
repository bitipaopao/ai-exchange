package com.shinner.data.aiexchange.core.entity;

import java.util.Date;

public class AiRequestDO {

    private Long requestId;
    private String uuid;
    private String aiKey;
    private String functionId;
    private String argument;
    private String argumentMd5;
    private String response;
    private Date doneTime;
    private Integer requestStatus;
    private Date createTime;
    private Date modifiedTime;

    public Long getRequestId() {
        return requestId;
    }

    public AiRequestDO setRequestId(Long requestId) {
        this.requestId = requestId;
        return this;
    }

    public String getUuid() {
        return uuid;
    }

    public AiRequestDO setUuid(String uuid) {
        this.uuid = uuid;
        return this;
    }

    public String getAiKey() {
        return aiKey;
    }

    public AiRequestDO setAiKey(String aiKey) {
        this.aiKey = aiKey;
        return this;
    }

    public String getFunctionId() {
        return functionId;
    }

    public AiRequestDO setFunctionId(String functionId) {
        this.functionId = functionId;
        return this;
    }



    public String getArgument() {
        return argument;
    }

    public AiRequestDO setArgument(String argument) {
        this.argument = argument;
        return this;
    }

    public String getArgumentMd5() {
        return argumentMd5;
    }

    public AiRequestDO setArgumentMd5(String argumentMd5) {
        this.argumentMd5 = argumentMd5;
        return this;
    }

    public Integer getRequestStatus() {
        return requestStatus;
    }

    public String getResponse() {
        return response;
    }

    public AiRequestDO setResponse(String response) {
        this.response = response;
        return this;
    }

    public AiRequestDO setRequestStatus(Integer requestStatus) {
        this.requestStatus = requestStatus;
        return this;
    }

    public Date getDoneTime() {
        return doneTime;
    }

    public AiRequestDO setDoneTime(Date doneTime) {
        this.doneTime = doneTime;
        return this;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public AiRequestDO setCreateTime(Date createTime) {
        this.createTime = createTime;
        return this;
    }

    public Date getModifiedTime() {
        return modifiedTime;
    }

    public AiRequestDO setModifiedTime(Date modifiedTime) {
        this.modifiedTime = modifiedTime;
        return this;
    }
}
