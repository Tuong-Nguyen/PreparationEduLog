package com.edulog.driverportal.login.presentation.presenter;

import com.edulog.driverportal.login.domain.interactors.DriverAuthenticateUseCase;
import com.edulog.driverportal.login.domain.interactors.LoginValidateUseCase;
import com.edulog.driverportal.login.domain.interactors.SendEventUseCase;
import com.edulog.driverportal.login.domain.services.DriverPreferences;
import com.edulog.driverportal.login.models.ErrorValidation;
import com.edulog.driverportal.login.models.Events;

import io.reactivex.observers.DisposableObserver;

/**
 * Handle methods, which communicate between LoginActivity <-> LoginPresenter and LoginPresenter -> Models
 */

public class LoginPresenterImplement implements LoginPresenter {

    // Layer View reference
    // TODO: ntmhanh Why do we use WeakReference?
    private LoginView loginView;
    // TODO: ntmhanh Do not prefix with m
    private DriverAuthenticateUseCase loginAuthenticateUseCase;
    public DriverAuthenticateUseCase.Params params;
    private DriverPreferences driverPreferences;
    private SendEventUseCase sendEventUseCase;
    private LoginValidateUseCase loginValidateUseCase;

    public LoginPresenterImplement(LoginView loginView, DriverAuthenticateUseCase loginAuthenticateUseCase, DriverPreferences driverPreferences, SendEventUseCase sendEventUseCase, LoginValidateUseCase loginValidateUseCase) {
        this.loginView = loginView;
        this.loginAuthenticateUseCase = loginAuthenticateUseCase;
        this.driverPreferences = driverPreferences;
        this.sendEventUseCase = sendEventUseCase;
        this.loginValidateUseCase = loginValidateUseCase;
    }

    @Override
    public void doLogin(String busID, String driverId, String password) {
        sendEventUseCase.execute(new EventObserver(), Events.LOG_IN);
        params = new DriverAuthenticateUseCase.Params(busID, driverId, password);
        loginAuthenticateUseCase.execute(new AuthenticateObserver(),params);
        loginValidateUseCase.execute(new ValidateObserver(), params);
    }

    /**
     * Setting remember driver id on view
     */
    @Override
    public void setViewRememberDriverId() {
        if (!driverPreferences.getDriverId().isEmpty()){
            loginView.setTextRememberDriverId(driverPreferences.getDriverId());
            loginView.rememberDriverIdCheckbox(true);
        }
    }

    /**
     * Implement authenticateObserve after receiving login driver result
     */
    public final class AuthenticateObserver extends DisposableObserver<Boolean> {
        @Override
        public void onNext(Boolean isLogin) {
          onLogin(params);
        }

        @Override
        public void onError(Throwable e) {
            loginView.onLoginError(e.getMessage());
        }

        @Override
        public void onComplete() {

        }
    }
    /**
     * Implement authenticateObserve after receiving login driver result
     */
    public final class ValidateObserver extends DisposableObserver<ErrorValidation> {

        @Override
        public void onNext(ErrorValidation errorValidation) {
            loginView.showErrorValidationMessage(errorValidation);
        }

        @Override
        // TODO: ntmhanh If validation fails, it should be handled here
        public void onError(Throwable e) {

        }

        @Override
        public void onComplete() {

        }
    }


    /**
     * Implement EventObserver after receiving send event result
     */
    public final class EventObserver extends DisposableObserver<Boolean> {
        @Override
        public void onNext(Boolean isSent) {
            loginView.showSentEventSuccess();
        }

        @Override
        public void onError(Throwable e) {
            loginView.showSentEventFailure(e.getMessage());
        }

        @Override
        public void onComplete() {

        }
    }

    public void onLogin(DriverAuthenticateUseCase.Params params){
        loginView.onLogged();
        rememberDriverId(params.driverId);
    }

    public void rememberDriverId(String driverId){
        if (loginView.isRememberChecked()) {
            driverPreferences.setValuePreferences(driverId);
        } else {
            driverPreferences.removeValueItem();
        }
    }

}
