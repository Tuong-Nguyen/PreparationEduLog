package com.edulog.driverportal.routedetails.domain;

import io.reactivex.Observable;
import okhttp3.ResponseBody;
import retrofit2.Response;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface EventService {
    @POST("/api/events")
    @FormUrlEncoded
    Observable<Response<ResponseBody>> createEvent(@Field("latitude") double latitude, @Field("longitude") double longitude);
}
