package com.edulog.driverportal.routes.presentation.view.fragment;

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
import com.edulog.driverportal.common.util.RetrofitServiceGenerator;
import com.edulog.driverportal.routes.data.net.DriverPortalRouteService;
import com.edulog.driverportal.routes.data.service.RouteServiceImpl;
import com.edulog.driverportal.routes.domain.interactor.SearchRoutesUseCase;
import com.edulog.driverportal.routes.domain.service.RouteService;
import com.edulog.driverportal.routes.model.RouteModel;
import com.edulog.driverportal.routes.presentation.presenter.SearchRoutesPresenter;
import com.edulog.driverportal.routes.presentation.presenter.SearchRoutesPresenterImpl;
import com.edulog.driverportal.routes.presentation.view.SearchRoutesView;
import com.edulog.driverportal.routes.presentation.view.activity.RouteSelectionActivity;
import com.edulog.driverportal.routes.presentation.view.adapter.SearchResultAdapter;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;

public class SearchRoutesFragment extends BaseFragment implements SearchRoutesView {
    private static final String KEY_QUERY = "com.edulog.driverportal.KEY_QUERY";
    private String query;
    private List<RouteModel> routeModels;
    private SearchResultAdapter searchResultAdapter;
    private SearchRoutesPresenter searchRoutesPresenter;

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

        routeModels = new ArrayList<>();

        DriverPortalRouteService service = RetrofitServiceGenerator.generate(DriverPortalRouteService.class);
        RouteService routeService = new RouteServiceImpl(service);
        SearchRoutesUseCase searchRoutesUseCase = new SearchRoutesUseCase(AndroidSchedulers.mainThread(), routeService);
        searchRoutesPresenter = new SearchRoutesPresenterImpl(searchRoutesUseCase);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);

        View root = inflater.inflate(R.layout.fragment_search_routes, container, false);
        searchResultAdapter = new SearchResultAdapter(routeModels, getActivity());
        searchResultAdapter.getItemClickObservable().subscribe(this::openPreviewRouteDialog);

        RecyclerView recyclerView = (RecyclerView) root.findViewById(R.id.rvSearchResults);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setAdapter(searchResultAdapter);

        RouteSelectionActivity routeSelectionActivity = (RouteSelectionActivity) getActivity();
        routeSelectionActivity.setTitle("Search results: " + query);

        return root;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        RouteSelectionActivity routeSelectionActivity = (RouteSelectionActivity) getActivity();
        routeSelectionActivity.normalizeAppBar(true);
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
    public void showSearchRouteResults(List<RouteModel> routeModels) {
        this.routeModels.clear();
        this.routeModels.addAll(routeModels);

        searchResultAdapter.notifyDataSetChanged();
    }

    private void openPreviewRouteDialog(RouteModel routeModel) {
        PreviewRouteDialogFragment previewRouteDialogFragment = PreviewRouteDialogFragment.newInstance(routeModel.getId());
        previewRouteDialogFragment.show(getActivity().getSupportFragmentManager(), null);
    }
}
