package com.edulog.driverportal.login;

import com.edulog.driverportal.login.domain.interactors.DriverAuthenticateUseCase;
import com.edulog.driverportal.login.presentations.presenter.DriverPreferences;
import com.edulog.driverportal.login.presentations.presenter.LoginPresenter;
import com.edulog.driverportal.login.presentations.presenter.LoginPresenterImplement;
import com.edulog.driverportal.login.tracking.EventTracking;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import io.reactivex.observers.DisposableObserver;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * Created by ntmhanh on 6/14/2017.
 */

public class LoginPresenterImplementTest {
    private DriverAuthenticateUseCase mLoginAuthenticateUseCase;
    private LoginPresenter.RequireViewOptions requireViewOptions;
    private DriverPreferences driverPreferences;
    private LoginPresenterImplement loginPresenterImplement;
    private EventTracking eventTracking;

    String busId = "1";
    String driverId = "2";
    String password = "123";
    @Before
    public void init(){
        //Arrange
        mLoginAuthenticateUseCase = Mockito.mock(DriverAuthenticateUseCase.class);
        requireViewOptions = Mockito.mock(LoginPresenter.RequireViewOptions.class);
        driverPreferences = Mockito.mock(DriverPreferences.class);
        eventTracking = Mockito.mock(EventTracking.class);
        //Action
        loginPresenterImplement = new LoginPresenterImplement(requireViewOptions, mLoginAuthenticateUseCase, driverPreferences, eventTracking);
        loginPresenterImplement.validateCredentials(busId, driverId, password);
    }
    @Test
    public void validateCredentials_getInformationFromLoginPage_returnExecuteWasCalled() {
        //Assert
        verify(mLoginAuthenticateUseCase).execute(any(DisposableObserver.class), any(DriverAuthenticateUseCase.Params.class));
    }
    @Test
    public void onLogin_loginSuccessAndRememberIdWasChecked_returnSettingValueWasCalled() {
        DriverAuthenticateUseCase.Params params = new DriverAuthenticateUseCase.Params(busId, driverId, password);
        when(requireViewOptions.isRememberChecked()).thenReturn(true);
        loginPresenterImplement.onLogin(true, params);
        //Assert
        verify(driverPreferences).settingValue(anyString());
    }
    @Test
    public void onLogin_loginSuccessAndRememberIdWasNotChecked_returnRemoveValueItemWasCalled() {
        DriverAuthenticateUseCase.Params params = new DriverAuthenticateUseCase.Params(busId, driverId, password);
        when(requireViewOptions.isRememberChecked()).thenReturn(false);
        loginPresenterImplement.onLogin(true, params);
        //Assert
        verify(driverPreferences).removeValueItem();
    }

    @Test
    public void onLogin_LoginFailedOverThreeTimes_returnShowWarningOverThreeTimesLoginWasCalled(){
        //Action
        DriverAuthenticateUseCase.Params params = new DriverAuthenticateUseCase.Params(busId, driverId, password);
        loginPresenterImplement.onLogin(false, params);
        loginPresenterImplement.onLogin(false, params);
        loginPresenterImplement.onLogin(false, params);
        loginPresenterImplement.onLogin(false, params);
        //Assert
        verify(requireViewOptions).showFailedOverThreeTimesLogin();
    }
    @Test
    public void onLogin_isLoginTrue_returnShowLoginSuccessWasCalled(){
        //Action
        DriverAuthenticateUseCase.Params params = new DriverAuthenticateUseCase.Params(busId, driverId, password);
        loginPresenterImplement.onLogin(true, params);
        //Assert
        verify(requireViewOptions).onLogged();
    }
    @Test
    public void onLogin_isLoginFalse_returnShowLoginFailWasCalled(){
        //Action
        DriverAuthenticateUseCase.Params params = new DriverAuthenticateUseCase.Params(busId, driverId, password);
        loginPresenterImplement.onLogin(false, params);
        //Assert
        verify(requireViewOptions).onNotLogged();
    }
}
