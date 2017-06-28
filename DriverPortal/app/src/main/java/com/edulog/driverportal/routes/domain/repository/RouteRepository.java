package com.edulog.driverportal.routes.domain.repository;

import com.edulog.driverportal.routes.data.entity.RouteEntity;

import java.util.List;

// why repository return model instead of entity
public interface RouteRepository {
    int upsert(RouteEntity routeEntity);

    RouteEntity findOne(String routeId);

    List<RouteEntity> findAll();
}
