package com.goldenictsolutions.win.goldenictjob365.Employee.PojoJavaClasses;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;


public class OtherQualification implements Serializable {

    @SerializedName("user_id")
    private String userId;

    @SerializedName("id")
    private String quaId;
    @SerializedName("center_name")
    private String centerName;
    private String course;
    @SerializedName("sdate")
    private String sDate;
    @SerializedName("edate")
    private String eDate;



    @SerializedName("start_date")
    private String start_date;


    @SerializedName("end_date")
    private String end_date;

    public OtherQualification(String userId,String quaId,String centerName, String course, String sDate, String eDate) {
        this.userId = userId;
        this.quaId = quaId;
        this.centerName = centerName;
        this.course = course;
        this.sDate = sDate;
        this.eDate = eDate;
    }
    public OtherQualification(String userId,String centerName, String course, String sDate, String eDate) {
        this.userId = userId;
        this.centerName = centerName;
        this.course = course;
        this.sDate = sDate;
        this.eDate = eDate;
    }

    public OtherQualification(String centerName, String course, String start_date, String end_date) {
        this.centerName = centerName;
        this.course = course;
        this.sDate = start_date;
        this.eDate = end_date;
    }

    public String getCenterName() {
        return centerName;
    }

    public void setCenterName(String centerName) {
        this.centerName = centerName;
    }

    public String getCourse() {
        return course;
    }

    public void setCourse(String course) {
        this.course = course;
    }

    public String getsDate() {
        return sDate;
    }

    public void setsDate(String sDate) {
        this.sDate = sDate;
    }

    public String geteDate() {
        return eDate;
    }

    public void seteDate(String eDate) {
        this.eDate = eDate;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getQuaId() {
        return quaId;
    }

    public void setQuaId(String quaId) {
        this.quaId = quaId;
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
}
