package com.edulog.driverportal.routes.domain.interactor;

import com.edulog.driverportal.common.domain.UseCase;
import com.edulog.driverportal.routes.data.entity.RouteEntity;
import com.edulog.driverportal.routes.data.session.Session;
import com.edulog.driverportal.routes.domain.repository.RouteRepository;
import com.edulog.driverportal.routes.model.RouteModel;

import io.reactivex.Observable;
import io.reactivex.Scheduler;

public class SaveRouteUseCase extends UseCase<Boolean, RouteModel> {
    private RouteRepository routeRepository;
    private Session session;

    public SaveRouteUseCase(Scheduler postExecutionScheduler, RouteRepository routeRepository, Session session) {
        super(postExecutionScheduler);

        this.routeRepository = routeRepository;
        this.session = session;
    }

    @Override
    protected Observable<Boolean> buildUseCaseObservable(RouteModel routeModel) {
        session.putRouteId(routeModel.getId());

        RouteEntity routeEntity = new RouteEntity();
        routeEntity.setId(routeModel.getId());
        routeEntity.setName(routeModel.getName());
        routeEntity.setStopCount(routeModel.getStopCount());
        routeRepository.insert(routeEntity);

        return Observable.just(true);
    }
}
