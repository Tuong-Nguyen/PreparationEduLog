package com.edulog.driverportal.routeselection.presentation.presenter;

import com.edulog.driverportal.common.presentation.BasePresenter;
import com.edulog.driverportal.routeselection.presentation.view.RouteSelectionView;

public interface RouteSelectionPresenter extends BasePresenter<RouteSelectionView> {
    void suggestRouteIds(String query);
}
