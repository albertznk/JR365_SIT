package com.goldenictsolutions.win.goldenictjob365.Employee.Services.Event;

import com.goldenictsolutions.win.goldenictjob365.Employee.Services.ServerResponse;

/**
 * Created by Win on 6/13/2017.
 */

public class ServerApplicantEvent {
    private ServerResponse serverResponse;

    public ServerApplicantEvent(ServerResponse serverResponse) {
        this.serverResponse = serverResponse;
    }

    public ServerResponse getServerResponse() {
        return serverResponse;
    }

    public void setServerResponse(ServerResponse serverResponse) {
        this.serverResponse = serverResponse;
    }
}
