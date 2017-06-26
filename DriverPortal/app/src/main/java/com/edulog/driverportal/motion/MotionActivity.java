package com.edulog.driverportal.motion;

import android.Manifest;
import android.content.Intent;
import android.content.IntentSender;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.location.Location;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.widget.Toast;

import com.edulog.driverportal.R;
import com.edulog.driverportal.common.presentation.BaseActivity;
import com.edulog.driverportal.common.util.RetrofitServiceGenerator;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.gms.location.LocationSettingsResult;
import com.google.android.gms.location.LocationSettingsStatusCodes;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolylineOptions;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;

public class MotionActivity extends BaseActivity
        implements OnMapReadyCallback,
        GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener,
        LocationListener {
    private static final String TAG = MotionActivity.class.getSimpleName();
    int count = 0;
    Marker prevMarker;
    private GoogleApiClient googleApiClient;
    private Location currentLocation;
    private LocationRequest locationRequest;
    private GoogleMap googleMap;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_motion);

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        if (mapFragment != null) {
            mapFragment.getMapAsync(this);
        }

        buildGoogleApiClient();


        // Here, thisActivity is the current activity
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

            // Should we show an explanation?
            if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.ACCESS_FINE_LOCATION)) {


                // Show an explanation to the user *asynchronously* -- don't block
                // this thread waiting for the user's response! After the user
                // sees the explanation, try again to request the permission.
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                        222);
            } else {

                // No explanation needed, we can request the permission.

                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                        222);

                // MY_PERMISSIONS_REQUEST_READ_CONTACTS is an
                // app-defined int constant. The callback method gets the
                // result of the request.
            }
        } else {
            Log.d(TAG, "Permission granted");
        }

        MapService mapService = new RetrofitServiceGenerator()
                .baseUrl("https://maps.googleapis.com/")
                .generate(MapService.class);
        String origin = "Toronto";
        String dest = "Montreal";
        mapService.getDirection(origin, dest, "AIzaSyChGu6otpqv7Df1ustS2Y1XAehQB9UJmBg")
                .subscribeOn(Schedulers.io())
                .map(new Function<String, List<LatLng>>() {
                    @Override
                    public List<LatLng> apply(String s) throws Exception {
                        return decodePoly(s);
                    }
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<List<LatLng>>() {
                    @Override
                    public void accept(List<LatLng> latLngs) throws Exception {
                        googleMap.addPolyline(new PolylineOptions().addAll(latLngs).color(Color.parseColor("#05b1fb")).geodesic(true));
                    }
                });
    }

    @Override
    protected void onStart() {
        super.onStart();
        googleApiClient.connect();
    }

    @Override
    protected void onResume() {
        super.onResume();
        startLocationUpdate();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case 222: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                    Log.d(TAG, "Permission granted");
                    googleApiClient.connect();

                } else {
                    Log.d(TAG, "Permission denied");

                    // permission denied, boo! Disable the
                    // functionality that depends on this permission.
                }
                return;
            }

            // other 'case' lines to check for other
            // permissions this app might request
        }
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        this.googleMap = googleMap;
        LatLng sydney = new LatLng(-33.852, 151.211);
        googleMap.addMarker(new MarkerOptions().position(sydney)
                .title("Marker in Sydney"));
        googleMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));
    }

    private void buildGoogleApiClient() {
        Log.d(TAG, "Build google api client");
        googleApiClient = new GoogleApiClient.Builder(this)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .build();
    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {
        startLocationUpdate();
    }

    @Override
    public void onConnectionSuspended(int i) {
        Log.d(TAG, "Connection Suspended");
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
        Log.d(TAG, connectionResult.getErrorMessage());
    }

    @Override
    public void onLocationChanged(Location location) {
        Log.d(TAG, "Latitude: " + location.getLatitude() + ", Longitude: " + location.getLongitude());
        if (prevMarker != null) prevMarker.remove();
        prevMarker = googleMap.addMarker(new MarkerOptions().position(new LatLng(location.getLatitude(), location.getLongitude())));
        googleMap.moveCamera(CameraUpdateFactory.newLatLng(new LatLng(location.getLatitude(), location.getLongitude())));
        googleMap.animateCamera(CameraUpdateFactory.zoomTo(15), 500, null);
    }

    @SuppressWarnings("MissingPermission")
    private void startLocationUpdate() {
        Log.d(TAG, "startLocationUpdate");
        // create location request
        locationRequest = new LocationRequest();
        locationRequest.setInterval(500);
        locationRequest.setFastestInterval(500);
        locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);

        // create location setting request
        LocationSettingsRequest.Builder builder = new LocationSettingsRequest.Builder();
        builder.addLocationRequest(locationRequest);
        LocationSettingsRequest settingsRequest = builder.build();

        // check location settings
        LocationServices.SettingsApi.checkLocationSettings(googleApiClient, settingsRequest)
                .setResultCallback(new ResultCallback<LocationSettingsResult>() {
                    @Override
                    public void onResult(@NonNull LocationSettingsResult locationSettingsResult) {
                        final Status status = locationSettingsResult.getStatus();
                        switch (status.getStatusCode()) {
                            case LocationSettingsStatusCodes.SUCCESS:
                                LocationServices.FusedLocationApi.requestLocationUpdates(googleApiClient, locationRequest, MotionActivity.this);
                                break;
                            case LocationSettingsStatusCodes.RESOLUTION_REQUIRED:
                                LocationServices.FusedLocationApi.requestLocationUpdates(googleApiClient, locationRequest, MotionActivity.this);

//                                try {
//                                    status.startResolutionForResult(MotionActivity.this, 1);
//                                } catch (IntentSender.SendIntentException e) {
//                                    Log.d(TAG, e.getMessage());
//                                }
                                break;
                            case LocationSettingsStatusCodes.SETTINGS_CHANGE_UNAVAILABLE:
                                String errorMessage = "Location settings are inadequate, and cannot be " +
                                        "fixed here. Fix in Settings.";
                                Log.e("MotionActivity", errorMessage);
                                Toast.makeText(MotionActivity.this, errorMessage, Toast.LENGTH_LONG).show();
                                break;
                        }
                    }
                });
    }

    private List<LatLng> decodePoly(String encoded) {

        List<LatLng> poly = new ArrayList<LatLng>();
        int index = 0, len = encoded.length();
        int lat = 0, lng = 0;

        while (index < len) {
            int b, shift = 0, result = 0;
            do {
                b = encoded.charAt(index++) - 63;
                result |= (b & 0x1f) << shift;
                shift += 5;
            } while (b >= 0x20);
            int dlat = ((result & 1) != 0 ? ~(result >> 1) : (result >> 1));
            lat += dlat;

            shift = 0;
            result = 0;
            do {
                b = encoded.charAt(index++) - 63;
                result |= (b & 0x1f) << shift;
                shift += 5;
            } while (b >= 0x20);
            int dlng = ((result & 1) != 0 ? ~(result >> 1) : (result >> 1));
            lng += dlng;

            LatLng p = new LatLng((((double) lat / 1E5)),
                    (((double) lng / 1E5)));
            poly.add(p);
        }

        return poly;
    }
}
