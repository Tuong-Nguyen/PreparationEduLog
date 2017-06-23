package com.edulog.driverportal.login.domain.interactors;

import com.edulog.driverportal.common.validation.model.ValidationResult;

/**
 * Created by ntmhanh on 6/20/2017.
 */

// TODO: Rename as LoginValidator and move it into interactors package
public final class LoginValidator {
    public static ValidationResult validateBusId(java.lang.String busId) {
        boolean driverIdValid = busId.length() >= 2;
        ValidationResult validationResult = new ValidationResult("BUS_ID");
        if (!driverIdValid) {
            validationResult.setErrorMessage("Driver id must be at least 2 characters.");
        }
        return validationResult;
    }
    public static ValidationResult validateDriverId(java.lang.String driverId) {
        boolean driverIdValid = driverId.length() >= 2;
        ValidationResult validationResult = new ValidationResult("DRIVER_ID");
        if (!driverIdValid) {
            validationResult.setErrorMessage("Driver id must be at least 2 characters.");
        }
        return validationResult;
    }

    public static ValidationResult validatePassword(java.lang.String password) {
        boolean oldPasswordValid = password.length() >= 4;
        ValidationResult validationResult = new ValidationResult("PASSWORD");
        if (!oldPasswordValid) {
            validationResult.setErrorMessage("Old password must be at least 4 characters.");
        }
        return validationResult;
    }


    public static ValidationResult validateAll(ValidationResult... validationResults) {
        boolean isValid = true;
        for (ValidationResult result : validationResults) {
            isValid = isValid && result.isValid();
        }
        ValidationResult validationResult = new ValidationResult("ALL");
        if (!isValid) {
            validationResult.setErrorMessage("Invalid information.");
        }
        return validationResult;
    }
}
