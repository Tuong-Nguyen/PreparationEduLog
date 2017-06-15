package com.edulog.driverportal.routeselection.presentation.view.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.edulog.driverportal.R;
import com.edulog.driverportal.common.presentation.BaseFragment;
import com.edulog.driverportal.routeselection.presentation.presenter.SearchRoutesPresenter;
import com.edulog.driverportal.routeselection.presentation.view.activity.RouteSelectionActivity;

public class SearchResultFragment extends BaseFragment {
    private static final String KEY_QUERY = "com.edulog.driverportal.KEY_QUERY";

    private SearchRoutesPresenter searchRoutesPresenter;
    private String query;

    public static SearchResultFragment newInstance(String query) {
        SearchResultFragment fragment = new SearchResultFragment();
        Bundle extra = new Bundle();
        extra.putString(KEY_QUERY, query);
        fragment.setArguments(extra);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        searchRoutesPresenter = ((RouteSelectionActivity)getActivity()).getSearchRoutesPresenter();
        query = getArguments().getString(KEY_QUERY);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);

        View root = inflater.inflate(R.layout.fragment_search_result, container, false);

        return root;
    }

    @Override
    public void onStart() {
        super.onStart();

        searchRoutesPresenter.searchRoutes(query);
    }
}
