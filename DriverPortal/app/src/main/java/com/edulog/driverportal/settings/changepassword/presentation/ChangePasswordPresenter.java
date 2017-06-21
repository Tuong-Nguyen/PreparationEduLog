package com.edulog.driverportal.settings.changepassword.presentation;

import com.edulog.driverportal.common.presentation.BasePresenter;

public abstract class ChangePasswordPresenter extends BasePresenter<ChangePasswordView> {
    public abstract void changePassword(String driverId, String oldPassword, String newPassword);

    public abstract void validateUserInputs(String driverId, String oldPassword, String newPassword, String confirmNewPassword);
}
