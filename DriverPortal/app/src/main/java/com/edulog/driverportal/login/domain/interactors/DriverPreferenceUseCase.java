package com.edulog.driverportal.login.domain.interactors;

import com.edulog.driverportal.login.domain.services.DriverPreferences;

/**
 * Created by ntmhanh on 6/23/2017.
 */

public class DriverPreferenceUseCase  {
    private DriverPreferences driverPreferences;

    public DriverPreferenceUseCase(DriverPreferences driverPreferences) {
        this.driverPreferences = driverPreferences;
    }

    public boolean getDriverId(){
       return driverPreferences.getDriverId().isEmpty();
    }
    public String driverId(){
        return driverPreferences.getDriverId();
    }
}
