package com.edulog.driverportal.settings.changepassword.presentation.model;

public class ValidationResult {
    private boolean isValid;
    private InputField field;
    private String errorMessage;

    public ValidationResult(InputField field) {
        this.field = field;
        isValid = true;
        errorMessage = null;
    }

    public InputField getField() {
        return field;
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
