package com.edulog.driverportal.routeselection.navigator;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;

import com.edulog.driverportal.R;
import com.edulog.driverportal.common.base.Navigator;
import com.edulog.driverportal.routeselection.presentation.RouteSelectionFragment;
import com.edulog.driverportal.routeselection.presentation.SearchRouteFragment;

public class RouteSelectionNavigator extends Navigator {
    public RouteSelectionNavigator(FragmentManager fragmentManager) {
        super(fragmentManager);
    }

    public void navigateToSearchRoute() {
        Fragment fragment = SearchRouteFragment.newInstance();
        fragmentManager.beginTransaction()
                .replace(R.id.contentFrame, fragment)
                .addToBackStack(null)
                .commit();
    }

    @Override
    protected Fragment createRootContent() {
        return RouteSelectionFragment.newInstance();
    }
}
