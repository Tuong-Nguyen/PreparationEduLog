package com.edulog.driverportal.routes.presentation.view.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.edulog.driverportal.DriverPortalApplication;
import com.edulog.driverportal.R;
import com.edulog.driverportal.common.device.Session;
import com.edulog.driverportal.common.presentation.BaseFragment;
import com.edulog.driverportal.common.presentation.BasePresenter;
import com.edulog.driverportal.common.presentation.BaseView;
import com.edulog.driverportal.routes.data.repository.DriverPortalDbHelper;
import com.edulog.driverportal.routes.data.repository.RouteRepositoryImpl;
import com.edulog.driverportal.routes.domain.interactor.ShowRouteHistoryUseCase;
import com.edulog.driverportal.routes.domain.repository.RouteRepository;
import com.edulog.driverportal.routes.model.LoadMode;
import com.edulog.driverportal.routes.model.RouteModel;
import com.edulog.driverportal.routes.presentation.presenter.RouteHistoryContract;
import com.edulog.driverportal.routes.presentation.presenter.RouteHistoryPresenterImpl;
import com.edulog.driverportal.routes.presentation.view.adapter.RouteHistoryAdapter;

import java.util.ArrayList;
import java.util.List;

public class RouteHistoryFragment extends BaseFragment implements RouteHistoryContract.RouteHistoryView {
    private RouteHistoryAdapter routeHistoryAdapter;
    private List<RouteModel> routeModels;
    private RouteHistoryContract.RouteHistoryPresenter routeHistoryPresenter;

    public static RouteHistoryFragment newInstance() {
        return new RouteHistoryFragment();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        routeModels = new ArrayList<>();

        RouteRepository routeRepository = new RouteRepositoryImpl(new DriverPortalDbHelper(getActivity()));
        Session session = ((DriverPortalApplication) getActivity().getApplication()).getSession();
        ShowRouteHistoryUseCase showRouteHistoryUseCase = new ShowRouteHistoryUseCase(routeRepository, session);
        routeHistoryPresenter = new RouteHistoryPresenterImpl(showRouteHistoryUseCase);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View root = inflater.inflate(R.layout.fragment_route_history, container, false);

        routeHistoryAdapter = new RouteHistoryAdapter(routeModels);
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
    public void showRouteHistory(List<RouteModel> routeModels) {
        this.routeModels.clear();
        if (routeModels != null && !routeModels.isEmpty()) {
            this.routeModels.addAll(routeModels);
        }
        routeHistoryAdapter.notifyDataSetChanged();
    }

    private void openPreviewRouteDialog(RouteModel routeModel) {
        PreviewRouteDialogFragment fragment = PreviewRouteDialogFragment.newInstance(routeModel.getId(), LoadMode.LOCAL);
        fragment.show(getActivity().getSupportFragmentManager(), null);
    }
}
