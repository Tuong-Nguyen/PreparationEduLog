package com.edulog.driverportal.routeselection.domain.interactor;

import com.edulog.driverportal.common.domain.UseCase;
import com.edulog.driverportal.routeselection.domain.repository.RouteRepository;
import com.edulog.driverportal.routeselection.domain.service.RouteService;
import com.edulog.driverportal.routeselection.model.LoadMode;
import com.edulog.driverportal.routeselection.model.RouteModel;
import com.edulog.driverportal.routeselection.model.RouteModelDataMapper;

import io.reactivex.Observable;
import io.reactivex.schedulers.Schedulers;

public class GetRouteUseCase extends UseCase<RouteModel, GetRouteUseCase.Params> {
    private RouteService routeService;
    private RouteRepository routeRepository;

    public GetRouteUseCase(RouteService routeService, RouteRepository routeRepository) {
        this.routeService = routeService;
        this.routeRepository = routeRepository;
    }

    @Override
    public Observable<RouteModel> buildUseCaseObservable(Params params) {
        String routeId = params.routeId;
        LoadMode loadMode = params.loadMode;

        Observable<RouteModel> observable;
        switch (loadMode) {
            case REMOTE:
                observable = routeService.getRoute(routeId)
                        .map(RouteModelDataMapper::transform);
                break;
            case LOCAL:
                observable = Observable.just(routeRepository.findOne(routeId))
                        .subscribeOn(Schedulers.io())
                        .map(RouteModelDataMapper::transform);
                break;
            default:
                observable = Observable.error(new RuntimeException("Load Mode not specified (LOCAL or REMOTE?)"));
                break;
        }

        return observable;
    }

    public static Params buildParams(String routeId, LoadMode loadMode) {
        Params params = new Params();

        params.routeId = routeId;
        params.loadMode = loadMode;

        return params;
    }

    public static class Params {
        public String routeId;
        public LoadMode loadMode;
    }
}
