package com.edulog.driverportal.location;

import android.content.Context;
import android.location.Location;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;

import io.reactivex.FlowableEmitter;
import io.reactivex.FlowableOnSubscribe;

public class LocationUpdateFlowableOnSubscribe implements FlowableOnSubscribe<Location> {
    private Context context;
    private LocationRequest locationRequest;
    private GoogleApiClient googleApiClient;
    private FlowableEmitter<Location> emitter;

    public LocationUpdateFlowableOnSubscribe(Context context, LocationRequest locationRequest) {
        this.context = context;
        this.locationRequest = locationRequest;
    }

    @Override
    public void subscribe(FlowableEmitter<Location> emitter) throws Exception {
        this.emitter = emitter;

        ApiClientConnectionCallbacks connectionCallbacks = new ApiClientConnectionCallbacks();
        googleApiClient = new GoogleApiClient.Builder(context)
                .addConnectionCallbacks(connectionCallbacks)
                .addOnConnectionFailedListener(connectionCallbacks)
                .addApi(LocationServices.API)
                .build();

        googleApiClient.connect();
    }

    @SuppressWarnings("MissingPermission")
    private void onGoogleApiAvailable() {
        LocationServices.FusedLocationApi.requestLocationUpdates(googleApiClient, locationRequest, location -> {
            emitter.onNext(location);
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
