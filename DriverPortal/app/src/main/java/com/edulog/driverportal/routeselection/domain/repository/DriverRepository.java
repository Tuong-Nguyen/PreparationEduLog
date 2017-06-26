package com.edulog.driverportal.routeselection.domain.repository;

import com.edulog.driverportal.routeselection.data.entity.DriverEntity;

// TODO: remove it if it is not used
public interface DriverRepository {
    int upsert(DriverEntity driverEntity);

    DriverEntity findOne(String driverId);
}
