package com.edulog.driverportal.login.domain.repositories;


import com.edulog.driverportal.login.models.Driver;
import io.reactivex.Observable;

/**
 * Created by ntmhanh on 6/12/2017.
 */

public class DriverRepository implements IDriverRepository {
    private Driver driver;

    @Override
    public Observable<Driver> getDriverById(String Id) {
        driver = new Driver(Driver.BUSID, Driver.DRIVERID, Driver.PASSWORD);
        return Observable.just(driver);
    }

    @Override
    public Observable<Boolean> authenticate(String driverId, String password) {
        return Observable.just(true);
    }

}
