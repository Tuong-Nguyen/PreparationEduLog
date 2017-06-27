package com.edulog.driverportal.routes.presentation.presenter;

import com.edulog.driverportal.common.presentation.DefaultObserver;
import com.edulog.driverportal.routes.domain.interactor.ShowRouteHistoryUseCase;
import com.edulog.driverportal.routes.model.RouteModel;

import java.util.List;

import io.reactivex.observers.DisposableObserver;

public class RouteHistoryPresenterImpl extends RouteHistoryContract.RouteHistoryPresenter {
    private RouteHistoryContract.RouteHistoryView routeHistoryView;
    private ShowRouteHistoryUseCase showRouteHistoryUseCase;
    private DisposableObserver<List<RouteModel>> routeHistoryObserver;

    public RouteHistoryPresenterImpl(ShowRouteHistoryUseCase showRouteHistoryUseCase) {
        this.showRouteHistoryUseCase = showRouteHistoryUseCase;
    }

    @Override
    public void attach(RouteHistoryContract.RouteHistoryView routeHistoryView) {
        this.routeHistoryView = routeHistoryView;
    }

    @Override
    public void detach() {
        super.detach();
    }

    @Override
    public void getRouteHistory() {
        disposeObserver(routeHistoryObserver);
        routeHistoryObserver = createRouteHistoryObserver();
        addDisposable(routeHistoryObserver);

        routeHistoryView.showProgress();
        showRouteHistoryUseCase.execute(routeHistoryObserver, null);
    }

    private DisposableObserver<List<RouteModel>> createRouteHistoryObserver() {
        return new DefaultObserver<List<RouteModel>>() {
            @Override
            public void onNext(List<RouteModel> routeModels) {
                routeHistoryView.hideProgress();
                routeHistoryView.showRouteHistory(routeModels);
            }

            @Override
            public void onError(Throwable e) {
                routeHistoryView.hideProgress();
                routeHistoryView.showError(e.getMessage());
            }
        };
    }
}
