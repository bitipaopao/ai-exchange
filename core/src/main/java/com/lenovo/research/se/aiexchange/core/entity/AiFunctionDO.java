package com.lenovo.research.se.aiexchange.core.entity;

import java.util.Date;

public class AiFunctionDO {

    private String aiKey;
    private String functionId;
    private Integer functionResType;
    private Integer functionBatch;
    private Integer flowIntervalSecond;
    private Integer flowRate;
    private String functionInfo;
    private String functionMethod;
    private String path;
    private Integer valid;
    private Date createTime;
    private Date modifiedTime;

    public String getAiKey() {
        return aiKey;
    }

    public AiFunctionDO setAiKey(String aiKey) {
        this.aiKey = aiKey;
        return this;
    }

    public String getFunctionId() {
        return functionId;
    }

    public AiFunctionDO setFunctionId(String functionId) {
        this.functionId = functionId;
        return this;
    }

    public Integer getFunctionResType() {
        return functionResType;
    }

    public AiFunctionDO setFunctionResType(Integer functionResType) {
        this.functionResType = functionResType;
        return this;
    }

    public Integer getFunctionBatch() {
        return functionBatch;
    }

    public AiFunctionDO setFunctionBatch(Integer functionBatch) {
        this.functionBatch = functionBatch;
        return this;
    }

    public Integer getFlowIntervalSecond() {
        return flowIntervalSecond;
    }

    public AiFunctionDO setFlowIntervalSecond(Integer flowIntervalSecond) {
        this.flowIntervalSecond = flowIntervalSecond;
        return this;
    }

    public Integer getFlowRate() {
        return flowRate;
    }

    public AiFunctionDO setFlowRate(Integer flowRate) {
        this.flowRate = flowRate;
        return this;
    }

    public String getPath() {
        return path;
    }

    public AiFunctionDO setPath(String path) {
        this.path = path;
        return this;
    }

    public Integer getValid() {
        return valid;
    }

    public AiFunctionDO setValid(Integer valid) {
        this.valid = valid;
        return this;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public AiFunctionDO setCreateTime(Date createTime) {
        this.createTime = createTime;
        return this;
    }

    public Date getModifiedTime() {
        return modifiedTime;
    }

    public AiFunctionDO setModifiedTime(Date modifiedTime) {
        this.modifiedTime = modifiedTime;
        return this;
    }

    public String getFunctionInfo() {
        return functionInfo;
    }

    public AiFunctionDO setFunctionInfo(String functionInfo) {
        this.functionInfo = functionInfo;
        return this;
    }

    public String getFunctionMethod() {
        return functionMethod;
    }

    public AiFunctionDO setFunctionMethod(String functionMethod) {
        this.functionMethod = functionMethod;
        return this;
    }
}
