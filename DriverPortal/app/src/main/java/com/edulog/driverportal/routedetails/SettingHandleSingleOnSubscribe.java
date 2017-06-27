package com.edulog.driverportal.routedetails;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;

import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.gms.location.LocationSettingsStatusCodes;

import io.reactivex.SingleEmitter;

import static android.content.Intent.FLAG_ACTIVITY_NEW_TASK;

public class SettingHandleSingleOnSubscribe extends RxLocationSingleOnSubscribe<Boolean> {
    private static SingleEmitter<Boolean> emitter;
    private Context context;
    private LocationRequest locationRequest;

    public SettingHandleSingleOnSubscribe(Context context, LocationRequest locationRequest) {
        super(context);
        this.context = context;
        this.locationRequest = locationRequest;
    }

    public static void onResolutionResult(int resultCode) {
        emitter.onSuccess(resultCode == Activity.RESULT_OK);
    }

    @Override
    protected void onGoogleApiAvailable() {
        emitter = getEmitter();

        LocationSettingsRequest.Builder builder = new LocationSettingsRequest.Builder();
        builder.addLocationRequest(locationRequest);
        LocationSettingsRequest locationSettingsRequest = builder.build();

        LocationServices.SettingsApi.checkLocationSettings(getGoogleApiClient(), locationSettingsRequest)
                .setResultCallback(locationSettingsResult -> {
                    final Status status = locationSettingsResult.getStatus();
                    switch (status.getStatusCode()) {
                        case LocationSettingsStatusCodes.SUCCESS:
                            getEmitter().onSuccess(true);
                            break;
                        case LocationSettingsStatusCodes.RESOLUTION_REQUIRED:
                            Intent intent = new Intent(context, LocationSettingsActivity.class);
                            intent.putExtra(LocationSettingsActivity.ARG_STATUS, status);
                            intent.setFlags(FLAG_ACTIVITY_NEW_TASK);
                            context.startActivity(intent);
                            break;
                        case LocationSettingsStatusCodes.SETTINGS_CHANGE_UNAVAILABLE:
                            getEmitter().onSuccess(false);
                            break;
                    }
                });
    }
}
