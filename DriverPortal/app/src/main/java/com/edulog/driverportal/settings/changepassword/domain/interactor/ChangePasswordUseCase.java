package com.edulog.driverportal.settings.changepassword.domain.interactor;

import com.edulog.driverportal.common.domain.UseCase;
import com.edulog.driverportal.settings.changepassword.domain.service.AuthService;

import io.reactivex.Observable;

public class ChangePasswordUseCase extends UseCase<Boolean, ChangePasswordUseCase.Params> {
    private AuthService authService;
    private ValidationUseCase validationUseCase;

    public ChangePasswordUseCase(AuthService authService, ValidationUseCase validationUseCase) {
        this.authService = authService;
        this.validationUseCase = validationUseCase;
    }

    public static Params buildParams(String driverId, String oldPassword, String newPassword) {
        Params params = new Params();
        params.driverId = driverId;
        params.oldPassword = oldPassword;
        params.newPassword = newPassword;
        return params;
    }

    @Override
    public Observable<Boolean> buildUseCaseObservable(Params params) {
        String driverId = params.driverId;
        String oldPassword = params.oldPassword;
        String newPassword = params.newPassword;

        return validationUseCase
                .buildUseCaseObservable(ValidationUseCase.buildParams(driverId, oldPassword, newPassword, newPassword))
                .zipWith(createRequestChangePasswordObservable(driverId, oldPassword, newPassword),
                        ((validationResults, isSuccess) -> isSuccess));
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
