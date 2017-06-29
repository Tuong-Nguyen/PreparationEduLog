package com.edulog.driverportal.routedetails.model;

import com.google.android.gms.maps.model.LatLng;
import com.google.maps.android.PolyUtil;

import java.util.ArrayList;
import java.util.List;

public class Polyline {
    private List<String> polylines = new ArrayList<>();

    public void addPolyline(String polyline) {
        polylines.add(polyline);
    }

    public List<LatLng> decode() {
        List<LatLng> latLngs = new ArrayList<>();
        for (String points : polylines) {
            latLngs.addAll(PolyUtil.decode(points));
        }
        return latLngs;
    }
}