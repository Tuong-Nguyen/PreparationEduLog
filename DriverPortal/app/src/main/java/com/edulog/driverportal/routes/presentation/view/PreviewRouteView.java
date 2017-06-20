package com.edulog.driverportal.routes.presentation.view;

import com.edulog.driverportal.common.presentation.BaseView;
import com.edulog.driverportal.routes.model.RouteModel;

public interface PreviewRouteView extends BaseView {
    void showPreviewRoute(RouteModel routeModel);
}
