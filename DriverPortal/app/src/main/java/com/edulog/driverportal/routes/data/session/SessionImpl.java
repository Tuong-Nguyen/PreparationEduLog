package com.edulog.driverportal.routes.data.session;

import android.content.Context;
import android.content.SharedPreferences;

public class SessionImpl implements Session {
    private SharedPreferences sharedPreferences;

    public SessionImpl(Context context) {
        sharedPreferences = context.getSharedPreferences("EDULOG", Context.MODE_PRIVATE);
    }

    @Override
    public void putRouteId(String routeId) {
        putString("route_id", routeId);
    }

    @Override
    public String getRouteId() {
        return getString("route_id");
    }

    private void putString(String key, String value) {
        sharedPreferences.edit()
                .putString(key, value)
                .apply();
    }

    private String getString(String key) {
        return sharedPreferences.getString(key, "");
    }
}
