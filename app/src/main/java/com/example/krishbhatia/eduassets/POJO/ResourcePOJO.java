package com.example.krishbhatia.eduassets.POJO;

public class ResourcePOJO {
    private String resType;
    private String name;
    private String url;
    private String course;
    private String subjectCode;

    public ResourcePOJO() {
    }

    public String getResType() {
        return resType;
    }

    public void setResType(String resType) {
        this.resType = resType;
    }

    public ResourcePOJO(String resType, String name, String url, String course, String subjectCode) {
        this.resType = resType;
        this.name = name;
        this.url = url;
        this.course = course;
        this.subjectCode = subjectCode;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
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
