package com.edulog.driverportal.routeselection.domain.interactor;

import com.edulog.driverportal.common.domain.UseCase;
import com.edulog.driverportal.routeselection.model.RouteModel;

import io.reactivex.Observable;

public class ShowRouteHistoryUseCase extends UseCase<RouteModel, Void> {
    @Override
    protected Observable<RouteModel> buildUseCaseObservable(Void aVoid) {
        return Observable.just(new RouteModel());
    }
}
