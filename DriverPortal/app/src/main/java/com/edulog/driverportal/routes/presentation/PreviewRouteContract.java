package com.edulog.driverportal.routes.presentation;

import com.edulog.driverportal.common.presentation.BasePresenter;
import com.edulog.driverportal.common.presentation.BaseView;
import com.edulog.driverportal.routes.model.LoadMode;
import com.edulog.driverportal.routes.model.Route;

public interface PreviewRouteContract {
    interface PreviewRouteView extends BaseView {
        void showPreviewRoute(Route route);
    }

    abstract class PreviewRoutePresenter extends BasePresenter<PreviewRouteView> {
        public abstract void previewRoute(String routeId, LoadMode loadMode);
    }
}
