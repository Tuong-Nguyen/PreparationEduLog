package com.edulog.driverportal.routeselection.domain.service;

import com.edulog.driverportal.routeselection.data.entity.RouteEntity;

import java.util.List;

import io.reactivex.Observable;

public interface RouteService {
    Observable<RouteEntity> getRoute(String routeId);

    Observable<List<RouteEntity>> findRoutes(String query);
}
