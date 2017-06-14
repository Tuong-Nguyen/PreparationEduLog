package com.edulog.driverportal.settings.changepassword.domain.interactor;

import com.edulog.driverportal.settings.changepassword.domain.service.AuthService;

import io.reactivex.Observable;
import io.reactivex.Scheduler;

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
    public Observable<Boolean> buildUseCaseObservable(Params params) {
        String driverId = params.driverId;
        String oldPassword = params.oldPassword;
        String newPassword = params.newPassword;
        return authService.changePassword(driverId, oldPassword, newPassword)
                .map(response -> response.code() == 200);
    }

    public static class Params {
        public String driverId;
        public String oldPassword;
        public String newPassword;
    }
}
