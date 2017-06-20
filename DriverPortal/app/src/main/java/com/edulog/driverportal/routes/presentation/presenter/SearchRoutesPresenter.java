package com.edulog.driverportal.routes.presentation.presenter;

import com.edulog.driverportal.common.presentation.BasePresenter;
import com.edulog.driverportal.routes.presentation.view.SearchRoutesView;

public interface SearchRoutesPresenter extends BasePresenter<SearchRoutesView> {
    void searchRoutes(String query);
}
