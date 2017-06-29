package com.edulog.driverportal.routes.presentation;

import com.edulog.driverportal.common.presentation.BasePresenter;
import com.edulog.driverportal.common.presentation.BaseView;
import com.edulog.driverportal.routes.model.LoadMode;
import com.edulog.driverportal.routes.model.Route;

public interface RouteDetailsContract {
    interface RouteDetailsView extends BaseView {
        void showRouteDetails(Route routeModel);
    }

    abstract class RouteDetailsPresenter extends BasePresenter<RouteDetailsView> {
        public abstract void setActiveRoute(String routeId, LoadMode loadMode);
    }
}