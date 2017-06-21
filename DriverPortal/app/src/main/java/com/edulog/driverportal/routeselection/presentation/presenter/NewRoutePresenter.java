package com.edulog.driverportal.routeselection.presentation.presenter;

import com.edulog.driverportal.common.presentation.BasePresenter;
import com.edulog.driverportal.routeselection.presentation.view.NewRouteView;

public abstract class NewRoutePresenter extends BasePresenter<NewRouteView> {
    public abstract void suggestRouteIds(String query);
}
