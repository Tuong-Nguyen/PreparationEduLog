package com.edulog.driverportal.routes.presentation;

import com.edulog.driverportal.base.BasePresenter;
import com.edulog.driverportal.base.BaseView;
import com.edulog.driverportal.routes.model.LoadMode;
import com.edulog.driverportal.routes.model.Route;

public interface RouteDetailsContract {
    interface View extends BaseView {
        void showRouteDetails(Route routeModel);
    }

    abstract class Presenter extends BasePresenter<View> {
        public abstract void setActiveRoute(String routeId, LoadMode loadMode);
    }
}
