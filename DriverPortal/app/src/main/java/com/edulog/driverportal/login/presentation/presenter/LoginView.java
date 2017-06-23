package com.edulog.driverportal.login.presentation.presenter;

/**
 * LoginPresenter access to View(LoginActivity)
 */

public interface LoginView {
    void setTextRememberDriverId(String driverId);
    void rememberDriverIdCheckbox(boolean isChecked);
    void onLoginError(String message);
}
