package com.goldenictsolutions.win.goldenictjob365.Employee.DataSet;

/**
 * Created by Kurio Tetsuya on 6/29/2017.
 */

public class Jobseeker {
    String UserName, Father_Name,Gender, NRC, mobile_no,email, cv_form;

    public Jobseeker(String userName, String father_Name, String gender, String NRC, String mobile_no, String email, String cv_form) {
        UserName = userName;
        Father_Name = father_Name;
        Gender = gender;
        this.NRC = NRC;
        this.mobile_no = mobile_no;
        this.email = email;
        this.cv_form = cv_form;
    }



    public String getUserName() {
        return UserName;
    }

    public String getFather_Name() {
        return Father_Name;
    }

    public String getGender() {
        return Gender;
    }

    public String getNRC() {
        return NRC;
    }

    public String getMobile_no() {
        return mobile_no;
    }

    public String getEmail() {
        return email;
    }

    public String getCv_form() {
        return cv_form;
    }
}