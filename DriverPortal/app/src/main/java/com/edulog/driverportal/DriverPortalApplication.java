package com.edulog.driverportal;

import android.app.Application;

import com.edulog.driverportal.common.device.Session;
import com.edulog.driverportal.common.device.SessionImpl;

public class DriverPortalApplication extends Application {
    private Session session;

    @Override
    public void onCreate() {
        super.onCreate();

        session = new SessionImpl(this);

        session.putRouteId("route01");
    }

    public Session getSession() {
        return session;
    }
}
