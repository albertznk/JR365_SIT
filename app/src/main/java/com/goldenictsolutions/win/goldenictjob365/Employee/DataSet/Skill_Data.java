package com.goldenictsolutions.win.goldenictjob365.Employee.DataSet;

/**
 * Created by Kurio Tetsuya on 6/28/2017.
 */

public class Skill_Data {
    String language_id;
    String language;
    String spoken;
    String written;

    public Skill_Data(String language, String spoken, String written, String language_id) {

        this.language = language;
        this.spoken = spoken;
        this.written = written;
        this.language_id = language_id;
    }


    public String getLanguage() {
        return language;
    }

    public String getLanguage_id() {
        return language_id;
    }

    public String getSpoken() {
        return spoken;
    }

    public String getWritten() {
        return written;
    }
}
