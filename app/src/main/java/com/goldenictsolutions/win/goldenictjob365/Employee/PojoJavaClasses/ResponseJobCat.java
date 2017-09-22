package com.goldenictsolutions.win.goldenictjob365.Employee.PojoJavaClasses;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Win on 1/18/2017.
 */

public class ResponseJobCat implements Serializable {

    @SerializedName("error")
    @Expose
    private Boolean error;

    @SerializedName("result")
    @Expose
    private List<JobCategory> result;





    @SerializedName("status_code")
    @Expose
    private Integer statusCode;

    public Boolean getError() {
        return error;
    }

    public void setError(Boolean error) {
        this.error = error;
    }

    public List<JobCategory> getResult() {
        return result;
    }

    public void setResult(List<JobCategory> result) {
        this.result = result;
    }



    public Integer getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(Integer statusCode) {
        this.statusCode = statusCode;
    }

}

