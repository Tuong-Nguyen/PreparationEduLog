package com.edulog.driverportal.routes.domain;

import com.edulog.driverportal.base.UseCase;
import com.edulog.driverportal.routes.model.Route;

import java.util.List;

import io.reactivex.Observable;

public class SearchRoutesUseCase extends UseCase<List<Route>, String> {
    private RouteService routeService;

    public SearchRoutesUseCase(RouteService routeService) {
        this.routeService = routeService;
    }

    @Override
    public Observable<List<Route>> buildUseCaseObservable(String query) {
        return routeService.findRoutes(query);
    }
}
