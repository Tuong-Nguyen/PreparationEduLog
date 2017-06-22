package com.edulog.driverportal;

import android.app.Application;

import com.edulog.driverportal.common.util.RetrofitServiceGenerator;
import com.edulog.driverportal.common.preference.Session;
import com.edulog.driverportal.common.preference.SessionImpl;
import com.edulog.driverportal.routeselection.data.entity.DriverEntity;
import com.edulog.driverportal.routeselection.data.repository.DriverPortalDbHelper;
import com.edulog.driverportal.routeselection.data.repository.DriverRepositoryImpl;
import com.edulog.driverportal.routeselection.domain.repository.DriverRepository;

public class DriverPortalApplication extends Application {
    private Session session;

    @Override
    public void onCreate() {
        super.onCreate();

        RetrofitServiceGenerator.baseUrl = "https://obscure-mesa-13550.herokuapp.com/";
        session = new SessionImpl(this);

        // for testing purpose

        // session.clear();

        DriverEntity driverEntity = new DriverEntity();
        driverEntity.setId("driver01");
        DriverRepository driverRepository = new DriverRepositoryImpl(new DriverPortalDbHelper(this));
        driverRepository.upsert(driverEntity);
        session.putDriverId(driverEntity.getId());
    }

    public Session getSession() {
        return session;
    }
}
