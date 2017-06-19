package com.edulog.driverportal;

import android.app.Application;

import com.edulog.driverportal.common.util.RetrofitServiceGenerator;
import com.edulog.driverportal.routes.presentation.device.Session;
import com.edulog.driverportal.routes.presentation.device.SessionImpl;

public class DriverPortalApplication extends Application {
    private Session session;

    @Override
    public void onCreate() {
        super.onCreate();

        RetrofitServiceGenerator.baseUrl = "https://obscure-mesa-13550.herokuapp.com/";
        session = new SessionImpl(this);
    }

    public Session getSession() {
        return session;
    }
}
