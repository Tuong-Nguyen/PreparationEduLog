package com.edulog.driverportal.presentation.settings.changepassword;

import com.edulog.driverportal.domain.interactor.ChangePasswordUseCase;

import io.reactivex.observers.DisposableObserver;
import okhttp3.ResponseBody;

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
            DisposableObserver<ResponseBody> observer = createChangePasswordObserver();
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
        // on error
    }

    private DisposableObserver<ResponseBody> createChangePasswordObserver() {
        return new DisposableObserver<ResponseBody>() {
            @Override
            public void onNext(ResponseBody response) {
                changePasswordView.showChangePasswordSuccess("Change password successfully!");
            }

            @Override
            public void onError(Throwable e) {
                changePasswordView.showError(e.getMessage());
            }

            @Override
            public void onComplete() {
                // on complete
            }
        };
    }
}
