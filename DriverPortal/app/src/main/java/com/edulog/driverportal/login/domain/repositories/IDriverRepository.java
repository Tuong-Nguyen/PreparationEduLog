package com.edulog.driverportal.login.domain.repositories;


import com.edulog.driverportal.login.models.Driver;
import io.reactivex.Observable;

/**
 * Created by ntmhanh on 6/12/2017.
 */

public interface IDriverRepository {
     Observable<Driver> getDriverById(String Id);
     Observable<Boolean> authenticate(String driverId, String password);

}
