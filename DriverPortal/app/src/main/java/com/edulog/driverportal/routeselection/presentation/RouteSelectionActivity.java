package com.edulog.driverportal.routeselection.presentation;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;

import com.edulog.driverportal.R;
import com.edulog.driverportal.base.BaseActivity;

public class RouteSelectionActivity extends BaseActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_route_selection);

        FragmentManager fm = getSupportFragmentManager();
        Fragment fragment = fm.findFragmentById(R.id.contentFrame);
        if (fragment == null) {
            fragment = RouteSelectionFragment.newInstance();
            fm.beginTransaction()
                    .add(R.id.contentFrame, fragment)
                    .commit();
        }
    }
}
