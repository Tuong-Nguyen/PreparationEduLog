package com.edulog.driverportal.base;

import android.app.Application;

import com.edulog.driverportal.session.Session;
import com.edulog.driverportal.session.SessionImpl;

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
