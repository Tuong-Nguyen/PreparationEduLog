package com.edulog.driverportal.settings.changepassword.presentation;

import com.edulog.driverportal.settings.changepassword.domain.interactor.ChangePasswordUseCase;

import io.reactivex.observers.DisposableObserver;

public class ChangePasswordPresenterImpl implements ChangePasswordPresenter {
    private ChangePasswordView changePasswordView;
    private ChangePasswordUseCase changePasswordUseCase;

    public ChangePasswordPresenterImpl(ChangePasswordUseCase changePasswordUseCase) {
        this.changePasswordUseCase = changePasswordUseCase;
    }

    @Override
    public void changePassword(String driverId, String oldPassword, String newPassword) {
        DisposableObserver<Boolean> observer = createChangePasswordObserver();
        ChangePasswordUseCase.Params params = ChangePasswordUseCase.buildParams(driverId, oldPassword, newPassword);
        changePasswordUseCase.execute(observer, params);

        changePasswordView.showProgress();
        changePasswordView.disableRequestChangePassword();
    }

    @Override
    public boolean validateUserInput(String driverId, String oldPassword, String newPassword, String confirmNewPassword) {
        boolean driverIdValid = checkDriverIdValid(driverId);
        boolean oldPasswordValid = checkOldPasswordValid(oldPassword);
        boolean newPasswordValid = checkNewPasswordValid(newPassword);
        boolean confirmNewPasswordValid = checkConfirmNewPasswordValid(newPassword, confirmNewPassword);

        return driverIdValid && oldPasswordValid && newPasswordValid && confirmNewPasswordValid;
    }

    @Override
    public void validateUserInput(boolean isValid) {
        if (isValid) {
            changePasswordView.enableRequestChangePassword();
        } else {
            changePasswordView.disableRequestChangePassword();
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

    private DisposableObserver<Boolean> createChangePasswordObserver() {
        return new DisposableObserver<Boolean>() {
            @Override
            public void onNext(Boolean isValid) {
                if (isValid) {
                    changePasswordView.showChangePasswordSuccess("Change password successfully!");
                } else {
                    changePasswordView.showError("Change password failed.");
                }
            }

            @Override
            public void onError(Throwable e) {
                onChangePasswordError(e.getMessage());
            }

            @Override
            public void onComplete() {
                changePasswordView.hideProgress();
                changePasswordView.enableRequestChangePassword();
            }
        };
    }

    private boolean checkDriverIdValid(String driverId) {
        boolean driverIdValid = driverId.length() >= 2;
        if (!driverIdValid) {
            changePasswordView.showInvalidDriverId("Driver id must be at least 2 characters.");
        } else {
            changePasswordView.hideInvalidDriverId();
        }
        return driverIdValid;
    }

    private boolean checkOldPasswordValid(String oldPassword) {
        boolean oldPasswordValid = oldPassword.length() >= 4;
        if (!oldPasswordValid) {
            changePasswordView.showInvalidOldPassword("Password must be at least 4 characters");
        } else {
            changePasswordView.hideInvalidOldPassword();
        }
        return oldPasswordValid;
    }

    private boolean checkNewPasswordValid(String newPassword) {
        boolean newPasswordValid = newPassword.length() >= 4;
        if (!newPasswordValid) {
            changePasswordView.showInvalidNewPassword("Password must be at least 4 characters");
        } else {
            changePasswordView.hideInvalidNewPassword();
        }
        return newPasswordValid;
    }

    private boolean checkConfirmNewPasswordValid(String newPassword, String confirmNewPassword) {
        boolean confirmNewPasswordValid = newPassword.equals(confirmNewPassword);
        if (!confirmNewPasswordValid) {
            changePasswordView.showPasswordDoesNotMatch();
        } else {
            changePasswordView.hidePasswordDoesNotMatch();
        }
        return confirmNewPasswordValid;
    }

    private void onChangePasswordError(String message) {
        changePasswordView.showError(message);
        changePasswordView.hideProgress();
        changePasswordView.enableRequestChangePassword();
    }
}
