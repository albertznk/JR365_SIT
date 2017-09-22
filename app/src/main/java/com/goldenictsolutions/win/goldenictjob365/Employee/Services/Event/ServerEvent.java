package com.goldenictsolutions.win.goldenictjob365.Employee.Services.Event;


import com.goldenictsolutions.win.goldenictjob365.Employee.Services.ServerResponse;

public class ServerEvent {
    private ServerResponse serverResponse;

    public ServerEvent(ServerResponse serverResponse) {
        this.serverResponse = serverResponse;
    }
    public ServerResponse getServerResponse() {
        return serverResponse;
    }

    public void setServerResponse(ServerResponse serverResponse) {
        this.serverResponse = serverResponse;
    }


}
