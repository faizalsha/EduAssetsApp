package com.example.krishbhatia.eduassets.POJO;

import java.util.ArrayList;
import java.util.List;

public class SubjectResPOJO {
    private int subjectCode;
    private ArrayList<SectionPOJO> section;

    public SubjectResPOJO() {
    }

    public SubjectResPOJO(int subjectCode, ArrayList<SectionPOJO> section) {
        this.subjectCode = subjectCode;
        this.section = section;
    }

    public int getSubjectCode() {
        return subjectCode;
    }

    public void setSubjectCode(int subjectCode) {
        this.subjectCode = subjectCode;
    }

    public ArrayList<SectionPOJO> getSection() {
        return section;
    }

    public void setSection(ArrayList<SectionPOJO> section) {
        this.section = section;
    }
}
