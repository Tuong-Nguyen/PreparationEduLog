package com.edulog.driverportal.routes.presentation.presenter;

import com.edulog.driverportal.common.presentation.BasePresenter;
import com.edulog.driverportal.common.presentation.BaseView;
import com.edulog.driverportal.routes.model.RouteModel;

import java.util.List;

public interface SearchRoutesContract {
    interface SearchRoutesView extends BaseView {
        void showSearchRouteResults(List<RouteModel> routeModels);
    }

    abstract class SearchRoutesPresenter extends BasePresenter<SearchRoutesView> {
        public abstract void searchRoutes(String query);
    }
}
