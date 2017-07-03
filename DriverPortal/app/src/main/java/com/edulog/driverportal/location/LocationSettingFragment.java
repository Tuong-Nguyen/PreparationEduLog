package com.edulog.driverportal.location;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentSender;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.gms.location.LocationSettingsStatusCodes;

import io.reactivex.SingleEmitter;
import io.reactivex.SingleOnSubscribe;

public class LocationSettingFragment extends Fragment implements SingleOnSubscribe<Boolean> {
    private static final String KEY_LOCATION_REQUEST = "com.edulog.driverportal.EXTRA_LOCATION_REQUEST";
    private static final int REQUEST_RESOLVE_PERMISSION = 100;
    private static final int REQUEST_RESOLVE_ERROR = 101;

    public static LocationSettingFragment newInstance(LocationRequest locationRequest) {
        Bundle args = new Bundle();
        args.putParcelable(KEY_LOCATION_REQUEST, locationRequest);

        LocationSettingFragment fragment = new LocationSettingFragment();
        fragment.setArguments(args);

        return fragment;
    }

    private SingleEmitter<Boolean> emitter;
    private LocationRequest locationRequest;
    private GoogleApiClient googleApiClient;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle args = getArguments();
        if (!args.isEmpty()) {
            locationRequest = args.getParcelable(KEY_LOCATION_REQUEST);
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case REQUEST_RESOLVE_PERMISSION:
                emitter.onSuccess(resultCode == Activity.RESULT_OK);
                break;
            case REQUEST_RESOLVE_ERROR:
                if (resultCode == Activity.RESULT_OK) {
                    if (!googleApiClient.isConnecting() && !googleApiClient.isConnected()) {
                        googleApiClient.connect();
                    }
                }
                break;
            default:
                super.onActivityResult(requestCode, resultCode, data);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        ApiClientConnectionCallbacks connectionCallbacks = new ApiClientConnectionCallbacks();

        // Start an auto managed connection
        // auto display ui for resolve issue
        googleApiClient = new GoogleApiClient.Builder(getActivity())
                .addConnectionCallbacks(connectionCallbacks)
                .addOnConnectionFailedListener(connectionCallbacks)
                .addApi(LocationServices.API)
                .build();
    }

    @Override
    public void onStart() {
        super.onStart();
        googleApiClient.connect();
    }

    @Override
    public void onStop() {
        super.onStop();
        googleApiClient.disconnect();
    }

    @Override
    public void subscribe(SingleEmitter<Boolean> emitter) throws Exception {
        this.emitter = emitter;
    }

    protected void onGoogleApiAvailable() {
        LocationSettingsRequest.Builder builder = new LocationSettingsRequest.Builder();
        builder.addLocationRequest(locationRequest);
        LocationSettingsRequest locationSettingsRequest = builder.build();

        LocationServices.SettingsApi.checkLocationSettings(googleApiClient, locationSettingsRequest)
                .setResultCallback(locationSettingsResult -> {
                    final Status status = locationSettingsResult.getStatus();
                    switch (status.getStatusCode()) {
                        case LocationSettingsStatusCodes.SUCCESS:
                            emitter.onSuccess(true);
                            break;
                        case LocationSettingsStatusCodes.RESOLUTION_REQUIRED:
                            try {
                                if (status.hasResolution()) {
                                    startIntentSenderForResult(status.getResolution().getIntentSender(), REQUEST_RESOLVE_PERMISSION, null, 0, 0, 0, null);
                                }
                            } catch (IntentSender.SendIntentException ex) {
                                emitter.onError(ex);
                            }
                            break;
                        case LocationSettingsStatusCodes.SETTINGS_CHANGE_UNAVAILABLE:
                            emitter.onSuccess(false);
                            break;
                    }
                });
    }

    protected class ApiClientConnectionCallbacks implements GoogleApiClient.ConnectionCallbacks,
            GoogleApiClient.OnConnectionFailedListener {

        @Override
        public void onConnected(@Nullable Bundle bundle) {
            onGoogleApiAvailable();
        }

        @Override
        public void onConnectionSuspended(int i) {
            emitter.onError(new RuntimeException("Google api connection suspended"));
        }

        @Override
        public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
            // Trigger when api not available (Wearable api, Google Play Service need to be updated, use need login (Google Drive, Game,..))
            if (connectionResult.hasResolution()) {
                try {
                    connectionResult.startResolutionForResult(getActivity(), REQUEST_RESOLVE_ERROR);
                } catch (IntentSender.SendIntentException ex) {
                    // Try to reconnect
                    googleApiClient.connect();
                }
            } else {
                ErrorDialogFragment errorDialogFragment = ErrorDialogFragment.newInstance(connectionResult.getErrorCode());
                errorDialogFragment.show(getActivity().getSupportFragmentManager(), null);
            }
        }
    }

    public static class ErrorDialogFragment extends DialogFragment {
        private static final String KEY_ERROR_CODE = "errorCode";

        public static ErrorDialogFragment newInstance(int errorCode) {
            Bundle args = new Bundle();
            args.putInt(KEY_ERROR_CODE, errorCode);

            ErrorDialogFragment fragment = new ErrorDialogFragment();
            fragment.setArguments(args);
            return fragment;
        }

        @NonNull
        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            super.onCreateDialog(savedInstanceState);

            int errorCode = getArguments().getInt(KEY_ERROR_CODE);

            return GoogleApiAvailability.getInstance()
                    .getErrorDialog(getActivity(), errorCode, REQUEST_RESOLVE_ERROR);
        }

        @Override
        public void onDismiss(DialogInterface dialog) {

        }
    }
}
