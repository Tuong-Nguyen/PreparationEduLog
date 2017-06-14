package com.edulog.driverportal.settings.changepassword.domain.interactor;

import com.edulog.driverportal.settings.changepassword.model.ValidationResult;
import com.edulog.driverportal.settings.changepassword.model.enums.InputField;

import io.reactivex.Observable;
import io.reactivex.Scheduler;

public class ValidationUseCase extends UseCase<ValidationResult, ValidationUseCase.Params> {
    public ValidationUseCase(Scheduler postExecutionScheduler) {
        super(postExecutionScheduler);
    }

    public static Params buildParams(String driverId, String oldPassword, String newPassword, String confirmNewPassword) {
        Params params = new Params();

        params.driverId = driverId;
        params.oldPassword = oldPassword;
        params.newPassword = newPassword;
        params.confirmNewPassword = confirmNewPassword;

        return params;
    }

    @Override
    public Observable<ValidationResult> buildUseCaseObservable(Params params) {
        ValidationResult driverIdResult = validateDriverId(params.driverId);
        ValidationResult oldPasswordResult = validateOldPassword(params.oldPassword);
        ValidationResult newPasswordResult = validateNewPassword(params.newPassword);
        ValidationResult confirmPasswordResult = validatePasswordConfirmation(params.newPassword, params.confirmNewPassword);
        ValidationResult allResult = validateAll(driverIdResult, oldPasswordResult, newPasswordResult, confirmPasswordResult);
        return Observable.fromArray(driverIdResult, oldPasswordResult, newPasswordResult, confirmPasswordResult, allResult);
    }

    private ValidationResult validateDriverId(String driverId) {
        boolean driverIdValid = driverId.length() >= 2;
        ValidationResult validationResult = new ValidationResult(InputField.DRIVER_ID);
        if (!driverIdValid) {
            validationResult.setErrorMessage("Driver id must be at least 2 characters.");
        }
        return validationResult;
    }

    private ValidationResult validateOldPassword(String oldPassword) {
        boolean oldPasswordValid = oldPassword.length() >= 4;
        ValidationResult validationResult = new ValidationResult(InputField.OLD_PASSWORD);
        if (!oldPasswordValid) {
            validationResult.setErrorMessage("Old password must be at least 4 characters.");
        }
        return validationResult;
    }

    private ValidationResult validateNewPassword(String newPassword) {
        boolean newPasswordValid = newPassword.length() >= 4;
        ValidationResult validationResult = new ValidationResult(InputField.NEW_PASSWORD);
        if (!newPasswordValid) {
            validationResult.setErrorMessage("New password must be at least 4 characters.");
        }
        return validationResult;
    }

    private ValidationResult validatePasswordConfirmation(String password, String confirmPassword) {
        boolean passwordMatch = password.equals(confirmPassword);
        ValidationResult validationResult = new ValidationResult(InputField.CONFIRM_NEW_PASSWORD);
        if (!passwordMatch) {
            validationResult.setErrorMessage("Your password does not match.");
        }
        return validationResult;
    }

    private ValidationResult validateAll(ValidationResult... validationResults) {
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

    public static class Params {
        public String driverId;
        public String oldPassword;
        public String newPassword;
        public String confirmNewPassword;
    }
}
