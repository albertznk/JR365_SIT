package com.goldenictsolutions.win.goldenictjob365.Employer.DataModel;

/**
 * Created by Kurio Tetsuya on 6/26/2017.
 */

public class Qualification_Data {

    String qua_id, center_name, course, start_date, end_date;

    public Qualification_Data(String qua_id, String center_name, String course, String start_date, String end_date) {
        this.qua_id = qua_id;
        this.center_name = center_name;
        this.course = course;
        this.start_date = start_date;
        this.end_date = end_date;
    }

    public String getQua_id() {
        return qua_id;
    }

    public String getCenter_name() {
        return center_name;
    }

    public String getCourse() {
        return course;
    }

    public String getStart_date() {
        return start_date;
    }

    public String getEnd_date() {
        return end_date;
    }
}
