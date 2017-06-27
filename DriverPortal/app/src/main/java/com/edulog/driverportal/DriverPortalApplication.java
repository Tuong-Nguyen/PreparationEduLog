package com.edulog.driverportal;

import android.app.Application;

import com.edulog.driverportal.common.device.Session;
import com.edulog.driverportal.common.device.SessionImpl;
import com.edulog.driverportal.routes.data.entity.DriverEntity;

public class DriverPortalApplication extends Application {
    private Session session;

    @Override
    public void onCreate() {
        super.onCreate();

        session = new SessionImpl(this);

        // for testing purpose

        // session.clear();

        DriverEntity driverEntity = new DriverEntity();
        driverEntity.setId("driver01");
        session.putDriverId(driverEntity.getId());
        session.putRouteId("route01");
    }

    public Session getSession() {
        return session;
    }
}
