package com.edulog.driverportal.settings.changepassword.domain.util;

import com.edulog.driverportal.common.validation.model.ValidationResult;

public final class ChangePasswordValidator {
    public static ValidationResult validateDriverId(java.lang.String driverId) {
        boolean driverIdValid = driverId.length() >= 2;
        ValidationResult validationResult = new ValidationResult("DRIVER_ID");
        if (!driverIdValid) {
            validationResult.setErrorMessage("Driver id must be at least 2 characters.");
        }
        return validationResult;
    }

    public static ValidationResult validateOldPassword(java.lang.String oldPassword) {
        boolean oldPasswordValid = oldPassword.length() >= 4;
        ValidationResult validationResult = new ValidationResult("OLD_PASSWORD");
        if (!oldPasswordValid) {
            validationResult.setErrorMessage("Old password must be at least 4 characters.");
        }
        return validationResult;
    }

    public static ValidationResult validateNewPassword(java.lang.String newPassword) {
        boolean newPasswordValid = newPassword.length() >= 4;
        ValidationResult validationResult = new ValidationResult("NEW_PASSWORD");
        if (!newPasswordValid) {
            validationResult.setErrorMessage("New password must be at least 4 characters.");
        }
        return validationResult;
    }

    public static ValidationResult validatePasswordConfirmation(java.lang.String password, java.lang.String confirmPassword) {
        boolean passwordMatch = password.equals(confirmPassword);
        ValidationResult validationResult = new ValidationResult("CONFIRM_NEW_PASSWORD");
        if (!passwordMatch) {
            validationResult.setErrorMessage("Your password does not match.");
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
