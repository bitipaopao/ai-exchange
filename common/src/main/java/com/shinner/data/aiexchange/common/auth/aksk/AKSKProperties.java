package com.shinner.data.aiexchange.common.auth.aksk;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "mw.auth.client.aksk")
public class AKSKProperties {

    private String akParamName;
    private String signParamName;
    private String slatParamName;
    private String timeStampName;
    private Long expireTime;
    private Boolean second;

    public String getAkParamName() {
        return akParamName;
    }

    public AKSKProperties setAkParamName(String akParamName) {
        this.akParamName = akParamName;
        return this;
    }

    public String getSignParamName() {
        return signParamName;
    }

    public AKSKProperties setSignParamName(String signParamName) {
        this.signParamName = signParamName;
        return this;
    }

    public String getSlatParamName() {
        return slatParamName;
    }

    public AKSKProperties setSlatParamName(String slatParamName) {
        this.slatParamName = slatParamName;
        return this;
    }

    public String getTimeStampName() {
        return timeStampName;
    }

    public AKSKProperties setTimeStampName(String timeStampName) {
        this.timeStampName = timeStampName;
        return this;
    }

    public Long getExpireTime() {
        return expireTime;
    }

    public AKSKProperties setExpireTime(Long expireTime) {
        this.expireTime = expireTime;
        return this;
    }

    public Boolean getSecond() {
        return second;
    }

    public AKSKProperties setSecond(Boolean second) {
        this.second = second;
        return this;
    }
}
