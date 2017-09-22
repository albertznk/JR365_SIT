package com.goldenictsolutions.win.goldenictjob365.Employee.PojoJavaClasses;

/**
 * Created by Win on 2/15/2017.
 */
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Employer implements Serializable {



    @SerializedName("id")
        private String id;
    @SerializedName("employer_id")
        private String employerId;
    @SerializedName("job_category_id")
        private String jobCategoryId;
    @SerializedName("job_nature_id")
        private String jobNatureId;
    @SerializedName("job_title")
        private String jobTitle;
    @SerializedName("company_id")
        private String companyId;
    @SerializedName("company_name")
        private String companyName;
    @SerializedName("salary_range")
        private String salaryRange;
    @SerializedName("summary")
        private String summary;
    @SerializedName("description")
        private String description;
    @SerializedName("requirement")
        private String requirement;
    @SerializedName("township_id")
        private String townshipId;
    @SerializedName("city_id")
        private String cityId;
    @SerializedName("country_id")
        private String countryId;
    @SerializedName("open_date")
        private String openDate;
    @SerializedName("close_date")
        private String closeDate;
    @SerializedName("contact_info")
        private String contactInfo;
    @SerializedName("education")
        private Object education;
    @SerializedName("experience")
        private Object experience;
    @SerializedName("language_skill")
        private Object languageSkill;
    @SerializedName("is_active")
        private String isActive;
    @SerializedName("created_at")
        private String createdAt;
    @SerializedName("updated_at")
    private String updatedAt;
    @SerializedName("township")
        private String township;
    @SerializedName("city")
        private String city;
    @SerializedName("type")
        private String type;
    @SerializedName("category")
        private String category;

    public Employer(String companyId) {
        this.companyId = companyId;
    }


    public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getEmployerId() {
            return employerId;
        }

        public void setEmployerId(String employerId) {
            this.employerId = employerId;
        }

        public String getJobCategoryId() {
            return jobCategoryId;
        }

        public void setJobCategoryId(String jobCategoryId) {
            this.jobCategoryId = jobCategoryId;
        }

        public String getJobNatureId() {
            return jobNatureId;
        }

        public void setJobNatureId(String jobNatureId) {
            this.jobNatureId = jobNatureId;
        }

        public String getJobTitle() {
            return jobTitle;
        }

        public void setJobTitle(String jobTitle) {
            this.jobTitle = jobTitle;
        }

        public String getCompanyId() {
            return companyId;
        }

        public void setCompanyId(String companyId) {
            this.companyId = companyId;
        }

        public String getCompanyName() {
            return companyName;
        }

        public void setCompanyName(String companyName) {
            this.companyName = companyName;
        }

        public String getSalaryRange() {
            return salaryRange;
        }

        public void setSalaryRange(String salaryRange) {
            this.salaryRange = salaryRange;
        }

        public String getSummary() {
            return summary;
        }

        public void setSummary(String summary) {
            this.summary = summary;
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }

        public String getRequirement() {
            return requirement;
        }

        public void setRequirement(String requirement) {
            this.requirement = requirement;
        }

        public String getTownshipId() {
            return townshipId;
        }

        public void setTownshipId(String townshipId) {
            this.townshipId = townshipId;
        }

        public String getCityId() {
            return cityId;
        }

        public void setCityId(String cityId) {
            this.cityId = cityId;
        }

        public String getCountryId() {
            return countryId;
        }

        public void setCountryId(String countryId) {
            this.countryId = countryId;
        }

        public String getOpenDate() {
            return openDate;
        }

        public void setOpenDate(String openDate) {
            this.openDate = openDate;
        }

        public String getCloseDate() {
            return closeDate;
        }

        public void setCloseDate(String closeDate) {
            this.closeDate = closeDate;
        }

        public String getContactInfo() {
            return contactInfo;
        }

        public void setContactInfo(String contactInfo) {
            this.contactInfo = contactInfo;
        }

        public Object getEducation() {
            return education;
        }

        public void setEducation(Object education) {
            this.education = education;
        }

        public Object getExperience() {
            return experience;
        }

        public void setExperience(Object experience) {
            this.experience = experience;
        }

        public Object getLanguageSkill() {
            return languageSkill;
        }

        public void setLanguageSkill(Object languageSkill) {
            this.languageSkill = languageSkill;
        }

        public String getIsActive() {
            return isActive;
        }

        public void setIsActive(String isActive) {
            this.isActive = isActive;
        }

        public String getCreatedAt() {
            return createdAt;
        }

        public void setCreatedAt(String createdAt) {
            this.createdAt = createdAt;
        }

        public String getUpdatedAt() {
            return updatedAt;
        }

        public void setUpdatedAt(String updatedAt) {
            this.updatedAt = updatedAt;
        }

        public String getTownship() {
            return township;
        }

        public void setTownship(String township) {
            this.township = township;
        }

        public String getCity() {
            return city;
        }

        public void setCity(String city) {
            this.city = city;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public String getCategory() {
            return category;
        }

        public void setCategory(String category) {
            this.category = category;
        }


    @Override
    public String toString() {
        return "Employer{" +
                "id='" + id + '\'' +
                ", employerId='" + employerId + '\'' +
                ", jobCategoryId='" + jobCategoryId + '\'' +
                ", jobNatureId='" + jobNatureId + '\'' +
                ", jobTitle='" + jobTitle + '\'' +
                ", companyId='" + companyId + '\'' +
                ", companyName='" + companyName + '\'' +
                ", salaryRange='" + salaryRange + '\'' +
                ", summary='" + summary + '\'' +
                ", description='" + description + '\'' +
                ", requirement='" + requirement + '\'' +
                ", townshipId='" + townshipId + '\'' +
                ", cityId='" + cityId + '\'' +
                ", countryId='" + countryId + '\'' +
                ", openDate='" + openDate + '\'' +
                ", closeDate='" + closeDate + '\'' +
                ", contactInfo='" + contactInfo + '\'' +
                ", education=" + education +
                ", experience=" + experience +
                ", languageSkill=" + languageSkill +
                ", isActive='" + isActive + '\'' +
                ", createdAt='" + createdAt + '\'' +
                ", updatedAt='" + updatedAt + '\'' +
                ", township='" + township + '\'' +
                ", city='" + city + '\'' +
                ", type='" + type + '\'' +
                ", category='" + category + '\'' +
                '}';
    }
}
