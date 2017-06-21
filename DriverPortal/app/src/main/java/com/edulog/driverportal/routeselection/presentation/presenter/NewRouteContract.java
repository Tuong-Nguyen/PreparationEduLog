package com.edulog.driverportal.routeselection.presentation.presenter;

import com.edulog.driverportal.common.presentation.BasePresenter;
import com.edulog.driverportal.common.presentation.BaseView;

import java.util.List;

public interface NewRouteContract {
    abstract class NewRoutePresenter extends BasePresenter<NewRouteView> {
        public abstract void suggestRouteIds(String query);
    }

    interface NewRouteView extends BaseView {
        void showRouteIdSuggestions(List<String> routeIds);
    }
}
