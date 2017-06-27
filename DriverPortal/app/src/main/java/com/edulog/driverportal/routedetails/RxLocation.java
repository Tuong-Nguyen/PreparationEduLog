package com.edulog.driverportal.routedetails;

import android.content.Context;
import android.location.Location;

import com.google.android.gms.location.LocationRequest;

import io.reactivex.Single;

public class RxLocation {
    private Context context;

    public RxLocation(Context context) {
        this.context = context;
    }

    public Single<Boolean> checkAndHandleResolution(LocationRequest locationRequest) {
        return Single.create(new SettingHandleSingleOnSubscribe(context, locationRequest));
    }

    public Single<Location> update(LocationRequest locationRequest) {
        return Single.create(new LocationUpdateSingleOnSubscribe(context, locationRequest));
    }
}
