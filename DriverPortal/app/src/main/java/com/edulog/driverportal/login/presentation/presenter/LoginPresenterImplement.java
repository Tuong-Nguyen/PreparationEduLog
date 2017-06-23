package com.edulog.driverportal.login.presentation.presenter;

import com.edulog.driverportal.login.domain.interactors.LoginUseCase;
import com.edulog.driverportal.login.domain.services.DriverPreferences;
import com.edulog.driverportal.login.domain.utils.ThrowableErrorValidation;
import com.edulog.driverportal.login.models.ErrorValidation;

import io.reactivex.observers.DisposableObserver;

/**
 * Handle methods, which communicate between LoginActivity <-> LoginPresenter and LoginPresenter -> Models
 */

// TODO: Rename as LoginPresenterImpl
public class LoginPresenterImplement implements LoginPresenter {

    // Layer View reference
    private LoginView loginView;
    public LoginUseCase.Params params;
    private DriverPreferences driverPreferences;
    private LoginUseCase loginUseCase;


    public LoginPresenterImplement(LoginView loginView, DriverPreferences driverPreferences, LoginUseCase loginUseCase) {
        this.loginView = loginView;
        this.loginUseCase = loginUseCase;
        this.driverPreferences = driverPreferences;
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
        if (!driverPreferences.getDriverId().isEmpty()){
            loginView.setTextRememberDriverId(driverPreferences.getDriverId());
            loginView.rememberDriverIdCheckbox(true);
        }
    }

    public final class LoginObserver extends DisposableObserver<Boolean> {
        @Override
        public void onNext(Boolean aBoolean) {

        }

        @Override
        public void onError(Throwable e) {
            ThrowableErrorValidation throwableErrorValidation = (ThrowableErrorValidation)e;
            ErrorValidation errorValidation = throwableErrorValidation.getErrorValidationUtil();
            if (errorValidation != null){
                loginView.showErrorValidationMessage(errorValidation);
            }else {
                loginView.onLoginError(e.getMessage());
            }
        }

        @Override
        public void onComplete() {

        }
    }
}
