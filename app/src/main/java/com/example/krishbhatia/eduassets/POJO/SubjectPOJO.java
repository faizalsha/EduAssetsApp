package com.example.krishbhatia.eduassets.POJO;

public class SubjectPOJO {
    private String title;
    private String desc;

    public SubjectPOJO(){}

    public SubjectPOJO(String title, String desc) {
        this.title = title;
        this.desc = desc;
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
