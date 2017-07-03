package com.edulog.driverportal.location;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.support.v7.app.AppCompatActivity;

import com.edulog.driverportal.R;
import com.google.android.gms.location.LocationRequest;

import io.reactivex.BackpressureStrategy;
import io.reactivex.Flowable;
import io.reactivex.Single;

public class RxLocation {
    private AppCompatActivity activity;

    public RxLocation(AppCompatActivity activity) {
        this.activity = activity;
    }

    public Single<Boolean> checkAndHandleResolution(LocationRequest locationRequest) {
        LocationSettingFragment fragment = LocationSettingFragment.newInstance(locationRequest);
        activity.getSupportFragmentManager()
                .beginTransaction()
                .add(R.id.contentFrame, fragment)
                .commit();
        return Single.create(fragment);
    }

    public Flowable<Location> update(LocationRequest locationRequest) {
        return Flowable.create(new LocationUpdate(activity, locationRequest), BackpressureStrategy.LATEST);
    }
}
