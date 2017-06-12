package edu.h2.layoutdemo.login.repositories;

import edu.h2.layoutdemo.login.Driver;

/**
 * Created by ntmhanh on 6/12/2017.
 */

public interface IDriverRepository {
    Driver getDriverById(String Id);

}
