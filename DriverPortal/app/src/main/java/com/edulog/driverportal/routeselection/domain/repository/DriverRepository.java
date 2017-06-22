package com.edulog.driverportal.routeselection.domain.repository;

import com.edulog.driverportal.routeselection.data.entity.DriverEntity;
import com.edulog.driverportal.routeselection.data.entity.RouteEntity;

public interface DriverRepository {
    int upsert(DriverEntity driverEntity);

    DriverEntity findOne(String driverId);
}
