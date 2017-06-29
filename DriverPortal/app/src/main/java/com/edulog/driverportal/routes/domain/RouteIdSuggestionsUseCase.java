package com.edulog.driverportal.routes.domain;

import com.edulog.driverportal.common.domain.UseCase;
import com.edulog.driverportal.routes.model.Route;
import com.edulog.driverportal.routes.domain.RouteService;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import io.reactivex.Observable;

public class
RouteIdSuggestionsUseCase extends UseCase<List<String>, String> {
    private RouteService routeService;

    public RouteIdSuggestionsUseCase(RouteService routeService) {
        this.routeService = routeService;
    }

    @Override
    public Observable<List<String>> buildUseCaseObservable(String query) {
        return routeService.findRoutes(query)
                .map(this::getSuggestionIds);
    }

    private List<String> getSuggestionIds(List<Route> routeEntities) {
        List<String> ids;

        if (routeEntities != null && !routeEntities.isEmpty()) {
            ids = new ArrayList<>();
            for (Route route : routeEntities) {
                ids.add(route.getId());
            }
        } else {
            ids = Collections.emptyList();
        }

        return ids;
    }
}
