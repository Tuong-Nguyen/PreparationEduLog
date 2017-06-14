package com.edulog.driverportal.settings.changepassword.presentation;

import com.edulog.driverportal.settings.base.BasePresenter;

public interface ChangePasswordPresenter extends BasePresenter<ChangePasswordView> {
    void changePassword(String driverId, String oldPassword, String newPassword);

    boolean validateUserInput(String driverId, String oldPassword, String newPassword, String confirmNewPassword);

    void validateUserInput(boolean isValid);
}
