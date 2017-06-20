package com.edulog.driverportal.routes.data.service;

import com.edulog.driverportal.common.util.RetrofitServiceGenerator;
import com.edulog.driverportal.routes.data.entity.RouteEntity;
import com.edulog.driverportal.routes.data.net.DriverPortalRouteService;
import com.edulog.driverportal.routes.domain.service.RouteService;

import java.util.List;

import io.reactivex.Observable;
import io.reactivex.schedulers.Schedulers;

public class RouteServiceImpl implements RouteService {
    @Override
    public Observable<List<RouteEntity>> findRoutes(String query) {
        DriverPortalRouteService driverPortalRouteService = RetrofitServiceGenerator.generate(DriverPortalRouteService.class);
        return driverPortalRouteService.findRoutes(query)
                .subscribeOn(Schedulers.io());
    }
}
