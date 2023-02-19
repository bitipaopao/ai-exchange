package com.shinner.data.aiexchange.core.entity;

import java.util.Date;

public class AiComposeDO {
    private String key;
    private String desc;
    private Integer resType;
    private Integer batch;
    private Integer flowIntervalSecond;
    private Integer flowRate;
    private String composeInfo;
    private Integer valid;
    private Date createTime;
    private Date modifiedTime;

    public String getKey() {
        return key;
    }

    public AiComposeDO setKey(String key) {
        this.key = key;
        return this;
    }

    public String getDesc() {
        return desc;
    }

    public AiComposeDO setDesc(String desc) {
        this.desc = desc;
        return this;
    }

    public Integer getResType() {
        return resType;
    }

    public AiComposeDO setResType(Integer resType) {
        this.resType = resType;
        return this;
    }

    public Integer getBatch() {
        return batch;
    }

    public AiComposeDO setBatch(Integer batch) {
        this.batch = batch;
        return this;
    }

    public Integer getFlowIntervalSecond() {
        return flowIntervalSecond;
    }

    public AiComposeDO setFlowIntervalSecond(Integer flowIntervalSecond) {
        this.flowIntervalSecond = flowIntervalSecond;
        return this;
    }

    public Integer getFlowRate() {
        return flowRate;
    }

    public AiComposeDO setFlowRate(Integer flowRate) {
        this.flowRate = flowRate;
        return this;
    }

    public String getComposeInfo() {
        return composeInfo;
    }

    public AiComposeDO setComposeInfo(String composeInfo) {
        this.composeInfo = composeInfo;
        return this;
    }

    public Integer getValid() {
        return valid;
    }

    public AiComposeDO setValid(Integer valid) {
        this.valid = valid;
        return this;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public AiComposeDO setCreateTime(Date createTime) {
        this.createTime = createTime;
        return this;
    }

    public Date getModifiedTime() {
        return modifiedTime;
    }

    public AiComposeDO setModifiedTime(Date modifiedTime) {
        this.modifiedTime = modifiedTime;
        return this;
    }
}
