package com.edulog.driverportal.login.presentation.presenter;

import com.edulog.driverportal.login.domain.interactors.LoginUseCase;
import com.edulog.driverportal.login.domain.services.DriverPreferences;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import io.reactivex.observers.DisposableObserver;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;

public class LoginPresenterImplementTest {
    private LoginUseCase loginUseCase;
    private LoginView loginView;
    private DriverPreferences driverPreferences;
    private LoginPresenterImplement loginPresenterImplement;
    boolean isRememberDriverChecked;

    String busId = "1";
    String driverId = "2";
    String password = "123";
    @Before
    public void init(){
        //Arrange
        loginView = Mockito.mock(LoginView.class);
        driverPreferences = Mockito.mock(DriverPreferences.class);
        loginUseCase = Mockito.mock(LoginUseCase.class);
        //Action
        loginPresenterImplement = new LoginPresenterImplement(loginView, driverPreferences, loginUseCase);
    }
    @Test
    public void doLogin_getInformationFromLoginPage_returnExecuteWasCalled() {
        //Action
        isRememberDriverChecked = true;
        loginPresenterImplement.doLogin(busId, driverId, password, true);
        //Assert
        verify(loginUseCase).execute(any(DisposableObserver.class), any(LoginUseCase.Params.class));
    }
}
