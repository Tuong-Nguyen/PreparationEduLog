package com.edulog.driverportal.common.device;

import android.content.Context;
import android.content.IntentSender;
import android.location.Location;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.gms.location.LocationSettingsResult;
import com.google.android.gms.location.LocationSettingsStatusCodes;

import io.reactivex.Single;
import io.reactivex.SingleEmitter;
import io.reactivex.SingleOnSubscribe;

public class RxLocation {
    private Context context;

    public RxLocation(Context context) {
        this.context = context;
    }

    public Single<Boolean> setting() {
        return Single.create(new SingleOnSubscribe<Boolean>() {
            @Override
            public void subscribe(SingleEmitter<Boolean> e) throws Exception {
                ApiClientConnectionCallbacks connectionCallbacks = new ApiClientConnectionCallbacks();
                final GoogleApiClient googleApiClient = new GoogleApiClient.Builder(context)
                        .addConnectionCallbacks(connectionCallbacks)
                        .addOnConnectionFailedListener(connectionCallbacks)
                        .addApi(LocationServices.API)
                        .build();

                onGoogleApiAvailable(googleApiClient);
            }
        });
    }

    private void onGoogleApiAvailable(GoogleApiClient googleApiClient) {
        LocationRequest locationRequest = new LocationRequest();
        locationRequest.setInterval(5000);
        locationRequest.setFastestInterval(5000);
        locationRequest.setInterval(LocationRequest.PRIORITY_HIGH_ACCURACY);

        LocationSettingsRequest.Builder builder = new LocationSettingsRequest.Builder();
        builder.addLocationRequest(locationRequest);
        LocationSettingsRequest locationSettingsRequest = builder.build();

        LocationServices.SettingsApi.checkLocationSettings(googleApiClient, locationSettingsRequest)
                .setResultCallback(new ResultCallback<LocationSettingsResult>() {
                    @Override
                    public void onResult(@NonNull LocationSettingsResult locationSettingsResult) {
                        final Status status = locationSettingsResult.getStatus();
                        switch (status.getStatusCode()) {
                            case LocationSettingsStatusCodes.SUCCESS:
                                LocationServices.FusedLocationApi.requestLocationUpdates(googleApiClient, locationRequest, new RxLocationListener());
                                break;
                            case LocationSettingsStatusCodes.RESOLUTION_REQUIRED:
                                try {
                                    status.startResolutionForResult(new LocationSettingsActivity(), 1);
                                } catch (IntentSender.SendIntentException e) {

                                }
                                break;
                            case LocationSettingsStatusCodes.SETTINGS_CHANGE_UNAVAILABLE:
                                break;
                        }
                    }
                });
    }

    protected class RxLocationListener implements LocationListener {
        @Override
        public void onLocationChanged(Location location) {

        }
    }

    protected class ApiClientConnectionCallbacks implements GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener {
        @Override
        public void onConnected(@Nullable Bundle bundle) {

        }

        @Override
        public void onConnectionSuspended(int i) {

        }

        @Override
        public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

        }
    }
}
