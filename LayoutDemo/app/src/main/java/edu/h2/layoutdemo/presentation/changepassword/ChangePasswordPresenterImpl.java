package edu.h2.layoutdemo.presentation.changepassword;

import edu.h2.layoutdemo.domain.interactor.ChangePasswordUseCase;
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
            changePasswordView.showChangePasswordFailure("Invalid information");
        } else if (!newPassword.equals(confirmNewPassword)) {
            changePasswordView.showChangePasswordFailure("Your new password does not match");
        } else {
            DisposableObserver<Boolean> observer = createChangePasswordObserver();
            ChangePasswordUseCase.Params params = changePasswordUseCase.buildParams(driverId, oldPassword, newPassword, confirmNewPassword);
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
                    changePasswordView.showChangePasswordFailure("Please try again!");
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
