package com.edulog.driverportal.presentation.settings.changepassword;

import com.edulog.driverportal.presentation.BasePresenter;

public interface ChangePasswordPresenter extends BasePresenter<ChangePasswordView> {
    void changePassword(String driverId, String oldPassword, String newPassword, String confirmNewPassword);
}
