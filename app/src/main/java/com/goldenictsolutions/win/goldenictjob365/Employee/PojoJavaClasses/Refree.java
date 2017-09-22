package com.goldenictsolutions.win.goldenictjob365.Employee.PojoJavaClasses;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;


public class Refree implements Serializable{
    @SerializedName("user_id")
    private String userId;
    @SerializedName("first_name")
    private String firstName;
    @SerializedName("last_name")
    private String lastName;

    private String organization;
    private String rank;

    @SerializedName("mobile_no")
    private String mobileNo;
    private String email;



    public Refree(String firstName, String lastName, String company, String rank, String email, String mobileNo) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.organization = company;
        this.rank = rank;
        this.email = email;
        this.mobileNo = mobileNo;
    }

    public Refree(String userId, String firstName, String lastName, String company, String rank, String email, String mobileNo) {
        this.userId = userId;
        this.firstName = firstName;

        this.lastName = lastName;
        this.organization = company;
        this.rank = rank;
        this.email = email;
        this.mobileNo = mobileNo;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getCompany() {
        return organization;
    }

    public void setCompany(String company) {
        this.organization= company;
    }

    public String getRank() {
        return rank;
    }

    public void setRank(String rank) {
        this.rank = rank;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getMobileNo() {
        return mobileNo;
    }

    public void setMobileNo(String mobileNo) {
        this.mobileNo = mobileNo;
    }


    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }
}