package edu.h2.layoutdemo.login;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import edu.h2.layoutdemo.login.repositories.DriverRepository;
import edu.h2.layoutdemo.login.usecase.DriverAuthenticateUseCase;

import static org.mockito.Mockito.when;

/**
 * Created by ntmhanh on 6/13/2017.
 */

public class DriverAuthenticateTest {
    private Driver driver;
    private DriverAuthenticateUseCase driverAuthenticateUseCase;
    String busId = "1";
    String driverId = "2";
    String password = "123";

    @Before
    public void init(){
        driver = new Driver(busId, driverId, password);
        DriverRepository driverRepository = Mockito.mock(DriverRepository.class);
        driverAuthenticateUseCase = new DriverAuthenticateUseCase(driverRepository);
    }

    @Test
    public void validateCredentials_InputDriverExistWithRightBusIdAndPassword_alertOnLoginSuccess(){
        // Arrange
        when(driverAuthenticateUseCase.mDriverRepository.getDriverById(Mockito.anyString())).thenReturn(driver);
        // Action
        boolean isLogin = driverAuthenticateUseCase.validateCredentials(busId, driverId, password);
        //Assert
        Assert.assertEquals(true, isLogin);
    }
    @Test
    public void validateCredentials_InputDriverExistWithWrongBusIdOrPassword_alertOnLoginFail(){
        // Arrange
        when(driverAuthenticateUseCase.mDriverRepository.getDriverById(Mockito.anyString())).thenReturn(driver);
        // Action
        boolean isLogin = driverAuthenticateUseCase.validateCredentials("2", driverId, password);
        //Assert
        Assert.assertEquals(false, isLogin);
    }
    @Test
    public void validateCredentials_InputDriverNotExist_returnFalse(){
        // Arrange
        when(driverAuthenticateUseCase.mDriverRepository.getDriverById(Mockito.anyString())).thenReturn(null);
        // Action
        boolean isLogin = driverAuthenticateUseCase.validateCredentials(busId, driverId, password);
        // Assert
        Assert.assertEquals(false, isLogin);
    }

}
