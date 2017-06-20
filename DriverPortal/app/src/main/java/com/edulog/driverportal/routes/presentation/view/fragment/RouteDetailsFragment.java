package com.edulog.driverportal.routes.presentation.view.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.edulog.driverportal.R;
import com.edulog.driverportal.common.presentation.BaseFragment;
import com.edulog.driverportal.routes.model.RouteModel;
import com.edulog.driverportal.routes.presentation.view.activity.RouteSelectionActivity;

public class RouteDetailsFragment extends BaseFragment {
    private static final String KEY_ROUTE = "com.edulog.driverportal.KEY_ROUTE";

    private RouteModel routeModel;

    public static RouteDetailsFragment newInstance(RouteModel routeModel) {
        Bundle args = new Bundle();
        args.putParcelable(KEY_ROUTE, routeModel);

        RouteDetailsFragment fragment = new RouteDetailsFragment();
        fragment.setArguments(args);

        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Bundle args = getArguments();
        if (!args.isEmpty()) {
            routeModel = args.getParcelable(KEY_ROUTE);
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_route_details, container, false);

        TextView routeIdTextView = (TextView)root.findViewById(R.id.tvRouteId);
        routeIdTextView.setText(routeModel.getId());

        TextView routeNameTextView = (TextView)root.findViewById(R.id.tvRouteName);
        routeNameTextView.setText(routeModel.getName());

        TextView stopCountTextView = (TextView)root.findViewById(R.id.tvStopCount);
        stopCountTextView.setText(String.valueOf(routeModel.getStopCount()));

        return root;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        ((RouteSelectionActivity)getActivity()).collapseSearchView(false);
    }
}
