package com.lenovo.research.se.aiexchange.model;

public class FiledAdapter {
    public final static String ROOT = "ROOT";

    private String source;
    private Integer sourceType;
    private Integer sourceIndex;
    private String target;
    private Integer targetType;

    public String getSource() {
        return source;
    }

    public FiledAdapter setSource(String source) {
        this.source = source;
        return this;
    }

    public String getTarget() {
        return target;
    }

    public FiledAdapter setTarget(String target) {
        this.target = target;
        return this;
    }

    public Integer getTargetType() {
        return targetType;
    }

    public FiledAdapter setTargetType(Integer targetType) {
        this.targetType = targetType;
        return this;
    }

    public Integer getSourceType() {
        return sourceType;
    }

    public FiledAdapter setSourceType(Integer sourceType) {
        this.sourceType = sourceType;
        return this;
    }

    public Integer getSourceIndex() {
        return sourceIndex;
    }

    public FiledAdapter setSourceIndex(Integer sourceIndex) {
        this.sourceIndex = sourceIndex;
        return this;
    }

    public boolean isSourceRoot() {
        return source.equals(ROOT);
    }

    public boolean isTargetRoot() {
        return target.equals(ROOT);
    }
}
