package edu.h2.layoutdemo.login.domain.repositories;


import edu.h2.layoutdemo.login.models.Driver;
import io.reactivex.Observable;

/**
 * Created by ntmhanh on 6/12/2017.
 */

public interface IDriverRepository {
     Observable<Driver> getDriverById(String Id);
     Observable<Boolean> authenticate(String driverId, String password);

}
