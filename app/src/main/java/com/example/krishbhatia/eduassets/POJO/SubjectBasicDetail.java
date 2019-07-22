package com.example.krishbhatia.eduassets.POJO;

public class SubjectBasicDetail {
    private String subjectName;
    private Integer subjectCode;

    public SubjectBasicDetail() {
    }

    public SubjectBasicDetail(String subjectName, Integer subjectCode) {
        this.subjectName = subjectName;
        this.subjectCode = subjectCode;
    }

    public String getSubjectName() {
        return subjectName;
    }

    public void setSubjectName(String subjectName) {
        this.subjectName = subjectName;
    }

    public Integer getSubjectCode() {
        return subjectCode;
    }

    public void setSubjectCode(Integer subjectCode) {
        this.subjectCode = subjectCode;
    }
}
