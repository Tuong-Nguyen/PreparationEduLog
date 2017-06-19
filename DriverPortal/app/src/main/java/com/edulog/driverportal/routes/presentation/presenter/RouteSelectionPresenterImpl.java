package com.edulog.driverportal.routes.presentation.presenter;

import com.edulog.driverportal.routes.domain.interactor.RouteIdSuggestionsUseCase;
import com.edulog.driverportal.routes.presentation.view.RouteSelectionView;

import java.util.List;

import io.reactivex.observers.DisposableObserver;

public class RouteSelectionPresenterImpl implements RouteSelectionPresenter {
    private RouteSelectionView routeSelectionView;
    private RouteIdSuggestionsUseCase routeIdSuggestionsUseCase;

    public RouteSelectionPresenterImpl(RouteIdSuggestionsUseCase routeIdSuggestionsUseCase) {
        this.routeIdSuggestionsUseCase = routeIdSuggestionsUseCase;
    }

    @Override
    public void attach(RouteSelectionView routeSelectionView) {
        this.routeSelectionView = routeSelectionView;
    }

    @Override
    public void detach() {
        routeSelectionView = null;
    }

    @Override
    public void onError(String message) {

    }

    @Override
    public void suggestRouteIds(String query) {
        routeIdSuggestionsUseCase.execute(createRouteIdSuggestionsObserver(), query);
        routeSelectionView.showProgress();
    }

    private DisposableObserver<List<String>> createRouteIdSuggestionsObserver() {
        return new DisposableObserver<List<String>>() {
            @Override
            public void onNext(List<String> ids) {
                routeSelectionView.showRouteIdsSuggestion(ids);
            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onComplete() {

            }
        };
    }
}
