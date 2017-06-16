package edu.h2.layoutdemo.login.presentations.presenter;

import android.content.Context;
import android.content.SharedPreferences;

import static android.content.Context.MODE_PRIVATE;

/**
 * DriverPreferences help to save values, which we want to keep for login the next time
 */

public class DriverPreferences {
    private Context context;
    private SharedPreferences loginPreferences;
    private SharedPreferences.Editor loginPrefsEditor;

    public DriverPreferences(Context context) {
        this.context = context;
        //Create SharedPreferences
        loginPreferences = context.getSharedPreferences("driverPrefs", MODE_PRIVATE);
        loginPrefsEditor = loginPreferences.edit();
    }

    // Retrieve a String value from the preferences.
    public String getDriverId(){
        return loginPreferences.getString("driverId", "");
    }

    //Set String values in the preferences editor
    public void settingValue(String driverId){
        loginPrefsEditor.putString("driverId", driverId);
        loginPrefsEditor.commit();  // commit changes
    }
    // Removed values from preferences
    public void removeValueItem(){
        loginPrefsEditor.remove("driverId");
        loginPrefsEditor.commit(); // commit changes
    }
}
