package com.shinner.data.aiexchange.model;

public class CoursePageVO {
    private Integer courseId;
    private Integer sort;
    private String imageCode;
    private String description;

    public Integer getCourseId() {
        return courseId;
    }

    public CoursePageVO setCourseId(Integer courseId) {
        this.courseId = courseId;
        return this;
    }

    public Integer getSort() {
        return sort;
    }

    public CoursePageVO setSort(Integer sort) {
        this.sort = sort;
        return this;
    }

    public String getImageCode() {
        return imageCode;
    }

    public CoursePageVO setImageCode(String imageCode) {
        this.imageCode = imageCode;
        return this;
    }

    public String getDescription() {
        return description;
    }

    public CoursePageVO setDescription(String description) {
        this.description = description;
        return this;
    }
}
