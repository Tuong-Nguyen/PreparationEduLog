package com.edulog.driverportal.routes.data.service;

import com.edulog.driverportal.routes.data.entity.RouteEntity;
import com.edulog.driverportal.routes.data.net.DriverPortalRouteService;
import com.edulog.driverportal.routes.domain.service.RouteService;

import java.util.List;

import io.reactivex.Observable;
import io.reactivex.schedulers.Schedulers;

public class RouteServiceImpl implements RouteService {
    private DriverPortalRouteService driverPortalRouteService;

    public RouteServiceImpl(DriverPortalRouteService driverPortalRouteService) {
        this.driverPortalRouteService = driverPortalRouteService;
    }

    @Override
    public Observable<RouteEntity> getRoute(String routeId) {
        return driverPortalRouteService.getRoute(routeId)
                .subscribeOn(Schedulers.io());
    }

    @Override
    public Observable<List<RouteEntity>> findRoutes(String query) {
        return driverPortalRouteService.findRoutes(query)
                .subscribeOn(Schedulers.io());
    }
}
