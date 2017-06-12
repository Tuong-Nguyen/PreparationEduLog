package edu.h2.layoutdemo.login.presenter;

/**
 * Created by ntmhanh on 6/12/2017.
 */

public interface LoginPresenter {
    interface LoginPresenterOptions{
        void showValidate(String busID, String driverId, String password);
    }
    interface RequireViewOptions{
        void showLoginSuccess();
        void showEmptyCredentials(String field);
        void showInvalidateCredentials();
    }
    interface RequireLoginPresenterOptions{
        void onValidateLogin();
        void onInvalidateLogin();
    }
}
