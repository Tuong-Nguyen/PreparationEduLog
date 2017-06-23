package com.edulog.driverportal.login.domain.services;

import android.content.Context;
import android.content.SharedPreferences;

import static android.content.Context.MODE_PRIVATE;

/**
 * DriverPreferences help to save values, which we want to keep for login the next time
 */
// TODO: Merge this code into SessionImpl and use SessionImpl instead of this class
public class DriverPreferences {
    // TODO: ntmhanh Remove if it is not used
    private Context context;
    private SharedPreferences loginPreferences;
    private SharedPreferences.Editor loginPrefsEditor;

    public DriverPreferences(Context context) {
        this.context = context;
        //Create SharedPreferences
        loginPreferences = this.context.getSharedPreferences("driverPrefs", MODE_PRIVATE);
        loginPrefsEditor = loginPreferences.edit();
    }

    // Retrieve a String value from the preferences.
    public String getDriverId(){
        return loginPreferences.getString("driverId", "");
    }

    // TODO: ntmhanh Please use name which reflects the method's purpose
    //Set String values in the preferences editor
    public void setValuePreferences(String driverId){
        loginPrefsEditor.putString("driverId", driverId);
        loginPrefsEditor.commit();  // commit changes
    }


    // Removed values from preferences
    public void removeValueItem(){
        loginPrefsEditor.remove("driverId");
        loginPrefsEditor.commit(); // commit changes
    }
}
