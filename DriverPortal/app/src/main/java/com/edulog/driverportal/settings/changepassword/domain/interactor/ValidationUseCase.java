package com.edulog.driverportal.settings.changepassword.domain.interactor;

import com.edulog.driverportal.common.domain.UseCase;
import com.edulog.driverportal.settings.changepassword.domain.exception.ValidationException;
import com.edulog.driverportal.settings.changepassword.presentation.model.ValidationResult;

import java.util.Arrays;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.functions.Consumer;

import static com.edulog.driverportal.settings.changepassword.domain.util.ChangePasswordValidator.validateAll;
import static com.edulog.driverportal.settings.changepassword.domain.util.ChangePasswordValidator.validateDriverId;
import static com.edulog.driverportal.settings.changepassword.domain.util.ChangePasswordValidator.validateNewPassword;
import static com.edulog.driverportal.settings.changepassword.domain.util.ChangePasswordValidator.validateOldPassword;
import static com.edulog.driverportal.settings.changepassword.domain.util.ChangePasswordValidator.validatePasswordConfirmation;

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
