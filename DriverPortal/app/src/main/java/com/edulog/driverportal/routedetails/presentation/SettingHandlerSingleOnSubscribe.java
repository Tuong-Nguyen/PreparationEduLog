package com.edulog.driverportal.routedetails.presentation;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.gms.location.LocationSettingsStatusCodes;

import io.reactivex.SingleEmitter;
import io.reactivex.SingleOnSubscribe;

import static android.content.Intent.FLAG_ACTIVITY_NEW_TASK;

public class SettingHandlerSingleOnSubscribe implements SingleOnSubscribe<Boolean> {
    private static SingleEmitter<Boolean> emitter;
    private Context context;
    private LocationRequest locationRequest;
    private GoogleApiClient googleApiClient;

    public SettingHandlerSingleOnSubscribe(Context context, LocationRequest locationRequest) {
        this.context = context;
        this.locationRequest = locationRequest;
    }

    @Override
    public void subscribe(SingleEmitter<Boolean> emitter) throws Exception {
        SettingHandlerSingleOnSubscribe.emitter = emitter;
        ApiClientConnectionCallbacks connectionCallbacks = new ApiClientConnectionCallbacks();
        googleApiClient = new GoogleApiClient.Builder(context)
                .addConnectionCallbacks(connectionCallbacks)
                .addOnConnectionFailedListener(connectionCallbacks)
                .addApi(LocationServices.API)
                .build();

        googleApiClient.connect();
    }

    public static void onResolutionResult(int resultCode) {
        emitter.onSuccess(resultCode == Activity.RESULT_OK);
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
//                            Intent intent = new Intent(context, LocationSettingsActivity.class);
//                            intent.putExtra(LocationSettingsActivity.ARG_STATUS, status);
//                            intent.setFlags(FLAG_ACTIVITY_NEW_TASK);
//                            context.startActivity(intent);
                            emitter.onSuccess(true);
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
            emitter.onError(new RuntimeException(connectionResult.getErrorMessage()));
        }
    }
}
