package com.edulog.driverportal.routeselection.domain.interactor;

import com.edulog.driverportal.common.domain.UseCase;
import com.edulog.driverportal.common.preference.Session;
import com.edulog.driverportal.routeselection.domain.repository.RouteRepository;
import com.edulog.driverportal.routeselection.model.RouteModel;
import com.edulog.driverportal.routeselection.model.RouteModelDataMapper;

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
                    .subscribeOn(Schedulers.io())
                    .doOnNext(Collections::reverse)
                    .map(RouteModelDataMapper::transform);
        }
        return Observable.error(new RuntimeException("Driver not logged in"));
    }
}