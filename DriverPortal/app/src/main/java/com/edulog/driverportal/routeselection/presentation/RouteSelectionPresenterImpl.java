package com.edulog.driverportal.routeselection.presentation;

import com.edulog.driverportal.routeselection.navigator.RouteSelectionNavigator;

public class RouteSelectionPresenterImpl implements RouteSelectionPresenter {
    private RouteSelectionView routeSelectionView;
    private RouteSelectionNavigator routeSelectionNavigator;

    public RouteSelectionPresenterImpl(RouteSelectionNavigator routeSelectionNavigator) {
        this.routeSelectionNavigator = routeSelectionNavigator;
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
    public void chooseRouteFromServer() {
        routeSelectionNavigator.navigateToSearchRoute();
    }
}
