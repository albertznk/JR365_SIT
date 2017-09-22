package com.goldenictsolutions.win.goldenictjob365.Employee.DataSet;

/**
 * Created by Kurio Tetsuya on 6/26/2017.
 */

public class Experience_Data {
    String exp_id,organization,rank,start_date,end_date;

    public Experience_Data(String exp_id,String organization, String rank, String start_date, String end_date) {
        this.exp_id=exp_id;
        this.organization = organization;
        this.rank = rank;
        this.start_date = start_date;
        this.end_date = end_date;
    }

    public String getExp_id(){
        return exp_id;
    }
    public String getOrganization() {
        return organization;
    }

    public String getRank() {
        return rank;
    }

    public String getStart_date() {
        return start_date;
    }

    public String getEnd_date() {
        return end_date;
    }
}
