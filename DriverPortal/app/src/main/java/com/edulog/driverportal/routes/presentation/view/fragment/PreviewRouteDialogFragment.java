package com.edulog.driverportal.routes.presentation.view.fragment;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.widget.TextView;

import com.edulog.driverportal.R;
import com.edulog.driverportal.common.presentation.BaseActivity;
import com.edulog.driverportal.common.util.RetrofitServiceGenerator;
import com.edulog.driverportal.routes.data.net.DriverPortalRouteService;
import com.edulog.driverportal.routes.data.service.RouteServiceImpl;
import com.edulog.driverportal.routes.domain.interactor.PreviewRouteUseCase;
import com.edulog.driverportal.routes.domain.service.RouteService;
import com.edulog.driverportal.routes.model.RouteModel;
import com.edulog.driverportal.routes.presentation.presenter.PreviewRoutePresenter;
import com.edulog.driverportal.routes.presentation.presenter.PreviewRoutePresenterImpl;
import com.edulog.driverportal.routes.presentation.view.PreviewRouteView;

import io.reactivex.android.schedulers.AndroidSchedulers;

public class PreviewRouteDialogFragment extends DialogFragment implements PreviewRouteView {
    private static final String KEY_ROUTE_ID = "com.edulog.driverportal.KEY_ROUTE_ID";
    private String routeId;
    private PreviewRoutePresenter previewRoutePresenter;

    private TextView routeNameTextView;
    private TextView stopCountTextView;

    public static PreviewRouteDialogFragment newInstance(String routeId) {
        PreviewRouteDialogFragment fragment = new PreviewRouteDialogFragment();

        Bundle args = new Bundle();
        args.putString(KEY_ROUTE_ID, routeId);
        fragment.setArguments(args);

        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        routeId = getArguments().getString(KEY_ROUTE_ID);

        DriverPortalRouteService service = RetrofitServiceGenerator.generate(DriverPortalRouteService.class);
        RouteService routeService = new RouteServiceImpl(service);
        PreviewRouteUseCase previewRouteUseCase = new PreviewRouteUseCase(AndroidSchedulers.mainThread(), routeService);
        previewRoutePresenter = new PreviewRoutePresenterImpl(previewRouteUseCase);
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        super.onCreateDialog(savedInstanceState);

        View dialogView = getActivity().getLayoutInflater().inflate(R.layout.dialog_route_preview, null, false);
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
        previewRoutePresenter.previewRoute(routeId);
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
    public void showPreviewRoute(RouteModel routeModel) {
        routeNameTextView.setText(routeModel.getName());
        stopCountTextView.setText(String.valueOf(routeModel.getStopCount()));
    }

    public void showRouteDetails() {
        ((BaseActivity) getActivity()).moveToFragment(RouteDetailsFragment.newInstance(routeId));
    }
}
