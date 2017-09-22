package com.goldenictsolutions.win.goldenictjob365.Employee.Services.Event;


import com.goldenictsolutions.win.goldenictjob365.Employee.Services.ServerResponse;

/**
 * Created by Win on 3/1/2017.
 */

public class ServerCountryEvent {
    private ServerResponse serverResponse;

    public ServerCountryEvent(ServerResponse serverResponse) {
        this.serverResponse = serverResponse;
    }

    public ServerResponse getServerResponse() {
        return serverResponse;
    }

    public void setServerResponse(ServerResponse serverResponse) {
        this.serverResponse = serverResponse;
    }
}
