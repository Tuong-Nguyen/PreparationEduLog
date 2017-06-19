package com.edulog.driverportal.routeselection.presentation.view;

import com.edulog.driverportal.common.presentation.BaseView;
import com.edulog.driverportal.routeselection.model.RouteModel;

import java.util.List;

public interface SearchRoutesView extends BaseView {
    void showRouteIdsSuggestion(List<String> routeIds);
    void showSearchRouteResults(List<RouteModel> routeModels);
}
