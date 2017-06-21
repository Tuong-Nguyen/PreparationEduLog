package com.edulog.driverportal.routeselection.presentation.presenter;

import com.edulog.driverportal.common.presentation.BasePresenter;
import com.edulog.driverportal.routeselection.presentation.view.RouteDetailsView;

public abstract class RouteDetailsPresenter extends BasePresenter<RouteDetailsView> {
    public abstract void setActiveRoute(String routeId);
}
