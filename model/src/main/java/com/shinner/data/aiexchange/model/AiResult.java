package com.shinner.data.aiexchange.model;

public class AiResult {

    private String uuid;
    private Integer statusCode;
    private String result;
    private Object data;

    public String getUuid() {
        return uuid;
    }

    public AiResult setUuid(String uuid) {
        this.uuid = uuid;
        return this;
    }

    public Integer getStatusCode() {
        return statusCode;
    }

    public AiResult setStatusCode(Integer statusCode) {
        this.statusCode = statusCode;
        return this;
    }

    public String getResult() {
        return result;
    }

    public AiResult setResult(String result) {
        this.result = result;
        return this;
    }

    public Object getData() {
        return data;
    }

    public AiResult setData(Object data) {
        this.data = data;
        return this;
    }
}
