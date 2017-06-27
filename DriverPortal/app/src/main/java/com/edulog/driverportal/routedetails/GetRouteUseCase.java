package com.edulog.driverportal.routedetails;

import com.edulog.driverportal.common.domain.UseCase;
import com.edulog.driverportal.routes.data.entity.RouteEntity;
import com.edulog.driverportal.routes.domain.repository.RouteRepository;
import com.edulog.driverportal.routes.domain.service.RouteService;
import com.google.android.gms.maps.model.LatLng;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.schedulers.Schedulers;

public class GetRouteUseCase extends UseCase<List<LatLng>, String> {
    private RouteService routeService;
    private RouteRepository routeRepository;
    private MapService mapService;
    private String apiKey;

    public GetRouteUseCase(RouteService routeService, RouteRepository routeRepository, MapService mapService, String apiKey) {
        this.routeService = routeService;
        this.routeRepository = routeRepository;
        this.mapService = mapService;
        this.apiKey = apiKey;
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
                .flatMap(this::getRouteDirectionObservable)
                .subscribeOn(Schedulers.io());
    }

    private Observable<List<LatLng>> getRouteDirectionObservable(RouteEntity routeEntity) {
        return mapService.getDirection(routeEntity.getOrigin(), routeEntity.getDestination(), apiKey)
                .map(this::decodePoly);
    }

    private List<LatLng> decodePoly(PolylineEntity polylineEntity) {
        String encoded = polylineEntity.getPoints();
        List<LatLng> poly = new ArrayList<>();
        int index = 0, len = encoded.length();
        int lat = 0, lng = 0;

        while (index < len) {
            int b, shift = 0, result = 0;
            do {
                b = encoded.charAt(index++) - 63;
                result |= (b & 0x1f) << shift;
                shift += 5;
            } while (b >= 0x20);
            int dlat = ((result & 1) != 0 ? ~(result >> 1) : (result >> 1));
            lat += dlat;

            shift = 0;
            result = 0;
            do {
                b = encoded.charAt(index++) - 63;
                result |= (b & 0x1f) << shift;
                shift += 5;
            } while (b >= 0x20);
            int dlng = ((result & 1) != 0 ? ~(result >> 1) : (result >> 1));
            lng += dlng;

            LatLng p = new LatLng((((double) lat / 1E5)),
                    (((double) lng / 1E5)));
            poly.add(p);
        }

        return poly;
    }
}
