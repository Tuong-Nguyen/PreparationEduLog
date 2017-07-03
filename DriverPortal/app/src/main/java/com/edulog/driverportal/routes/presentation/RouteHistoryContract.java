package com.edulog.driverportal.routes.presentation;

import com.edulog.driverportal.base.BasePresenter;
import com.edulog.driverportal.base.BaseView;
import com.edulog.driverportal.routes.model.Route;

import java.util.List;

public interface RouteHistoryContract {
    interface View extends BaseView {
        void showRouteHistory(List<Route> routes);
    }

    abstract class Presenter extends BasePresenter<View> {
        public abstract void getRouteHistory();
    }

}
