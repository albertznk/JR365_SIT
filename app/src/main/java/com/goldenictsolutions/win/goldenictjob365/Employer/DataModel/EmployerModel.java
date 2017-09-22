package com.goldenictsolutions.win.goldenictjob365.Employer.DataModel;

/**
 * Created by nuke on 8/2/17.
 */

public class EmployerModel {

    public static class companyListModel {
        String compId;
        String compName;


        String type;

        public companyListModel(String company_name, String id, String type) {
            this.compName = company_name;
            this.compId = id;
            this.type = type;
        }

        public String getCompId() {
            return compId;
        }

        public String getType() {
            return type;
        }

        public String getCompName() {
            return compName;
        }
    }


    public static class OpenJobModel {
        public String job_count;
        public String job_title;
        public String id;
        public String job_id;


        public OpenJobModel(String job_count, String job_title, String id, String job_id) {
            this.job_count = job_count;
            this.job_title = job_title;
            this.id = id;
            this.job_id = job_id;
        }

        public String getJob_count() {
            return job_count;
        }

        public String getJob_title() {
            return job_title;
        }

        public String getId() {
            return id;
        }


        public String getJob_id() {
            return job_id;
        }
    }

    public static class Search_Applicant_Data {
        String id;
        String user_id;
        String name;
        String father_name;
        String marital_status;
        String gender;
        String expected_salary;
        String date_of_birth;
        String nrc_no;
        String religion;
        String place_of_birth;
        String photo;
        String mobile_no;
        String email;
        String address;
        String nationality;
        String cv_views;
        String attach_cv;
        String current_position;
        String desired_position;
        String driving_license;
        String city;
        String country;
        String township;

        public Search_Applicant_Data(String id, String user_id, String name, String father_name, String marital_status, String gender,
                                     String expected_salary, String date_of_birth, String nrc_no, String religion,
                                     String place_of_birth, String photo, String mobile_no, String email,
                                     String address, String nationality, String cv_views, String attach_cv,
                                     String current_position, String desired_position, String driving_license, String city,
                                     String country, String township) {
            this.gender = gender;
            this.id = id;
            this.user_id = user_id;
            this.name = name;
            this.father_name = father_name;
            this.marital_status = marital_status;
            this.expected_salary = expected_salary;
            this.date_of_birth = date_of_birth;
            this.nrc_no = nrc_no;
            this.religion = religion;
            this.place_of_birth = place_of_birth;
            this.photo = photo;
            this.mobile_no = mobile_no;
            this.email = email;
            this.address = address;
            this.nationality = nationality;
            this.cv_views = cv_views;
            this.attach_cv = attach_cv;
            this.current_position = current_position;
            this.desired_position = desired_position;
            this.driving_license = driving_license;
            this.city = city;
            this.country = country;
            this.township = township;
        }

        public String getGender() {
            return gender;
        }

        public String getId() {
            return id;
        }

        public String getUser_id() {
            return user_id;
        }

        public String getName() {
            return name;
        }

        public String getFather_name() {
            return father_name;
        }

        public String getMarital_status() {
            return marital_status;
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

        public String getPhoto() {
            return photo;
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

        public String getNationality() {
            return nationality;
        }

        public String getCv_views() {
            return cv_views;
        }

        public String getAttach_cv() {
            return attach_cv;
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

        public String getCity() {
            return city;
        }

        public String getCountry() {
            return country;
        }

        public String getTownship() {
            return township;
        }
    }

    public static class CompanyInfo {
        String CompanyName, CompanyLogo, CompanyAddress, CompanyMobile, Companyemail, CompanySebsite,
                AboutCompany, PcontactP, PcontactM, ScontactP, ScontactM, CompanyCity, CompanyTownship,
                CompanyCounty, CompanyType;

        public CompanyInfo(String companyName, String companyLogo, String companyAddress, String companyMobile, String companyemail, String companySebsite, String aboutCompany, String pcontactP, String pcontactM, String scontactP, String scontactM, String companyCity, String companyTownship, String companyCounty, String CompanyType) {
            CompanyName = companyName;
            CompanyLogo = companyLogo;
            CompanyAddress = companyAddress;
            CompanyMobile = companyMobile;
            Companyemail = companyemail;
            CompanySebsite = companySebsite;
            AboutCompany = aboutCompany;
            PcontactP = pcontactP;
            PcontactM = pcontactM;
            ScontactP = scontactP;
            ScontactM = scontactM;
            CompanyCity = companyCity;
            CompanyTownship = companyTownship;
            CompanyCounty = companyCounty;
            this.CompanyType = CompanyType;
        }

        public String getCompanyType() {
            return CompanyType;
        }

        public String getCompanyName() {
            return CompanyName;
        }

        public String getCompanyLogo() {
            return CompanyLogo;
        }

        public String getCompanyAddress() {
            return CompanyAddress;
        }

        public String getCompanyMobile() {
            return CompanyMobile;
        }

        public String getCompanyemail() {
            return Companyemail;
        }

        public String getCompanySebsite() {
            return CompanySebsite;
        }

        public String getAboutCompany() {
            return AboutCompany;
        }

        public String getPcontactP() {
            return PcontactP;
        }

        public String getPcontactM() {
            return PcontactM;
        }

        public String getScontactP() {
            return ScontactP;
        }

        public String getScontactM() {
            return ScontactM;
        }

        public String getCompanyCity() {
            return CompanyCity;
        }

        public String getCompanyTownship() {
            return CompanyTownship;
        }

        public String getCompanyCounty() {
            return CompanyCounty;
        }
    }

    public static class CandidateListModel {
        String user_id, name, email, mobile_no, photo;
        private String id;

        public CandidateListModel(String id, String user_id, String name, String email, String mobile_no, String photo) {
            this.id = id;
            this.user_id = user_id;
            this.name = name;
            this.email = email;
            this.mobile_no = mobile_no;
            this.photo = photo;
        }

        public String getId() {
            return id;
        }

        public String getUser_id() {
            return user_id;
        }

        public String getName() {
            return name;
        }

        public String getEmail() {
            return email;
        }

        public String getMobile_no() {
            return mobile_no;
        }

        public String getPhoto() {
            return photo;
        }
    }

    public static class JobTypeModel {
        private String type;
        private String id;

        public JobTypeModel(String id, String type) {
            this.id = id;
            this.type = type;
        }

        public String getType() {
            return type;
        }

        public String getId() {
            return id;
        }
    }

    public static class JobCategoryModel {
        String id, category;

        public JobCategoryModel(String id, String category) {
            this.id = id;
            this.category = category;
        }

        public String getId() {
            return id;
        }

        public String getCategory() {
            return category;
        }
    }

    public static class ShortlistModel {
        private String job_count;
        private String job_title;
        private String id;

        public ShortlistModel(String job_count, String job_title, String id) {
            this.job_count = job_count;
            this.job_title = job_title;
            this.id = id;
        }

        public String getJob_count() {
            return job_count;
        }

        public String getJob_title() {
            return job_title;
        }

        public String getId() {
            return id;
        }
    }

    public static class ShortListedModel {
        private String company_name;
        private String id;

        public ShortListedModel(String company_name, String id) {
            this.company_name = company_name;
            this.id = id;
        }

        public String getCompany_name() {
            return company_name;
        }

        public String getId() {
            return id;
        }
    }
}
