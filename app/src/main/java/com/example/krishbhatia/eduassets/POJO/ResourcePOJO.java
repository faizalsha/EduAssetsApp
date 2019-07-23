package com.example.krishbhatia.eduassets.POJO;

public class ResourcePOJO {
    private String resType;
    private String resName;
    private String resUrl;
    private String course;
    private String subjectCode;

    public ResourcePOJO() {
    }

    public ResourcePOJO(String resType, String resName, String resUrl, String course, String subjectCode) {
        this.resType = resType;
        this.resName = resName;
        this.resUrl = resUrl;
        this.course = course;
        this.subjectCode = subjectCode;
    }

    public String getResType() {
        return resType;
    }

    public void setResType(String resType) {
        this.resType = resType;
    }

    public String getResName() {
        return resName;
    }

    public void setResName(String resName) {
        this.resName = resName;
    }

    public String getResUrl() {
        return resUrl;
    }

    public void setResUrl(String resUrl) {
        this.resUrl = resUrl;
    }

    public String getCourse() {
        return course;
    }

    public void setCourse(String course) {
        this.course = course;
    }

    public String getSubjectCode() {
        return subjectCode;
    }

    public void setSubjectCode(String subjectCode) {
        this.subjectCode = subjectCode;
    }
}
