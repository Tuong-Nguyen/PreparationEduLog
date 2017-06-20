package com.edulog.driverportal.routes.presentation.view;

import com.edulog.driverportal.common.presentation.BaseView;

import java.util.List;

public interface RouteSelectionView extends BaseView {
    void showRouteIdSuggestions(List<String> routeIds);
}
