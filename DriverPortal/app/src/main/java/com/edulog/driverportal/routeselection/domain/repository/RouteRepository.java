package com.edulog.driverportal.routeselection.domain.repository;

import com.edulog.driverportal.routeselection.data.entity.RouteEntity;

import java.util.List;

public interface RouteRepository {
    // TODO: upsert is a method we would use (ie: insert if does not exist - otherwise, update)
    long insert(RouteEntity routeEntity);

    void delete(String routeId);

    RouteEntity findOne(String routeId);

    List<RouteEntity> findAll();
}
