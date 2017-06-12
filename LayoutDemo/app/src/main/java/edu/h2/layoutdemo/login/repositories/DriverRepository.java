package edu.h2.layoutdemo.login.repositories;

import edu.h2.layoutdemo.login.Driver;

/**
 * Created by ntmhanh on 6/12/2017.
 */

public class DriverRepository implements IDriverRepository {
    private Driver driver;

    @Override
    public Driver getDriverById(String Id) {
        driver = new Driver(Driver.BUSID, Driver.DRIVERID, Driver.PASSWORD);
        return driver;
    }
}
