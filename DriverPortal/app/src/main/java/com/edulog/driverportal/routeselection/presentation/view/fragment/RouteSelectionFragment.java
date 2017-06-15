package com.edulog.driverportal.routeselection.presentation.view.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.edulog.driverportal.R;
import com.edulog.driverportal.common.presentation.BaseFragment;
import com.edulog.driverportal.common.presentation.navigator.Navigator;
import com.edulog.driverportal.routeselection.presentation.view.activity.RouteSelectionActivity;

public class RouteSelectionFragment extends BaseFragment {
    private Navigator navigator;

    public static BaseFragment newInstance() {
        return new RouteSelectionFragment();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        navigator = ((RouteSelectionActivity) getActivity()).getNavigator();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_route_selection, container, false);

        Button selectRouteButton = (Button) root.findViewById(R.id.btnSelectRoute);
        selectRouteButton.setOnClickListener(v -> navigator.moveToFragment(SearchRoutesFragment.newInstance()));

        return root;
    }
}
