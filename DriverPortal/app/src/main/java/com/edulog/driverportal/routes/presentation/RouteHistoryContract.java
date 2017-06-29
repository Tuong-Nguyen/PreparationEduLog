package com.edulog.driverportal.routes.presentation;

import com.edulog.driverportal.common.presentation.BasePresenter;
import com.edulog.driverportal.common.presentation.BaseView;
import com.edulog.driverportal.routes.model.Route;

import java.util.List;

public interface RouteHistoryContract {
    interface RouteHistoryView extends BaseView {
        public void showRouteHistory(List<Route> routes);
    }

    abstract class RouteHistoryPresenter extends BasePresenter<RouteHistoryView> {
        public abstract void getRouteHistory();
    }

}
