package com.edulog.driverportal.routeselection.presentation.presenter;

import com.edulog.driverportal.common.presentation.BasePresenter;
import com.edulog.driverportal.routeselection.presentation.view.SearchRoutesView;

public interface SearchRoutesPresenter extends BasePresenter<SearchRoutesView> {
    void searchRoutes(String query);
}
