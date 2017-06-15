package com.edulog.driverportal.settings.changepassword.domain.util;

import com.edulog.driverportal.settings.changepassword.model.ValidationResult;
import com.edulog.driverportal.settings.changepassword.model.enums.InputField;

public final class ChangePasswordValidator {
    public static ValidationResult validateDriverId(String driverId) {
        boolean driverIdValid = driverId.length() >= 2;
        ValidationResult validationResult = new ValidationResult(InputField.DRIVER_ID);
        if (!driverIdValid) {
            validationResult.setErrorMessage("Driver id must be at least 2 characters.");
        }
        return validationResult;
    }

    public static ValidationResult validateOldPassword(String oldPassword) {
        boolean oldPasswordValid = oldPassword.length() >= 4;
        ValidationResult validationResult = new ValidationResult(InputField.OLD_PASSWORD);
        if (!oldPasswordValid) {
            validationResult.setErrorMessage("Old password must be at least 4 characters.");
        }
        return validationResult;
    }

    public static ValidationResult validateNewPassword(String newPassword) {
        boolean newPasswordValid = newPassword.length() >= 4;
        ValidationResult validationResult = new ValidationResult(InputField.NEW_PASSWORD);
        if (!newPasswordValid) {
            validationResult.setErrorMessage("New password must be at least 4 characters.");
        }
        return validationResult;
    }

    public static ValidationResult validatePasswordConfirmation(String password, String confirmPassword) {
        boolean passwordMatch = password.equals(confirmPassword);
        ValidationResult validationResult = new ValidationResult(InputField.CONFIRM_NEW_PASSWORD);
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
        ValidationResult validationResult = new ValidationResult(InputField.ALL);
        if (!isValid) {
            validationResult.setErrorMessage("Invalid information.");
        }
        return validationResult;
    }
}
