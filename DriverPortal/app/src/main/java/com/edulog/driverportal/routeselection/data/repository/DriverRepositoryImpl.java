package com.edulog.driverportal.routeselection.data.repository;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.edulog.driverportal.routeselection.data.entity.DriverEntity;
import com.edulog.driverportal.routeselection.domain.repository.DriverRepository;

// TODO: This class is not used - Please remove it
public class DriverRepositoryImpl implements DriverRepository {
    private static final String TABLE = DriverPortalContract.DriverEntry.TABLE_NAME;
    private DriverPortalDbHelper dbHelper;

    public DriverRepositoryImpl(DriverPortalDbHelper dbHelper) {
        this.dbHelper = dbHelper;
    }

    @Override
    public int upsert(DriverEntity driverEntity) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        int status;

        if (driverEntity != null) {
            ContentValues values = new ContentValues();
            values.put(DriverPortalContract.DriverEntry.COLUMN_NAME_ID, driverEntity.getId());

            if (isExists(driverEntity.getId())) {
                String whereClause = DriverPortalContract.DriverEntry.COLUMN_NAME_ID + " = ?";
                String[] whereArgs = {driverEntity.getId()};
                db.update(TABLE, values, whereClause, whereArgs);
            } else {
                db.insert(TABLE, null, values);
            }
            status = 1;
        } else {
            status = -1;
        }

        return status;
    }

    @Override
    public DriverEntity findOne(String driverId) {
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        String selection = DriverPortalContract.DriverEntry.COLUMN_NAME_ID + " = ?";
        String[] selectionArgs = {driverId};
        Cursor cursor = db.query(TABLE, null, selection, selectionArgs, null, null, null);

        DriverEntity driverEntity;
        if (cursor.moveToFirst()) {
            driverEntity = createDriverEntityFromCursor(cursor);
        } else {
            driverEntity = null;
        }

        cursor.close();

        return driverEntity;
    }

    private boolean isExists(String driverId) {
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        String query = "SELECT COUNT(*) FROM " + TABLE + " WHERE " + DriverPortalContract.DriverEntry.COLUMN_NAME_ID + " = ?";
        String[] selectionArgs = {driverId};
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

    private DriverEntity createDriverEntityFromCursor(Cursor cursor) {
        DriverEntity driverEntity = new DriverEntity();

        String id = cursor.getString(cursor.getColumnIndexOrThrow(DriverPortalContract.DriverEntry.COLUMN_NAME_ID));
        driverEntity.setId(id);

        return driverEntity;
    }
}
