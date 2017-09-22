package com.goldenictsolutions.win.goldenictjob365.Employee.Services.Event;

import com.goldenictsolutions.win.goldenictjob365.Employee.Services.ServerResponse;

/**
 * Created by kurio on 8/10/17.
 */

public class ServerUploadCompanyLogoEvent {
    private ServerResponse serverResponse;

    public ServerUploadCompanyLogoEvent(ServerResponse serverResponse) {
        this.serverResponse = serverResponse;
    }

    public  ServerResponse getServerResponse() {
        return serverResponse;
    }

    public void setServerResponse(ServerResponse serverResponse) {
        this.serverResponse = serverResponse;
    }
}
