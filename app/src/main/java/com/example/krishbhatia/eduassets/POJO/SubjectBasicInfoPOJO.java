package com.example.krishbhatia.eduassets.POJO;

public class SubjectBasicInfoPOJO {
    private String courseName;
    private String courseAbbreviation;
    private String subjectName;
    private Long subjectCode;

    public SubjectBasicInfoPOJO() {
    }

    public SubjectBasicInfoPOJO(String courseName, String courseAbbreviation, String subjectName, Long subjectCode) {
        this.courseName = courseName;
        this.courseAbbreviation = courseAbbreviation;
        this.subjectName = subjectName;
        this.subjectCode = subjectCode;
    }

    public String getCourseAbbreviation() {
        return courseAbbreviation;
    }

    public void setCourseAbbreviation(String courseAbbreviation) {
        this.courseAbbreviation = courseAbbreviation;
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
