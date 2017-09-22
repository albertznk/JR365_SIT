package com.goldenictsolutions.win.goldenictjob365.Employee.Services.Event;

/**
 * Created by Win on 24.07.2017.
 */

public class ErrorUploadPhotoEvent {
    private int errorCode;
    private String errorMsg;

    public ErrorUploadPhotoEvent(int errorCode, String errorMsg) {
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
}
