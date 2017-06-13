package edu.h2.layoutdemo.login.presenter;

import java.lang.ref.WeakReference;

import edu.h2.layoutdemo.login.usecase.DriverAuthenticateUseCase;

/**
 * Created by ntmhanh on 6/12/2017.
 */

public class LoginPresenterImplement implements LoginPresenter.LoginPresenterOptions, LoginPresenter.RequireLoginPresenterOptions{

    // Layer View reference
    private WeakReference<LoginPresenter.RequireViewOptions> mView;
    private DriverAuthenticateUseCase mloginAuthenticateUseCase;

    public LoginPresenterImplement(LoginPresenter.RequireViewOptions view) {
        this.mView = new WeakReference<>(view);
        this.mloginAuthenticateUseCase = new DriverAuthenticateUseCase(this);
    }

    @Override
    public void alertLogin(String busID, String driverId, String password) {
        if (busID.isEmpty()){
            mView.get().showEmptyCredentials(busID);
        }else if(driverId.isEmpty()){
            mView.get().showEmptyCredentials(driverId);
        }else if(password.isEmpty()) {
            mView.get().showEmptyCredentials(password);
        }else{
            mloginAuthenticateUseCase.validateCredentials(busID, driverId, password);
        }
    }

    @Override
    public void onLoginSuccess() {
        mView.get().showLoginSuccess();
    }

    @Override
    public void onLoginFail() {
        mView.get().showInvalidateCredentials();
    }
}
