package com.goldenictsolutions.win.goldenictjob365.Employee.Services.Event;

/**
 * Created by kurio on 8/14/17.
 */

public class Update_Language_Error_Event {
    private int errorCode;
    private String errorMsg;

    public Update_Language_Error_Event(int errorCode, String errorMsg) {
        this.errorCode = errorCode;
        this.errorMsg = errorMsg;
    }

    public int getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(int errorCode) {
        this.errorCode = errorCode;
    }

    public String getErrorMsg() {
        return errorMsg;
    }

    public void setErrorMsg(String errorMsg) {
        this.errorMsg = errorMsg;
    }
}
