package com.edulog.driverportal.routes.domain.interactor;

import com.edulog.driverportal.common.domain.UseCase;
import com.edulog.driverportal.routes.data.entity.RouteEntity;
import com.edulog.driverportal.routes.domain.service.RouteService;
import com.edulog.driverportal.routes.model.RouteModel;
import com.edulog.driverportal.routes.model.RouteModelDataMapper;

import io.reactivex.Observable;
import io.reactivex.Scheduler;

public class PreviewRouteUseCase extends UseCase<RouteModel, String> {
    private RouteService routeService;

    public PreviewRouteUseCase(Scheduler postExecutionScheduler, RouteService routeService) {
        super(postExecutionScheduler);

        this.routeService = routeService;
    }

    @Override
    protected Observable<RouteModel> buildUseCaseObservable(String routeId) {
        return routeService.getRoute(routeId)
                .map(RouteModelDataMapper::transform);
    }
}
