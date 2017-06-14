package edu.h2.layoutdemo.login;

import org.junit.Test;
import org.mockito.Mockito;

import edu.h2.layoutdemo.login.models.Driver;
import edu.h2.layoutdemo.login.presenter.LoginPresenter;
import edu.h2.layoutdemo.login.presenter.LoginPresenterImplement;
import edu.h2.layoutdemo.login.usecase.DriverAuthenticateUseCase;
import io.reactivex.observers.DisposableObserver;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;

/**
 * Created by ntmhanh on 6/14/2017.
 */

public class LoginPresenterImplementTest {
    String busId = "1";
    String driverId = "2";
    String password = "123";
    @Test
    public void validateCredentials_getInformationFromLoginPage_returnExecuteWasCalled() {
        //Arrange
        DriverAuthenticateUseCase mloginAuthenticateUseCase = Mockito.mock(DriverAuthenticateUseCase.class);
        LoginPresenter.RequireViewOptions requireViewOptions = Mockito.mock(LoginPresenter.RequireViewOptions.class);
        //Action
        LoginPresenterImplement loginPresenterImplement = new LoginPresenterImplement(requireViewOptions, mloginAuthenticateUseCase);
        loginPresenterImplement.validateCredentials(busId, driverId, password);
        //Assert
        verify(mloginAuthenticateUseCase).execute(any(DisposableObserver.class), any(DriverAuthenticateUseCase.Params.class));
    }
    @Test
    public void onLogin_inputDriverIdExistInformation_returnShowLoginSuccessWasCalled() {
        //Arrange
        DriverAuthenticateUseCase mloginAuthenticateUseCase = Mockito.mock(DriverAuthenticateUseCase.class);
        LoginPresenter.RequireViewOptions requireViewOptions = Mockito.mock(LoginPresenter.RequireViewOptions.class);
        //Action
        LoginPresenterImplement loginPresenterImplement = new LoginPresenterImplement(requireViewOptions, mloginAuthenticateUseCase);
        Driver driver = new Driver(busId, driverId, password);
        DriverAuthenticateUseCase.Params params = new DriverAuthenticateUseCase.Params(busId, driverId, password);
        loginPresenterImplement.onLogin(driver, params);
        //Assert
        //verify(loginPresenterImplement.onLogin(driver, params));
    }

}
