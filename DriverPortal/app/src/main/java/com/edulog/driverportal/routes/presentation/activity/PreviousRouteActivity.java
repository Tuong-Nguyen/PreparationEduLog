package com.edulog.driverportal.routes.presentation.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;

import com.edulog.driverportal.R;
import com.edulog.driverportal.base.BaseActivity;
import com.edulog.driverportal.routes.presentation.fragment.RouteHistoryFragment;

public class PreviousRouteActivity extends BaseActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_previous_route);

        openAsRoot(RouteHistoryFragment.newInstance());
    }
}
