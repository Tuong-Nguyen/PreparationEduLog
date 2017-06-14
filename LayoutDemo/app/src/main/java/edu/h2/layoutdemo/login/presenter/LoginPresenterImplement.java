package edu.h2.layoutdemo.login.presenter;

import java.lang.ref.WeakReference;

import edu.h2.layoutdemo.login.Driver;
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

    public LoginPresenterImplement(LoginPresenter.RequireViewOptions view, DriverAuthenticateUseCase loginAuthenticateUseCase) {
        this.mView = new WeakReference<>(view);
        this.mloginAuthenticateUseCase = loginAuthenticateUseCase;
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
    private final class AuthenticateObserver extends DisposableObserver<Driver> {
        @Override
        public void onNext(Driver driver) {
            onLogin(driver);
        }

        @Override
        public void onError(Throwable e) {
        }

        @Override
        public void onComplete() {

        }
    }

    /**
     * verify information between driver get from server by driverId and from screen
     * @param driver
     */
    private void onLogin(Driver driver) {
        if (driver.getBusId().equals(params.busId) && driver.getPassword().equals(params.password)){
            mView.get().showLoginSuccess();
        }else {
            mView.get().showLoginFail();
        }
    }

}
