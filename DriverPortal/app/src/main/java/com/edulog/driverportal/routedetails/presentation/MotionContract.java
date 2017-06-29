package com.edulog.driverportal.routedetails.presentation;

import android.location.Location;

import com.edulog.driverportal.common.presentation.BasePresenter;
import com.edulog.driverportal.common.presentation.BaseView;
import com.google.android.gms.maps.model.LatLng;

import java.util.List;

public interface MotionContract {
    interface MotionView extends BaseView {
        void drawRoute(List<LatLng> latLngs);
    }

    abstract class MotionPresenter extends BasePresenter<MotionView> {
        public abstract void getActiveRoute();
        public abstract void synchronize(Location location, double speed);
    }
}
