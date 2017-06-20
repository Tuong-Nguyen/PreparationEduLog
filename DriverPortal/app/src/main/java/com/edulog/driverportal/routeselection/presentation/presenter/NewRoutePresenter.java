package com.edulog.driverportal.routeselection.presentation.presenter;

import com.edulog.driverportal.common.presentation.BasePresenter;
import com.edulog.driverportal.routeselection.presentation.view.NewRouteView;

public interface NewRoutePresenter extends BasePresenter<NewRouteView> {
    void suggestRouteIds(String query);
}
