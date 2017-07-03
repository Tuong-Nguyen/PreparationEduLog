package com.edulog.driverportal.routes.domain;

import com.edulog.driverportal.base.UseCase;
import com.edulog.driverportal.session.Session;
import com.edulog.driverportal.routes.model.Route;
import com.edulog.driverportal.routes.data.RouteRepository;

import java.util.Collections;
import java.util.List;

import io.reactivex.Observable;

public class ShowRouteHistoryUseCase extends UseCase<List<Route>, Void> {
    private RouteRepository routeRepository;
    private Session session;

    public ShowRouteHistoryUseCase(RouteRepository routeRepository, Session session) {
        this.routeRepository = routeRepository;
        this.session = session;
    }

    @Override
    public Observable<List<Route>> buildUseCaseObservable(Void aVoid) {
        String driverId = session.getDriverId();
        if (driverId != null) {
            return Observable.just(routeRepository.findAll())
                    .doOnNext(Collections::reverse);
        }
        return Observable.error(new RuntimeException("Driver not logged in"));
    }
}