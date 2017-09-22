package com.goldenictsolutions.win.goldenictjob365.Employee.Services;

import com.goldenictsolutions.win.goldenictjob365.Employee.PojoJavaClasses.*;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class ServerResponse implements Serializable {
    @SerializedName("email")
    private String email;
    @SerializedName("password")
    private String password;
    @SerializedName("message")
    private String message;
    @SerializedName("error_code")
    private int errorCode;
    private int status;
    @SerializedName("error")
    private String error;

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    @SerializedName("result")
    private String result;

    public String getResume_photo() {
        return resume_photo;
    }

    public void setResume_photo(String resume_photo) {
        this.resume_photo = resume_photo;
    }

    @SerializedName("resume-photo")
    private String resume_photo;

    public String getCompany_photo() {
        return company_photo;
    }

    @SerializedName("last_id")
    private String company_photo;
    public int getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(int statusCode) {
        this.statusCode = statusCode;
    }

    @SerializedName("status_code")
    private int statusCode;






    @SerializedName("applicant")
    private List<Applicant> applicant = null;

    @SerializedName("job")
    private ArrayList<Employer> employerList = null;

    //   @SerializedName("result")
    //  private String result;






    @SerializedName("user")
    private List<User> userList = null;

    @SerializedName("skill")
    private ArrayList<Skill> skillList = null;

    @SerializedName("education")
    private List<Education> educationList = null;

    @SerializedName("refree")
    private List<Refree> refereeList = null;

    @SerializedName("experience")
    private List<Experience> experienceList = null;

    @SerializedName("city")
    private List<Location> locationList = null;

    public List<OtherQualification> getOtherQualificationList() {
        return otherQualificationList;
    }

    public void setOtherQualificationList(List<OtherQualification> otherQualificationList) {
        this.otherQualificationList = otherQualificationList;
    }

    @SerializedName("qualification")
    private List<OtherQualification> otherQualificationList = null;


    @SerializedName("township")
    private List<Township> townshipList = null;

    private String token;


    @SerializedName("jobcategory")
    private List<JobCategory> jobCategories;

    public ServerResponse(String email, String password, String message, int errorCode, int status, String error) {
        this.email = email;
        this.password = password;
        this.message = message;
        this.errorCode = errorCode;
        this.status = status;
        this.error = error;

    }

    public ServerResponse(List<Location> city) {
        this.locationList = city;
    }


    public String getUsername() {
        return email;
    }

    public void setUsername(String username) {
        this.email = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public int getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(int errorCode) {
        this.errorCode = errorCode;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }


    public List<User> getUserList() {
        return userList;
    }

    public void setUserList(List<User> userList) {
        this.userList = userList;
    }

    public ArrayList<Employer> getEmployerList() {
        return employerList;
    }

    public void setEmployerList(ArrayList<Employer> employerList) {
        this.employerList = employerList;
    }


    public List<Applicant> getApplicant() {
        return applicant;
    }


    public void setApplicant(List<Applicant> applicant) {
        this.applicant = applicant;
    }


    public ArrayList<Skill> getSkillList() {
        return skillList;
    }

    public void setSkillList(ArrayList<Skill> skillList) {
        this.skillList = skillList;
    }

    public List<Education> getEducationList() {
        return educationList;
    }


    public void setEducationList(List<Education> educationList) {
        this.educationList = educationList;
    }

    public List<Refree> getRefereeList() {
        return refereeList;
    }

    public void setRefereeList(List<Refree> refereeList) {
        this.refereeList = refereeList;
    }


    public List<Experience> getExperienceList() {
        return experienceList;
    }

    public void setExperienceList(List<Experience> experienceList) {
        this.experienceList = experienceList;
    }

    public List<Location> getLocationList() {
        return locationList;
    }

    public void setLocationList(List<Location> locationList) {
        this.locationList = locationList;
    }

    public List<Township> getTownshipList() {
        return townshipList;
    }

    public List<JobCategory> getJobCategories() {
        return jobCategories;
    }

    public void setTownshipList(List<Township> townshipList) {
        this.townshipList = townshipList;
    }
}