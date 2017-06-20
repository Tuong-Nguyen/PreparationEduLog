package com.edulog.driverportal.routeselection.presentation.presenter;

import com.edulog.driverportal.common.presentation.CompositeDisposableObserver;
import com.edulog.driverportal.common.presentation.DefaultObserver;
import com.edulog.driverportal.routeselection.domain.interactor.SetActiveRouteUseCase;
import com.edulog.driverportal.routeselection.model.RouteModel;
import com.edulog.driverportal.routeselection.presentation.view.RouteDetailsView;

import io.reactivex.observers.DisposableObserver;

public class RouteDetailsPresenterImpl implements RouteDetailsPresenter {
    private RouteDetailsView routeDetailsView;
    private SetActiveRouteUseCase setActiveRouteUseCase;

    private CompositeDisposableObserver disposables;

    public RouteDetailsPresenterImpl(SetActiveRouteUseCase setActiveRouteUseCase) {
        this.setActiveRouteUseCase = setActiveRouteUseCase;

        disposables = new CompositeDisposableObserver();
    }

    @Override
    public void attach(RouteDetailsView routeDetailsView) {
        this.routeDetailsView = routeDetailsView;
    }

    @Override
    public void detach() {
        routeDetailsView = null;
        disposables.dispose();
    }

    @Override
    public void onError(String message) {

    }

    @Override
    public void setActiveRoute(String routeId) {
        DisposableObserver<RouteModel> activeRouteObserver = createActiveRouteObserver();
        disposables.add(activeRouteObserver);

        setActiveRouteUseCase.execute(activeRouteObserver, routeId);

        routeDetailsView.showProgress();
    }

    private DisposableObserver<RouteModel> createActiveRouteObserver() {
        return new DefaultObserver<RouteModel>() {
            @Override
            public void onNext(RouteModel routeModel) {
                routeDetailsView.showRouteDetails(routeModel);
                routeDetailsView.hideProgress();
            }
        };
    }
}
