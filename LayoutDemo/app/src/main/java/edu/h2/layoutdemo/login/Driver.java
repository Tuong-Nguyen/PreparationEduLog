package edu.h2.layoutdemo.login;

/**
 * Created by ntmhanh on 6/12/2017.
 */

public class Driver {
    public static final String BUSID = "1";
    public static final String DRIVERID = "2";
    public static final String PASSWORD = "123";

    private String busId;
    private String driverId;
    private String password;

    public Driver(String busId, String driverId, String password) {
        this.busId = busId;
        this.driverId = driverId;
        this.password = password;
    }

    public String getBusId() {
        return busId;
    }


    public String getDriverId() {
        return driverId;
    }

    public void setDriverId(String diverid) {
        this.driverId = diverid;
    }

    public String getPassword() {
        return password;
    }

}
