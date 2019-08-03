package com.example.krishbhatia.eduassets.POJO;

public class CourseBasicInfoPOJO {
    private String courseName;
    private String courseFullTitle;
    private int courseId;
    private String courseUrl;

    public CourseBasicInfoPOJO() {
    }

    public CourseBasicInfoPOJO(String courseName, String courseFullTitle, int courseId, String courseUrl) {
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

    public int getCourseId() {
        return courseId;
    }

    public void setCourseId(int courseId) {
        this.courseId = courseId;
    }

    public String getCourseUrl() {
        return courseUrl;
    }

    public void setCourseUrl(String courseUrl) {
        this.courseUrl = courseUrl;
    }
}
