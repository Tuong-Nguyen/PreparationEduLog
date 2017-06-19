package com.edulog.driverportal.routeselection.presentation.presenter;

import com.edulog.driverportal.common.presentation.BasePresenter;
import com.edulog.driverportal.routeselection.presentation.view.SearchRoutesView;

public interface SearchRoutesPresenter extends BasePresenter<SearchRoutesView> {
    void suggestRouteIds(String query);
    void searchRoutes(String query);
}
