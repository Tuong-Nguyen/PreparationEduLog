package com.edulog.driverportal.routeselection.presentation.presenter;

import com.edulog.driverportal.common.presentation.BasePresenter;
import com.edulog.driverportal.routeselection.presentation.view.RouteDetailsView;

public interface RouteDetailsPresenter extends BasePresenter<RouteDetailsView> {
    void setActiveRoute(String routeId);
}
