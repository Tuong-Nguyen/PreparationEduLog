package com.edulog.driverportal.routeselection.presentation.view.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.edulog.driverportal.DriverPortalApplication;
import com.edulog.driverportal.R;
import com.edulog.driverportal.common.preference.Session;
import com.edulog.driverportal.common.presentation.BaseFragment;
import com.edulog.driverportal.common.presentation.BasePresenter;
import com.edulog.driverportal.common.presentation.BaseView;
import com.edulog.driverportal.common.util.RetrofitServiceGenerator;
import com.edulog.driverportal.routeselection.data.net.DriverPortalRouteService;
import com.edulog.driverportal.routeselection.data.repository.DriverPortalDbHelper;
import com.edulog.driverportal.routeselection.data.repository.RouteRepositoryImpl;
import com.edulog.driverportal.routeselection.data.service.RouteServiceImpl;
import com.edulog.driverportal.routeselection.domain.interactor.SetActiveRouteUseCase;
import com.edulog.driverportal.routeselection.domain.repository.RouteRepository;
import com.edulog.driverportal.routeselection.domain.service.RouteService;
import com.edulog.driverportal.routeselection.model.LoadMode;
import com.edulog.driverportal.routeselection.model.RouteModel;
import com.edulog.driverportal.routeselection.presentation.presenter.RouteDetailsContract;
import com.edulog.driverportal.routeselection.presentation.presenter.RouteDetailsPresenterImpl;

public class RouteDetailsFragment extends BaseFragment implements RouteDetailsContract.RouteDetailsView {
    private static final String KEY_ROUTE_ID = "com.edulog.driverportal.KEY_ROUTE_ID";
    private static final String KEY_LOAD_MORE = "com.edulog.driverportal.KEY_LOAD_MORE";

    private String routeId;
    private LoadMode loadMode;
    private RouteDetailsContract.RouteDetailsPresenter routeDetailsPresenter;

    private TextView routeIdTextView;
    private TextView routeNameTextView;
    private TextView stopCountTextView;

    public static RouteDetailsFragment newInstance(String routeId, LoadMode loadMode) {
        Bundle args = new Bundle();
        args.putString(KEY_ROUTE_ID, routeId);
        args.putSerializable(KEY_LOAD_MORE, loadMode);

        RouteDetailsFragment fragment = new RouteDetailsFragment();
        fragment.setArguments(args);

        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Bundle args = getArguments();
        if (!args.isEmpty()) {
            routeId = args.getString(KEY_ROUTE_ID);
            loadMode = (LoadMode) args.getSerializable(KEY_LOAD_MORE);
        }

        DriverPortalRouteService service = RetrofitServiceGenerator.generate(DriverPortalRouteService.class);
        RouteService routeService = new RouteServiceImpl(service);
        Session session = ((DriverPortalApplication) getActivity().getApplication()).getSession();
        RouteRepository routeRepository = new RouteRepositoryImpl(new DriverPortalDbHelper(getActivity()));
        SetActiveRouteUseCase setActiveRouteUseCase = new SetActiveRouteUseCase(routeService, routeRepository, session);
        routeDetailsPresenter = new RouteDetailsPresenterImpl(setActiveRouteUseCase);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_route_details, container, false);

        routeIdTextView = (TextView) root.findViewById(R.id.tvRouteId);
        routeNameTextView = (TextView) root.findViewById(R.id.tvRouteName);
        stopCountTextView = (TextView) root.findViewById(R.id.tvStopCount);

        return root;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public void onStart() {
        super.onStart();

        routeDetailsPresenter.setActiveRoute(routeId, loadMode);
    }

    @Override
    protected BasePresenter getPresenter() {
        return routeDetailsPresenter;
    }

    @Override
    protected BaseView getViewLayer() {
        return this;
    }

    @Override
    public void showRouteDetails(RouteModel routeModel) {
        routeIdTextView.setText(routeModel.getId());
        routeNameTextView.setText(routeModel.getName());
        stopCountTextView.setText(String.valueOf(routeModel.getStopCount()));
    }
}
