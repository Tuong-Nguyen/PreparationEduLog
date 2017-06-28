package com.edulog.driverportal.routedetails;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface MapService {
    @GET("/maps/api/directions/json")
    Observable<EncodedPolylineEntity> getDirection(@Query("origin") String origin,
                                                   @Query("destination") String destination,
                                                   @Query("key") String key);
}
