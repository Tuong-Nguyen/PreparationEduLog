package com.edulog.driverportal.routes.presentation.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.edulog.driverportal.R;
import com.edulog.driverportal.common.presentation.BaseFragment;
import com.edulog.driverportal.common.presentation.BasePresenter;
import com.edulog.driverportal.common.presentation.BaseView;
import com.edulog.driverportal.routes.model.Route;
import com.edulog.driverportal.util.RetrofitServiceGenerator;
import com.edulog.driverportal.routes.domain.RouteService;
import com.edulog.driverportal.routes.domain.SearchRoutesUseCase;
import com.edulog.driverportal.routes.model.LoadMode;
import com.edulog.driverportal.routes.presentation.SearchRoutesContract;
import com.edulog.driverportal.routes.presentation.SearchRoutesPresenterImpl;
import com.edulog.driverportal.routes.presentation.activity.NewRouteActivity;
import com.edulog.driverportal.routes.presentation.adapter.SearchResultAdapter;

import java.util.ArrayList;
import java.util.List;

public class SearchRoutesFragment extends BaseFragment implements SearchRoutesContract.SearchRoutesView {
    private static final String KEY_QUERY = "com.edulog.driverportal.KEY_QUERY";
    private String query;
    private List<Route> routes;
    private SearchResultAdapter searchResultAdapter;
    private SearchRoutesContract.SearchRoutesPresenter searchRoutesPresenter;

    public static SearchRoutesFragment newInstance(String query) {
        SearchRoutesFragment fragment = new SearchRoutesFragment();
        Bundle args = new Bundle();
        args.putString(KEY_QUERY, query);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        query = getArguments().getString(KEY_QUERY);

        routes = new ArrayList<>();

        RouteService routeService = new RetrofitServiceGenerator().generate(RouteService.class);
        SearchRoutesUseCase searchRoutesUseCase = new SearchRoutesUseCase(routeService);
        searchRoutesPresenter = new SearchRoutesPresenterImpl(searchRoutesUseCase);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);

        View root = inflater.inflate(R.layout.fragment_search_routes, container, false);
        searchResultAdapter = new SearchResultAdapter(routes, getActivity());
        searchResultAdapter.getItemClickObservable().subscribe(this::openPreviewRouteDialog);

        RecyclerView recyclerView = (RecyclerView) root.findViewById(R.id.rvSearchResults);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setAdapter(searchResultAdapter);

        NewRouteActivity newRouteActivity = (NewRouteActivity) getActivity();
        newRouteActivity.setTitle("Search results: " + query);

        return root;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        NewRouteActivity newRouteActivity = (NewRouteActivity) getActivity();
        newRouteActivity.normalizeAppBar(true);
    }

    @Override
    public void onStart() {
        super.onStart();

        searchRoutesPresenter.searchRoutes(query);
    }

    @Override
    public void onStop() {
        super.onStop();

    }

    @Override
    protected BasePresenter getPresenter() {
        return searchRoutesPresenter;
    }

    @Override
    protected BaseView getViewLayer() {
        return this;
    }

    @Override
    public void showSearchRouteResults(List<Route> routes) {
        this.routes.clear();
        this.routes.addAll(routes);

        searchResultAdapter.notifyDataSetChanged();
    }

    private void openPreviewRouteDialog(Route route) {
        PreviewRouteDialogFragment previewRouteDialogFragment = PreviewRouteDialogFragment.newInstance(route.getId(), LoadMode.REMOTE);
        previewRouteDialogFragment.show(getActivity().getSupportFragmentManager(), null);
    }
}
