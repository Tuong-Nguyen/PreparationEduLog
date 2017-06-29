package com.edulog.driverportal.routes.model;

import android.support.annotation.NonNull;

import java.util.Date;

public class Route implements Comparable<Route> {
    private String id;
    private String name;
    private int stopCount;
    private transient Date updatedAt;
    private String origin;
    private String destination;

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

    public Date getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Date updatedAt) {
        this.updatedAt = updatedAt;
    }

    public String getOrigin() {
        return origin;
    }

    public void setOrigin(String origin) {
        this.origin = origin;
    }

    public String getDestination() {
        return destination;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }

    @Override
    public int compareTo(@NonNull Route o) {
        return updatedAt.compareTo(o.getUpdatedAt());
    }
}
