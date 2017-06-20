package com.edulog.driverportal.routeselection.presentation.view;

import com.edulog.driverportal.common.presentation.BaseView;
import com.edulog.driverportal.routeselection.model.RouteModel;

// TODO: Should we define a View<T> with showResult(T)
public interface PreviewRouteView extends BaseView {
    void showPreviewRoute(RouteModel routeModel);
}
