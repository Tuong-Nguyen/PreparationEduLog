package com.edulog.driverportal.login.presentation.presenter;

import com.edulog.driverportal.login.models.Events;

/**
 *  View(LoginActivity) access to LoginPresenter
 */

public interface LoginPresenter {
        // TODO: ntmhanh What other events should we put into doLogin except Events.LOG_IN
        void doLogin(String busID, String driverId, String password, Events events);
        void validateCredentials(String busID, String driverId, String password);
        // TODO: ntmhanh Remove it if it is not used
        void setViewRememberDriverId();
}