package edu.h2.layoutdemo.login.usecase;

import edu.h2.layoutdemo.login.Driver;
import edu.h2.layoutdemo.login.presenter.LoginPresenter;
import edu.h2.layoutdemo.login.repositories.DriverRepository;

/**
 * Created by ntmhanh on 6/12/2017.
 */

public class DriverAuthenticateUseCase {

    private Driver driver;
    private DriverRepository driverRepository;
    private LoginPresenter.RequireLoginPresenterOptions mLoginPresenter;

    public DriverAuthenticateUseCase(LoginPresenter.RequireLoginPresenterOptions mLoginPresenter) {
        this.mLoginPresenter = mLoginPresenter;
        driverRepository = new DriverRepository();
    }


    public boolean validateCredentials(String busId, String driverId, String password) {
        driver = driverRepository.getDriverById(driverId);
        if ( driver != null){
          if (driver.getBusId().equals(busId) && driver.getPassword().equals(password)){
              mLoginPresenter.onValidateLogin();
              return true;
          }else{
              mLoginPresenter.onInvalidateLogin();
              return false;
          }
        }
        return false;
    }
}
