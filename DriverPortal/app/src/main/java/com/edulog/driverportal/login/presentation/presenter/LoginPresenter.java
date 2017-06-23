package com.edulog.driverportal.login.presentation.presenter;

/**
 *  View(LoginActivity) access to LoginPresenter
 */

public interface LoginPresenter {
        void doLogin(String busID, String driverId, String password, boolean isRememberChecked);
        // TODO: ntmhanh Remove it if it is not used
        void getRememberDriverId();
}