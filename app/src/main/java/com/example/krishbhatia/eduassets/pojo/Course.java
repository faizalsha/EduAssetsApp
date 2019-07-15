package com.example.krishbhatia.eduassets.pojo;

public class Course {
    private int code;
    private String title;
    private String desc;

    public Course() {
    }

    public Course(String title, String desc) {
        this.title = title;
        this.desc = desc;
    }

    public Course(int code, String title, String desc) {
        this.code = code;
        this.title = title;
        this.desc = desc;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getTitle() {
        return title;
    }

    public String getDesc() {
        return desc;
    }
}
