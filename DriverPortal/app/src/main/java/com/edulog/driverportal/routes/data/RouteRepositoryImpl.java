package com.edulog.driverportal.routes.data;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.edulog.driverportal.routes.model.Route;
import com.edulog.driverportal.util.DateConverter;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class RouteRepositoryImpl implements RouteRepository {
    private String table = DriverPortalContract.RouteEntry.TABLE_NAME;
    private DriverPortalDbHelper dbHelper;

    public RouteRepositoryImpl(DriverPortalDbHelper dbHelper) {
        this.dbHelper = dbHelper;
    }

    @Override
    public Route findOne(String routeId) {
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        String selection = DriverPortalContract.RouteEntry.COLUMN_NAME_ID + " = ?";
        String[] selectionArgs = {routeId};
        Cursor cursor = db.query(table, null, selection, selectionArgs, null, null, null);

        Route route;
        if (cursor.moveToFirst()) {
            route = createRouteEntityFromCursor(cursor);
        } else {
            route = null;
        }

        cursor.close();

        return route;
    }

    @Override
    public int upsert(Route route) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        int status;

        if (route != null) {
            ContentValues values = new ContentValues();
            values.put(DriverPortalContract.RouteEntry.COLUMN_NAME_ID, route.getId());
            values.put(DriverPortalContract.RouteEntry.COLUMN_NAME_NAME, route.getName());
            values.put(DriverPortalContract.RouteEntry.COLUMN_NAME_STOP_COUNT, route.getStopCount());
            values.put(DriverPortalContract.RouteEntry.COLUMN_NAME_UPDATED_AT, new DateConverter().toString(new Date()));
            values.put(DriverPortalContract.RouteEntry.COLUMN_NAME_ORIGIN, route.getOrigin());
            values.put(DriverPortalContract.RouteEntry.COLUMN_NAME_DESTINATION, route.getDestination());

            if (isExists(route)) {
                String whereClause = DriverPortalContract.RouteEntry.COLUMN_NAME_ID + " = ?";
                String[] whereArgs = {route.getId()};
                db.update(table, values, whereClause, whereArgs);
            } else {
                db.insert(table, null, values);
            }
            status = 1;
        } else {
            status = -1;
        }

        return status;
    }

    @Override
    public List<Route> findAll() {
        List<Route> routeEntities = new ArrayList<>();

        SQLiteDatabase db = dbHelper.getReadableDatabase();
        try (Cursor cursor = db.query(table, null, null, null, null, null, null)) {
            while (cursor.moveToNext()) {
                Route route = createRouteEntityFromCursor(cursor);
                routeEntities.add(route);
            }
        }

        return routeEntities;
    }

    private Route createRouteEntityFromCursor(Cursor cursor) {
        Route route = new Route();

        String id = cursor.getString(cursor.getColumnIndexOrThrow(DriverPortalContract.RouteEntry.COLUMN_NAME_ID));
        route.setId(id);

        String name = cursor.getString(cursor.getColumnIndexOrThrow(DriverPortalContract.RouteEntry.COLUMN_NAME_NAME));
        route.setName(name);

        int stopCount = cursor.getInt(cursor.getColumnIndexOrThrow(DriverPortalContract.RouteEntry.COLUMN_NAME_STOP_COUNT));
        route.setStopCount(stopCount);

        String dateStr = cursor.getString(cursor.getColumnIndexOrThrow(DriverPortalContract.RouteEntry.COLUMN_NAME_UPDATED_AT));
        DateConverter dateConverter = new DateConverter();
        route.setUpdatedAt(dateConverter.toDate(dateStr));

        String origin = cursor.getString(cursor.getColumnIndexOrThrow(DriverPortalContract.RouteEntry.COLUMN_NAME_ORIGIN));
        route.setOrigin(origin);

        String destination = cursor.getString(cursor.getColumnIndexOrThrow(DriverPortalContract.RouteEntry.COLUMN_NAME_DESTINATION));
        route.setDestination(destination);

        return route;
    }

    private boolean isExists(Route route) {
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        String query = "SELECT COUNT(*) FROM " + table +
                " WHERE " + DriverPortalContract.RouteEntry.COLUMN_NAME_ID + " = ?";
        String[] selectionArgs = {route.getId()};
        Cursor cursor = db.rawQuery(query, selectionArgs);

        int count;
        if (cursor.moveToFirst()) {
            count = cursor.getInt(0);
        } else {
            count = 0;
        }
        cursor.close();

        return count != 0;
    }
}
