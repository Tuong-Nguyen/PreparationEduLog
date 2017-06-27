package com.edulog.driverportal.routes.domain.interactor;

import com.edulog.driverportal.common.domain.UseCase;
import com.edulog.driverportal.routes.domain.service.RouteService;
import com.edulog.driverportal.routes.model.RouteModel;
import com.edulog.driverportal.routes.model.RouteModelDataMapper;

import java.util.List;

import io.reactivex.Observable;

public class SearchRoutesUseCase extends UseCase<List<RouteModel>, String> {
    private RouteService routeService;

    public SearchRoutesUseCase(RouteService routeService) {
        this.routeService = routeService;
    }

    @Override
    public Observable<List<RouteModel>> buildUseCaseObservable(String query) {
        return routeService.findRoutes(query)
                .map(RouteModelDataMapper::transform);
    }
}
