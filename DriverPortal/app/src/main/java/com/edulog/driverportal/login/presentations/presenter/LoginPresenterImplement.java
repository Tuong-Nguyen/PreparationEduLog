package com.edulog.driverportal.login.presentations.presenter;

import com.edulog.driverportal.login.domain.interactors.DriverAuthenticateUseCase;
import com.edulog.driverportal.login.models.Events;
import com.edulog.driverportal.login.tracking.EventTracking;

import java.lang.ref.WeakReference;

import io.reactivex.observers.DisposableObserver;

/**
 * Handle methods, which communicate between LoginActivity <-> LoginPresenter and LoginPresenter -> Models
 */

public class LoginPresenterImplement implements LoginPresenter.LoginPresenterOptions {

    // Layer View reference
    private WeakReference<LoginPresenter.RequireViewOptions> mView;
    private DriverAuthenticateUseCase mloginAuthenticateUseCase;
    public DriverAuthenticateUseCase.Params params;
    private DriverPreferences mDriverPreferences;
    private EventTracking mEventTracking;
    private int loginCount = 0;

    public LoginPresenterImplement(LoginPresenter.RequireViewOptions view, DriverAuthenticateUseCase loginAuthenticateUseCase, DriverPreferences driverPreferences, EventTracking eventTracking) {
        this.mView = new WeakReference<>(view);
        this.mloginAuthenticateUseCase = loginAuthenticateUseCase;
        this.mDriverPreferences = driverPreferences;
        this.mEventTracking = eventTracking;
    }


    @Override
    public void sendEventLogin(Events events) {
        mEventTracking.execute(new EventObserver(), events);
    }

    /**
     * Implement validateCredentials, which get values from view and return for UseCase
     * @param busID
     * @param driverId
     * @param password
     */

    @Override
    public void validateCredentials(String busID, String driverId, String password) {
        if (busID.isEmpty()) {
            mView.get().showEmptyCredentials(busID);
        } else if (driverId.isEmpty()) {
            mView.get().showEmptyCredentials(driverId);
        } else if (password.isEmpty()) {
            mView.get().showEmptyCredentials(password);
        } else {
           params = new DriverAuthenticateUseCase.Params(busID, driverId, password);
           mloginAuthenticateUseCase.execute(new AuthenticateObserver(),params);
        }
    }

    /**
     * Setting remember driver id
     */
    @Override
    public void doRememberDriverId() {
        if (!mDriverPreferences.getDriverId().isEmpty()){
            mView.get().setTextRememberDriverId(mDriverPreferences.getDriverId());
            mView.get().rememberDriverIdCheckbox(true);
        }
    }

    @Override
    public void moveToRouteScreen(String driverId) {

    }

    /**
     * Setting value for driver preference
     * @param driverId
     */
    @Override
    public void rememberDriverId(String driverId){
        if (mView != null) {
            if (mView.get().isRememberChecked()) {
                mDriverPreferences.settingValue(driverId);
            } else {
                mDriverPreferences.removeValueItem();
            }
        }
    }

    /**
     * Implement authenticateObserve after receiving authenticate driver result
     */
    public final class AuthenticateObserver extends DisposableObserver<Boolean> {
        @Override
        public void onNext(Boolean isLogin) {
            onLogin(isLogin, params);
        }

        @Override
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
        public void onNext(Boolean isSentSuccess) {

        }

        @Override
        public void onError(Throwable e) {
        }

        @Override
        public void onComplete() {

        }
    }

    /**
     * Handle login after return result from authenticate observable
     * @param isLogin
     * @param params
     */
    public void onLogin(Boolean isLogin, DriverAuthenticateUseCase.Params params) {
        if (isLogin){
            mView.get().onLogged();
            rememberDriverId(params.driverId);
            this.loginCount = 0;
        }else {
            this.loginCount++;
            mView.get().onNotLogged();
            if (this.loginCount > 3) {
                mView.get().showFailedOverThreeTimesLogin();
                return;
            }
        }
    }

}
