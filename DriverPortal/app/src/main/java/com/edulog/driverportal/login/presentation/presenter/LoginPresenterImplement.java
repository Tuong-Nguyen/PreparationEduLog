package com.edulog.driverportal.login.presentation.presenter;

import com.edulog.driverportal.login.domain.interactors.DriverAuthenticateUseCase;
import com.edulog.driverportal.login.domain.interactors.LoginValidateUseCase;
import com.edulog.driverportal.login.domain.interactors.SendEventUseCase;
import com.edulog.driverportal.login.models.DriverPreferences;
import com.edulog.driverportal.login.models.Events;
import com.edulog.driverportal.login.models.ErrorValidation;

import java.lang.ref.WeakReference;

import io.reactivex.observers.DisposableObserver;

/**
 * Handle methods, which communicate between LoginActivity <-> LoginPresenter and LoginPresenter -> Models
 */

public class LoginPresenterImplement implements LoginPresenter {

    // Layer View reference
    // TODO: ntmhanh Why do we use WeakReference?
    private WeakReference<LoginView> mView;
    // TODO: ntmhanh Do not prefix with m
    private DriverAuthenticateUseCase mloginAuthenticateUseCase;
    public DriverAuthenticateUseCase.Params params;
    private DriverPreferences mDriverPreferences;
    private SendEventUseCase sendEventUseCase;
    private LoginValidateUseCase mLoginValidateUseCase;

    public LoginPresenterImplement(LoginView view, DriverAuthenticateUseCase loginAuthenticateUseCase, DriverPreferences driverPreferences, SendEventUseCase sendEventUseCase, LoginValidateUseCase loginValidateUseCase) {
        this.mView = new WeakReference<>(view);
        this.mloginAuthenticateUseCase = loginAuthenticateUseCase;
        this.mDriverPreferences = driverPreferences;
        this.sendEventUseCase = sendEventUseCase;
        this.mLoginValidateUseCase = loginValidateUseCase;
    }

    @Override
    public void doLogin(String busID, String driverId, String password, Events events) {
        sendEventUseCase.execute(new EventObserver(), events);
        params = new DriverAuthenticateUseCase.Params(busID, driverId, password);
        mloginAuthenticateUseCase.execute(new AuthenticateObserver(),params);
    }

    /**
     * Implement doLogin, which get values from view and return for UseCase
     * @param busID
     * @param driverId
     * @param password
     */

    @Override
    public void validateCredentials(String busID, String driverId, String password) {
        params = new DriverAuthenticateUseCase.Params(busID, driverId, password);
        mLoginValidateUseCase.execute(new ValidateObserver(), params);
    }

    /**
     * Setting remember driver id on view
     */
    @Override
    public void setViewRememberDriverId() {
        if (!mDriverPreferences.getDriverId().isEmpty()){
            mView.get().setTextRememberDriverId(mDriverPreferences.getDriverId());
            mView.get().rememberDriverIdCheckbox(true);
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
            mView.get().onLoginError(e.getMessage());
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
            mView.get().showLoginValidation(errorValidation);
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
            mView.get().showSentEventSuccess();
        }

        @Override
        public void onError(Throwable e) {
            mView.get().showSentEventFailure(e.getMessage());
        }

        @Override
        public void onComplete() {

        }
    }

    public void onLogin(DriverAuthenticateUseCase.Params params){
        mView.get().onLogged();
        rememberDriverId(params.driverId);
    }

    public void rememberDriverId(String driverId){
        if (mView != null) {
            if (mView.get().isRememberChecked()) {
                mDriverPreferences.settingValue(driverId);
            } else {
                mDriverPreferences.removeValueItem();
            }
        }
    }

}
