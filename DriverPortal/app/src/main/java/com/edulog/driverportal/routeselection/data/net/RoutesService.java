package com.edulog.driverportal.routeselection.data.net;

import com.edulog.driverportal.routeselection.data.entity.RouteEntity;

import java.util.List;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface RoutesService {
    @GET("/api/routes")
    Observable<List<RouteEntity>> findRoutesById(@Query("route_id") String routeId);
}
