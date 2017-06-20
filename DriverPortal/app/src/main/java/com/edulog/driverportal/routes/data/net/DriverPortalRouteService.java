package com.edulog.driverportal.routes.data.net;

import com.edulog.driverportal.routes.data.entity.RouteEntity;

import java.util.List;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface DriverPortalRouteService {
    @GET("/api/search/routes")
    Observable<List<RouteEntity>> findRoutes(@Query("route_id") String query);
}
