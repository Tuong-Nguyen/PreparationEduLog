package com.edulog.driverportal.routeselection.presentation.presenter;

import com.edulog.driverportal.common.presentation.BasePresenter;
import com.edulog.driverportal.common.presentation.BaseView;
import com.edulog.driverportal.routeselection.model.RouteModel;

public interface PreviewRouteContract {
    abstract class PreviewRoutePresenter extends BasePresenter<PreviewRouteView> {
        public abstract void previewRoute(String routeId);
    }

    interface PreviewRouteView extends BaseView {
        void showPreviewRoute(RouteModel routeModel);
    }
}
