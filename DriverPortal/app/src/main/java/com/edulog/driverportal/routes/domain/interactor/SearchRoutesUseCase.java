package com.edulog.driverportal.routes.domain.interactor;

import com.edulog.driverportal.common.domain.UseCase;
import com.edulog.driverportal.routes.domain.service.RoutesService;
import com.edulog.driverportal.routes.model.RouteModel;
import com.edulog.driverportal.routes.model.RouteModelDataMapper;

import java.util.List;

import io.reactivex.Observable;
import io.reactivex.Scheduler;

public class SearchRoutesUseCase extends UseCase<List<RouteModel>, String> {
    private RoutesService routesService;

    public SearchRoutesUseCase(Scheduler postExecutionScheduler,
                               RoutesService routesService) {
        super(postExecutionScheduler);

        this.routesService = routesService;
    }

    @Override
    protected Observable<List<RouteModel>> buildUseCaseObservable(String query) {
        return routesService.findRoutes(query)
                .map(RouteModelDataMapper::transform);
    }
}
