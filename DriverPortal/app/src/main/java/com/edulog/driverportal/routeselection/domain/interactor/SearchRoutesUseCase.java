package com.edulog.driverportal.routeselection.domain.interactor;

import com.edulog.driverportal.common.domain.UseCase;
import com.edulog.driverportal.routeselection.domain.service.RouteService;
import com.edulog.driverportal.routeselection.model.RouteModel;
import com.edulog.driverportal.routeselection.model.RouteModelDataMapper;

import java.util.List;

import io.reactivex.Observable;
import io.reactivex.Scheduler;

public class SearchRoutesUseCase extends UseCase<List<RouteModel>, String> {
    private RouteService routeService;

    public SearchRoutesUseCase(Scheduler postExecutionScheduler, RouteService routeService) {
        super(postExecutionScheduler);

        this.routeService = routeService;
    }

    // TODO: Transform should be moved to Presenter class
    @Override
    protected Observable<List<RouteModel>> buildUseCaseObservable(String query) {
        return routeService.findRoutes(query)
                .map(RouteModelDataMapper::transform);
    }
}
