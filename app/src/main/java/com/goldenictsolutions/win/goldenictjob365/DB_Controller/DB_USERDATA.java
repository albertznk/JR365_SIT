package com.goldenictsolutions.win.goldenictjob365.DB_Controller;

/**
 * Created by Kurio Tetsuya on 6/25/2017.
 */

public class DB_USERDATA {
    String user_id, user_name, user_password;





    public String getUser_id() {
        return user_id;
    }

    public String getUser_name() {
        return user_name;
    }

    public String getUser_password() {
        return user_password;
    }

    public DB_USERDATA(String user_id) {
        this.user_id = user_id;
    }

    public DB_USERDATA(String user_id, String user_name, String user_password) {
        this.user_id = user_id;
        this.user_name = user_name;
        this.user_password = user_password;
    }

}
