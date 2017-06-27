package com.edulog.driverportal.routes.domain.repository;

import com.edulog.driverportal.routes.data.entity.DriverEntity;

public interface DriverRepository {
    int upsert(DriverEntity driverEntity);

    DriverEntity findOne(String driverId);
}
