package com.goldenictsolutions.win.goldenictjob365.Employee.Services.Event;


import com.goldenictsolutions.win.goldenictjob365.Employee.Services.ServerResponse;

/**
 * Created by Win on 2/27/2017.
 */

public class ServerEducationEvent {
    private  ServerResponse serverResponse;

    public ServerEducationEvent(ServerResponse serverResponse) {
        this.serverResponse = serverResponse;
    }

    public  ServerResponse getServerResponse() {
        return serverResponse;
    }

    public void setServerResponse(ServerResponse serverResponse) {
        this.serverResponse = serverResponse;
    }
}
