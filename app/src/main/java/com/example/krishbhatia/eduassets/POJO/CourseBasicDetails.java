package com.example.krishbhatia.eduassets.POJO;

import java.util.ArrayList;

public class CourseBasicDetails {
    private float courseId;
    private String courseName;
    private String description;
    private String imageUrl;
    private ArrayList<SubjectBasicDetail> subjects;

    public CourseBasicDetails() {
    }

    public CourseBasicDetails(float courseId, String courseName, String description, String imageUrl, ArrayList<SubjectBasicDetail> subjects, String syllabus, String university) {
        this.courseId = courseId;
        this.courseName = courseName;
        this.description = description;
        this.imageUrl = imageUrl;
        this.subjects = subjects;
        this.syllabus = syllabus;
        this.university = university;
    }

    private String syllabus;
    private String university;


    // Getter Methods

    public float getCourseId() {
        return courseId;
    }

    public String getCourseName() {
        return courseName;
    }

    public String getDescription() {
        return description;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public String getSyllabus() {
        return syllabus;
    }

    public String getUniversity() {
        return university;
    }

    // Setter Methods

    public void setCourseId(float courseId) {
        this.courseId = courseId;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public void setSyllabus(String syllabus) {
        this.syllabus = syllabus;
    }

    public void setUniversity(String university) {
        this.university = university;
    }
    public ArrayList<SubjectBasicDetail> getSubjects() {
        return subjects;
    }

    public void setSubjects(ArrayList<SubjectBasicDetail> subjects) {
        this.subjects = subjects;
    }
}
