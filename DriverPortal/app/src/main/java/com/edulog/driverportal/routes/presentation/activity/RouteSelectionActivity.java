package com.edulog.driverportal.routes.presentation.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.Button;

import com.edulog.driverportal.R;
import com.edulog.driverportal.base.BaseActivity;

public class RouteSelectionActivity extends BaseActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_route_selection);

        Button newRouteButton = (Button) findViewById(R.id.btnNewRoute);
        newRouteButton.setOnClickListener(v -> {
            Intent intent = new Intent(RouteSelectionActivity.this, NewRouteActivity.class);
            startActivity(intent);
        });

        Button usePreviousRouteButton = (Button) findViewById(R.id.btnUsePreviousRoute);
        usePreviousRouteButton.setOnClickListener(v -> {
            Intent intent = new Intent(RouteSelectionActivity.this, PreviousRouteActivity.class);
            startActivity(intent);
        });
    }
}
