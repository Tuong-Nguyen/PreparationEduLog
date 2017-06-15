package com.edulog.driverportal.settings.changepassword.presentation;

import com.edulog.driverportal.common.base.BaseView;

public interface ChangePasswordView extends BaseView {
    void showChangePasswordSuccess(String message);

    void showInvalidDriverId(String message);

    void hideInvalidDriverId();

    void showInvalidOldPassword(String message);

    void hideInvalidOldPassword();

    void showInvalidNewPassword(String message);

    void hideInvalidNewPassword();

    void showPasswordDoesNotMatch();

    void hidePasswordDoesNotMatch();

    void enableRequestChangePassword();

    void disableRequestChangePassword();
}
