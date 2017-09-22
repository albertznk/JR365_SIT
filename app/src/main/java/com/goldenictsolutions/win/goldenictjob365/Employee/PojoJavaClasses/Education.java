package com.goldenictsolutions.win.goldenictjob365.Employee.PojoJavaClasses;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;


public class Education implements Serializable {
    @SerializedName("user_id")
    private String userId;
    @SerializedName("id")
    private String eduId;
    @SerializedName("university")
    private String university;
    private String degree;
    private String year;

    @SerializedName("start_date")
    private String getSStart_date;

    public String getGetSStart_date() {
        return getSStart_date;
    }

    public String getGetEEnd_date() {
        return getEEnd_date;
    }

    @SerializedName("end_date")
    private String getEEnd_date;

    @SerializedName("sdate")
    private String start_date;
    @SerializedName("edate")
    private String end_date;


    public Education(String eduId, String userId, String university, String degree, String start_date, String end_date) {
        this.eduId = eduId;
        this.userId = userId;
        this.start_date = start_date;
        this.end_date = end_date;
        this.university = university;
        this.degree = degree;
    }

    public Education(String userId, String university, String degree, String start_date, String end_date) {
        this.userId = userId;
        this.university = university;
        this.degree = degree;
        this.start_date = start_date;
        this.end_date = end_date;
    }

    public Education(String university, String degree, String start_date, String end) {
        this.university=university;
        this.degree=degree;
        this.start_date=start_date;
        this.end_date=end;
    }


    public String getUniversity() {
        return university;
    }

    public void setUniversity(String university) {
        this.university = university;
    }

    public String getDegree() {
        return degree;
    }

    public void setDegree(String degree) {
        this.degree = degree;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }


    public String getEduId() {
        return eduId;
    }

    public String getStart_date() {
        return start_date;
    }

    public void setStart_date(String start_date) {
        this.start_date = start_date;
    }

    public String getEnd_date() {
        return end_date;
    }

    public void setEnd_date(String end_date) {
        this.end_date = end_date;
    }

    public void setEduId(String eduId) {
        this.eduId = eduId;
    }
}

