package com.edulog.driverportal.routes.presentation.presenter;

import com.edulog.driverportal.common.presentation.BasePresenter;
import com.edulog.driverportal.common.presentation.BaseView;

import java.util.List;

public interface NewRouteContract {
    interface NewRouteView extends BaseView {
        void showRouteIdSuggestions(List<String> routeIds);
    }

    abstract class NewRoutePresenter extends BasePresenter<NewRouteView> {
        public abstract void suggestRouteIds(String query);
    }
}
