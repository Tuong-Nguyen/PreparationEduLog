package com.edulog.driverportal.routedetails;

import android.content.Context;
import android.location.Location;

import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;

public class LocationUpdateSingleOnSubscribe extends RxLocationSingleOnSubscribe<Location> {
    private Context context;
    private LocationRequest locationRequest;

    public LocationUpdateSingleOnSubscribe(Context context, LocationRequest locationRequest) {
        super(context);
        this.context = context;
        this.locationRequest = locationRequest;
    }

    @Override
    @SuppressWarnings("MissingPermission")
    protected void onGoogleApiAvailable() {
        LocationServices.FusedLocationApi.requestLocationUpdates(getGoogleApiClient(), locationRequest, location -> {
            getEmitter().onSuccess(location);
        });
    }
}
