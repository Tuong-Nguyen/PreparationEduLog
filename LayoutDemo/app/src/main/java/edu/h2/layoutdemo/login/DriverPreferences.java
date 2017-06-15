package edu.h2.layoutdemo.login;

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
    public Boolean isSaveDriverId;

    public DriverPreferences(Context context) {
        this.context = context;
        //Create SharedPreferences
        loginPreferences = context.getSharedPreferences("driverPrefs", MODE_PRIVATE);
        loginPrefsEditor = loginPreferences.edit();
    }

    public void saveDriverId(){
        isSaveDriverId = loginPreferences.getBoolean("saveDriverId", false);
    }
    public String getDriverId(){
        return loginPreferences.getString("driverId", "");
    }

    public void settingValue(String driverId){
        loginPrefsEditor.putBoolean("saveDriverId", true);
        loginPrefsEditor.putString("driverId", driverId);
        loginPrefsEditor.commit();  // commit changes
    }
    public void removeValueItem(){
        loginPrefsEditor.remove("driverId");
        loginPrefsEditor.commit(); // commit changes
    }

    public void setRememberDriverId(boolean isRemember, String driverId) {
        if (isRemember) {
            settingValue(driverId);
        } else {
            removeValueItem();
        }
    }
}
