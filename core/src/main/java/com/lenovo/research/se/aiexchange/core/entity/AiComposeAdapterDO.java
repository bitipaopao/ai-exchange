package com.lenovo.research.se.aiexchange.core.entity;

import java.util.Date;

public class AiComposeAdapterDO {
    private String composeKey;
    private Integer step;
    private String sources;
    private String serialType;
    private String fieldAdapters;
    private Integer valid;
    private Date createTime;
    private Date modifiedTime;

    public String getComposeKey() {
        return composeKey;
    }

    public AiComposeAdapterDO setComposeKey(String composeKey) {
        this.composeKey = composeKey;
        return this;
    }

    public Integer getStep() {
        return step;
    }

    public AiComposeAdapterDO setStep(Integer step) {
        this.step = step;
        return this;
    }

    public String getSerialType() {
        return serialType;
    }

    public AiComposeAdapterDO setSerialType(String serialType) {
        this.serialType = serialType;
        return this;
    }

    public String getFieldAdapters() {
        return fieldAdapters;
    }

    public AiComposeAdapterDO setFieldAdapters(String fieldAdapters) {
        this.fieldAdapters = fieldAdapters;
        return this;
    }

    public Integer getValid() {
        return valid;
    }

    public AiComposeAdapterDO setValid(Integer valid) {
        this.valid = valid;
        return this;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public AiComposeAdapterDO setCreateTime(Date createTime) {
        this.createTime = createTime;
        return this;
    }

    public Date getModifiedTime() {
        return modifiedTime;
    }

    public AiComposeAdapterDO setModifiedTime(Date modifiedTime) {
        this.modifiedTime = modifiedTime;
        return this;
    }

    public String getSources() {
        return sources;
    }

    public AiComposeAdapterDO setSources(String sources) {
        this.sources = sources;
        return this;
    }
}
