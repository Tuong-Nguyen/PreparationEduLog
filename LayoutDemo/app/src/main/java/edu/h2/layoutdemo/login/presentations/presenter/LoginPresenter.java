package edu.h2.layoutdemo.login.presentations.presenter;

import edu.h2.layoutdemo.login.models.Event;

/**
 * Created by ntmhanh on 6/12/2017.
 */

public interface LoginPresenter {

    // View(LoginActivity) access to LoginPresenter
    interface LoginPresenterOptions{
        void sendEventLogin(Event event);
        void validateCredentials(String busID, String driverId, String password);
        void doRememberDriverId();
        void moveToRouteScreen(String driverID);
        void rememberDriverId(String driverId);
    }

    // LoginPresenter access to View(LoginActivity)
    interface RequireViewOptions{
        void setTextRememberDriverId(String driverId);
        void showLoginSuccess();
        void showEmptyCredentials(String field);
        void showLoginFail();
        void rememberDriverIdCheckbox(boolean isChecked);
        boolean isRememberChecked();
        void showWarningOverThreeTimesLogin();
    }
}
