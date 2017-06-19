package com.edulog.driverportal.routeselection.presentation.presenter;

import com.edulog.driverportal.routeselection.domain.interactor.SearchRoutesUseCase;
import com.edulog.driverportal.routeselection.domain.interactor.RouteIdSuggestionsUseCase;
import com.edulog.driverportal.routeselection.model.RouteModel;
import com.edulog.driverportal.routeselection.presentation.view.SearchRoutesView;

import java.util.List;

import io.reactivex.observers.DisposableObserver;

public class SearchRoutesPresenterImpl implements SearchRoutesPresenter {
    private SearchRoutesView searchRoutesView;
    private SearchRoutesUseCase searchRoutesUseCase;
    private RouteIdSuggestionsUseCase routeIdSuggestionsUseCase;

    public SearchRoutesPresenterImpl(SearchRoutesUseCase searchRoutesUseCase, RouteIdSuggestionsUseCase routeIdSuggestionsUseCase) {
        this.searchRoutesUseCase = searchRoutesUseCase;
        this.routeIdSuggestionsUseCase = routeIdSuggestionsUseCase;
    }

    @Override
    public void attach(SearchRoutesView searchRoutesView) {
        this.searchRoutesView = searchRoutesView;
    }

    @Override
    public void detach() {
        searchRoutesView = null;
    }

    @Override
    public void onError(String message) {

    }

    @Override
    public void suggestRouteIds(String query) {
        routeIdSuggestionsUseCase.execute(createRouteIdSuggestionsObserver(), query);
        searchRoutesView.showProgress();
    }

    @Override
    public void searchRoutes(String query) {
        searchRoutesUseCase.execute(createSearchRoutesObserver(), query);
    }

    private DisposableObserver<List<String>> createRouteIdSuggestionsObserver() {
        return new DisposableObserver<List<String>>() {
            @Override
            public void onNext(List<String> ids) {
                searchRoutesView.showRouteIdsSuggestion(ids);
            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onComplete() {

            }
        };
    }

    private DisposableObserver<List<RouteModel>> createSearchRoutesObserver() {
        return new DisposableObserver<List<RouteModel>>() {
            @Override
            public void onNext(List<RouteModel> routeModels) {
                searchRoutesView.showSearchRouteResults(routeModels);
            }

            @Override
            public void onError(Throwable e) {
                searchRoutesView.showError(e.getMessage());
            }

            @Override
            public void onComplete() {

            }
        };
    }
}
