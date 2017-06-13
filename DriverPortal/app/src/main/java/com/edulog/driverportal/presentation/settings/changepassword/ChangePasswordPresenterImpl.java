package com.edulog.driverportal.presentation.settings.changepassword;

import com.edulog.driverportal.domain.interactor.ChangePasswordUseCase;

import io.reactivex.observers.DisposableObserver;

public class ChangePasswordPresenterImpl implements ChangePasswordPresenter {
    private ChangePasswordView changePasswordView;
    private ChangePasswordUseCase changePasswordUseCase;

    public ChangePasswordPresenterImpl(ChangePasswordUseCase changePasswordUseCase) {
        this.changePasswordUseCase = changePasswordUseCase;
    }

    @Override
    public void changePassword(String driverId, String oldPassword, String newPassword, String confirmNewPassword) {
        if (driverId.length() == 0 || oldPassword.length() == 0 || newPassword.length() == 0 || confirmNewPassword.length() == 0) {
            changePasswordView.showError("Invalid information");
        } else if (!newPassword.equals(confirmNewPassword)) {
            changePasswordView.showError("Your new password does not match");
        } else {
            DisposableObserver<Boolean> observer = createChangePasswordObserver();
            ChangePasswordUseCase.Params params = ChangePasswordUseCase.buildParams(driverId, oldPassword, newPassword);
            changePasswordUseCase.execute(observer, params);
        }
    }

    @Override
    public void attach(ChangePasswordView changePasswordView) {
        this.changePasswordView = changePasswordView;
    }

    @Override
    public void detach() {
        changePasswordView = null;
        changePasswordUseCase.dispose();
    }

    @Override
    public void onError(String message) {

    }

    private DisposableObserver<Boolean> createChangePasswordObserver() {
        return new DisposableObserver<Boolean>() {
            @Override
            public void onNext(Boolean isSuccess) {
                if (isSuccess) {
                    changePasswordView.showChangePasswordSuccess("Change password successfully!");
                } else {
                    changePasswordView.showError("Please try again!");
                }
            }

            @Override
            public void onError(Throwable e) {
            }

            @Override
            public void onComplete() {

            }
        };
    }
}
