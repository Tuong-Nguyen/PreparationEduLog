package com.edulog.driverportal.routes.domain.interactor;

import com.edulog.driverportal.common.domain.UseCase;
import com.edulog.driverportal.routes.data.entity.RouteEntity;
import com.edulog.driverportal.routes.domain.service.RoutesService;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.Scheduler;

public class RouteIdSuggestionsUseCase extends UseCase<List<String>, String> {
    private RoutesService routesService;

    public RouteIdSuggestionsUseCase(Scheduler postExecutionScheduler, RoutesService routesService) {
        super(postExecutionScheduler);

        this.routesService = routesService;
    }

    @Override
    protected Observable<List<String>> buildUseCaseObservable(String query) {
        return routesService.findRoutes(query)
                .map(this::getSuggestionIds);
    }

    private List<String> getSuggestionIds(List<RouteEntity> routeEntities) {
        List<String> ids = new ArrayList<>();
        for (RouteEntity routeEntity : routeEntities) {
            ids.add(routeEntity.getId());
        }
        return ids;
    }
}
