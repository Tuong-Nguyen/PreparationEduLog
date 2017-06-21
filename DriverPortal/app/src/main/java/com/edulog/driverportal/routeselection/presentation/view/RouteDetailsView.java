package com.edulog.driverportal.routeselection.presentation.view;

import com.edulog.driverportal.common.presentation.BaseView;
import com.edulog.driverportal.routeselection.presentation.model.RouteModel;

public interface RouteDetailsView extends BaseView {
    void showRouteDetails(RouteModel routeModel);
}
