package com.edulog.driverportal.routes.data.repository;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.edulog.driverportal.routes.data.entity.RouteEntity;
import com.edulog.driverportal.routes.domain.repository.RouteRepository;

import java.sql.Driver;
import java.util.ArrayList;
import java.util.List;

public class RouteRepositoryImpl implements RouteRepository {
    private String table = DriverPortalContract.RouteEntry.TABLE_NAME;
    private DriverPortalDbHelper dbHelper;

    public RouteRepositoryImpl(DriverPortalDbHelper dbHelper) {
        this.dbHelper = dbHelper;
    }

    @Override
    public RouteEntity findOne(String routeId) {
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        String selection = DriverPortalContract.RouteEntry.COLUMN_NAME_ID + " = ?";
        String[] selectionArgs = { routeId };
        Cursor cursor = db.query(table, null, selection, selectionArgs, null, null, null);

        RouteEntity routeEntity;
        if (cursor.moveToFirst()) {
            routeEntity = createRouteEntityFromCursor(cursor);
        } else {
            routeEntity = null;
        }

        cursor.close();

        return routeEntity;
    }

    @Override
    public List<RouteEntity> findAll() {
        List<RouteEntity> routeEntities = new ArrayList<>();

        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.query(table, null, null, null, null, null, null);
        while (cursor.moveToNext()) {
            RouteEntity routeEntity = createRouteEntityFromCursor(cursor);
            routeEntities.add(routeEntity);
        }

        cursor.close();

        return routeEntities;
    }

    @Override
    public long insert(RouteEntity routeEntity) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(DriverPortalContract.RouteEntry.COLUMN_NAME_ID, routeEntity.getId());
        values.put(DriverPortalContract.RouteEntry.COLUMN_NAME_NAME, routeEntity.getName());
        values.put(DriverPortalContract.RouteEntry.COLUMN_NAME_STOP_COUNT, routeEntity.getStopCount());

        return db.insert(table, null, values);
    }

    @Override
    public void delete(String routeId) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        String whereClause = DriverPortalContract.RouteEntry.COLUMN_NAME_ID + " = ?";
        String[] whereArgs = { routeId };
        db.delete(table, whereClause, whereArgs);
    }

    private RouteEntity createRouteEntityFromCursor(Cursor cursor) {
        RouteEntity routeEntity = new RouteEntity();

        String id = cursor.getString(cursor.getColumnIndexOrThrow(DriverPortalContract.RouteEntry.COLUMN_NAME_ID));
        routeEntity.setId(id);

        String name = cursor.getString(cursor.getColumnIndexOrThrow(DriverPortalContract.RouteEntry.COLUMN_NAME_NAME));
        routeEntity.setName(name);

        int stopCount = cursor.getInt(cursor.getColumnIndexOrThrow(DriverPortalContract.RouteEntry.COLUMN_NAME_STOP_COUNT));
        routeEntity.setStopCount(stopCount);

        return routeEntity;
    }
}
