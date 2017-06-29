package com.edulog.driverportal.routes.domain;

import com.edulog.driverportal.common.domain.UseCase;
import com.edulog.driverportal.routes.model.Route;
import com.edulog.driverportal.routes.data.RouteRepository;
import com.edulog.driverportal.routes.model.LoadMode;

import io.reactivex.Observable;
import io.reactivex.schedulers.Schedulers;

public class GetRouteUseCase extends UseCase<Route, GetRouteUseCase.Params> {
    private RouteService routeService;
    private RouteRepository routeRepository;

    public GetRouteUseCase(RouteService routeService, RouteRepository routeRepository) {
        this.routeService = routeService;
        this.routeRepository = routeRepository;
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
                observable = routeService.getRoute(routeId);
                break;
            case LOCAL:
                observable = Observable.just(routeRepository.findOne(routeId))
                        .subscribeOn(Schedulers.io());
                break;
            default:
                observable = Observable.error(new RuntimeException("Load Mode not specified (LOCAL or REMOTE?)"));
                break;
        }

        return observable;
    }

    public static class Params {
        public String routeId;
        public LoadMode loadMode;
    }
}
