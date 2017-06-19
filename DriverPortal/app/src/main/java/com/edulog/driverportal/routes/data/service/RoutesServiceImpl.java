package com.edulog.driverportal.routes.data.service;

import com.edulog.driverportal.common.util.RetrofitServiceGenerator;
import com.edulog.driverportal.routes.data.entity.RouteEntity;
import com.edulog.driverportal.routes.data.net.EduLogRoutesService;
import com.edulog.driverportal.routes.domain.service.RoutesService;

import java.util.List;

import io.reactivex.Observable;
import io.reactivex.schedulers.Schedulers;

public class RoutesServiceImpl implements RoutesService {
    @Override
    public Observable<List<RouteEntity>> findRoutes(String query) {
        EduLogRoutesService eduLogRoutesService = RetrofitServiceGenerator.generate(EduLogRoutesService.class);
        return eduLogRoutesService.findRoutes(query)
                .subscribeOn(Schedulers.io());
    }
}
