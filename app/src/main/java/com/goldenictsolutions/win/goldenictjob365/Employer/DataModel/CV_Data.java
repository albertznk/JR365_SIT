package com.goldenictsolutions.win.goldenictjob365.Employer.DataModel;

/**
 * Created by Kyrie on 6/22/2017.
 */

public class CV_Data {
    String name, father_name, matrtal_status, gender, expected_salary, date_of_birth, nrc_no, religion, place_of_birth, mobile_no, email, address, township, nationality, city, country_id, current_position, desired_position, driving_license, created_at, updated_at, photo, attach_cv, counrty;
    String app_id;

    public CV_Data(String name, String father_name, String marital_status, String gender,
                   String expected_salary, String date_of_birth, String nrc_no, String religion, String place_of_birth,
                   String mobile_no, String email, String address, String township, String nationality,
                   String city, String country_id, String current_position, String desired_position, String driving_license,
                   String created_at, String updated_at, String photo, String attach_cv, String country, String app_id) {
        this.name = name;
        this.father_name = father_name;
        this.matrtal_status = marital_status;
        this.gender = gender;
        this.expected_salary = expected_salary;
        this.date_of_birth = date_of_birth;
        this.nrc_no = nrc_no;
        this.religion = religion;
        this.place_of_birth = place_of_birth;
        this.mobile_no = mobile_no;
        this.email = email;
        this.address = address;
        this.township = township;
        this.nationality = nationality;
        this.city = city;
        this.country_id = country_id;
        this.current_position = current_position;
        this.desired_position = desired_position;
        this.driving_license = driving_license;
        this.created_at = created_at;
        this.updated_at = updated_at;
        this.photo = photo;
        this.app_id = app_id;
        this.attach_cv = attach_cv;
        this.counrty = country;
    }

    public String getApp_id() {
        return app_id;
    }

    public String getName() {
        return name;
    }

    public String getFather_name() {
        return father_name;
    }

    public String getMatrtal_status() {
        return matrtal_status;
    }

    public String getGender() {
        return gender;
    }

    public String getExpected_salary() {
        return expected_salary;
    }

    public String getDate_of_birth() {
        return date_of_birth;
    }

    public String getNrc_no() {
        return nrc_no;
    }

    public String getReligion() {
        return religion;
    }

    public String getPlace_of_birth() {
        return place_of_birth;
    }

    public String getMobile_no() {
        return mobile_no;
    }

    public String getEmail() {
        return email;
    }

    public String getAddress() {
        return address;
    }

    public String getTownship() {
        return township;
    }


    public String getNationality() {
        return nationality;
    }

    public String getCity() {
        return city;
    }

    public String getCountry_id() {
        return country_id;
    }

    public String getCurrent_position() {
        return current_position;
    }

    public String getDesired_position() {
        return desired_position;
    }

    public String getDriving_license() {
        return driving_license;
    }

    public String getCreated_at() {
        return created_at;
    }

    public String getUpdated_at() {
        return updated_at;
    }

    public String getPhoto() {
        return photo;
    }

    public String getAttach_cv() {
        return attach_cv;
    }

    public String getCounrty() {
        return counrty;
    }


}