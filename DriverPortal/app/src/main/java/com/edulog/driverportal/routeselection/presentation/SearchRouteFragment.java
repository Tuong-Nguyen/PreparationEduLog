package com.edulog.driverportal.routeselection.presentation;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.edulog.driverportal.R;
import com.edulog.driverportal.common.base.BaseFragment;

public class SearchRouteFragment extends BaseFragment {
    public static SearchRouteFragment newInstance() {
        return new SearchRouteFragment();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_search_route, container, false);

        return root;
    }
}
