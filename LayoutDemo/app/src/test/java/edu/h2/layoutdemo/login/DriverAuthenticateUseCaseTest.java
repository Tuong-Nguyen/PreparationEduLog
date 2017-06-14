package edu.h2.layoutdemo.login;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import edu.h2.layoutdemo.login.repositories.DriverRepository;
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
    DriverRepository driverRepository;

    @Before
    public void init(){
        driverRepository = Mockito.mock(DriverRepository.class);
        driverAuthenticateUseCase = new DriverAuthenticateUseCase(driverRepository);
    }
    @Test
    public void buildUseCaseObservable_inputLoginInformation_returnGetDriverByDriverIdWasCalled(){
        // Arrange
         DriverAuthenticateUseCase.Params params = new DriverAuthenticateUseCase.Params(busId, driverId, password);
        // Action
         driverAuthenticateUseCase.buildUseCaseObservable(params);
        // Assert
         verify(driverRepository).getDriverById(params.driverId);
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
