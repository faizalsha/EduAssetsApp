package com.example.krishbhatia.eduassets.POJO;

public class UserPOJO {
    private String name;
    private String course;
    private String college;
    private String semester;
    private String userId;
    private String email;
    private String enrolledCourse;

    public void setEnrolledCourse(String enrolledCourse) {
        this.enrolledCourse = enrolledCourse;
    }

    public String getEnrolledCourse() {
        return enrolledCourse;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUserId() {
        return userId;
    }

    public String getEmail() {
        return email;
    }

    //Default Constructor
    public UserPOJO(){

    }

    public UserPOJO(String name, String course, String college, String semester, String userId, String email, String enrolledCourse) {
        this.name = name;
        this.course = course;
        this.college = college;
        this.semester = semester;
        this.userId = userId;
        this.email = email;
        this.enrolledCourse = enrolledCourse;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setCourse(String course) {
        this.course = course;
    }

    public void setCollege(String college) {
        this.college = college;
    }

    public void setSemester(String semester) {
        this.semester = semester;
    }

    public String getName() {
        return name;
    }

    public String getCourse() {
        return course;
    }

    public String getCollege() {
        return college;
    }

    public String getSemester() {
        return semester;
    }



}
