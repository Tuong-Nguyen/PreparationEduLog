package com.edulog.driverportal.settings.changepassword.domain.interactor;

import com.edulog.driverportal.common.domain.UseCase;
import com.edulog.driverportal.settings.changepassword.domain.service.AuthService;
import com.edulog.driverportal.settings.changepassword.model.ValidationResult;

import io.reactivex.Observable;
import io.reactivex.Scheduler;

import static com.edulog.driverportal.settings.changepassword.domain.util.ChangePasswordValidator.validateAll;
import static com.edulog.driverportal.settings.changepassword.domain.util.ChangePasswordValidator.validateDriverId;
import static com.edulog.driverportal.settings.changepassword.domain.util.ChangePasswordValidator.validateNewPassword;
import static com.edulog.driverportal.settings.changepassword.domain.util.ChangePasswordValidator.validateOldPassword;

public class ChangePasswordUseCase extends UseCase<Boolean, ChangePasswordUseCase.Params> {
    private AuthService authService;

    public ChangePasswordUseCase(Scheduler postExecutionScheduler, AuthService authService) {
        super(postExecutionScheduler);
        this.authService = authService;
    }

    public static Params buildParams(String driverId, String oldPassword, String newPassword) {
        Params params = new Params();
        params.driverId = driverId;
        params.oldPassword = oldPassword;
        params.newPassword = newPassword;
        return params;
    }

    @Override
    protected Observable<Boolean> buildUseCaseObservable(Params params) {
        String driverId = params.driverId;
        String oldPassword = params.oldPassword;
        String newPassword = params.newPassword;

        ValidationResult driverIdResult = validateDriverId(driverId);
        ValidationResult oldPasswordResult = validateOldPassword(oldPassword);
        ValidationResult newPasswordResult = validateNewPassword(newPassword);
        ValidationResult allResult = validateAll(driverIdResult, oldPasswordResult, newPasswordResult);

        return Observable.just(allResult.isValid())
                .doOnNext(isValid -> {
                    if (!isValid) throw new RuntimeException("Invalid information.");
                })
                .skip(1)
                .mergeWith(createRequestChangePasswordObservable(driverId, oldPassword, newPassword));
    }

    private Observable<Boolean> createRequestChangePasswordObservable(String driverId, String oldPassword, String newPassword) {
        return authService.changePassword(driverId, oldPassword, newPassword)
                .doOnNext(isSuccess -> {
                    if (!isSuccess) throw new RuntimeException("Change password error.");
                });
    }

    public static class Params {
        public String driverId;
        public String oldPassword;
        public String newPassword;
    }
}
