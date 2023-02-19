package com.shinner.data.aiexchange.core.entity;

import java.util.Date;

public class ClientDO {

    private String clientId;
    private String env;
    private String clientAccessKey;
    private String accessSecret;
    private String appId;
    private Integer projectId;
    private Date expireTime;
    private Date createTime;
    private Date modifiedTime;

    public String getClientId() {
        return clientId;
    }

    public ClientDO setClientId(String clientId) {
        this.clientId = clientId;
        return this;
    }

    public String getEnv() {
        return env;
    }

    public ClientDO setEnv(String env) {
        this.env = env;
        return this;
    }

    public String getClientAccessKey() {
        return clientAccessKey;
    }

    public ClientDO setClientAccessKey(String clientAccessKey) {
        this.clientAccessKey = clientAccessKey;
        return this;
    }

    public String getAccessSecret() {
        return accessSecret;
    }

    public ClientDO setAccessSecret(String accessSecret) {
        this.accessSecret = accessSecret;
        return this;
    }

    public String getAppId() {
        return appId;
    }

    public ClientDO setAppId(String appId) {
        this.appId = appId;
        return this;
    }

    public Integer getProjectId() {
        return projectId;
    }

    public ClientDO setProjectId(Integer projectId) {
        this.projectId = projectId;
        return this;
    }

    public Date getExpireTime() {
        return expireTime;
    }

    public ClientDO setExpireTime(Date expireTime) {
        this.expireTime = expireTime;
        return this;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public ClientDO setCreateTime(Date createTime) {
        this.createTime = createTime;
        return this;
    }

    public Date getModifiedTime() {
        return modifiedTime;
    }

    public ClientDO setModifiedTime(Date modifiedTime) {
        this.modifiedTime = modifiedTime;
        return this;
    }
}
