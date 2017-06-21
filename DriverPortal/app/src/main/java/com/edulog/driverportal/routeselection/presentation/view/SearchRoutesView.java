package com.edulog.driverportal.routeselection.presentation.view;

import com.edulog.driverportal.common.presentation.BaseView;
import com.edulog.driverportal.routeselection.presentation.model.RouteModel;

import java.util.List;

public interface SearchRoutesView extends BaseView {
    void showSearchRouteResults(List<RouteModel> routeModels);
}
