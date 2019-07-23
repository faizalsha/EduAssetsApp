package com.example.krishbhatia.eduassets.POJO;

public class CourseBasicInfoPOJO {
    private String courseName;
    private Long courseId;

    public CourseBasicInfoPOJO() {
    }

    public CourseBasicInfoPOJO(String courseName, Long courseId) {
        this.courseName = courseName;
        this.courseId = courseId;
    }

    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    public Long getCourseId() {
        return courseId;
    }

    public void setCourseId(Long courseId) {
        this.courseId = courseId;
    }
}
