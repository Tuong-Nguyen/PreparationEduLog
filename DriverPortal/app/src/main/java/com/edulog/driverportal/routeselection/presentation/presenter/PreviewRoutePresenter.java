package com.edulog.driverportal.routeselection.presentation.presenter;

import com.edulog.driverportal.common.presentation.BasePresenter;
import com.edulog.driverportal.routeselection.presentation.view.PreviewRouteView;

public interface PreviewRoutePresenter extends BasePresenter<PreviewRouteView> {
    void previewRoute(String routeId);
}
