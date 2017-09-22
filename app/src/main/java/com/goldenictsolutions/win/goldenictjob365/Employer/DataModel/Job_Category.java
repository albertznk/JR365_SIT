package com.goldenictsolutions.win.goldenictjob365.Employer.DataModel;


public class Job_Category {


    String job_name, id;

    public Job_Category(String job_name, String id) {
        this.job_name = job_name;
        this.id = id;
    }

    public String getJob_name() {
        return job_name;
    }

    public String getId() {
        return id;
    }
}
