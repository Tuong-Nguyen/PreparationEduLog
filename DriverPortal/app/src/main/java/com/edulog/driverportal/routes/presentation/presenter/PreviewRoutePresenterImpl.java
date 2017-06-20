package com.edulog.driverportal.routes.presentation.presenter;

import com.edulog.driverportal.common.presentation.CompositeDisposableObserver;
import com.edulog.driverportal.common.presentation.DefaultObserver;
import com.edulog.driverportal.routes.domain.interactor.PreviewRouteUseCase;
import com.edulog.driverportal.routes.model.RouteModel;
import com.edulog.driverportal.routes.presentation.view.PreviewRouteView;

import io.reactivex.observers.DisposableObserver;

public class PreviewRoutePresenterImpl implements PreviewRoutePresenter {
    private PreviewRouteView previewRouteView;
    private PreviewRouteUseCase previewRouteUseCase;

    private CompositeDisposableObserver disposables;

    public PreviewRoutePresenterImpl(PreviewRouteUseCase previewRouteUseCase) {
        this.previewRouteUseCase = previewRouteUseCase;

        disposables = new CompositeDisposableObserver();
    }

    @Override
    public void attach(PreviewRouteView previewRouteView) {
        this.previewRouteView = previewRouteView;
    }

    @Override
    public void detach() {
        previewRouteView = null;
        disposables.dispose();
    }

    @Override
    public void onError(String message) {

    }

    @Override
    public void previewRoute(String routeId) {
        DisposableObserver<RouteModel> previewRouteObserver = createPreviewRouteObserver();
        disposables.add(previewRouteObserver);

        previewRouteUseCase.execute(previewRouteObserver, routeId);

        previewRouteView.showProgress();
    }

    private DisposableObserver<RouteModel> createPreviewRouteObserver() {
        return new DefaultObserver<RouteModel>() {
            @Override
            public void onNext(RouteModel routeModel) {
                previewRouteView.showPreviewRoute(routeModel);
                previewRouteView.hideProgress();
            }
        };
    }
}
