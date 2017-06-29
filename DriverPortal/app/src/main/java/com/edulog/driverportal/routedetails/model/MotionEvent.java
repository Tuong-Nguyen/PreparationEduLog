package com.edulog.driverportal.routedetails.model;

public class MotionEvent {
    public static final int NO_MOTION_BEGIN = 1;
    public static final int NO_MOTION_END = 2;

    private double latitude;
    private double longitude;

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }
}
