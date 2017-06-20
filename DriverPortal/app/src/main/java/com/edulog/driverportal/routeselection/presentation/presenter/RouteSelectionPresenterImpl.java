package com.edulog.driverportal.routeselection.presentation.presenter;

import com.edulog.driverportal.common.presentation.CompositeDisposableObserver;
import com.edulog.driverportal.common.presentation.DefaultObserver;
import com.edulog.driverportal.routeselection.domain.interactor.RouteIdSuggestionsUseCase;
import com.edulog.driverportal.routeselection.presentation.view.RouteSelectionView;

import java.util.List;

import io.reactivex.observers.DisposableObserver;

public class RouteSelectionPresenterImpl implements RouteSelectionPresenter {
    private RouteSelectionView routeSelectionView;
    private RouteIdSuggestionsUseCase routeIdSuggestionsUseCase;
    private CompositeDisposableObserver disposables;

    public RouteSelectionPresenterImpl(RouteIdSuggestionsUseCase routeIdSuggestionsUseCase) {
        this.routeIdSuggestionsUseCase = routeIdSuggestionsUseCase;
        disposables = new CompositeDisposableObserver();
    }

    @Override
    public void attach(RouteSelectionView routeSelectionView) {
        this.routeSelectionView = routeSelectionView;
    }

    @Override
    public void detach() {
        routeSelectionView = null;
        disposables.dispose();
    }

    @Override
    public void onError(String message) {

    }

    @Override
    public void suggestRouteIds(String query) {
        DisposableObserver<List<String>> observer = createRouteIdSuggestionsObserver();
        disposables.add(observer);

        routeIdSuggestionsUseCase.execute(observer, query);

        routeSelectionView.showProgress();
    }

    private DisposableObserver<List<String>> createRouteIdSuggestionsObserver() {
        return new DefaultObserver<List<String>>() {
            @Override
            public void onNext(List<String> ids) {
                routeSelectionView.showRouteIdSuggestions(ids);
            }
        };
    }
}
