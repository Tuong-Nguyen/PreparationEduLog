package com.edulog.driverportal.routeselection.domain.interactor;

import com.edulog.driverportal.common.domain.UseCase;
import com.edulog.driverportal.common.preference.Session;
import com.edulog.driverportal.routeselection.domain.repository.RouteRepository;
import com.edulog.driverportal.routeselection.domain.service.RouteService;
import com.edulog.driverportal.routeselection.model.LoadMode;
import com.edulog.driverportal.routeselection.model.RouteModel;
import com.edulog.driverportal.routeselection.model.RouteModelDataMapper;

import java.io.IOException;
import java.sql.ParameterMetaData;

import io.reactivex.Observable;
import io.reactivex.schedulers.Schedulers;

public class SetActiveRouteUseCase extends UseCase<RouteModel, SetActiveRouteUseCase.Params> {
    private RouteService routeService;
    private RouteRepository routeRepository;
    private Session session;

    public SetActiveRouteUseCase(RouteService routeService, RouteRepository routeRepository, Session session) {
        this.routeService = routeService;
        this.routeRepository = routeRepository;
        this.session = session;
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
                                throw new RuntimeException("Use not logged in!");
                            }
                        })
                        .map(routeEntity -> {
                            routeEntity.setDriverId(session.getDriverId());
                            routeRepository.upsert(routeEntity);
                            session.putRouteId(routeEntity.getId());
                            return RouteModelDataMapper.transform(routeEntity);
                        })
                        .subscribeOn(Schedulers.io());
                break;
            case LOCAL:
                observable = Observable.just(routeRepository.findOne(routeId))
                        .doOnNext(routeEntity -> {
                            if (session.getDriverId() == null) {
                                throw new RuntimeException("Use not logged in!");
                            }
                        })
                        .map(routeEntity -> {
                            session.putRouteId(routeEntity.getId());
                            return RouteModelDataMapper.transform(routeEntity);
                        })
                        .subscribeOn(Schedulers.io());
                break;
            default:
                observable = Observable.error(new IllegalArgumentException("Load mode not specified."));
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
