package com.edulog.driverportal.routes.domain.service;

import com.edulog.driverportal.routes.data.entity.RouteEntity;

import java.util.List;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface RouteService {
    @GET("/api/routes/{route_id}")
    Observable<RouteEntity> getRoute(@Path("route_id") String routeId);

    @GET("/api/search/routes")
    Observable<List<RouteEntity>> findRoutes(@Query("route_id") String query);
}
