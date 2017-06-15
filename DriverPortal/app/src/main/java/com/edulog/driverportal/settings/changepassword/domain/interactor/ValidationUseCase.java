package com.edulog.driverportal.settings.changepassword.domain.interactor;

import com.edulog.driverportal.base.UseCase;
import com.edulog.driverportal.settings.changepassword.domain.util.ChangePasswordValidator;
import com.edulog.driverportal.settings.changepassword.model.ValidationResult;
import com.edulog.driverportal.settings.changepassword.model.enums.InputField;

import io.reactivex.Observable;
import io.reactivex.Scheduler;

import static com.edulog.driverportal.settings.changepassword.domain.util.ChangePasswordValidator.validateAll;
import static com.edulog.driverportal.settings.changepassword.domain.util.ChangePasswordValidator.validateDriverId;
import static com.edulog.driverportal.settings.changepassword.domain.util.ChangePasswordValidator.validateNewPassword;
import static com.edulog.driverportal.settings.changepassword.domain.util.ChangePasswordValidator.validateOldPassword;
import static com.edulog.driverportal.settings.changepassword.domain.util.ChangePasswordValidator.validatePasswordConfirmation;

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

    public static class Params {
        public String driverId;
        public String oldPassword;
        public String newPassword;
        public String confirmNewPassword;
    }
}
