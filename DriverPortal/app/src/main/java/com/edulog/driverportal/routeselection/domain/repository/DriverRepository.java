package com.edulog.driverportal.routeselection.domain.repository;

import com.edulog.driverportal.routeselection.data.entity.DriverEntity;

public interface DriverRepository {
    int upsert(DriverEntity driverEntity);

    DriverEntity findOne(String driverId);
}
