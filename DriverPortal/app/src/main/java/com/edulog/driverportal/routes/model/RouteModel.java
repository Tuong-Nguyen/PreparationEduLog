package com.edulog.driverportal.routes.model;

import android.os.Parcel;
import android.os.Parcelable;

import okhttp3.Route;

public class RouteModel {
    private String id;
    private String name;
    private int stopCount;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getStopCount() {
        return stopCount;
    }

    public void setStopCount(int stopCount) {
        this.stopCount = stopCount;
    }
}
