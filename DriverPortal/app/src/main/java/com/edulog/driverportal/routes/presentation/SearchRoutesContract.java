package com.edulog.driverportal.routes.presentation;

import com.edulog.driverportal.common.presentation.BasePresenter;
import com.edulog.driverportal.common.presentation.BaseView;
import com.edulog.driverportal.routes.model.Route;

import java.util.List;

public interface SearchRoutesContract {
    interface SearchRoutesView extends BaseView {
        void showSearchRouteResults(List<Route> routes);
    }

    abstract class SearchRoutesPresenter extends BasePresenter<SearchRoutesView> {
        public abstract void searchRoutes(String query);
    }
}
