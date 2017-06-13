package edu.h2.layoutdemo.login.repositories;


import edu.h2.layoutdemo.login.Driver;
import io.reactivex.Observable;

/**
 * Created by ntmhanh on 6/12/2017.
 */

public interface IDriverRepository {
     Observable<Driver> getDriverById(String Id);
}
