package com.edulog.driverportal.routeselection.presentation.presenter;

import com.edulog.driverportal.common.presentation.DefaultObserver;
import com.edulog.driverportal.routeselection.domain.interactor.SetActiveRouteUseCase;
import com.edulog.driverportal.routeselection.model.LoadMode;
import com.edulog.driverportal.routeselection.model.RouteModel;

import io.reactivex.observers.DisposableObserver;

public class RouteDetailsPresenterImpl extends RouteDetailsContract.RouteDetailsPresenter {
    private RouteDetailsContract.RouteDetailsView routeDetailsView;
    private SetActiveRouteUseCase setActiveRouteUseCase;
    private DisposableObserver<RouteModel> activeRouteObserver;

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

    private DisposableObserver<RouteModel> createActiveRouteObserver() {
        return new DefaultObserver<RouteModel>() {
            @Override
            public void onNext(RouteModel routeModel) {
                routeDetailsView.hideProgress();
                routeDetailsView.showRouteDetails(routeModel);
            }

            @Override
            public void onError(Throwable e) {
                routeDetailsView.hideProgress();
                routeDetailsView.showError(e.getMessage());
            }
        };
    }
}
