package com.example.krishbhatia.eduassets.POJO;

class SubjectBasicDetail {
    private String subjectName;
    private float subjectCode;

    public SubjectBasicDetail() {
    }

    public SubjectBasicDetail(String subjectName, float subjectCode) {
        this.subjectName = subjectName;
        this.subjectCode = subjectCode;
    }

    public String getSubjectName() {
        return subjectName;
    }

    public void setSubjectName(String subjectName) {
        this.subjectName = subjectName;
    }

    public float getSubjectCode() {
        return subjectCode;
    }

    public void setSubjectCode(float subjectCode) {
        this.subjectCode = subjectCode;
    }
}
