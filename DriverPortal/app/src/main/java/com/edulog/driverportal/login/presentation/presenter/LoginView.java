package com.edulog.driverportal.login.presentation.presenter;

import com.edulog.driverportal.login.models.ErrorValidation;

/**
 * LoginPresenter access to View(LoginActivity)
 */

public interface LoginView {
    void setTextRememberDriverId(String driverId);
    void rememberDriverIdCheckbox(boolean isChecked);
    void onLoginError(String message);
    void showErrorValidationMessage(ErrorValidation errorValidation);
    void showSentEventSuccess();
    void showSentEventFailure(String message);
    void onLogged();
    boolean isRememberChecked();
}
