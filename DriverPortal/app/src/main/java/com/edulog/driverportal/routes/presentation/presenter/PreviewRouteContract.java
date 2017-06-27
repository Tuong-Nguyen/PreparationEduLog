package com.edulog.driverportal.routes.presentation.presenter;

import com.edulog.driverportal.common.presentation.BasePresenter;
import com.edulog.driverportal.common.presentation.BaseView;
import com.edulog.driverportal.routes.model.LoadMode;
import com.edulog.driverportal.routes.model.RouteModel;

public interface PreviewRouteContract {
    interface PreviewRouteView extends BaseView {
        void showPreviewRoute(RouteModel routeModel);
    }

    abstract class PreviewRoutePresenter extends BasePresenter<PreviewRouteView> {
        public abstract void previewRoute(String routeId, LoadMode loadMode);
    }
}
