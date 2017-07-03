package com.edulog.driverportal.routes.presentation.fragment;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.widget.TextView;

import com.edulog.driverportal.R;
import com.edulog.driverportal.base.BaseActivity;
import com.edulog.driverportal.base.Config;
import com.edulog.driverportal.routes.model.Route;
import com.edulog.driverportal.api.Api;
import com.edulog.driverportal.routes.domain.RouteService;
import com.edulog.driverportal.routes.data.DriverPortalDbHelper;
import com.edulog.driverportal.routes.data.RouteRepositoryImpl;
import com.edulog.driverportal.routes.domain.GetRouteUseCase;
import com.edulog.driverportal.routes.data.RouteRepository;
import com.edulog.driverportal.routes.model.LoadMode;
import com.edulog.driverportal.routes.presentation.PreviewRouteContract;
import com.edulog.driverportal.routes.presentation.PreviewRoutePresenterImpl;

public class PreviewRouteDialogFragment extends DialogFragment implements PreviewRouteContract.View {
    private static final String KEY_ROUTE_ID = "com.edulog.driverportal.KEY_ROUTE_ID";
    private static final String KEY_LOAD_MODE = "com.edulog.driverportal.KEY_LOAD_MODE";

    private String routeId;
    private LoadMode loadMode;
    private PreviewRouteContract.Presenter previewRoutePresenter;

    private TextView routeNameTextView;
    private TextView stopCountTextView;

    public static PreviewRouteDialogFragment newInstance(String routeId, LoadMode loadMode) {
        PreviewRouteDialogFragment fragment = new PreviewRouteDialogFragment();

        Bundle args = new Bundle();
        args.putString(KEY_ROUTE_ID, routeId);
        args.putSerializable(KEY_LOAD_MODE, loadMode);
        fragment.setArguments(args);

        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            routeId = getArguments().getString(KEY_ROUTE_ID);
            loadMode = (LoadMode) getArguments().getSerializable(KEY_LOAD_MODE);
        }

        RouteService routeService = new Api()
                .baseUrl(Config.EDULOG_URL)
                .generate(RouteService.class);
        RouteRepository routeRepository = new RouteRepositoryImpl(new DriverPortalDbHelper(getActivity()));
        GetRouteUseCase getRouteUseCase = new GetRouteUseCase(routeService, routeRepository);
        previewRoutePresenter = new PreviewRoutePresenterImpl(getRouteUseCase);
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        super.onCreateDialog(savedInstanceState);

        android.view.View dialogView = getActivity().getLayoutInflater().inflate(R.layout.dialog_route_preview, null, false);
        routeNameTextView = (TextView) dialogView.findViewById(R.id.tvRouteName);
        stopCountTextView = (TextView) dialogView.findViewById(R.id.tvStopCount);

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle(String.valueOf(routeId));
        builder.setView(dialogView);
        builder.setPositiveButton(R.string.set_active_route,
                (DialogInterface dialog, int which) -> showRouteDetails());
        builder.setNegativeButton(R.string.cancel,
                (DialogInterface dialog, int which) -> getDialog().cancel());

        return builder.create();
    }

    @Override
    public void onStart() {
        super.onStart();
        previewRoutePresenter.attach(this);
        previewRoutePresenter.previewRoute(routeId, loadMode);
    }

    @Override
    public void onStop() {
        super.onStop();
        previewRoutePresenter.detach();
    }

    @Override
    public void showProgress() {

    }

    @Override
    public void hideProgress() {

    }

    @Override
    public void showError(String message) {

    }

    @Override
    public void showPreviewRoute(Route route) {
        routeNameTextView.setText(route.getName());
        stopCountTextView.setText(String.valueOf(route.getStopCount()));
    }

    public void showRouteDetails() {
        ((BaseActivity) getActivity()).moveToFragment(RouteDetailsFragment.newInstance(routeId, loadMode));
    }
}
