package com.edulog.driverportal.routes.domain.repository;

import com.edulog.driverportal.routes.data.entity.RouteEntity;

import java.util.List;

public interface RouteRepository {
    long insert(RouteEntity routeEntity);

    void delete(String routeId);

    RouteEntity findOne(String routeId);

    List<RouteEntity> findAll();
}
