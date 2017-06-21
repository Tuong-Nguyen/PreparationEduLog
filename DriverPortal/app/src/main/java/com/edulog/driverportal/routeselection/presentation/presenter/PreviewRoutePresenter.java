package com.edulog.driverportal.routeselection.presentation.presenter;

import com.edulog.driverportal.common.presentation.BasePresenter;
import com.edulog.driverportal.routeselection.presentation.view.PreviewRouteView;

public abstract class PreviewRoutePresenter extends BasePresenter<PreviewRouteView> {
    public abstract void previewRoute(String routeId);
}
