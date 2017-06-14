package com.edulog.driverportal.settings.changepassword.presentation;

import com.edulog.driverportal.settings.base.BasePresenter;

public interface ChangePasswordPresenter extends BasePresenter<ChangePasswordView> {
    void changePassword(String driverId, String oldPassword, String newPassword);

    void validateUserInputs(String driverId, String oldPassword, String newPassword, String confirmNewPassword);
}
