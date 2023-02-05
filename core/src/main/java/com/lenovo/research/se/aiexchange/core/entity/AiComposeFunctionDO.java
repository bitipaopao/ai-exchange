package com.lenovo.research.se.aiexchange.core.entity;

import java.util.Date;

public class AiComposeFunctionDO {
    private String composeKey;
    private Integer step;
    private String aiKey;
    private String functionId;
    private String desc;
    private Integer valid;
    private Date createTime;
    private Date modifiedTime;

    public String getComposeKey() {
        return composeKey;
    }

    public AiComposeFunctionDO setComposeKey(String composeKey) {
        this.composeKey = composeKey;
        return this;
    }

    public Integer getStep() {
        return step;
    }

    public AiComposeFunctionDO setStep(Integer step) {
        this.step = step;
        return this;
    }

    public String getAiKey() {
        return aiKey;
    }

    public AiComposeFunctionDO setAiKey(String aiKey) {
        this.aiKey = aiKey;
        return this;
    }

    public String getFunctionId() {
        return functionId;
    }

    public AiComposeFunctionDO setFunctionId(String functionId) {
        this.functionId = functionId;
        return this;
    }

    public String getDesc() {
        return desc;
    }

    public AiComposeFunctionDO setDesc(String desc) {
        this.desc = desc;
        return this;
    }

    public Integer getValid() {
        return valid;
    }

    public AiComposeFunctionDO setValid(Integer valid) {
        this.valid = valid;
        return this;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public AiComposeFunctionDO setCreateTime(Date createTime) {
        this.createTime = createTime;
        return this;
    }

    public Date getModifiedTime() {
        return modifiedTime;
    }

    public AiComposeFunctionDO setModifiedTime(Date modifiedTime) {
        this.modifiedTime = modifiedTime;
        return this;
    }
}
