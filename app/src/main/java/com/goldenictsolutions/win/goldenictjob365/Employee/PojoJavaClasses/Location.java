package com.goldenictsolutions.win.goldenictjob365.Employee.PojoJavaClasses;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Win on 1/27/2017.
 */

public class Location implements Serializable {
  //  @SerializedName("employer_id")
  //  @Expose
    private int id;
    private String city;
    private String countryId;
    private String createdAt;
    private String updatedAt;

    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    public Location(int id,String city ) {
        this.id = id;
        this.city = city;

    }

    public Location(String city) {
        this.city = city;
    }

    public int getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getCountryId() {
        return countryId;
    }

    public void setCountryId(String countryId) {
        this.countryId = countryId;
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

    public Map<String, Object> getAdditionalProperties() {
        return this.additionalProperties;
    }

    public void setAdditionalProperty(String name, Object value) {
        this.additionalProperties.put(name, value);
    }


}
