package com.edulog.driverportal.routeselection.presentation.presenter;

import com.edulog.driverportal.common.presentation.CompositeDisposableObserver;
import com.edulog.driverportal.common.presentation.DefaultObserver;
import com.edulog.driverportal.routeselection.domain.interactor.SetActiveRouteUseCase;
import com.edulog.driverportal.routeselection.presentation.model.RouteModel;
import com.edulog.driverportal.routeselection.presentation.view.RouteDetailsView;

import io.reactivex.observers.DisposableObserver;

public class RouteDetailsPresenterImpl extends RouteDetailsPresenter {
    private RouteDetailsView routeDetailsView;
    private SetActiveRouteUseCase setActiveRouteUseCase;

    public RouteDetailsPresenterImpl(SetActiveRouteUseCase setActiveRouteUseCase) {
        this.setActiveRouteUseCase = setActiveRouteUseCase;
    }

    @Override
    public void attach(RouteDetailsView routeDetailsView) {
        this.routeDetailsView = routeDetailsView;
    }

    @Override
    public void detach() {
        super.detach();
        routeDetailsView = null;
    }

    @Override
    public void setActiveRoute(String routeId) {
        DisposableObserver<RouteModel> activeRouteObserver = createActiveRouteObserver();
        addDisposable(activeRouteObserver);

        routeDetailsView.showProgress();

        setActiveRouteUseCase.execute(activeRouteObserver, routeId);
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
