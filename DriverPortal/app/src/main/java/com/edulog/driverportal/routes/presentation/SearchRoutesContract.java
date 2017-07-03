package com.edulog.driverportal.routes.presentation;

import com.edulog.driverportal.base.BasePresenter;
import com.edulog.driverportal.base.BaseView;
import com.edulog.driverportal.routes.model.Route;

import java.util.List;

public interface SearchRoutesContract {
    interface View extends BaseView {
        void showSearchRouteResults(List<Route> routes);
    }

    abstract class Presenter extends BasePresenter<View> {
        public abstract void searchRoutes(String query);
    }
}
