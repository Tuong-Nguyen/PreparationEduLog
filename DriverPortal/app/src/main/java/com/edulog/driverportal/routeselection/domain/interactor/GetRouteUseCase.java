package com.edulog.driverportal.routeselection.domain.interactor;

import com.edulog.driverportal.common.domain.UseCase;
import com.edulog.driverportal.routeselection.domain.service.RouteService;
import com.edulog.driverportal.routeselection.model.RouteModel;
import com.edulog.driverportal.routeselection.model.RouteModelDataMapper;

import io.reactivex.Observable;

public class GetRouteUseCase extends UseCase<RouteModel, String> {
    private RouteService routeService;

    public GetRouteUseCase(RouteService routeService) {
        this.routeService = routeService;
    }

    @Override
    public Observable<RouteModel> buildUseCaseObservable(String routeId) {
        return routeService.getRoute(routeId)
                .map(RouteModelDataMapper::transform);
    }
}
