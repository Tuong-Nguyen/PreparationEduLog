package com.edulog.driverportal.login.models;

public class LoginValidation {
    private boolean isValid;
    private String errorMessage;

    public LoginValidation() {
        isValid = true;
        errorMessage = null;
    }

    public boolean isValid() {
        return isValid;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
        isValid = false;
    }
}
