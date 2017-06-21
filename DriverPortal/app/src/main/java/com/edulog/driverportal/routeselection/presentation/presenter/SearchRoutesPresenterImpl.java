package com.edulog.driverportal.routeselection.presentation.presenter;

import com.edulog.driverportal.common.presentation.CompositeDisposableObserver;
import com.edulog.driverportal.common.presentation.DefaultObserver;
import com.edulog.driverportal.routeselection.domain.interactor.SearchRoutesUseCase;
import com.edulog.driverportal.routeselection.presentation.model.RouteModel;
import com.edulog.driverportal.routeselection.presentation.view.SearchRoutesView;

import java.util.List;

import io.reactivex.observers.DisposableObserver;

public class SearchRoutesPresenterImpl extends SearchRoutesPresenter {
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
        super.detach();
        this.searchRoutesView = null;
    }

    @Override
    public void searchRoutes(String query) {
        DisposableObserver<List<RouteModel>> searchRoutesObserver = createSearchRoutesObserver();
        addDisposable(searchRoutesObserver);

        searchRoutesView.showProgress();

        searchRoutesUseCase.execute(searchRoutesObserver, query);
    }

    private DisposableObserver<List<RouteModel>> createSearchRoutesObserver() {
        return new DefaultObserver<List<RouteModel>>() {
            @Override
            public void onNext(List<RouteModel> routeModels) {
                searchRoutesView.hideProgress();
                searchRoutesView.showSearchRouteResults(routeModels);
            }

            @Override
            public void onError(Throwable e) {
                searchRoutesView.hideProgress();
                searchRoutesView.showError(e.getMessage());
            }
        };
    }
}
