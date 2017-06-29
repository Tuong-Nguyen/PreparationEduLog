package com.edulog.driverportal.common.device;

public interface Session {
    void putRouteId(String routeId);

    String getRouteId();

    void putDriverId(String driverId);

    String getDriverId();

    void putSpeedThreshold();

    double getSpeedThreshold();

    void clear();
}
