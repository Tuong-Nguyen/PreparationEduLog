package com.edulog.driverportal.routeselection.domain.interactor;

import com.edulog.driverportal.common.domain.UseCase;
import com.edulog.driverportal.routeselection.domain.service.RouteService;
import com.edulog.driverportal.routeselection.model.RouteModel;
import com.edulog.driverportal.routeselection.model.RouteModelDataMapper;

import io.reactivex.Observable;
import io.reactivex.Scheduler;

public class PreviewRouteUseCase extends UseCase<RouteModel, String> {
    private RouteService routeService;

    public PreviewRouteUseCase(Scheduler postExecutionScheduler, RouteService routeService) {
        super(postExecutionScheduler);

        this.routeService = routeService;
    }

    // TODO: Transform should be moved to Presenter class
    @Override
    protected Observable<RouteModel> buildUseCaseObservable(String routeId) {
        return routeService.getRoute(routeId)
                .map(RouteModelDataMapper::transform);
    }
}
