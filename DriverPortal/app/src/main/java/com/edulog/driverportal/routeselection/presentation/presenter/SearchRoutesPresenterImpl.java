package com.edulog.driverportal.routeselection.presentation.presenter;

import com.edulog.driverportal.common.presentation.CompositeDisposableObserver;
import com.edulog.driverportal.common.presentation.DefaultObserver;
import com.edulog.driverportal.routeselection.domain.interactor.SearchRoutesUseCase;
import com.edulog.driverportal.routeselection.model.RouteModel;
import com.edulog.driverportal.routeselection.presentation.view.SearchRoutesView;

import java.util.List;

import io.reactivex.observers.DisposableObserver;

public class SearchRoutesPresenterImpl implements SearchRoutesPresenter {
    private SearchRoutesView searchRoutesView;
    private SearchRoutesUseCase searchRoutesUseCase;
    private CompositeDisposableObserver disposables;

    public SearchRoutesPresenterImpl(SearchRoutesUseCase searchRoutesUseCase) {
        this.searchRoutesUseCase = searchRoutesUseCase;

        disposables = new CompositeDisposableObserver();
    }

    @Override
    public void attach(SearchRoutesView searchRoutesView) {
        this.searchRoutesView = searchRoutesView;
    }

    @Override
    public void detach() {
        this.searchRoutesView = null;
        disposables.dispose();
    }

    @Override
    public void onError(String message) {

    }

    @Override
    public void searchRoutes(String query) {
        DisposableObserver<List<RouteModel>> searchRoutesObserver = createSearchRoutesObserver();
        disposables.add(searchRoutesObserver);

        searchRoutesUseCase.execute(searchRoutesObserver, query);

        searchRoutesView.showProgress();
    }

    // TODO: Add error handler
    private DisposableObserver<List<RouteModel>> createSearchRoutesObserver() {
        return new DefaultObserver<List<RouteModel>>() {
            @Override
            public void onNext(List<RouteModel> routeModels) {
                searchRoutesView.showSearchRouteResults(routeModels);
            }
        };
    }
}
