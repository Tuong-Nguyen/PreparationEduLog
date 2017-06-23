package com.edulog.driverportal.login.presentation.presenter;

import com.edulog.driverportal.login.domain.interactors.DevicePreferenceUseCase;
import com.edulog.driverportal.login.domain.interactors.LoginUseCase;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import io.reactivex.observers.DisposableObserver;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;

public class LoginPresenterImplTest {
    private LoginUseCase loginUseCase;
    private LoginView loginView;
    private DevicePreferenceUseCase devicePreferenceUseCase;
    private LoginPresenterImpl loginPresenterImpl;
    boolean isRememberDriverChecked;

    String busId = "1";
    String driverId = "2";
    String password = "123";
    @Before
    public void init(){
        //Arrange
        loginView = Mockito.mock(LoginView.class);
        loginUseCase = Mockito.mock(LoginUseCase.class);
        devicePreferenceUseCase = Mockito.mock(DevicePreferenceUseCase.class);
        //Action
        loginPresenterImpl = new LoginPresenterImpl(loginView, devicePreferenceUseCase, loginUseCase);
    }
    @Test
    public void doLogin_getInformationFromLoginPage_returnExecuteWasCalled() {
        //Action
        isRememberDriverChecked = true;
        loginPresenterImpl.doLogin(busId, driverId, password, true);
        //Assert
        verify(loginUseCase).execute(any(DisposableObserver.class), any(LoginUseCase.Params.class));
    }
}
