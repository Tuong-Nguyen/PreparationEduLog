package com.edulog.driverportal.routeselection.presentation.presenter;

import com.edulog.driverportal.common.presentation.BasePresenter;
import com.edulog.driverportal.common.presentation.BaseView;
import com.edulog.driverportal.routeselection.model.RouteModel;

import java.util.List;

public interface RouteHistoryContract {
    abstract class RouteHistoryPresenter extends BasePresenter<RouteHistoryView> {
        public abstract void getRouteHistory();
    }

    interface RouteHistoryView extends BaseView {
        public void showRouteHistory(List<RouteModel> routeModels);
    }

}
