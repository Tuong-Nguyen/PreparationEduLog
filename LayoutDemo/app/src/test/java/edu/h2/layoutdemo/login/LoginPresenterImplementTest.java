package edu.h2.layoutdemo.login;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import edu.h2.layoutdemo.login.presenter.LoginPresenter;
import edu.h2.layoutdemo.login.presenter.LoginPresenterImplement;
import edu.h2.layoutdemo.login.usecase.DriverAuthenticateUseCase;
import io.reactivex.observers.DisposableObserver;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyBoolean;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.verify;

/**
 * Created by ntmhanh on 6/14/2017.
 */

public class LoginPresenterImplementTest {
    private  DriverAuthenticateUseCase mloginAuthenticateUseCase;
    private LoginPresenter.RequireViewOptions requireViewOptions;
    private DriverPreferences driverPreferences;
    private LoginPresenterImplement loginPresenterImplement;

    String busId = "1";
    String driverId = "2";
    String password = "123";
    @Before
    public void init(){
        //Arrange
        mloginAuthenticateUseCase = Mockito.mock(DriverAuthenticateUseCase.class);
        requireViewOptions = Mockito.mock(LoginPresenter.RequireViewOptions.class);
        driverPreferences = Mockito.mock(DriverPreferences.class);
        //Action
        loginPresenterImplement = new LoginPresenterImplement(requireViewOptions, mloginAuthenticateUseCase, driverPreferences);
        loginPresenterImplement.validateCredentials(busId, driverId, password);
    }
    @Test
    public void validateCredentials_getInformationFromLoginPage_returnExecuteWasCalled() {

        //Assert
        verify(mloginAuthenticateUseCase).execute(any(DisposableObserver.class), any(DriverAuthenticateUseCase.Params.class));
    }
    @Test
    public void onLogin_loginSuccess_returnSetRememberDriverIdWasCalled() {
        DriverAuthenticateUseCase.Params params = new DriverAuthenticateUseCase.Params(busId, driverId, password);
        loginPresenterImplement.onLogin(true, params,0);
        //Assert
        verify(driverPreferences).setRememberDriverId(anyBoolean(),anyString());
    }

    @Test
    public void onLogin_LoginFailedOverThreeTimes_returnShowWarningOverThreeTimesLoginWasCalled(){
        DriverAuthenticateUseCase.Params params = new DriverAuthenticateUseCase.Params(busId, driverId, password);
        loginPresenterImplement.onLogin(false, params,4);
        //Assert
        verify(requireViewOptions).showWarningOverThreeTimesLogin();
    }
    @Test
    public void onLogin_isLoginTrue_returnShowLoginSuccessWasCalled(){
             //Action
        DriverAuthenticateUseCase.Params params = new DriverAuthenticateUseCase.Params(busId, driverId, password);
        loginPresenterImplement.onLogin(true, params,0);
        //Assert
        verify(requireViewOptions).showLoginSuccess();
    }
    @Test
    public void onLogin_isLoginFalse_returnShowLoginFailWasCalled(){
        //Action
        DriverAuthenticateUseCase.Params params = new DriverAuthenticateUseCase.Params(busId, driverId, password);
        loginPresenterImplement.onLogin(false, params,0);
        //Assert
        verify(requireViewOptions).showLoginFail();
    }
}
