package com.edulog.driverportal.routeselection.data.entity;

import android.support.annotation.NonNull;

import java.util.Date;

public class RouteEntity implements Comparable<RouteEntity> {
    private String id;
    private String name;
    private int stopCount;
    private Date updatedAt;

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

    @Override
    public int compareTo(@NonNull RouteEntity o) {
        return updatedAt.compareTo(o.getUpdatedAt());
    }
}
