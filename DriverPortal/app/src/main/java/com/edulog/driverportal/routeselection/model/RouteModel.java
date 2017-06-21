package com.edulog.driverportal.routeselection.model;

import android.os.Parcel;

public class RouteModel {

    private String id;
    private String name;
    private int stopCount;

    public RouteModel() {
    }

    public RouteModel(Parcel source) {
        id = source.readString();
        name = source.readString();
        stopCount = source.readInt();
    }

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
