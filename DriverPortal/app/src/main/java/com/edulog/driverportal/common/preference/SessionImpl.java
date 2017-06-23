package com.edulog.driverportal.common.preference;

import android.content.Context;
import android.content.SharedPreferences;

public class SessionImpl implements Session {
    private static final String NAME = "edulog";
    private static final String KEY_ROUTE_ID = "route_id";
    private static final String KEY_DRIVER_ID = "driver_id";

    private SharedPreferences sharedPreferences;

    public SessionImpl(Context context) {
        sharedPreferences = context.getSharedPreferences(NAME, Context.MODE_PRIVATE);
    }

    @Override
    public void putRouteId(String routeId) {
        putString(KEY_ROUTE_ID, routeId);
    }

    @Override
    public String getRouteId() {
        return getString(KEY_ROUTE_ID);
    }

    @Override
    public void putDriverId(String driverId) {
        putString(KEY_DRIVER_ID, driverId);
    }

    @Override
    public String getDriverId() {
        return getString(KEY_DRIVER_ID);
    }

    @Override
    public void removeDriverId() {
        removeString(KEY_DRIVER_ID);
    }

    private void putString(String key, String value) {
        sharedPreferences.edit()
                .putString(key, value)
                .apply();
    }

    private String getString(String key) {
        return sharedPreferences.getString(key, "");
    }

    public void removeString(String key){
        sharedPreferences.edit()
                .remove(key)
                .apply();
    }
}
