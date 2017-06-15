package com.edulog.driverportal.routeselection.presentation;

import android.os.Bundle;
import android.support.annotation.Nullable;

import com.edulog.driverportal.R;
import com.edulog.driverportal.common.base.BaseActivity;
import com.edulog.driverportal.routeselection.navigator.RouteSelectionNavigator;

public class RouteSelectionActivity extends BaseActivity {
    private RouteSelectionPresenter routeSelectionPresenter;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_route_selection);

        RouteSelectionNavigator routeSelectionNavigator = new RouteSelectionNavigator(getSupportFragmentManager());
        routeSelectionNavigator.addRootContent();

        routeSelectionPresenter = new RouteSelectionPresenterImpl(routeSelectionNavigator);
    }

    public RouteSelectionPresenter getRouteSelectionPresenter() {
        return routeSelectionPresenter;
    }
}
