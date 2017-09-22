package com.goldenictsolutions.win.goldenictjob365.Employee.PojoJavaClasses;

/**
 * Created by Win on 5/27/2017.
 */

public class SignUp {
    private String telephone_no;
    private String password;
    private int user_type;
    private String login_name;
    private String email;

    public SignUp(String telephone_no, String password, int user_type, String login_name, String email) {
        this.telephone_no = telephone_no;
        this.password = password;
        this.user_type = user_type;
        this.login_name = login_name;
        this.email = email;
    }
}
