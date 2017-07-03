package com.edulog.driverportal.routes.presentation.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.edulog.driverportal.base.DriverPortalApplication;
import com.edulog.driverportal.R;
import com.edulog.driverportal.session.Session;
import com.edulog.driverportal.base.BaseFragment;
import com.edulog.driverportal.base.BasePresenter;
import com.edulog.driverportal.base.BaseView;
import com.edulog.driverportal.routes.model.Route;
import com.edulog.driverportal.routes.data.DriverPortalDbHelper;
import com.edulog.driverportal.routes.data.RouteRepositoryImpl;
import com.edulog.driverportal.routes.domain.ShowRouteHistoryUseCase;
import com.edulog.driverportal.routes.data.RouteRepository;
import com.edulog.driverportal.routes.model.LoadMode;
import com.edulog.driverportal.routes.presentation.RouteHistoryContract;
import com.edulog.driverportal.routes.presentation.RouteHistoryPresenterImpl;
import com.edulog.driverportal.routes.presentation.adapter.RouteHistoryAdapter;

import java.util.ArrayList;
import java.util.List;

public class RouteHistoryFragment extends BaseFragment implements RouteHistoryContract.View {
    private RouteHistoryAdapter routeHistoryAdapter;
    private List<Route> routes;
    private RouteHistoryContract.Presenter routeHistoryPresenter;

    public static RouteHistoryFragment newInstance() {
        return new RouteHistoryFragment();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        routes = new ArrayList<>();

        RouteRepository routeRepository = new RouteRepositoryImpl(new DriverPortalDbHelper(getActivity()));
        Session session = ((DriverPortalApplication) getActivity().getApplication()).getSession();
        ShowRouteHistoryUseCase showRouteHistoryUseCase = new ShowRouteHistoryUseCase(routeRepository, session);
        routeHistoryPresenter = new RouteHistoryPresenterImpl(showRouteHistoryUseCase);
    }

    @Nullable
    @Override
    public android.view.View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        android.view.View root = inflater.inflate(R.layout.fragment_route_history, container, false);

        routeHistoryAdapter = new RouteHistoryAdapter(routes);
        routeHistoryAdapter.getItemClickObservable().subscribe(this::openPreviewRouteDialog);

        RecyclerView routeHistoryList = (RecyclerView) root.findViewById(R.id.rvRouteHistory);
        routeHistoryList.setLayoutManager(new LinearLayoutManager(getActivity()));
        routeHistoryList.setAdapter(routeHistoryAdapter);

        return root;
    }

    @Override
    public void onStart() {
        super.onStart();

        routeHistoryPresenter.getRouteHistory();
    }

    @Override
    protected BasePresenter getPresenter() {
        return routeHistoryPresenter;
    }

    @Override
    protected BaseView getViewLayer() {
        return this;
    }

    @Override
    public void showRouteHistory(List<Route> routes) {
        this.routes.clear();
        if (routes != null && !routes.isEmpty()) {
            this.routes.addAll(routes);
        }
        routeHistoryAdapter.notifyDataSetChanged();
    }

    private void openPreviewRouteDialog(Route route) {
        PreviewRouteDialogFragment fragment = PreviewRouteDialogFragment.newInstance(route.getId(), LoadMode.LOCAL);
        fragment.show(getActivity().getSupportFragmentManager(), null);
    }
}
