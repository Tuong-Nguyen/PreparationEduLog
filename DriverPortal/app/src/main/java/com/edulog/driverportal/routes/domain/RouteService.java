package com.edulog.driverportal.routes.domain;

import com.edulog.driverportal.routes.model.Route;

import java.util.List;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface RouteService {
    @GET("/api/routes/{route_id}")
    Observable<Route> getRoute(@Path("route_id") String routeId);

    @GET("/api/search/routes")
    Observable<List<Route>> findRoutes(@Query("route_id") String query);
}
