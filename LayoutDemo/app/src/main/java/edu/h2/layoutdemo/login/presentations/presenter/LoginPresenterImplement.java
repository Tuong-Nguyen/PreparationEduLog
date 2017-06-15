package edu.h2.layoutdemo.login.presentations.presenter;

import java.lang.ref.WeakReference;

import edu.h2.layoutdemo.login.domain.interactors.DriverAuthenticateUseCase;
import edu.h2.layoutdemo.login.models.Event;
import edu.h2.layoutdemo.login.tracking.EventTracking;
import io.reactivex.observers.DisposableObserver;

/**
 * Created by ntmhanh on 6/12/2017.
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
    public void sendEventLogin(Event event) {
        mEventTracking.execute(new EventObserver(), event);
    }

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
    @Override
    public void rememberDriverId(String driverId){
         if (mView != null) {
            boolean isRemember = mView.get().isRememberChecked();
            mDriverPreferences.setRememberDriverId(isRemember, driverId);
        }
    }

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
     *
     * @param isLogin
     * @param params
     */
    public void onLogin(Boolean isLogin, DriverAuthenticateUseCase.Params params) {
        if (isLogin){
            mView.get().showLoginSuccess();
            rememberDriverId(params.driverId);
            this.loginCount = 0;
        }else {
            this.loginCount++;
            mView.get().showLoginFail();
            if (this.loginCount > 3) {
                mView.get().showWarningOverThreeTimesLogin();
                return;
            }
        }
    }

}
