package com.edulog.driverportal.routes.presentation.presenter;

import com.edulog.driverportal.common.presentation.BasePresenter;
import com.edulog.driverportal.routes.presentation.view.RouteSelectionView;

public interface RouteSelectionPresenter extends BasePresenter<RouteSelectionView> {
    void suggestRouteIds(String query);
}
