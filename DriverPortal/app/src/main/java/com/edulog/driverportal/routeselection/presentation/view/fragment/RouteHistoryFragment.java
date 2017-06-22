package com.edulog.driverportal.routeselection.presentation.view.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.edulog.driverportal.R;
import com.edulog.driverportal.common.presentation.BaseFragment;
import com.edulog.driverportal.routeselection.model.RouteModel;
import com.edulog.driverportal.routeselection.presentation.view.adapter.RouteHistoryAdapter;

import java.util.ArrayList;
import java.util.List;

public class RouteHistoryFragment extends BaseFragment {
    private RouteHistoryAdapter routeHistoryAdapter;
    private List<RouteModel> routeModels;

    public static RouteHistoryFragment newInstance() {
        return new RouteHistoryFragment();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        routeModels = new ArrayList<>();
        routeHistoryAdapter = new RouteHistoryAdapter(routeModels);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View root = inflater.inflate(R.layout.fragment_route_history, container, false);

        RecyclerView routeHistoryList = (RecyclerView)root.findViewById(R.id.rvRouteHistory);
        routeHistoryList.setLayoutManager(new LinearLayoutManager(getActivity()));
        routeHistoryList.setAdapter(routeHistoryAdapter);

        return root;
    }
}
