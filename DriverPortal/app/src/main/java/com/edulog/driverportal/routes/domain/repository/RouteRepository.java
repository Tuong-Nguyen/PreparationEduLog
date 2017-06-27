package com.edulog.driverportal.routes.domain.repository;

import com.edulog.driverportal.routes.data.entity.RouteEntity;

import java.util.List;

public interface RouteRepository {
    int upsert(RouteEntity routeEntity);

    RouteEntity findOne(String routeId);

    List<RouteEntity> findAll();
}
