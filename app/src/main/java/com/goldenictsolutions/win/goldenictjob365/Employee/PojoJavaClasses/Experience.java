package com.goldenictsolutions.win.goldenictjob365.Employee.PojoJavaClasses;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by Win on 2/6/2017.
 */
public class Experience implements Serializable {
    @SerializedName("user_id")
    private String userId;

    @SerializedName("id")
    private String expId;
    private String organization;
    private String rank;
    @SerializedName("start_date")
    private String startDate;
    @SerializedName("end_date")
    private String endDate;


    public Experience(String organization, String rank, String startDate, String endDate) {
        this.organization = organization;
        this.rank = rank;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    public Experience(String userId,String expId,String organization, String rank, String startDate, String endDate) {
        this.userId = userId;
        this.expId = expId;
        this.organization = organization;
        this.rank = rank;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    public Experience(String userId,String organization, String rank, String startDate, String endDate) {
        this.userId = userId;

        this.organization = organization;
        this.rank = rank;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    public String getOrganization() {
        return organization;
    }

    public void setOrganization(String organization) {
        this.organization = organization;
    }

    public String getRank() {
        return rank;
    }

    public void setRank(String rank) {
        this.rank = rank;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }


    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getExpId() {
        return expId;
    }

    public void setExpId(String expId) {
        this.expId = expId;
    }
}



