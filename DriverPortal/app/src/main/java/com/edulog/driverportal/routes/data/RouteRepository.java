package com.edulog.driverportal.routes.data;

import com.edulog.driverportal.routes.model.Route;

import java.util.List;

// why repository return model instead of entity
public interface RouteRepository {
    int upsert(Route route);

    Route findOne(String routeId);

    List<Route> findAll();
}
