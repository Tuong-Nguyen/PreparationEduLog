package com.edulog.driverportal.routeselection.domain.interactor;

import com.edulog.driverportal.common.domain.UseCase;
import com.edulog.driverportal.routeselection.domain.service.SearchRoutesService;
import com.edulog.driverportal.routeselection.model.RouteModel;
import com.edulog.driverportal.routeselection.model.mapper.RouteModelDataMapper;

import java.util.List;

import io.reactivex.Observable;
import io.reactivex.Scheduler;

public class SearchRoutesUseCase extends UseCase<List<RouteModel>, String> {
    private SearchRoutesService searchRoutesService;
    private RouteModelDataMapper routeModelDataMapper;

    public SearchRoutesUseCase(Scheduler postExecutionScheduler,
                               SearchRoutesService searchRoutesService,
                               RouteModelDataMapper routeModelDataMapper) {
        super(postExecutionScheduler);

        this.searchRoutesService = searchRoutesService;
        this.routeModelDataMapper = routeModelDataMapper;
    }

    @Override
    protected Observable<List<RouteModel>> buildUseCaseObservable(String query) {
        return searchRoutesService.findRoutesById(query)
                .map(routeModelDataMapper::transform);
    }
}
