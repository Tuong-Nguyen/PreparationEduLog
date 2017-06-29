package com.edulog.driverportal.routes.domain;

import com.edulog.driverportal.common.domain.UseCase;
import com.edulog.driverportal.common.device.Session;
import com.edulog.driverportal.routes.model.Route;
import com.edulog.driverportal.routes.data.RouteRepository;
import com.edulog.driverportal.routes.model.LoadMode;

import io.reactivex.Observable;

public class SetActiveRouteUseCase extends UseCase<Route, SetActiveRouteUseCase.Params> {
    private RouteService routeService;
    private RouteRepository routeRepository;
    private Session session;

    public SetActiveRouteUseCase(RouteService routeService, RouteRepository routeRepository, Session session) {
        this.routeService = routeService;
        this.routeRepository = routeRepository;
        this.session = session;
    }

    public static Params buildParams(String routeId, LoadMode loadMode) {
        Params params = new Params();

        params.routeId = routeId;
        params.loadMode = loadMode;

        return params;
    }

    @Override
    public Observable<Route> buildUseCaseObservable(Params params) {
        String routeId = params.routeId;
        LoadMode loadMode = params.loadMode;

        Observable<Route> observable;
        switch (loadMode) {
            case REMOTE:
                observable = routeService.getRoute(routeId)
                        .doOnNext(route -> {
                            if (session.getDriverId() == null) {
                                throw new RuntimeException("Driver not logged in!");
                            }
                        })
                        .doOnNext(route -> {
                            routeRepository.upsert(route);
                            session.putRouteId(route.getId());
                        });
                break;
            case LOCAL:
                observable = Observable.just(routeRepository.findOne(routeId))
                        .doOnNext(routeEntity -> {
                            if (session.getDriverId() == null) {
                                throw new RuntimeException("Driver not logged in!");
                            }
                        })
                        .doOnNext(routeEntity -> {
                            session.putRouteId(routeEntity.getId());
                        });
                break;
            default:
                observable = Observable.error(new IllegalArgumentException("Load mode not specified."));
        }

        return observable;
    }

    public static class Params {
        public String routeId;
        public LoadMode loadMode;
    }
}
