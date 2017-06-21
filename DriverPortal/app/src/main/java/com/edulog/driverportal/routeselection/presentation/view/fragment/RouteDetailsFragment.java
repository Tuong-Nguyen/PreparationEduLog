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
import com.edulog.driverportal.common.presentation.BaseFragment;
import com.edulog.driverportal.common.presentation.BasePresenter;
import com.edulog.driverportal.common.presentation.BaseView;
import com.edulog.driverportal.common.util.RetrofitServiceGenerator;
import com.edulog.driverportal.routeselection.data.net.DriverPortalRouteService;
import com.edulog.driverportal.routeselection.data.repository.DriverPortalDbHelper;
import com.edulog.driverportal.routeselection.data.repository.RouteRepositoryImpl;
import com.edulog.driverportal.routeselection.data.service.RouteServiceImpl;
import com.edulog.driverportal.common.preference.Session;
import com.edulog.driverportal.routeselection.domain.interactor.SetActiveRouteUseCase;
import com.edulog.driverportal.routeselection.domain.repository.RouteRepository;
import com.edulog.driverportal.routeselection.domain.service.RouteService;
import com.edulog.driverportal.routeselection.presentation.model.RouteModel;
import com.edulog.driverportal.routeselection.presentation.presenter.RouteDetailsPresenter;
import com.edulog.driverportal.routeselection.presentation.presenter.RouteDetailsPresenterImpl;
import com.edulog.driverportal.routeselection.presentation.view.RouteDetailsView;
import com.edulog.driverportal.routeselection.presentation.view.activity.NewRouteActivity;

import io.reactivex.android.schedulers.AndroidSchedulers;

public class RouteDetailsFragment extends BaseFragment implements RouteDetailsView {
    private static final String KEY_ROUTE_ID = "com.edulog.driverportal.KEY_ROUTE_ID";
    TextView routeIdTextView;
    TextView routeNameTextView;
    TextView stopCountTextView;
    private RouteDetailsPresenter routeDetailsPresenter;
    private String routeId;

    public static RouteDetailsFragment newInstance(String routeId) {
        Bundle args = new Bundle();
        args.putString(KEY_ROUTE_ID, routeId);

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

        NewRouteActivity newRouteActivity = (NewRouteActivity) getActivity();
        newRouteActivity.setTitle(routeId);

        return root;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        NewRouteActivity newRouteActivity = (NewRouteActivity) getActivity();
        newRouteActivity.normalizeAppBar(false);
        newRouteActivity.setTitle(routeId);
    }

    @Override
    public void onStart() {
        super.onStart();

        routeDetailsPresenter.setActiveRoute(routeId);
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
