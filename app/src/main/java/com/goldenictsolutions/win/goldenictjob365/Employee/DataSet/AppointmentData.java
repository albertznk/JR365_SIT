package com.goldenictsolutions.win.goldenictjob365.Employee.DataSet;

/**
 * Created by Kurio Tetsuya on 6/29/2017.
 */

public class AppointmentData {
    String idAp;
    String employer_id;
    String applicant_id;
    String discricption;
    String contact_info;
    String created_at;
    String updated_at;
    String contact_no;

    public AppointmentData(String id, String employer_id, String applicant_id, String discricption, String contact_info, String contact_no, String created_at, String updated_at) {
        this.idAp = id;
        this.employer_id = employer_id;
        this.applicant_id = applicant_id;
        this.discricption = discricption;
        this.contact_info = contact_info;
        this.created_at = created_at;
        this.updated_at = updated_at;
        this.contact_no = contact_no;
    }

    public String getContact_no() {
        return contact_no;
    }

    public String getIdAp() {
        return idAp;
    }

    public String getEmployer_id() {
        return employer_id;
    }

    public String getApplicant_id() {
        return applicant_id;
    }

    public String getDiscricption() {
        return discricption;
    }

    public String getContact_info() {
        return contact_info;
    }

    public String getCreated_at() {
        return created_at;
    }

    public String getUpdated_at() {
        return updated_at;
    }

}