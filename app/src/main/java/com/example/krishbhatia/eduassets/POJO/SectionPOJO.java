package com.example.krishbhatia.eduassets.POJO;

import java.util.ArrayList;

public class SectionPOJO {
    private ArrayList<ResourcePOJO> resource;
    private String sectionName;

    public SectionPOJO() {
    }

    public SectionPOJO(ArrayList<ResourcePOJO> resource, String sectionName) {
        this.resource = resource;
        this.sectionName = sectionName;
    }

    public ArrayList<ResourcePOJO> getResource() {
        return resource;
    }

    public void setResource(ArrayList<ResourcePOJO> resource) {
        this.resource = resource;
    }

    public String getSectionName() {
        return sectionName;
    }

    public void setSectionName(String sectionName) {
        this.sectionName = sectionName;
    }
}
