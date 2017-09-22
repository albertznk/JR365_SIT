package com.goldenictsolutions.win.goldenictjob365.Employer.DataModel;


public class Company_Profile_Data {

    String id, company_name, company_logo, address, township, job_industry,
            city, mobile_no, email, website, description, primary_person, primary_tele, secodary_person, secondary_tele,
            country;

    public Company_Profile_Data(String id, String company_name, String company_logo,
                                String address, String township, String job_industry,
                                String city, String mobile_no, String email, String website,
                                String description, String primary_person, String primary_tele,
                                String secodary_person, String secondary_tele, String country) {
        this.id = id;
        this.company_name = company_name;
        this.company_logo = company_logo;
        this.address = address;
        this.township = township;
        this.job_industry = job_industry;
        this.city = city;
        this.mobile_no = mobile_no;
        this.email = email;
        this.website = website;
        this.description = description;
        this.primary_person = primary_person;
        this.primary_tele = primary_tele;
        this.secodary_person = secodary_person;
        this.secondary_tele = secondary_tele;
        this.country = country;
    }

    public String getId() {
        return id;
    }

    public String getCompany_name() {
        return company_name;
    }

    public String getCompany_logo() {
        return company_logo;
    }

    public String getAddress() {
        return address;
    }

    public String getTownship() {
        return township;
    }

    public String getJob_industry() {
        return job_industry;
    }

    public String getCity() {
        return city;
    }

    public String getMobile_no() {
        return mobile_no;
    }

    public String getEmail() {
        return email;
    }

    public String getWebsite() {
        return website;
    }

    public String getDescription() {
        return description;
    }

    public String getPrimary_person() {
        return primary_person;
    }

    public String getPrimary_tele() {
        return primary_tele;
    }

    public String getSecodary_person() {
        return secodary_person;
    }

    public String getSecondary_tele() {
        return secondary_tele;
    }

    public String getCountry() {
        return country;
    }


}
