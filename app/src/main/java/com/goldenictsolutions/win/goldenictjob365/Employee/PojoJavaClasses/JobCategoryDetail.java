package com.goldenictsolutions.win.goldenictjob365.Employee.PojoJavaClasses;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by Win on 1/18/2017.
 */

public class JobCategoryDetail implements Serializable {
    @SerializedName("id")
        @Expose
        private String id;
        @SerializedName("employer_id")
        @Expose
        private String employerId;
        @SerializedName("job_category_id")
        @Expose
        private Integer jobCategoryId;
        @SerializedName("job_nature_id")
        @Expose
        private Integer jobNatureId;
        @SerializedName("job_title")
        @Expose
        private String jobTitle;
        @SerializedName("company_name")
        @Expose
        private String companyName;
        @SerializedName("salary_range")
        @Expose
        private String salaryRange;
        @SerializedName("summary")
        @Expose
        private String summary;
        @SerializedName("description")
        @Expose
        private String description;
        @SerializedName("requirement")
        @Expose
        private String requirement;
        @SerializedName("township")
        @Expose
        private String township;
        @SerializedName("city_id")
        @Expose
        private Integer cityId;
        @SerializedName("country_id")
        @Expose
        private Integer countryId;
        @SerializedName("open_date")
        @Expose
        private String openDate;
        @SerializedName("close_date")
        @Expose
        private String closeDate;
        @SerializedName("contact_info")
        @Expose
        private String contactInfo;
        @SerializedName("is_active")
        @Expose
        private Integer isActive;
        @SerializedName("created_at")
        @Expose
        private String createdAt;
        @SerializedName("updated_at")
        @Expose
        private String updatedAt;
        @SerializedName("category")
        @Expose
        private String category;
        @SerializedName("type")
        @Expose
        private String type;

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

        public Integer getJobCategoryId() {
            return jobCategoryId;
        }

        public void setJobCategoryId(Integer jobCategoryId) {
            this.jobCategoryId = jobCategoryId;
        }

        public Integer getJobNatureId() {
            return jobNatureId;
        }

        public void setJobNatureId(Integer jobNatureId) {
            this.jobNatureId = jobNatureId;
        }

        public String getJobTitle() {
            return jobTitle;
        }

        public void setJobTitle(String jobTitle) {
            this.jobTitle = jobTitle;
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

        public String getTownship() {
            return township;
        }

        public void setTownship(String township) {
            this.township = township;
        }

        public Integer getCityId() {
            return cityId;
        }

        public void setCityId(Integer cityId) {
            this.cityId = cityId;
        }

        public Integer getCountryId() {
            return countryId;
        }

        public void setCountryId(Integer countryId) {
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

        public Integer getIsActive() {
            return isActive;
        }

        public void setIsActive(Integer isActive) {
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

        public String getCategory() {
            return category;
        }

        public void setCategory(String category) {
            this.category = category;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

    @Override
    public String toString() {
        return "JobCategoryDetail{" +
                "id='" + id + '\'' +
                ", employerId='" + employerId + '\'' +
                ", jobCategoryId=" + jobCategoryId +
                ", jobNatureId=" + jobNatureId +
                ", jobTitle='" + jobTitle + '\'' +
                ", companyName='" + companyName + '\'' +
                ", salaryRange='" + salaryRange + '\'' +
                ", summary='" + summary + '\'' +
                ", description='" + description + '\'' +
                ", requirement='" + requirement + '\'' +
                ", township='" + township + '\'' +
                ", cityId=" + cityId +
                ", countryId=" + countryId +
                ", openDate='" + openDate + '\'' +
                ", closeDate='" + closeDate + '\'' +
                ", contactInfo='" + contactInfo + '\'' +
                ", isActive=" + isActive +
                ", createdAt='" + createdAt + '\'' +
                ", updatedAt='" + updatedAt + '\'' +
                ", category='" + category + '\'' +
                ", type='" + type + '\'' +
                '}';
    }
}

