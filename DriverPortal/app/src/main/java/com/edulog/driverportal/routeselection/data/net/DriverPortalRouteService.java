package com.edulog.driverportal.routeselection.data.net;

import com.edulog.driverportal.routeselection.data.entity.RouteEntity;

import java.util.List;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface DriverPortalRouteService {
    @GET("/api/routes/{route_id}")
    Observable<RouteEntity> getRoute(@Path("route_id") String routeId);

    @GET("/api/search/routes")
    Observable<List<RouteEntity>> findRoutes(@Query("route_id") String query);
}
