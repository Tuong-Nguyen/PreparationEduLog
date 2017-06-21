package com.edulog.driverportal.routeselection.domain.interactor;

import com.edulog.driverportal.common.domain.UseCase;
import com.edulog.driverportal.common.preference.Session;
import com.edulog.driverportal.routeselection.domain.repository.RouteRepository;
import com.edulog.driverportal.routeselection.domain.service.RouteService;
import com.edulog.driverportal.routeselection.presentation.model.RouteModel;
import com.edulog.driverportal.routeselection.presentation.model.RouteModelDataMapper;

import io.reactivex.Observable;
import io.reactivex.schedulers.Schedulers;

public class SetActiveRouteUseCase extends UseCase<RouteModel, String> {
    private RouteService routeService;
    private RouteRepository routeRepository;
    private Session session;

    public SetActiveRouteUseCase(RouteService routeService, RouteRepository routeRepository, Session session) {
        this.routeService = routeService;
        this.routeRepository = routeRepository;
        this.session = session;
    }

    // TODO: Transform should be moved to Presenter class
    @Override
    protected Observable<RouteModel> buildUseCaseObservable(String routeId) {
        return routeService.getRoute(routeId)
                .map(routeEntity -> {
                    // TODO: Why do we need to insert route?
                    routeRepository.insert(routeEntity);
                    session.putRouteId(routeEntity.getId());
                    return RouteModelDataMapper.transform(routeEntity);
                })
                .subscribeOn(Schedulers.io());
    }
}
