package com.goldenictsolutions.win.goldenictjob365.Employer.DataModel;


public class Township_List {


    String name;


    String id;

    public Township_List(String name, String id) {
        this.name = name;
        this.id = id;
    }

    public String getTownshipName() {
        return name;
    }

    public String getId() {
        return id;
    }


}
