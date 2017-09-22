package com.goldenictsolutions.win.goldenictjob365.Employee.PojoJavaClasses;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by Win on 2/6/2017.
 */

public class Applicant implements Serializable {

    public void setId(String id) {
        Id = id;
    }

    public void setDate_OfBirth(String date_OfBirth) {
        this.date_OfBirth = date_OfBirth;
    }

    public void setNrc_No(String nrc_No) {
        this.nrc_No = nrc_No;
    }

    @SerializedName("id")
    private String Id;

    @SerializedName("gender")
    private String gender;

    @SerializedName("cv_view")
    private String CvViews;

    @SerializedName("name")
    private String firstName;

    @SerializedName("father_name")
    private String FatherName;

    @SerializedName("dob")
    private String dateOfBirth;

    @SerializedName("pob")
    private String pob;

    @SerializedName("marital_status")
    private String maritalStatus;

    public String getPhoto() {
        return photo;
    }

    @SerializedName("photo")
    private String photo;

    @SerializedName("nationality")
    private String nationality;

    @SerializedName("religion")
    private String religion;

    @SerializedName("nrc")
    private String nrc;

    @SerializedName("mobile_no")
    private String mobileNo;

    @SerializedName("email")
    private String email;

    @SerializedName("expected_salary")
    private String expectedSalary;

    @SerializedName("address")
    private String address;

    @SerializedName("township")
    private String township;

    @SerializedName("township_id")
    private int townshipId;

    @SerializedName("city_id")
    private int cityId;

    @SerializedName("city")
    private String city;


    public String getDate_OfBirth() {
        return date_OfBirth;
    }

    public String getNrc_No() {
        return nrc_No;
    }

    @SerializedName("date_of_birth")
    private String date_OfBirth;
    @SerializedName("nrc_no")
    private String nrc_No;

    public Applicant(String Id,String gender, String cvViews, String firstName, String fatherName, String dateOfBirth, String pob, String maritalStatus, String nationality, String religion, String nrc, String mobileNo, String email, String expectedSalary,String city, String address, int townshipId, int cityId, String currentPosition, String desiredPosition, int chkDriving) {
        this.Id = Id;
        this.city = city;
        this.gender = gender;
        CvViews = cvViews;
        this.firstName = firstName;
        FatherName = fatherName;
        this.dateOfBirth = dateOfBirth;
        this.pob = pob;
        this.maritalStatus = maritalStatus;
        this.nationality = nationality;
        this.religion = religion;
        this.nrc = nrc;
        this.mobileNo = mobileNo;
        this.email = email;
        this.expectedSalary = expectedSalary;
        this.address = address;
        this.townshipId = townshipId;
        this.cityId = cityId;
        this.currentPosition = currentPosition;
        this.desiredPosition = desiredPosition;
        this.chkDriving = chkDriving;
    }

    public Applicant(String township,String Id,String gender, String cvViews, String firstName, String fatherName, String dateOfBirth, String pob, String maritalStatus, String nationality, String religion, String nrc, String mobileNo, String email, String expectedSalary,String city, String address, int townshipId, int cityId, String currentPosition, String desiredPosition, int chkDriving) {
        this.Id = Id;
        this.city = city;
        this.gender = gender;
        CvViews = cvViews;
        this.firstName = firstName;
        FatherName = fatherName;
        this.dateOfBirth = dateOfBirth;
        this.pob = pob;
        this.maritalStatus = maritalStatus;
        this.nationality = nationality;
        this.religion = religion;
        this.nrc = nrc;
        this.mobileNo = mobileNo;
        this.email = email;
        this.expectedSalary = expectedSalary;
        this.address = address;
        this.townshipId = townshipId;
        this.cityId = cityId;
        this.currentPosition = currentPosition;
        this.desiredPosition = desiredPosition;
        this.chkDriving = chkDriving;
        this.township = township;
    }


    @SerializedName("current_position")
    private String currentPosition;

    @SerializedName("desired_position")
    private String desiredPosition;

    @SerializedName("chkDriving")
    private int chkDriving;






    public Applicant(String Id,String gender, String cvViews, String firstName, String fatherName, String dateOfBirth, String pob, String maritalStatus, String nationality, String religion, String nrc, String mobileNo, String email, String expectedSalary, String address, String townshipId, String cityId, String currentPosition, String desiredPosition, int chkDriving) {
        this.Id = Id;

        this.gender = gender;
        CvViews = cvViews;
        this.firstName = firstName;
        FatherName = fatherName;
        this.dateOfBirth = dateOfBirth;
        this.pob = pob;
        this.maritalStatus = maritalStatus;
        this.nationality = nationality;
        this.religion = religion;
        this.nrc = nrc;
        this.mobileNo = mobileNo;
        this.email = email;
        this.expectedSalary = expectedSalary;
        this.address = address;
        this.township = townshipId;
        this.city = cityId;
        this.currentPosition = currentPosition;
        this.desiredPosition = desiredPosition;
        this.chkDriving = chkDriving;
    }


    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getCvViews() {
        return CvViews;
    }

    public void setCvViews(String cvViews) {
        CvViews = cvViews;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getFatherName() {
        return FatherName;
    }

    public void setFatherName(String fatherName) {
        FatherName = fatherName;
    }

    public String getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(String dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public String getPob() {
        return pob;
    }

    public void setPob(String pob) {
        this.pob = pob;
    }

    public String getMaritalStatus() {
        return maritalStatus;
    }

    public void setMaritalStatus(String maritalStatus) {
        this.maritalStatus = maritalStatus;
    }

    public String getNationality() {
        return nationality;
    }

    public void setNationality(String nationality) {
        this.nationality = nationality;
    }

    public String getReligion() {
        return religion;
    }

    public void setReligion(String religion) {
        this.religion = religion;
    }

    public String getNrc() {
        return nrc;
    }

    public void setNrc(String nrc) {
        this.nrc = nrc;
    }

    public String getMobileNo() {
        return mobileNo;
    }

    public void setMobileNo(String mobileNo) {
        this.mobileNo = mobileNo;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getExpectedSalary() {
        return expectedSalary;
    }

    public void setExpectedSalary(String expectedSalary) {
        this.expectedSalary = expectedSalary;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public int getTownshipId() {
        return townshipId;
    }

    public void setTownshipId(int townshipId) {
        this.townshipId = townshipId;
    }

    public int getCityId() {
        return cityId;
    }

    public void setCityId(int cityId) {
        this.cityId = cityId;
    }

    public String getCurrentPosition() {
        return currentPosition;
    }

    public void setCurrentPosition(String currentPosition) {
        this.currentPosition = currentPosition;
    }

    public String getDesiredPosition() {
        return desiredPosition;
    }

    public void setDesiredPosition(String desiredPosition) {
        this.desiredPosition = desiredPosition;
    }

    public int getChkDriving() {
        return chkDriving;
    }

    public void setChkDriving(int chkDriving) {
        this.chkDriving = chkDriving;
    }

    public String getId() {
        return Id;
    }

    public void setUserId(String Id) {
        this.Id = Id;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getTownship() {
        return township;
    }

    public void setTownship(String township) {
        this.township = township;
    }
}