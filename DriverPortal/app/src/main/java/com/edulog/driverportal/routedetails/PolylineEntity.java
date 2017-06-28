package com.edulog.driverportal.routedetails;

import com.google.android.gms.maps.model.LatLng;
import com.google.maps.android.PolyUtil;

import java.util.ArrayList;
import java.util.List;

public class PolylineEntity {
    private List<String> polylines = new ArrayList<>();

    public void addPolyline(String polyline) {
        polylines.add(polyline);
    }

    public List<String> getPolylines() {
        return polylines;
    }

    public List<LatLng> decode() {
        List<LatLng> latLngs = new ArrayList<>();
        for (String points : polylines) {
            latLngs.addAll(PolyUtil.decode(points));
        }
        return latLngs;
    }
}
