package com.edulog.driverportal.routes.presentation;

import com.edulog.driverportal.base.BasePresenter;
import com.edulog.driverportal.base.BaseView;

import java.util.List;

public interface NewRouteContract {
    interface View extends BaseView {
        void showRouteIdSuggestions(List<String> routeIds);
    }

    abstract class Presenter extends BasePresenter<View> {
        public abstract void suggestRouteIds(String query);
    }
}
