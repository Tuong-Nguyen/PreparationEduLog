package edu.h2.layoutdemo.login.presenter;

import android.content.SharedPreferences;

import java.lang.ref.WeakReference;

import edu.h2.layoutdemo.login.models.Driver;
import edu.h2.layoutdemo.login.usecase.DriverAuthenticateUseCase;
import edu.h2.layoutdemo.login.view.LoginActivity;
import io.reactivex.observers.DisposableObserver;

import static android.content.Context.MODE_PRIVATE;

/**
 * Created by ntmhanh on 6/12/2017.
 */

public class LoginPresenterImplement implements LoginPresenter.LoginPresenterOptions {

    // Layer View reference
    private WeakReference<LoginPresenter.RequireViewOptions> mView;
    private DriverAuthenticateUseCase mloginAuthenticateUseCase;
    public DriverAuthenticateUseCase.Params params;
    private LoginActivity mloginActivity;
    private SharedPreferences loginPreferences;
    private SharedPreferences.Editor loginPrefsEditor;
    private Boolean saveLogin;

    public LoginPresenterImplement(LoginPresenter.RequireViewOptions view, DriverAuthenticateUseCase loginAuthenticateUseCase, LoginActivity loginActivity) {
        this.mView = new WeakReference<>(view);
        this.mloginAuthenticateUseCase = loginAuthenticateUseCase;
        this.mloginActivity = loginActivity;
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

        //Create SharedPreferences
        loginPreferences = mloginActivity.getSharedPreferences("driverIdPrefs", MODE_PRIVATE);
        loginPrefsEditor = loginPreferences.edit();
        saveLogin = loginPreferences.getBoolean("saveDriverId", false);
        if (saveLogin == true) {
            // Retrieve data from preference and set text
            mView.get().setTextRememberDriverId(loginPreferences.getString("driverId", ""));
            mView.get().saveLoginCheckBox(true);
        }
    }

    @Override
    public void moveToRouteScreen(String driverId) {
        if (mView.get().isRememberChecked()){
            ///Setting values in Preference:
            loginPrefsEditor.putBoolean("saveDriverId", true);
            loginPrefsEditor.putString("driverId", driverId);
            // Save the changes in SharedPreferences
            loginPrefsEditor.commit();  // commit changes
        }else{
            loginPrefsEditor.clear();
            loginPrefsEditor.commit(); // commit changes
        }
    }
    private void onLoginFailed(String error) {

    }

    public final class AuthenticateObserver extends DisposableObserver<Driver> {
        @Override
        public void onNext(Driver driver) {
            onLogin(driver, params);
        }

        @Override
        public void onError(Throwable e) {
            onLoginFailed(e.getMessage());
        }

        @Override
        public void onComplete() {

        }
    }

    /**
     * verify information between driver get from server by driverId and from screen
     * @param driver
     */
    public void onLogin(Driver driver, DriverAuthenticateUseCase.Params params) {
        int countLoginFail = 0;
        if (driver.getBusId().equals(params.busId) && driver.getPassword().equals(params.password)){
            mView.get().showLoginSuccess();
            moveToRouteScreen(params.driverId);
        }else {
            mView.get().showLoginFail();
            countLoginFail ++;
            if (countLoginFail > 3 ){
                mloginAuthenticateUseCase.lockedAccount();
                countLoginFail = 0;
            }
        }
    }

}
