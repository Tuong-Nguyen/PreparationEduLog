package com.edulog.driverportal.routes.domain.interactor;

import com.edulog.driverportal.common.domain.UseCase;
import com.edulog.driverportal.routes.model.RouteModel;

import io.reactivex.Observable;
import io.reactivex.Scheduler;

public class SaveRouteUseCase extends UseCase<Boolean, RouteModel> {

    public SaveRouteUseCase(Scheduler postExecutionScheduler) {
        super(postExecutionScheduler);
    }

    @Override
    protected Observable<Boolean> buildUseCaseObservable(RouteModel routeModel) {
        return null;
    }
}
