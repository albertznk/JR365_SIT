package com.goldenictsolutions.win.goldenictjob365.Employee.Services.Event;

import com.goldenictsolutions.win.goldenictjob365.Employee.Services.ServerResponse;

/**
 * Created by kurio on 8/4/17.
 */

public class UpdateServerQuaEvent {
    private ServerResponse serverResponse;

    public UpdateServerQuaEvent(ServerResponse serverResponse) {
        this.serverResponse = serverResponse;
    }

    public  ServerResponse getServerResponse() {
        return serverResponse;
    }

    public void setServerResponse(ServerResponse serverResponse) {
        this.serverResponse = serverResponse;
    }
}
