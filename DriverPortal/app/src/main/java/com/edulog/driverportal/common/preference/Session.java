package com.edulog.driverportal.common.preference;

public interface Session {
    void putRouteId(String routeId);

    String getRouteId();

    void putDriverId(String driverId);

    String getDriverId();

    void clear();
    void removeDriverId();
}
