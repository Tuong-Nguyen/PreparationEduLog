package com.edulog.driverportal.routeselection.domain.interactor;

import com.edulog.driverportal.common.domain.UseCase;
import com.edulog.driverportal.routeselection.data.entity.RouteEntity;
import com.edulog.driverportal.routeselection.domain.service.RouteService;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.Scheduler;

public class RouteIdSuggestionsUseCase extends UseCase<List<String>, String> {
    private RouteService routeService;

    public RouteIdSuggestionsUseCase(Scheduler postExecutionScheduler, RouteService routeService) {
        super(postExecutionScheduler);

        this.routeService = routeService;
    }

    @Override
    protected Observable<List<String>> buildUseCaseObservable(String query) {
        return routeService.findRoutes(query)
                .map(this::getSuggestionIds);
    }

    private List<String> getSuggestionIds(List<RouteEntity> routeEntities) {
        List<String> ids;

        if (routeEntities != null && !routeEntities.isEmpty()) {
            ids = new ArrayList<>();
            for (RouteEntity routeEntity : routeEntities) {
                ids.add(routeEntity.getId());
            }
        } else {
            ids = Collections.emptyList();
        }

        return ids;
    }
}
