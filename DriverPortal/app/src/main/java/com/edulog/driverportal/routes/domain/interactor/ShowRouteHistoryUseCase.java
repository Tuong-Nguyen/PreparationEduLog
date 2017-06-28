package com.edulog.driverportal.routes.domain.interactor;

import com.edulog.driverportal.common.domain.UseCase;
import com.edulog.driverportal.common.device.Session;
import com.edulog.driverportal.routes.domain.repository.RouteRepository;
import com.edulog.driverportal.routes.model.RouteModel;
import com.edulog.driverportal.routes.model.RouteModelDataMapper;

import java.util.Collections;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.schedulers.Schedulers;

public class ShowRouteHistoryUseCase extends UseCase<List<RouteModel>, Void> {
    private RouteRepository routeRepository;
    private Session session;

    public ShowRouteHistoryUseCase(RouteRepository routeRepository, Session session) {
        this.routeRepository = routeRepository;
        this.session = session;
    }

    @Override
    public Observable<List<RouteModel>> buildUseCaseObservable(Void aVoid) {
        String driverId = session.getDriverId();
        if (driverId != null) {
            return Observable.just(routeRepository.findAll())
                    .doOnNext(Collections::reverse)
                    .map(RouteModelDataMapper::transform);
        }
        return Observable.error(new RuntimeException("Driver not logged in"));
    }
}