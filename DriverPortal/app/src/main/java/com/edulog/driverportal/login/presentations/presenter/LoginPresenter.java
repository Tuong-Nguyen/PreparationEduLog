package com.edulog.driverportal.login.presentations.presenter;

import com.edulog.driverportal.login.models.Events;

/**
 *  View(LoginActivity) access to LoginPresenter
 */

public interface LoginPresenter {
        void doLogin(String busID, String driverId, String password, Events events);
        void validateCredentials(String busID, String driverId, String password);
        void setViewRememberDriverId();
}