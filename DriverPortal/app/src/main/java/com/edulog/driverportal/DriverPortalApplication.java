package com.edulog.driverportal;

import android.app.Application;

import com.edulog.driverportal.common.net.RetrofitServiceGenerator;

public class DriverPortalApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();

        RetrofitServiceGenerator.baseUrl = "https://obscure-mesa-13550.herokuapp.com/";
    }
}
