package com.edulog.driverportal.routeselection.data.service;

import com.edulog.driverportal.common.util.RetrofitServiceGenerator;
import com.edulog.driverportal.routeselection.data.entity.RouteEntity;
import com.edulog.driverportal.routeselection.data.net.RoutesService;
import com.edulog.driverportal.routeselection.domain.service.RouteSelectionService;

import java.util.List;

import io.reactivex.Observable;
import io.reactivex.schedulers.Schedulers;

public class RouteSelectionServiceImpl implements RouteSelectionService {
    @Override
    public Observable<List<RouteEntity>> findRoutesById(String routeId) {
        RoutesService routesService = RetrofitServiceGenerator.generate(RoutesService.class);
        return routesService.findRoutesById(routeId)
                .subscribeOn(Schedulers.io());
    }
}
