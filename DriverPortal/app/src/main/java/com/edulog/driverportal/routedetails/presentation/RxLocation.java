package com.edulog.driverportal.routedetails.presentation;

import android.content.Context;
import android.location.Location;

import com.google.android.gms.location.LocationRequest;

import io.reactivex.BackpressureStrategy;
import io.reactivex.Flowable;
import io.reactivex.Single;

public class RxLocation {
    private Context context;

    public RxLocation(Context context) {
        this.context = context;
    }

    public Single<Boolean> checkAndHandleResolution(LocationRequest locationRequest) {
        return Single.create(new SettingHandlerSingleOnSubscribe(context, locationRequest));
    }

    public Flowable<Location> update(LocationRequest locationRequest) {
        return Flowable.create(new LocationUpdateFlowableOnSubscribe(context, locationRequest), BackpressureStrategy.LATEST);
    }
}
