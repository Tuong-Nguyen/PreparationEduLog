package com.edulog.driverportal.routeselection.domain.interactor;

import com.edulog.driverportal.common.domain.UseCase;
import com.edulog.driverportal.routeselection.domain.service.RouteSelectionService;
import com.edulog.driverportal.routeselection.model.RouteModel;
import com.edulog.driverportal.routeselection.model.mapper.RouteModelDataMapper;

import java.util.List;

import io.reactivex.Observable;
import io.reactivex.Scheduler;

public class SearchRoutesUseCase extends UseCase<List<RouteModel>, SearchRoutesUseCase.Params> {
    private RouteSelectionService routeSelectionService;
    private RouteModelDataMapper routeModelDataMapper;

    public SearchRoutesUseCase(Scheduler postExecutionScheduler,
                               RouteSelectionService routeSelectionService,
                               RouteModelDataMapper routeModelDataMapper) {
        super(postExecutionScheduler);

        this.routeSelectionService = routeSelectionService;
        this.routeModelDataMapper = routeModelDataMapper;
    }

    @Override
    protected Observable<List<RouteModel>> buildUseCaseObservable(Params params) {
        String routeId = params.query;
        return routeSelectionService.findRoutesById(routeId)
                .map(routeModelDataMapper::transform);
    }

    public static Params buildParams(String query) {
        Params params = new Params();

        params.query = query;

        return params;
    }

    public static class Params {
        public String query;
    }
}
