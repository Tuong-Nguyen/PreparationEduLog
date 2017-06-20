package com.edulog.driverportal.routes.presentation.presenter;

import com.edulog.driverportal.common.presentation.BasePresenter;
import com.edulog.driverportal.routes.presentation.view.RouteDetailsView;

public interface RouteDetailsPresenter extends BasePresenter<RouteDetailsView> {
    void setActiveRoute(String routeId);
}
