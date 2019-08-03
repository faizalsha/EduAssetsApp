package com.example.krishbhatia.eduassets.POJO;

public class SubjectBasicInfoPOJO {
    private String courseName;

    private String subjectName;
    private Long subjectCode;
    private int courseId;

    public SubjectBasicInfoPOJO() {
    }

    public SubjectBasicInfoPOJO(String courseName, int courseId, String subjectName, Long subjectCode) {
        this.courseName = courseName;
        this.courseId = courseId;
        this.subjectName = subjectName;
        this.subjectCode = subjectCode;
    }

    public int getCourseId() {
        return courseId;
    }

    public void setCourseId(int courseId) {
        this.courseId = courseId;
    }

    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    public String getSubjectName() {
        return subjectName;
    }

    public void setSubjectName(String subjectName) {
        this.subjectName = subjectName;
    }

    public Long getSubjectCode() {
        return subjectCode;
    }

    public void setSubjectCode(Long subjectCode) {
        this.subjectCode = subjectCode;
    }
}
