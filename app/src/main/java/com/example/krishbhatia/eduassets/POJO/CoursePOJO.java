package com.example.krishbhatia.eduassets.POJO;

public class CoursePOJO {
    private int code;
    private String title;
    private String desc;
    private Double price;

    public CoursePOJO() {
    }

    public CoursePOJO(String title, String desc) {
        this.title = title;
        this.desc = desc;
    }

    public CoursePOJO(int code, String title, String desc, Double price) {
        this.code = code;
        this.title = title;
        this.desc = desc;
        this.price = price;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
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
