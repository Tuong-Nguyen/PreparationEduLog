package com.edulog.driverportal.routes.presentation;

import com.edulog.driverportal.common.presentation.DefaultObserver;
import com.edulog.driverportal.routes.model.Route;
import com.edulog.driverportal.routes.domain.ShowRouteHistoryUseCase;

import java.util.List;

import io.reactivex.observers.DisposableObserver;

public class RouteHistoryPresenterImpl extends RouteHistoryContract.RouteHistoryPresenter {
    private RouteHistoryContract.RouteHistoryView routeHistoryView;
    private ShowRouteHistoryUseCase showRouteHistoryUseCase;
    private DisposableObserver<List<Route>> routeHistoryObserver;

    public RouteHistoryPresenterImpl(ShowRouteHistoryUseCase showRouteHistoryUseCase) {
        this.showRouteHistoryUseCase = showRouteHistoryUseCase;
    }

    @Override
    public void attach(RouteHistoryContract.RouteHistoryView routeHistoryView) {
        this.routeHistoryView = routeHistoryView;
    }

    @Override
    public void detach() {
        super.detach();
    }

    @Override
    public void getRouteHistory() {
        disposeObserver(routeHistoryObserver);
        routeHistoryObserver = createRouteHistoryObserver();
        addDisposable(routeHistoryObserver);

        routeHistoryView.showProgress();
        showRouteHistoryUseCase.execute(routeHistoryObserver, null);
    }

    private DisposableObserver<List<Route>> createRouteHistoryObserver() {
        return new DefaultObserver<List<Route>>() {
            @Override
            public void onNext(List<Route> routes) {
                routeHistoryView.hideProgress();
                routeHistoryView.showRouteHistory(routes);
            }

            @Override
            public void onError(Throwable e) {
                routeHistoryView.hideProgress();
                routeHistoryView.showError(e.getMessage());
            }
        };
    }
}
