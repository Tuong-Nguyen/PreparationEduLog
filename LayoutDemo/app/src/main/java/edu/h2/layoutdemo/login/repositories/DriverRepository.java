package edu.h2.layoutdemo.login.repositories;


import edu.h2.layoutdemo.login.models.Driver;
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

}
