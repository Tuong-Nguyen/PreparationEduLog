package com.edulog.driverportal.routeselection.presentation.presenter;

import com.edulog.driverportal.common.presentation.BasePresenter;
import com.edulog.driverportal.common.presentation.BaseView;
import com.edulog.driverportal.routeselection.model.RouteModel;

public interface RouteDetailsContract {
    abstract class RouteDetailsPresenter extends BasePresenter<RouteDetailsView> {
        public abstract void setActiveRoute(String routeId);
    }

    interface RouteDetailsView extends BaseView {
        void showRouteDetails(RouteModel routeModel);
    }
}
