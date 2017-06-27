package com.edulog.driverportal.routes.presentation.view.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;

import com.edulog.driverportal.R;
import com.edulog.driverportal.common.presentation.BaseActivity;
import com.edulog.driverportal.routes.presentation.view.fragment.RouteHistoryFragment;

public class PreviousRouteActivity extends BaseActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_previous_route);

        openAsRoot(RouteHistoryFragment.newInstance());
    }
}
