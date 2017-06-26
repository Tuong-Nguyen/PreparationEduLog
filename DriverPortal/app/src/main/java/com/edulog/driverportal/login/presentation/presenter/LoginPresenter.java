package com.edulog.driverportal.login.presentation.presenter;

/**
 *  View(LoginActivity) access to LoginPresenter
 */

public interface LoginPresenter {
        void onLogin(String busID, String driverId, String password, boolean isRememberChecked);
        void onInitPreferences();
}