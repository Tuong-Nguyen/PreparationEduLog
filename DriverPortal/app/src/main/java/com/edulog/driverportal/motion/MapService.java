package com.edulog.driverportal.motion;

import io.reactivex.Observable;
import okhttp3.ResponseBody;
import retrofit2.Response;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface MapService {
    @GET("/maps/api/directions/json")
    Observable<String> getDirection(@Query("origin") String origin,
                                                    @Query("destination") String destination,
                                                    @Query("key") String key);
}
