package com.edulog.driverportal.login.presentation.presenter;

import com.edulog.driverportal.login.domain.interactors.DevicePreferenceUseCase;
import com.edulog.driverportal.login.domain.interactors.LoginUseCase;

import io.reactivex.observers.DisposableObserver;

/**
 * Handle methods, which communicate between LoginActivity <-> LoginPresenter and LoginPresenter -> Models
 */

public class LoginPresenterImpl implements LoginPresenter {

    // Layer View reference
    private LoginView loginView;
    public LoginUseCase.Params params;
    private LoginUseCase loginUseCase;
    private DevicePreferenceUseCase devicePreferenceUseCase;


    public LoginPresenterImpl(LoginView loginView, DevicePreferenceUseCase devicePreferenceUseCase, LoginUseCase loginUseCase) {
        this.loginView = loginView;
        this.loginUseCase = loginUseCase;
        this.devicePreferenceUseCase = devicePreferenceUseCase;
    }

    @Override
    public void doLogin(String busID, String driverId, String password, boolean isRememberChecked) {
        params = new LoginUseCase.Params(busID, driverId, password, isRememberChecked);
        loginUseCase.execute(new LoginObserver(), params);
    }

    /**
     * Setting rememberDriverId driver id on view
     */
    @Override
    public void getRememberDriverId() {
        if (devicePreferenceUseCase.isDriverId()){
            loginView.setTextRememberDriverId(devicePreferenceUseCase.driverId());
            loginView.rememberDriverIdCheckbox(true);
        }
    }

    public final class LoginObserver extends DisposableObserver<Boolean> {
        @Override
        public void onNext(Boolean aBoolean) {}

        @Override
        public void onError(Throwable e) {
                loginView.onLoginError(e.getMessage());
        }

        @Override
        public void onComplete() {}
    }
}
