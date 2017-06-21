package com.edulog.driverportal.routeselection.domain.interactor;

import com.edulog.driverportal.common.domain.UseCase;
import com.edulog.driverportal.routeselection.domain.service.RouteService;
import com.edulog.driverportal.routeselection.presentation.model.RouteModel;
import com.edulog.driverportal.routeselection.presentation.model.RouteModelDataMapper;

import java.util.List;

import io.reactivex.Observable;

public class SearchRoutesUseCase extends UseCase<List<RouteModel>, String> {
    private RouteService routeService;

    public SearchRoutesUseCase(RouteService routeService) {
        this.routeService = routeService;
    }

    // TODO: Transform should be moved to Presenter class
    @Override
    protected Observable<List<RouteModel>> buildUseCaseObservable(String query) {
        return routeService.findRoutes(query)
                .map(RouteModelDataMapper::transform);
    }
}
