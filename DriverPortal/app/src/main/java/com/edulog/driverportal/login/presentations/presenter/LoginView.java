package com.edulog.driverportal.login.presentations.presenter;

import com.edulog.driverportal.login.models.LoginValidation;

/**
 * LoginPresenter access to View(LoginActivity)
 */

public interface LoginView {
    void setTextRememberDriverId(String driverId);
    void rememberDriverIdCheckbox(boolean isChecked);
    void onLoginError(String message);
    void showLoginValidation(LoginValidation loginValidation);
    void showSentEventSuccess();
    void showSentEventFailed(String message);
    void onLogged();
    boolean isRememberChecked();
}
