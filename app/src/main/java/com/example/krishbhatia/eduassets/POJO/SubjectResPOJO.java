package com.example.krishbhatia.eduassets.POJO;

import java.util.ArrayList;
import java.util.List;

public class SubjectResPOJO {
    private String subjectName;
    private ArrayList<SectionPOJO> section;

    public SubjectResPOJO() {
    }

    public SubjectResPOJO(String subjectName, ArrayList<SectionPOJO> section) {
        this.subjectName = subjectName;
        this.section = section;
    }

    public String getSubjectName() {
        return subjectName;
    }

    public void setSubjectName(String subjectName) {
        this.subjectName = subjectName;
    }

    public ArrayList<SectionPOJO> getSection() {
        return section;
    }

    public void setSection(ArrayList<SectionPOJO> section) {
        this.section = section;
    }
}
