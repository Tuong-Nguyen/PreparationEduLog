package com.edulog.driverportal.routes.presentation;

import com.edulog.driverportal.common.presentation.DefaultObserver;
import com.edulog.driverportal.routes.model.Route;
import com.edulog.driverportal.routes.domain.SetActiveRouteUseCase;
import com.edulog.driverportal.routes.model.LoadMode;

import io.reactivex.observers.DisposableObserver;

public class RouteDetailsPresenterImpl extends RouteDetailsContract.RouteDetailsPresenter {
    private RouteDetailsContract.RouteDetailsView routeDetailsView;
    private SetActiveRouteUseCase setActiveRouteUseCase;
    private DisposableObserver<Route> activeRouteObserver;

    public RouteDetailsPresenterImpl(SetActiveRouteUseCase setActiveRouteUseCase) {
        this.setActiveRouteUseCase = setActiveRouteUseCase;
    }

    @Override
    public void attach(RouteDetailsContract.RouteDetailsView routeDetailsView) {
        this.routeDetailsView = routeDetailsView;
    }

    @Override
    public void detach() {
        super.detach();
        routeDetailsView = null;
    }

    @Override
    public void setActiveRoute(String routeId, LoadMode loadMode) {
        disposeObserver(activeRouteObserver);
        activeRouteObserver = createActiveRouteObserver();
        addDisposable(activeRouteObserver);

        SetActiveRouteUseCase.Params params = SetActiveRouteUseCase.buildParams(routeId, loadMode);
        routeDetailsView.showProgress();
        setActiveRouteUseCase.execute(activeRouteObserver, params);
    }

    private DisposableObserver<Route> createActiveRouteObserver() {
        return new DefaultObserver<Route>() {
            @Override
            public void onNext(Route route) {
                routeDetailsView.hideProgress();
                routeDetailsView.showRouteDetails(route);
            }

            @Override
            public void onError(Throwable e) {
                routeDetailsView.hideProgress();
                routeDetailsView.showError(e.getMessage());
            }
        };
    }
}
