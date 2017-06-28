package com.edulog.driverportal.routes.data.repository;

import android.provider.BaseColumns;

public final class DriverPortalContract {
    private DriverPortalContract() {
    }

    public static class RouteEntry implements BaseColumns {
        public static final String TABLE_NAME = "route";
        public static final String COLUMN_NAME_ID = "id";
        public static final String COLUMN_NAME_NAME = "name";
        public static final String COLUMN_NAME_STOP_COUNT = "stop_count";
        public static final String COLUMN_NAME_UPDATED_AT = "updated_at";
        public static final String COLUMN_NAME_ORIGIN = "origin";
        public static final String COLUMN_NAME_DESTINATION = "destination";
    }
}
