package com.edulog.driverportal.login.presentations.presenter;

import com.edulog.driverportal.login.models.Events;

/**
 * LoginPresenter interface, which define interface to communicate between LoginActivity and LoginPresenter
 */

public interface LoginPresenter {

    // View(LoginActivity) access to LoginPresenter
    interface LoginPresenterOptions{
        void sendEventLogin(Events events);
        void validateCredentials(String busID, String driverId, String password);
        void doRememberDriverId();
        void moveToRouteScreen(String driverID);
        void rememberDriverId(String driverId);
    }

    // LoginPresenter access to View(LoginActivity)
    interface RequireViewOptions{
        void setTextRememberDriverId(String driverId);
        void onLogged();
        void showEmptyCredentials(String field);
        void onNotLogged();
        void rememberDriverIdCheckbox(boolean isChecked);
        boolean isRememberChecked();
        void showFailedOverThreeTimesLogin();
        void showSentEventSuccess();
    }
}
