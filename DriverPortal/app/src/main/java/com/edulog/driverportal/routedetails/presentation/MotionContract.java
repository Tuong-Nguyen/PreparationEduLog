package com.edulog.driverportal.routedetails.presentation;

import android.location.Location;

import com.edulog.driverportal.base.BasePresenter;
import com.edulog.driverportal.base.BaseView;
import com.google.android.gms.maps.model.LatLng;

import java.util.List;

public interface MotionContract {
    interface View extends BaseView {
        void drawRoute(List<LatLng> latLngs);
    }

    abstract class Presenter extends BasePresenter<View> {
        public abstract void getActiveRoute();
        public abstract void synchronize(Location location, double speed);
    }
}
