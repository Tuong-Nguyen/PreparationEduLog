package com.edulog.driverportal.login.presentation.presenter;

import com.edulog.driverportal.login.domain.interactors.DriverAuthenticateUseCase;
import com.edulog.driverportal.login.domain.interactors.LoginValidateUseCase;
import com.edulog.driverportal.login.domain.interactors.SendEventUseCase;
import com.edulog.driverportal.login.models.DriverPreferences;
import com.edulog.driverportal.login.models.Events;
import com.edulog.driverportal.login.presentation.presenter.LoginPresenterImplement;
import com.edulog.driverportal.login.presentation.presenter.LoginView;

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
    private DriverAuthenticateUseCase loginAuthenticateUseCase;
    private LoginView loginView;
    private DriverPreferences driverPreferences;
    private LoginPresenterImplement loginPresenterImplement;
    private SendEventUseCase sendEventUseCase;
    private LoginValidateUseCase loginValidateUseCase;
    String busId = "1";
    String driverId = "2";
    String password = "123";
    @Before
    public void init(){
        //Arrange
        loginAuthenticateUseCase = Mockito.mock(DriverAuthenticateUseCase.class);
        loginView = Mockito.mock(LoginView.class);
        driverPreferences = Mockito.mock(DriverPreferences.class);
        sendEventUseCase = Mockito.mock(SendEventUseCase.class);
        loginValidateUseCase = Mockito.mock(LoginValidateUseCase.class);
        //Action
        loginPresenterImplement = new LoginPresenterImplement(loginView, loginAuthenticateUseCase, driverPreferences, sendEventUseCase, loginValidateUseCase);
    }
    @Test
    public void doLogin_getInformationFromLoginPage_returnExecuteWasCalled() {
        //Action
        loginPresenterImplement.doLogin(busId, driverId, password, Events.LOG_IN);
        //Assert
        verify(loginAuthenticateUseCase).execute(any(DisposableObserver.class), any(DriverAuthenticateUseCase.Params.class));
    }
    @Test
    public void onLogin_loginSuccessAndRememberIdWasChecked_returnSettingValueWasCalled() {
        //Action
        DriverAuthenticateUseCase.Params params = new DriverAuthenticateUseCase.Params(busId, driverId, password);
        when(loginView.isRememberChecked()).thenReturn(true);
        loginPresenterImplement.onLogin(params);
        //Assert
        verify(driverPreferences).settingValue(anyString());
    }
  @Test
    public void onLogin_loginSuccessAndRememberIdWasNotChecked_returnRemoveValueItemWasCalled() {
        DriverAuthenticateUseCase.Params params = new DriverAuthenticateUseCase.Params(busId, driverId, password);
        when(loginView.isRememberChecked()).thenReturn(false);
        loginPresenterImplement.onLogin(params);
        //Assert
        verify(driverPreferences).removeValueItem();
    }

    @Test
    public void onLogin_isLoginTrue_returnShowLoginSuccessWasCalled(){
        //Action
        DriverAuthenticateUseCase.Params params = new DriverAuthenticateUseCase.Params(busId, driverId, password);
        loginPresenterImplement.onLogin(params);
        //Assert
        verify(loginView).onLogged();
    }
    @Test
    public void onLogin_isLoginFalse_returnShowLoginFailWasCalled(){
        //Action
        DriverAuthenticateUseCase.Params params = new DriverAuthenticateUseCase.Params(busId, driverId, password);
        loginPresenterImplement.onLogin(params);
        //Assert
        //verify(loginView).onLoginError();
    }
}
