package com.edulog.driverportal.routeselection.presentation.view.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.edulog.driverportal.R;
import com.edulog.driverportal.common.presentation.BaseActivity;
import com.edulog.driverportal.common.presentation.BaseFragment;
import com.edulog.driverportal.routeselection.model.LoadMode;
import com.edulog.driverportal.routeselection.presentation.view.adapter.RouteIdSuggestionsAdapter;

import java.util.ArrayList;
import java.util.List;

public class RouteIdSuggestionsFragment extends BaseFragment {
    private List<String> routeIds;
    private RouteIdSuggestionsAdapter routeIdSuggestionsAdapter;

    public static RouteIdSuggestionsFragment newInstance() {
        RouteIdSuggestionsFragment fragment = new RouteIdSuggestionsFragment();
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        routeIds = new ArrayList<>();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);

        View root = inflater.inflate(R.layout.fragment_route_id_suggestions, container, false);

        routeIdSuggestionsAdapter = new RouteIdSuggestionsAdapter(routeIds, getActivity());
        routeIdSuggestionsAdapter.getItemClickObservable().subscribe(routeId -> {
            PreviewRouteDialogFragment fragment = PreviewRouteDialogFragment.newInstance(routeId, LoadMode.REMOTE);
            fragment.show(getActivity().getSupportFragmentManager(), null);
        });

        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        RecyclerView recyclerView = (RecyclerView) root.findViewById(R.id.rvRouteIdSuggestions);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(routeIdSuggestionsAdapter);

        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(getActivity(), layoutManager.getOrientation());
        recyclerView.addItemDecoration(dividerItemDecoration);

        ((BaseActivity) getActivity()).setTitle("Search routes");

        return root;
    }

    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    public void onStop() {
        super.onStop();

        routeIds.clear();
        routeIdSuggestionsAdapter.notifyDataSetChanged();
    }

    public void showRouteIdSuggestions(List<String> routeIds) {
        this.routeIds.clear();
        this.routeIds.addAll(routeIds);

        routeIdSuggestionsAdapter.notifyDataSetChanged();
    }
}
