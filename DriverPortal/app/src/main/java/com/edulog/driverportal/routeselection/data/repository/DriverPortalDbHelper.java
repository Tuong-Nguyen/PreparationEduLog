package com.edulog.driverportal.routeselection.data.repository;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DriverPortalDbHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "DriverPortal.db";
    private static final int DATABASE_VERSION = 1;

    private static final String SQL_CREATE_DRIVER_ENTRY = "CREATE TABLE " + DriverPortalContract.DriverEntry.TABLE_NAME +
            " (" + DriverPortalContract.DriverEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
            DriverPortalContract.DriverEntry.COLUMN_NAME_ID + " TEXT NOT NULL UNIQUE)";
    private static final String SQL_DELETE_DRIVER_ENTRY = "DROP TABLE IF EXISTS " + DriverPortalContract.DriverEntry.TABLE_NAME;

    private static final String SQL_CREATE_ROUTE_ENTRY = "CREATE TABLE " + DriverPortalContract.RouteEntry.TABLE_NAME +
            " (" + DriverPortalContract.RouteEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
            DriverPortalContract.RouteEntry.COLUMN_NAME_ID + " TEXT NOT NULL UNIQUE, " +
            DriverPortalContract.RouteEntry.COLUMN_NAME_DRIVER_ID + " TEXT NOT NULL, " +
            DriverPortalContract.RouteEntry.COLUMN_NAME_NAME + " TEXT NOT NULL, " +
            DriverPortalContract.RouteEntry.COLUMN_NAME_STOP_COUNT + " INTEGER NOT NULL, " +
            "FOREIGN KEY(" + DriverPortalContract.RouteEntry.COLUMN_NAME_DRIVER_ID + ") REFERENCES " + DriverPortalContract.DriverEntry.TABLE_NAME + "(" + DriverPortalContract.DriverEntry.COLUMN_NAME_ID + "))";
    private static final String SQL_DELETE_ROUTE_ENTRY = "DROP TABLE IF EXISTS " + DriverPortalContract.RouteEntry.TABLE_NAME;

    public DriverPortalDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_DRIVER_ENTRY);
        db.execSQL(SQL_CREATE_ROUTE_ENTRY);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(SQL_DELETE_DRIVER_ENTRY);
        db.execSQL(SQL_DELETE_ROUTE_ENTRY);

        db.execSQL(SQL_CREATE_DRIVER_ENTRY);
        db.execSQL(SQL_CREATE_ROUTE_ENTRY);
    }

    @Override
    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        onUpgrade(db, oldVersion, newVersion);
    }
}
