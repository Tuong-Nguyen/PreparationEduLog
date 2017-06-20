package com.edulog.driverportal.routeselection.domain.interactor;

import com.edulog.driverportal.common.domain.UseCase;
import com.edulog.driverportal.routeselection.data.session.Session;
import com.edulog.driverportal.routeselection.domain.repository.RouteRepository;
import com.edulog.driverportal.routeselection.domain.service.RouteService;
import com.edulog.driverportal.routeselection.model.RouteModel;
import com.edulog.driverportal.routeselection.model.RouteModelDataMapper;

import io.reactivex.Observable;
import io.reactivex.Scheduler;
import io.reactivex.schedulers.Schedulers;

public class SetActiveRouteUseCase extends UseCase<RouteModel, String> {
    private RouteService routeService;
    private RouteRepository routeRepository;
    private Session session;

    public SetActiveRouteUseCase(Scheduler postExecutionScheduler,
                                 RouteService routeService,
                                 RouteRepository routeRepository,
                                 Session session) {
        super(postExecutionScheduler);

        this.routeService = routeService;
        this.routeRepository = routeRepository;
        this.session = session;
    }

    @Override
    protected Observable<RouteModel> buildUseCaseObservable(String routeId) {
        return routeService.getRoute(routeId)
                .map(routeEntity -> {
                    routeRepository.insert(routeEntity);
                    session.putRouteId(routeEntity.getId());
                    return RouteModelDataMapper.transform(routeEntity);
                })
                .subscribeOn(Schedulers.io());
    }
}