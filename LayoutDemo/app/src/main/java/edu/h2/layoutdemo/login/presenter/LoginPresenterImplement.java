package edu.h2.layoutdemo.login.presenter;

import java.lang.ref.WeakReference;

import edu.h2.layoutdemo.login.DriverPreferences;
import edu.h2.layoutdemo.login.usecase.DriverAuthenticateUseCase;
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
    private int loginCount = 0;

    public LoginPresenterImplement(LoginPresenter.RequireViewOptions view, DriverAuthenticateUseCase loginAuthenticateUseCase, DriverPreferences driverPreferences) {
        this.mView = new WeakReference<>(view);
        this.mloginAuthenticateUseCase = loginAuthenticateUseCase;
        this.mDriverPreferences = driverPreferences;
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
    public void initBeforeCheckRemember() {
        mDriverPreferences.saveDriverId();
        if (mDriverPreferences.isSaveDriverId){
            mView.get().setTextRememberDriverId(mDriverPreferences.getDriverId());
            mView.get().saveLoginCheckBox(true);
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

    private void onLoginFailed(String error) {

    }

    public final class AuthenticateObserver extends DisposableObserver<Boolean> {
        @Override
        public void onNext(Boolean isLogin) {
            onLogin(isLogin, params, loginCount);
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
    public void onLogin(Boolean isLogin, DriverAuthenticateUseCase.Params params, int loginCount) {
        if (isLogin){
            mView.get().showLoginSuccess();
            rememberDriverId(params.driverId);
            this.loginCount = 0;
        }else {
            this.loginCount++;
            mView.get().showLoginFail();
            if (loginCount > 3) {
                mView.get().showWarningOverThreeTimesLogin();
                return;
            }
        }
    }

}
