package com.lenovo.research.se.aiexchange.core.entity;

import java.util.Date;

public class AiServiceDO {
    private String aiKey;
    private String vendor;
    private String protocol;
    private String port;
    private String host;
    private String callbackHost;
    private String echoUrl;
    private Date createTime;
    private Date modifiedTime;

    public String getAiKey() {
        return aiKey;
    }

    public AiServiceDO setAiKey(String aiKey) {
        this.aiKey = aiKey;
        return this;
    }

    public String getVendor() {
        return vendor;
    }

    public AiServiceDO setVendor(String vendor) {
        this.vendor = vendor;
        return this;
    }

    public String getProtocol() {
        return protocol;
    }

    public AiServiceDO setProtocol(String protocol) {
        this.protocol = protocol;
        return this;
    }

    public String getPort() {
        return port;
    }

    public AiServiceDO setPort(String port) {
        this.port = port;
        return this;
    }

    public String getHost() {
        return host;
    }

    public AiServiceDO setHost(String host) {
        this.host = host;
        return this;
    }

    public String getCallbackHost() {
        return callbackHost;
    }

    public AiServiceDO setCallbackHost(String callbackHost) {
        this.callbackHost = callbackHost;
        return this;
    }

    public String getEchoUrl() {
        return echoUrl;
    }

    public AiServiceDO setEchoUrl(String echoUrl) {
        this.echoUrl = echoUrl;
        return this;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public AiServiceDO setCreateTime(Date createTime) {
        this.createTime = createTime;
        return this;
    }

    public Date getModifiedTime() {
        return modifiedTime;
    }

    public AiServiceDO setModifiedTime(Date modifiedTime) {
        this.modifiedTime = modifiedTime;
        return this;
    }
}
