package com.edulog.driverportal.routes.presentation.presenter;

import com.edulog.driverportal.common.presentation.BasePresenter;
import com.edulog.driverportal.common.presentation.BaseView;
import com.edulog.driverportal.routes.model.RouteModel;

import java.util.List;

public interface RouteHistoryContract {
    interface RouteHistoryView extends BaseView {
        public void showRouteHistory(List<RouteModel> routeModels);
    }

    abstract class RouteHistoryPresenter extends BasePresenter<RouteHistoryView> {
        public abstract void getRouteHistory();
    }

}
