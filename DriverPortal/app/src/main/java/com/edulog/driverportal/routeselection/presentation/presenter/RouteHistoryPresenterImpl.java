package com.edulog.driverportal.routeselection.presentation.presenter;

import com.edulog.driverportal.routeselection.domain.interactor.ShowRouteHistoryUseCase;

public class RouteHistoryPresenterImpl extends RouteHistoryContract.RouteHistoryPresenter {
    private RouteHistoryContract.RouteHistoryView routeHistoryView;
    private ShowRouteHistoryUseCase showRouteHistoryUseCase;

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


}
