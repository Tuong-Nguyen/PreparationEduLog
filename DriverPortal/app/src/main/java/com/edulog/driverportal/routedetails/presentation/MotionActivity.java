package com.edulog.driverportal.routedetails.presentation;

import android.Manifest;
import android.graphics.Color;
import android.location.Location;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;

import com.edulog.driverportal.base.DriverPortalApplication;
import com.edulog.driverportal.R;
import com.edulog.driverportal.location.RxLocation;
import com.edulog.driverportal.session.Session;
import com.edulog.driverportal.base.BaseActivity;
import com.edulog.driverportal.base.BasePresenter;
import com.edulog.driverportal.base.BaseView;
import com.edulog.driverportal.util.DefaultObserver;
import com.edulog.driverportal.routedetails.domain.GetRouteUseCase;
import com.edulog.driverportal.routedetails.domain.MapService;
import com.edulog.driverportal.routes.domain.RouteService;
import com.edulog.driverportal.routes.data.DriverPortalDbHelper;
import com.edulog.driverportal.routes.data.RouteRepositoryImpl;
import com.edulog.driverportal.routes.data.RouteRepository;
import com.edulog.driverportal.util.RetrofitServiceGenerator;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolylineOptions;
import com.tbruyelle.rxpermissions2.RxPermissions;

import java.util.List;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class MotionActivity extends BaseActivity implements OnMapReadyCallback,
        MotionContract.View {
    private static final String TAG = MotionActivity.class.getSimpleName();

    private MotionContract.Presenter motionPresenter;
    private Marker prevMarker;
    private GoogleMap googleMap;

    private LocationRequest locationRequest;
    private RxLocation rxLocation;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_motion);

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        if (mapFragment != null) {
            mapFragment.getMapAsync(this);
        }

        RouteService routeService = new RetrofitServiceGenerator().generate(RouteService.class);
        MapService mapService = new RetrofitServiceGenerator()
                .baseUrl("https://maps.googleapis.com/")
                .generate(MapService.class);
        RouteRepository routeRepository = new RouteRepositoryImpl(new DriverPortalDbHelper(this));
        String apiKey = getResources().getString(R.string.google_maps_api_key);
        GetRouteUseCase getRouteUseCase = new GetRouteUseCase(routeService, routeRepository, mapService, apiKey);
        Session session = ((DriverPortalApplication) getApplication()).getSession();
        motionPresenter = new MotionPresenterImpl(getRouteUseCase, session);
    }

    @Override
    protected void onResume() {
        super.onResume();
        startLocationUpdate();
    }

    @Override
    protected BasePresenter getPresenter() {
        return motionPresenter;
    }

    @Override
    protected BaseView getView() {
        return this;
    }

    @Override
    public void drawRoute(List<LatLng> latLngs) {
//        prevMarker = googleMap.addMarker(new MarkerOptions().position(latLngs.get(0)));
//        googleMap.moveCamera(CameraUpdateFactory.newLatLng(latLngs.get(0)));
//        googleMap.animateCamera(CameraUpdateFactory.zoomTo(15), 500, null);
        PolylineOptions options = new PolylineOptions()
                .addAll(latLngs)
                .color(Color.parseColor("#05b1fb"))
                .geodesic(true);
        googleMap.addPolyline(options);
    }

    private void startLocationUpdate() {
        locationRequest = new LocationRequest();
        locationRequest.setInterval(2000);
        locationRequest.setFastestInterval(2000);
        locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);

        rxLocation = new RxLocation(this);

        RxPermissions rxPermissions = new RxPermissions(this);
        rxPermissions.request(Manifest.permission.ACCESS_FINE_LOCATION)
                .flatMap(this::getUpdateLocationObservable)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new DefaultObserver<Location>() {
                    @Override
                    public void onNext(Location location) {
                        move(location);
                        double speed = location.getSpeed();
                        Log.d(TAG, "speed: " + speed);
                        motionPresenter.synchronize(location, speed);
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.d(TAG, "onError: " + e.getMessage());
                    }
                });
    }

    private Observable<Location> getUpdateLocationObservable(boolean permissionGranted) {
        if (permissionGranted) {
            return rxLocation
                    .checkAndHandleResolution(locationRequest)
                    .flatMapObservable(this::getInternalUpdateLocationObservable);
        } else {
            throw new RuntimeException("RxPermission: permission not granted.");
        }
    }

    private Observable<Location> getInternalUpdateLocationObservable(boolean success) {
        if (success) {
            return rxLocation
                    .update(locationRequest)
                    .subscribeOn(Schedulers.io())
                    .toObservable();
        } else {
            throw new RuntimeException("RxLocation settings: permission not permitted.");
        }
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        this.googleMap = googleMap;
        motionPresenter.getActiveRoute();
    }

    public void move(Location location) {
        if (prevMarker != null) prevMarker.remove();
        prevMarker = googleMap.addMarker(new MarkerOptions().position(new LatLng(location.getLatitude(), location.getLongitude())));
        googleMap.moveCamera(CameraUpdateFactory.newLatLng(new LatLng(location.getLatitude(), location.getLongitude())));
        googleMap.animateCamera(CameraUpdateFactory.zoomTo(15), 500, null);
    }
}
