package edu.h2.layoutdemo.login;

import org.junit.Test;
import org.mockito.Mockito;

import edu.h2.layoutdemo.login.presenter.LoginPresenter;
import edu.h2.layoutdemo.login.presenter.LoginPresenterImplement;
import edu.h2.layoutdemo.login.repositories.DriverRepository;
import edu.h2.layoutdemo.login.usecase.DriverAuthenticateUseCase;

import static org.mockito.Mockito.when;

/**
 * Created by ntmhanh on 6/13/2017.
 */

public class DriverAuthenticateTest {
    @Test
    public void validateCredentials_InputDriverExist_returnLoginSuccess(){

        String busId = "1";
        String driverId = "2";
        String password = "123";
        Driver driver = new Driver("1","2", "123");
        LoginPresenter.RequireLoginPresenterOptions requireLoginPresenterOptions = Mockito.mock(LoginPresenterImplement.class);
        DriverRepository driverRepository = Mockito.mock(DriverRepository.class);
        when(driverRepository.getDriverById(Mockito.anyString())).thenReturn(driver);

        DriverAuthenticateUseCase driverAuthenticateUseCase = new DriverAuthenticateUseCase(requireLoginPresenterOptions);
        when(driverAuthenticateUseCase.driverRepository.getDriverById(Mockito.anyString())).thenReturn(driver);
        driverAuthenticateUseCase.validateCredentials(busId, driverId, password);
        //verify(requireLoginPresenterOptions.onLoginSuccess())
}
}
