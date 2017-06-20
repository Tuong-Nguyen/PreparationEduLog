package com.edulog.driverportal.routes.presentation.presenter;

import com.edulog.driverportal.common.presentation.BasePresenter;
import com.edulog.driverportal.routes.presentation.view.PreviewRouteView;

public interface PreviewRoutePresenter extends BasePresenter<PreviewRouteView> {
    void previewRoute(String routeId);
}
