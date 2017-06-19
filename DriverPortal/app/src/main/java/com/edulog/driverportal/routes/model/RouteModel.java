package com.edulog.driverportal.routes.model;

import android.os.Parcel;
import android.os.Parcelable;

import okhttp3.Route;

public class RouteModel implements Parcelable {
    public static final Creator<RouteModel> CREATOR = new Creator<RouteModel>() {
        @Override
        public RouteModel createFromParcel(Parcel source) {
            return new RouteModel(source);
        }

        @Override
        public RouteModel[] newArray(int size) {
            return new RouteModel[size];
        }
    };

    private String id;
    private String name;
    private int stopCount;

    public RouteModel() {}

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

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(id);
        dest.writeString(name);
        dest.writeInt(stopCount);
    }

    @Override
    public int describeContents() {
        return 0;
    }
}
