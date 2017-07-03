package com.edulog.driverportal.routedetails.domain;

import com.edulog.driverportal.base.UseCase;
import com.edulog.driverportal.routedetails.model.Polyline;
import com.edulog.driverportal.routes.model.Route;
import com.edulog.driverportal.routes.data.RouteRepository;
import com.edulog.driverportal.routes.domain.RouteService;
import com.google.android.gms.maps.model.LatLng;

import java.util.List;

import io.reactivex.Observable;

public class GetRouteUseCase extends UseCase<List<LatLng>, String> {
    private RouteService routeService;
    private RouteRepository routeRepository;
    private MapService mapService;

    public GetRouteUseCase(RouteService routeService, RouteRepository routeRepository, MapService mapService) {
        this.routeService = routeService;
        this.routeRepository = routeRepository;
        this.mapService = mapService;
    }

    @Override
    public Observable<List<LatLng>> buildUseCaseObservable(String routeId) {
        return routeService.getRoute(routeId)
                .onErrorResumeNext(throwable -> {
                    // If get route from server failed, then try to get route from local database
                    return Observable.just(routeRepository.findOne(routeId));
                })
                .doOnNext(routeEntity -> {
                    if (routeEntity == null) {
                        throw new RuntimeException("Can not get route entity from both sever and local.");
                    }
                })
                .flatMap(this::getRouteDirectionObservable);
    }

    private Observable<List<LatLng>> getRouteDirectionObservable(Route route) {
        return mapService.getDirection(route.getOrigin(), route.getDestination())
                .map(Polyline::decode);
    }
}
