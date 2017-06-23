package com.edulog.driverportal.common.preference;

// TODO: Rename this inteface and class to DevicePreference
public interface Session {
    void putRouteId(String routeId);

    String getRouteId();

    void putDriverId(String driverId);

    String getDriverId();

    void removeDriverId();
}
