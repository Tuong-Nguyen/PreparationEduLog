package com.edulog.driverportal.login.domain.interactors;

import com.edulog.driverportal.common.preference.Session;

/**
 * Created by ntmhanh on 6/23/2017.
 */

public class DevicePreferenceUseCase {
    private Session session;

    public DevicePreferenceUseCase(Session session) {
        this.session = session;
    }

    public boolean isDriverId(){
       return session.getDriverId().isEmpty();
    }
    public String driverId(){
        return session.getDriverId();
    }
}
