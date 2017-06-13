package edu.h2.layoutdemo.login;

import org.junit.Assert;
import org.junit.Test;
import org.mockito.Mockito;

import edu.h2.layoutdemo.login.presenter.LoginPresenter;
import edu.h2.layoutdemo.login.presenter.LoginPresenterImplement;
import edu.h2.layoutdemo.login.repositories.DriverRepository;
import edu.h2.layoutdemo.login.usecase.DriverAuthenticateUseCase;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * Created by ntmhanh on 6/13/2017.
 */

public class DriverAuthenticateTest {

    @Test
    public void validateCredentials_InputDriverExistWithRightBusIdAndPassword_alertOnLoginSuccess(){
        // Arrange
        String busId = "1";
        String driverId = "2";
        String password = "123";
        Driver driver = new Driver("1","2", "123");
        LoginPresenter.RequireLoginPresenterOptions requireLoginPresenterOptions = Mockito.mock(LoginPresenterImplement.class);
        DriverRepository driverRepository = Mockito.mock(DriverRepository.class);
        DriverAuthenticateUseCase driverAuthenticateUseCase = new DriverAuthenticateUseCase(requireLoginPresenterOptions, driverRepository);
        when(driverAuthenticateUseCase.mDriverRepository.getDriverById(Mockito.anyString())).thenReturn(driver);
        // Action
        driverAuthenticateUseCase.validateCredentials(busId, driverId, password);
        //Assert
        verify(requireLoginPresenterOptions,times(1)).onLoginSuccess();
    }
    @Test
    public void validateCredentials_InputDriverExistWithWrongBusIdOrPassword_alertOnLoginFail(){
        // Arrange
        String busId = "1";
        String driverId = "2";
        String password = "123";
        Driver driver = new Driver("1","2", "123");
        LoginPresenter.RequireLoginPresenterOptions requireLoginPresenterOptions = Mockito.mock(LoginPresenterImplement.class);
        DriverRepository driverRepository = Mockito.mock(DriverRepository.class);
        DriverAuthenticateUseCase driverAuthenticateUseCase = new DriverAuthenticateUseCase(requireLoginPresenterOptions, driverRepository);
        when(driverAuthenticateUseCase.mDriverRepository.getDriverById(Mockito.anyString())).thenReturn(driver);
        // Action
        driverAuthenticateUseCase.validateCredentials("2", driverId, password);
        //Assert
        verify(requireLoginPresenterOptions,times(1)).onLoginFail();
    }
    @Test
    public void validateCredentials_InputDriverNotExist_returnFalse(){
        // Arrange
        String busId = "1";
        String driverId = "2";
        String password = "123";
        LoginPresenter.RequireLoginPresenterOptions requireLoginPresenterOptions = Mockito.mock(LoginPresenterImplement.class);
        DriverRepository driverRepository = Mockito.mock(DriverRepository.class);
        DriverAuthenticateUseCase driverAuthenticateUseCase = new DriverAuthenticateUseCase(requireLoginPresenterOptions, driverRepository);
        when(driverAuthenticateUseCase.mDriverRepository.getDriverById(Mockito.anyString())).thenReturn(null);
        // Action
        boolean isLogin = driverAuthenticateUseCase.validateCredentials(busId, driverId, password);
        //Assert
        Assert.assertEquals(false, isLogin);
    }

}
