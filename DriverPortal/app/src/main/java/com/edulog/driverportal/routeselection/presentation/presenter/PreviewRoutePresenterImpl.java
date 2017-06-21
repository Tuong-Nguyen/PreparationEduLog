package com.edulog.driverportal.routeselection.presentation.presenter;

import com.edulog.driverportal.common.presentation.CompositeDisposableObserver;
import com.edulog.driverportal.common.presentation.DefaultObserver;
import com.edulog.driverportal.routeselection.domain.interactor.PreviewRouteUseCase;
import com.edulog.driverportal.routeselection.presentation.model.RouteModel;
import com.edulog.driverportal.routeselection.presentation.view.PreviewRouteView;

import io.reactivex.observers.DisposableObserver;

public class PreviewRoutePresenterImpl extends PreviewRoutePresenter {
    private PreviewRouteView previewRouteView;
    private PreviewRouteUseCase previewRouteUseCase;

    public PreviewRoutePresenterImpl(PreviewRouteUseCase previewRouteUseCase) {
        this.previewRouteUseCase = previewRouteUseCase;
    }

    @Override
    public void attach(PreviewRouteView previewRouteView) {
        this.previewRouteView = previewRouteView;
    }

    @Override
    public void detach() {
        super.detach();
        previewRouteView = null;
    }

    @Override
    public void previewRoute(String routeId) {
        DisposableObserver<RouteModel> previewRouteObserver = createPreviewRouteObserver();
        addDisposable(previewRouteObserver);

        previewRouteView.showProgress();

        previewRouteUseCase.execute(previewRouteObserver, routeId);
    }

    private DisposableObserver<RouteModel> createPreviewRouteObserver() {
        return new DefaultObserver<RouteModel>() {
            @Override
            public void onNext(RouteModel routeModel) {
                previewRouteView.hideProgress();
                previewRouteView.showPreviewRoute(routeModel);
            }

            @Override
            public void onError(Throwable e) {
                previewRouteView.hideProgress();
                previewRouteView.showError(e.getMessage());
            }
        };
    }
}
