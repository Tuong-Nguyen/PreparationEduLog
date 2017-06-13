package edu.h2.layoutdemo.login.presenter;

/**
 * Created by ntmhanh on 6/12/2017.
 */

public interface LoginPresenter {
    // View(LoginActivity) access to LoginPresenter
    interface LoginPresenterOptions{
        void alertLogin(String busID, String driverId, String password);
    }
    // LoginPresenter access to View(LoginActivity)
    interface RequireViewOptions{
        void showLoginSuccess();
        void showEmptyCredentials(String field);
        void showLoginFail();
    }
}
