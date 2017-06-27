package com.edulog.driverportal.routedetails;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationServices;

import io.reactivex.SingleEmitter;
import io.reactivex.SingleOnSubscribe;

public abstract class RxLocationSingleOnSubscribe<T> implements SingleOnSubscribe<T> {
    private GoogleApiClient googleApiClient;
    private SingleEmitter<T> emitter;
    private Context context;

    public RxLocationSingleOnSubscribe(Context context) {
        this.context = context;
    }

    @Override
    public void subscribe(SingleEmitter<T> emitter) throws Exception {
        this.emitter = emitter;

        ApiClientConnectionCallbacks connectionCallbacks = new ApiClientConnectionCallbacks();
        googleApiClient = new GoogleApiClient.Builder(context)
                .addConnectionCallbacks(connectionCallbacks)
                .addOnConnectionFailedListener(connectionCallbacks)
                .addApi(LocationServices.API)
                .build();

        googleApiClient.connect();
    }


    protected abstract void onGoogleApiAvailable();

    public GoogleApiClient getGoogleApiClient() {
        return googleApiClient;
    }

    public SingleEmitter<T> getEmitter() {
        return emitter;
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
