package com.edulog.driverportal.domain.interactor;

import com.edulog.driverportal.domain.service.ChangePasswordService;

import io.reactivex.Observable;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.observers.DisposableObserver;

public class ChangePasswordUseCase extends UseCase<Boolean, ChangePasswordUseCase.Params> {
    private ChangePasswordService changePasswordService;

    public ChangePasswordUseCase(ChangePasswordService changePasswordService) {
        this.changePasswordService = changePasswordService;
    }

    @Override
    public Observable<Boolean> buildUseCaseObservable(Params params) {
        String driverId = params.driverId;
        String oldPassword = params.oldPassword;
        String newPassword = params.newPassword;
        return changePasswordService.changePassword(driverId, oldPassword, newPassword);
    }

    public static Params buildParams(String driverId, String oldPassword, String newPassword) {
        Params params = new Params();
        params.driverId = driverId;
        params.oldPassword = oldPassword;
        params.newPassword = newPassword;
        return params;
    }

    public static class Params {
        public String driverId;
        public String oldPassword;
        public String newPassword;
    }
}
