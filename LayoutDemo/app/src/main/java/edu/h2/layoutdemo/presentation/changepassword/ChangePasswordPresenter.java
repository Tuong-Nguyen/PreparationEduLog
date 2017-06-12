package edu.h2.layoutdemo.presentation.changepassword;

import edu.h2.layoutdemo.presentation.BasePresenter;

public interface ChangePasswordPresenter extends BasePresenter<ChangePasswordView> {
    void changePassword(String driverId, String oldPassword, String newPassword, String confirmNewPassword);
}
