package com.edulog.driverportal.routes.domain.interactor;

import com.edulog.driverportal.common.domain.UseCase;
import com.edulog.driverportal.common.device.Session;
import com.edulog.driverportal.routes.domain.repository.RouteRepository;
import com.edulog.driverportal.routes.domain.service.RouteService;
import com.edulog.driverportal.routes.model.LoadMode;
import com.edulog.driverportal.routes.model.RouteModel;
import com.edulog.driverportal.routes.model.RouteModelDataMapper;

import io.reactivex.Observable;

public class SetActiveRouteUseCase extends UseCase<RouteModel, SetActiveRouteUseCase.Params> {
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
    public Observable<RouteModel> buildUseCaseObservable(Params params) {
        String routeId = params.routeId;
        LoadMode loadMode = params.loadMode;

        Observable<RouteModel> observable;
        switch (loadMode) {
            case REMOTE:
                observable = routeService.getRoute(routeId)
                        .doOnNext(routeEntity -> {
                            if (session.getDriverId() == null) {
                                throw new RuntimeException("Driver not logged in!");
                            }
                        })
                        .map(routeEntity -> {
                            routeRepository.upsert(routeEntity);
                            session.putRouteId(routeEntity.getId());
                            return RouteModelDataMapper.transform(routeEntity);
                        });
                break;
            case LOCAL:
                observable = Observable.just(routeRepository.findOne(routeId))
                        .doOnNext(routeEntity -> {
                            if (session.getDriverId() == null) {
                                throw new RuntimeException("Driver not logged in!");
                            }
                        })
                        .map(routeEntity -> {
                            session.putRouteId(routeEntity.getId());
                            return RouteModelDataMapper.transform(routeEntity);
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
