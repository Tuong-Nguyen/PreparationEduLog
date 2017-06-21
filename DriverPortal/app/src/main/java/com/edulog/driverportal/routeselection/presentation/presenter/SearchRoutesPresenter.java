package com.edulog.driverportal.routeselection.presentation.presenter;

import com.edulog.driverportal.common.presentation.BasePresenter;
import com.edulog.driverportal.routeselection.presentation.view.SearchRoutesView;

public abstract class SearchRoutesPresenter extends BasePresenter<SearchRoutesView> {
    public abstract void searchRoutes(String query);
}
