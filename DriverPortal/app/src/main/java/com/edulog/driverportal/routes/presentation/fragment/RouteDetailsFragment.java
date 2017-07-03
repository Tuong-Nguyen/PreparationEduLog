package com.edulog.driverportal.routes.presentation.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.TextView;

import com.edulog.driverportal.base.Config;
import com.edulog.driverportal.base.DriverPortalApplication;
import com.edulog.driverportal.R;
import com.edulog.driverportal.session.Session;
import com.edulog.driverportal.base.BaseFragment;
import com.edulog.driverportal.base.BasePresenter;
import com.edulog.driverportal.base.BaseView;
import com.edulog.driverportal.routes.model.Route;
import com.edulog.driverportal.util.RetrofitServiceGenerator;
import com.edulog.driverportal.routes.domain.RouteService;
import com.edulog.driverportal.routes.data.DriverPortalDbHelper;
import com.edulog.driverportal.routes.data.RouteRepositoryImpl;
import com.edulog.driverportal.routes.domain.SetActiveRouteUseCase;
import com.edulog.driverportal.routes.data.RouteRepository;
import com.edulog.driverportal.routes.model.LoadMode;
import com.edulog.driverportal.routes.presentation.RouteDetailsContract;
import com.edulog.driverportal.routes.presentation.RouteDetailsPresenterImpl;

public class RouteDetailsFragment extends BaseFragment implements RouteDetailsContract.View {
    private static final String KEY_ROUTE_ID = "com.edulog.driverportal.KEY_ROUTE_ID";
    private static final String KEY_LOAD_MORE = "com.edulog.driverportal.KEY_LOAD_MORE";

    private String routeId;
    private LoadMode loadMode;
    private RouteDetailsContract.Presenter routeDetailsPresenter;

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

        RouteService routeService = new RetrofitServiceGenerator()
                .baseUrl(Config.EDULOG_URL)
                .generate(RouteService.class);
        Session session = ((DriverPortalApplication) getActivity().getApplication()).getSession();
        RouteRepository routeRepository = new RouteRepositoryImpl(new DriverPortalDbHelper(getActivity()));
        SetActiveRouteUseCase setActiveRouteUseCase = new SetActiveRouteUseCase(routeService, routeRepository, session);
        routeDetailsPresenter = new RouteDetailsPresenterImpl(setActiveRouteUseCase);
    }

    @Nullable
    @Override
    public android.view.View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        android.view.View root = inflater.inflate(R.layout.fragment_route_details, container, false);

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
    public void showRouteDetails(Route route) {
        routeIdTextView.setText(route.getId());
        routeNameTextView.setText(route.getName());
        stopCountTextView.setText(String.valueOf(route.getStopCount()));
    }
}
