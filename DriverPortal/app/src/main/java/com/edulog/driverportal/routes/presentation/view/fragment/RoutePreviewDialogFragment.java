package com.edulog.driverportal.routes.presentation.view.fragment;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.view.View;

import com.edulog.driverportal.R;
import com.edulog.driverportal.routes.model.RouteModel;

public class RoutePreviewDialogFragment extends DialogFragment {
    private static final String KEY_ROUTE = "com.edulog.driverportal.KEY_ROUTE";

    public interface RoutePreviewDialogListener {
        void onDialogPositiveClick(RouteModel routeModel);
    }

    private RoutePreviewDialogListener routePreviewDialogListener;

    private RouteModel routeModel;

    public static RoutePreviewDialogFragment newInstance(RouteModel routeModel) {
        RoutePreviewDialogFragment fragment = new RoutePreviewDialogFragment();

        Bundle args = new Bundle();
        args.putParcelable(KEY_ROUTE, routeModel);
        fragment.setArguments(args);

        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        routeModel = getArguments().getParcelable(KEY_ROUTE);

        routePreviewDialogListener = (RoutePreviewDialogListener)getTargetFragment();
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        super.onCreateDialog(savedInstanceState);

        View dialogView = getActivity().getLayoutInflater().inflate(R.layout.dialog_route_preview, null, false);

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle(String.valueOf(routeModel.getId()));
        builder.setView(dialogView);
        builder.setPositiveButton(R.string.set_active_route, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                routePreviewDialogListener.onDialogPositiveClick(routeModel);
            }
        });
        builder.setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                getDialog().cancel();
            }
        });

        return builder.create();
    }
}
