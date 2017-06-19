package com.edulog.driverportal.routeselection.domain.interactor;

import com.edulog.driverportal.common.domain.UseCase;
import com.edulog.driverportal.routeselection.data.entity.RouteEntity;
import com.edulog.driverportal.routeselection.domain.service.SearchRoutesService;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.Scheduler;
import io.reactivex.schedulers.Schedulers;

public class RouteIdSuggestionsUseCase extends UseCase<List<String>, String> {
    private SearchRoutesService searchRoutesService;

    public RouteIdSuggestionsUseCase(Scheduler postExecutionScheduler, SearchRoutesService searchRoutesService) {
        super(postExecutionScheduler);

        this.searchRoutesService = searchRoutesService;
    }

    @Override
    protected Observable<List<String>> buildUseCaseObservable(String query) {
        return searchRoutesService.findRoutesById(query)
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
