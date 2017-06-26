package com.edulog.driverportal.common.validation.model;

public class ValidationResult {
    private boolean isValid;
    private String field;
    private java.lang.String errorMessage;

    public ValidationResult(String field) {
        this.field = field;
        isValid = true;
        errorMessage = null;
    }

    public String getField() {
        return field;
    }

    public boolean isValid() {
        return isValid;
    }

    public java.lang.String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(java.lang.String errorMessage) {
        this.errorMessage = errorMessage;
        isValid = false;
    }
}
