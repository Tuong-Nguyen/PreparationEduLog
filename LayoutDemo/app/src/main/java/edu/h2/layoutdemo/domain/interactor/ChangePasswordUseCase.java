package edu.h2.layoutdemo.domain.interactor;

import edu.h2.layoutdemo.data.service.ChangePasswordServiceImpl;
import edu.h2.layoutdemo.domain.service.ChangePasswordService;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.observers.DisposableObserver;

public class ChangePasswordUseCase extends UseCase<Boolean, ChangePasswordUseCase.Params> {
    private ChangePasswordService changePasswordService;
    private CompositeDisposable disposables;

    public ChangePasswordUseCase(ChangePasswordService changePasswordService) {
        this.changePasswordService = changePasswordService;
        disposables = new CompositeDisposable();
    }

    @Override
    public void execute(DisposableObserver<Boolean> observer, Params params) {
        String driverId = params.driverId;
        String oldPassword = params.oldPassword;
        String newPassword = params.newPassword;
        String confirmNewPassword = params.confirmNewPassword;
        Disposable disposable = changePasswordService.requestChangePassword(driverId, oldPassword, newPassword, confirmNewPassword)
                .subscribeWith(observer);
        disposables.add(disposable);
    }

    public Params buildParams(String driverId, String oldPassword, String newPassword, String confirmNewPassword) {
        Params params = new Params();
        params.driverId = driverId;
        params.oldPassword = oldPassword;
        params.newPassword = newPassword;
        params.confirmNewPassword = confirmNewPassword;
        return params;
    }

    public static class Params {
        public String driverId;
        public String oldPassword;
        public String newPassword;
        public String confirmNewPassword;
    }

    public void dispose() {
        disposables.dispose();
    }
}
