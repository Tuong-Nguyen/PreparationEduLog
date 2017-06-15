package com.edulog.driverportal.routeselection.presentation;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.edulog.driverportal.R;
import com.edulog.driverportal.common.base.BaseFragment;

public class RouteSelectionFragment extends BaseFragment {
    public static BaseFragment newInstance() {
        return new RouteSelectionFragment();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_route_selection, container, false);

        Button selectRouteButton = (Button)root.findViewById(R.id.btnSelectRoute);
        selectRouteButton.setOnClickListener(v -> {
            Toast.makeText(getContext(), "ddd", Toast.LENGTH_SHORT).show();
        });

        return root;
    }
}
