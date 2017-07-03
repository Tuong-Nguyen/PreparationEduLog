package com.edulog.driverportal.routes.presentation;

import com.edulog.driverportal.base.BasePresenter;
import com.edulog.driverportal.base.BaseView;
import com.edulog.driverportal.routes.model.LoadMode;
import com.edulog.driverportal.routes.model.Route;

public interface PreviewRouteContract {
    interface View extends BaseView {
        void showPreviewRoute(Route route);
    }

    abstract class Presenter extends BasePresenter<View> {
        public abstract void previewRoute(String routeId, LoadMode loadMode);
    }
}
