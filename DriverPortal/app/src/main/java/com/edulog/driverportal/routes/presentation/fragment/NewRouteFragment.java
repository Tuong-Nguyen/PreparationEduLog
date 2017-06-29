package com.edulog.driverportal.routes.presentation.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.edulog.driverportal.R;
import com.edulog.driverportal.common.presentation.BaseActivity;
import com.edulog.driverportal.common.presentation.BaseFragment;

public class NewRouteFragment extends BaseFragment {
    public static BaseFragment newInstance() {
        return new NewRouteFragment();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_new_route, container, false);

        ((BaseActivity) getActivity()).setTitle("Choose a new route from server");

        return root;
    }
}
