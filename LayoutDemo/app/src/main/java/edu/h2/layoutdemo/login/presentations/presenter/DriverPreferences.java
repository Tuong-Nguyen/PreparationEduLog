package edu.h2.layoutdemo.login.presentations.presenter;

import android.content.Context;
import android.content.SharedPreferences;

import static android.content.Context.MODE_PRIVATE;

/**
 * Created by ntmhanh on 6/15/2017.
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

    public String getDriverId(){
        return loginPreferences.getString("driverId", "");
    }

    public void settingValue(String driverId){
        loginPrefsEditor.putString("driverId", driverId);
        loginPrefsEditor.commit();  // commit changes
    }
    public void removeValueItem(){
        loginPrefsEditor.remove("driverId");
        loginPrefsEditor.commit(); // commit changes
    }
}
