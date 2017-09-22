package com.goldenictsolutions.win.goldenictjob365.Employee.DataSet;

/**
 * Created by Kurio Tetsuya on 6/26/2017.
 */

public class Education_Data {
    String edu_id;
    String university;
    String degree;
    String edu_start_date;
    String edu_end_date;

    public Education_Data(String edu_id,String university,String degree,String edu_start_date,String edu_end_date) {
        this.edu_id=edu_id;
        this.edu_start_date=edu_start_date;
        this.edu_end_date=edu_end_date;
        this.university = university;
        this.degree= degree;
    }
    public String getEdu_start_date() {
        return edu_start_date;
    }

    public String getEdu_end_date() {
        return edu_end_date;
    }

    public String getEdu_id(){
        return edu_id;
    }
    public String getDegree() {
        return degree;
    }

    public String getUniversity() {
        return university;
    }



}
