package com.example.krishbhatia.eduassets.POJO;

public class CourseBasicInfoPOJO {
    private String courseName;
    private String courseFullTitle;
    private Long courseId;
    private String courseUrl;

    public CourseBasicInfoPOJO() {
    }

    public CourseBasicInfoPOJO(String courseName, String courseFullTitle, Long courseId, String courseUrl) {
        this.courseName = courseName;
        this.courseFullTitle = courseFullTitle;
        this.courseId = courseId;
        this.courseUrl = courseUrl;
    }

    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    public String getCourseFullTitle() {
        return courseFullTitle;
    }

    public void setCourseFullTitle(String courseFullTitle) {
        this.courseFullTitle = courseFullTitle;
    }

    public Long getCourseId() {
        return courseId;
    }

    public void setCourseId(Long courseId) {
        this.courseId = courseId;
    }

    public String getCourseUrl() {
        return courseUrl;
    }

    public void setCourseUrl(String courseUrl) {
        this.courseUrl = courseUrl;
    }
}
