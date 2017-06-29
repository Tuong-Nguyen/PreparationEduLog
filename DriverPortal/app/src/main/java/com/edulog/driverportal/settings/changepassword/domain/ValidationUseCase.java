package com.edulog.driverportal.settings.changepassword.domain;

import com.edulog.driverportal.common.domain.UseCase;
import com.edulog.driverportal.settings.changepassword.model.ValidationResult;

import java.util.Arrays;
import java.util.List;

import io.reactivex.Observable;

import static com.edulog.driverportal.settings.changepassword.domain.ChangePasswordValidator.validateAll;
import static com.edulog.driverportal.settings.changepassword.domain.ChangePasswordValidator.validateDriverId;
import static com.edulog.driverportal.settings.changepassword.domain.ChangePasswordValidator.validateNewPassword;
import static com.edulog.driverportal.settings.changepassword.domain.ChangePasswordValidator.validateOldPassword;
import static com.edulog.driverportal.settings.changepassword.domain.ChangePasswordValidator.validatePasswordConfirmation;

public class ValidationUseCase extends UseCase<List<ValidationResult>, ValidationUseCase.Params> {
    public static Params buildParams(String driverId, String oldPassword, String newPassword, String confirmNewPassword) {
        Params params = new Params();

        params.driverId = driverId;
        params.oldPassword = oldPassword;
        params.newPassword = newPassword;
        params.confirmNewPassword = confirmNewPassword;

        return params;
    }

    @Override
    public Observable<List<ValidationResult>> buildUseCaseObservable(Params params) {
        ValidationResult driverIdResult = validateDriverId(params.driverId);
        ValidationResult oldPasswordResult = validateOldPassword(params.oldPassword);
        ValidationResult newPasswordResult = validateNewPassword(params.newPassword);
        ValidationResult confirmPasswordResult = validatePasswordConfirmation(params.newPassword, params.confirmNewPassword);
        ValidationResult allResult = validateAll(driverIdResult, oldPasswordResult, newPasswordResult, confirmPasswordResult);

        List<ValidationResult> validationResults = Arrays.asList(driverIdResult, oldPasswordResult, newPasswordResult, confirmPasswordResult);
        return Observable.just(validationResults)
                .doOnNext(results -> {
                    if (!allResult.isValid())
                        throw new ValidationException(results);
                });
    }

    public static class Params {
        public String driverId;
        public String oldPassword;
        public String newPassword;
        public String confirmNewPassword;
    }
}
