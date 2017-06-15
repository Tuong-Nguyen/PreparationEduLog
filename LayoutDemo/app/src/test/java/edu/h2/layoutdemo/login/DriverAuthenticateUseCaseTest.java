package edu.h2.layoutdemo.login;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import edu.h2.layoutdemo.login.domain.AuthenticateServiceImplement;
import edu.h2.layoutdemo.login.usecase.DriverAuthenticateUseCase;

import static org.mockito.Mockito.verify;

/**
 * Created by ntmhanh on 6/13/2017.
 */

public class DriverAuthenticateUseCaseTest {
    private DriverAuthenticateUseCase driverAuthenticateUseCase;
    String busId = "1";
    String driverId = "2";
    String password = "123";
    AuthenticateServiceImplement authenticateServiceImplement;

    @Before
    public void init(){
        authenticateServiceImplement = Mockito.mock(AuthenticateServiceImplement.class);
        driverAuthenticateUseCase = new DriverAuthenticateUseCase(authenticateServiceImplement);
    }
    @Test
    public void buildUseCaseObservable_inputLoginInformation_returnGetDriverByDriverIdWasCalled(){
        // Arrange
         DriverAuthenticateUseCase.Params params = new DriverAuthenticateUseCase.Params(busId, driverId, password);
        // Action
         driverAuthenticateUseCase.buildUseCaseObservable(params);
        // Assert
         verify(authenticateServiceImplement).authenticate(params.driverId, params.password);
    }

    /*@Test
    public void validateCredentials_InputDriverExistWithRightBusIdAndPassword_alertOnLoginSuccess(){
        // Arrange
        when(driverAuthenticateUseCase.mDriverRepository.getDriverById(Mockito.anyString())).thenReturn(driver);
        // Action
        //boolean isLogin = driverAuthenticateUseCase.buildUseCaseObservable(busId, driverId, password);
        //Assert
       // Assert.assertEquals(true, isLogin);
    }
    /*
    @Test
    public void validateCredentials_InputDriverExistWithWrongBusIdOrPassword_alertOnLoginFail(){
        // Arrange
        when(driverAuthenticateUseCase.mDriverRepository.getDriverById(Mockito.anyString())).thenReturn(driver);
        // Action
        boolean isLogin = driverAuthenticateUseCase.buildUseCaseObservable("2", driverId, password);
        //Assert
        Assert.assertEquals(false, isLogin);
    }
    @Test
    public void validateCredentials_InputDriverNotExist_returnFalse(){
        // Arrange
        when(driverAuthenticateUseCase.mDriverRepository.getDriverById(Mockito.anyString())).thenReturn(null);
        // Action
        boolean isLogin = driverAuthenticateUseCase.buildUseCaseObservable(busId, driverId, password);
        // Assert
        Assert.assertEquals(false, isLogin);
    }
*/
}
