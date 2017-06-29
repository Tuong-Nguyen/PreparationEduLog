package com.edulog.driverportal.routes.presentation;

import com.edulog.driverportal.common.presentation.DefaultObserver;
import com.edulog.driverportal.routes.model.Route;
import com.edulog.driverportal.routes.domain.SearchRoutesUseCase;

import java.util.List;

import io.reactivex.observers.DisposableObserver;

public class SearchRoutesPresenterImpl extends SearchRoutesContract.SearchRoutesPresenter {
    private SearchRoutesContract.SearchRoutesView searchRoutesView;
    private SearchRoutesUseCase searchRoutesUseCase;
    private DisposableObserver<List<Route>> searchRoutesObserver;

    public SearchRoutesPresenterImpl(SearchRoutesUseCase searchRoutesUseCase) {
        this.searchRoutesUseCase = searchRoutesUseCase;
    }

    @Override
    public void attach(SearchRoutesContract.SearchRoutesView searchRoutesView) {
        this.searchRoutesView = searchRoutesView;
    }

    @Override
    public void detach() {
        super.detach();
        this.searchRoutesView = null;
    }

    @Override
    public void searchRoutes(String query) {
        disposeObserver(searchRoutesObserver);
        searchRoutesObserver = createSearchRoutesObserver();
        addDisposable(searchRoutesObserver);

        searchRoutesView.showProgress();

        searchRoutesUseCase.execute(searchRoutesObserver, query);
    }

    private DisposableObserver<List<Route>> createSearchRoutesObserver() {
        return new DefaultObserver<List<Route>>() {
            @Override
            public void onNext(List<Route> routes) {
                searchRoutesView.hideProgress();
                searchRoutesView.showSearchRouteResults(routes);
            }

            @Override
            public void onError(Throwable e) {
                searchRoutesView.hideProgress();
                searchRoutesView.showError(e.getMessage());
            }
        };
    }
}
