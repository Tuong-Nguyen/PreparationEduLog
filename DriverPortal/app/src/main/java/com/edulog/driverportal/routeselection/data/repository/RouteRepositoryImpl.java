package com.edulog.driverportal.routeselection.data.repository;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.edulog.driverportal.routeselection.data.entity.RouteEntity;
import com.edulog.driverportal.routeselection.domain.repository.RouteRepository;

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
        String[] selectionArgs = {routeId};
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
        long insertedId;

        if (routeEntity != null && !isExists(routeEntity.getId())) {
            SQLiteDatabase db = dbHelper.getWritableDatabase();

            ContentValues values = new ContentValues();
            values.put(DriverPortalContract.RouteEntry.COLUMN_NAME_ID, routeEntity.getId());
            values.put(DriverPortalContract.RouteEntry.COLUMN_NAME_NAME, routeEntity.getName());
            values.put(DriverPortalContract.RouteEntry.COLUMN_NAME_STOP_COUNT, routeEntity.getStopCount());

            insertedId =  db.insert(table, null, values);
        } else {
            insertedId =  -1;
        }

        return insertedId;
    }

    @Override
    public int upsert(RouteEntity routeEntity) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        int status;

        if (routeEntity != null) {
            ContentValues values = new ContentValues();
            values.put(DriverPortalContract.RouteEntry.COLUMN_NAME_ID, routeEntity.getId());
            values.put(DriverPortalContract.RouteEntry.COLUMN_NAME_NAME, routeEntity.getName());
            values.put(DriverPortalContract.RouteEntry.COLUMN_NAME_STOP_COUNT, routeEntity.getStopCount());

            if (isExists(routeEntity.getId())) {
                String whereClause = DriverPortalContract.RouteEntry.COLUMN_NAME_ID + " = ?";
                String[] whereArgs = { routeEntity.getId() };
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
    public void delete(String routeId) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        String whereClause = DriverPortalContract.RouteEntry.COLUMN_NAME_ID + " = ?";
        String[] whereArgs = {routeId};
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

    private boolean isExists(String routeId) {
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        String query = "SELECT COUNT(*) FROM " + table + " WHERE " + DriverPortalContract.RouteEntry.COLUMN_NAME_ID + " = ?";
        String[] selectionArgs = { routeId };
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
