package com.lenovo.research.se.aiexchange.model;

public class CourseVO {
    private Integer courseId;
    private String kpoint;
    private String name;
    private Integer pageNumber;

    public Integer getCourseId() {
        return courseId;
    }

    public CourseVO setCourseId(Integer courseId) {
        this.courseId = courseId;
        return this;
    }

    public String getKpoint() {
        return kpoint;
    }

    public CourseVO setKpoint(String kpoint) {
        this.kpoint = kpoint;
        return this;
    }

    public String getName() {
        return name;
    }

    public CourseVO setName(String name) {
        this.name = name;
        return this;
    }

    public Integer getPageNumber() {
        return pageNumber;
    }

    public CourseVO setPageNumber(Integer pageNumber) {
        this.pageNumber = pageNumber;
        return this;
    }
}
