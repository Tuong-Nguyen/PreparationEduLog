package com.edulog.driverportal.routeselection.domain.interactor;

import com.edulog.driverportal.common.domain.UseCase;
import com.edulog.driverportal.routeselection.domain.service.RouteService;
import com.edulog.driverportal.routeselection.presentation.model.RouteModel;
import com.edulog.driverportal.routeselection.presentation.model.RouteModelDataMapper;

import io.reactivex.Observable;

public class PreviewRouteUseCase extends UseCase<RouteModel, String> {
    private RouteService routeService;

    public PreviewRouteUseCase(RouteService routeService) {
        this.routeService = routeService;
    }

    @Override
    protected Observable<RouteModel> buildUseCaseObservable(String routeId) {
        return routeService.getRoute(routeId)
                .map(RouteModelDataMapper::transform);
    }
}
