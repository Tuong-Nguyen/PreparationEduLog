package com.edulog.driverportal.routeselection.domain.repository;

import com.edulog.driverportal.routeselection.data.entity.DriverEntity;
import com.edulog.driverportal.routeselection.data.entity.RouteEntity;

import java.util.List;

public interface RouteRepository {
    int upsert(RouteEntity routeEntity);
    RouteEntity findOne(String routeId);
    List<RouteEntity> findByDriverId(String driverId);
}
