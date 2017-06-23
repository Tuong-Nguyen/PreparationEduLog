package com.edulog.driverportal.common.validation.exception;

import com.edulog.driverportal.common.validation.model.ValidationResult;

import java.util.List;

public class ValidationException extends RuntimeException {
    private List<ValidationResult> validationResults;

    public ValidationException(List<ValidationResult> validationResults) {
        this.validationResults = validationResults;
    }

    public List<ValidationResult> getValidationResults() {
        return validationResults;
    }
}
