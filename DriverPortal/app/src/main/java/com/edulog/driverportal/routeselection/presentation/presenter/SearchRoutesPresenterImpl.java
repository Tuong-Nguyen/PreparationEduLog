package com.edulog.driverportal.routeselection.presentation.presenter;

import com.edulog.driverportal.routeselection.domain.interactor.SearchRoutesUseCase;
import com.edulog.driverportal.routeselection.model.RouteModel;
import com.edulog.driverportal.routeselection.presentation.view.SearchRoutesView;

import java.util.List;

import io.reactivex.observers.DisposableObserver;

public class SearchRoutesPresenterImpl implements SearchRoutesPresenter {
    private SearchRoutesView searchRoutesView;
    private SearchRoutesUseCase searchRoutesUseCase;

    public SearchRoutesPresenterImpl(SearchRoutesUseCase searchRoutesUseCase) {
        this.searchRoutesUseCase = searchRoutesUseCase;
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
    public void searchRoutes(String query) {
        SearchRoutesUseCase.Params params = SearchRoutesUseCase.buildParams(query);
        searchRoutesUseCase.execute(createSearchRoutesObserver(), params);
    }

    private DisposableObserver<List<RouteModel>> createSearchRoutesObserver() {
        return new DisposableObserver<List<RouteModel>>() {
            @Override
            public void onNext(List<RouteModel> routeModels) {
                searchRoutesView.showRouteList(routeModels);
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
