package com.goldenictsolutions.win.goldenictjob365.Employee.PojoJavaClasses;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by Win on 1/18/2017.
 */

public class JobCategory implements Serializable {
    // result of jobcategory

        @SerializedName("id")
        @Expose
        private Integer id;

        @SerializedName("category")
        @Expose
        private String category;
        @SerializedName("created_at")
        @Expose
        private String createdAt;
        @SerializedName("updated_at")
        @Expose
        private String updatedAt;

    public JobCategory(Integer id, String category, String createdAt, String updatedAt) {
        this.id = id;
        this.category = category;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public Integer getId() {
            return id;
        }

        public void setId(Integer id) {
            this.id = id;
        }

        public String getCategory() {
            return category;
        }

        public void setCategory(String category) {
            this.category = category;
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

    @Override
    public String toString() {
        return category;
    }
}

