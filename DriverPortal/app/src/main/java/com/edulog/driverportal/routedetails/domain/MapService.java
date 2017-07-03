package com.edulog.driverportal.routedetails.domain;

import com.edulog.driverportal.routedetails.model.Polyline;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface MapService {
    @GET("/maps/api/directions/json")
    Observable<Polyline> getDirection(@Query("origin") String origin,
                                      @Query("destination") String destination);
}
