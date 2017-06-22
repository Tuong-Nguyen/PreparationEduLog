package com.edulog.driverportal.routeselection.presentation.presenter;

import com.edulog.driverportal.common.presentation.DefaultObserver;
import com.edulog.driverportal.routeselection.domain.interactor.GetRouteUseCase;
import com.edulog.driverportal.routeselection.model.LoadMode;
import com.edulog.driverportal.routeselection.model.RouteModel;

import io.reactivex.observers.DisposableObserver;

public class PreviewRoutePresenterImpl extends PreviewRouteContract.PreviewRoutePresenter {
    private PreviewRouteContract.PreviewRouteView previewRouteView;
    private GetRouteUseCase getRouteUseCase;
    private DisposableObserver<RouteModel> previewRouteObserver;

    public PreviewRoutePresenterImpl(GetRouteUseCase getRouteUseCase) {
        this.getRouteUseCase = getRouteUseCase;
    }

    @Override
    public void attach(PreviewRouteContract.PreviewRouteView previewRouteView) {
        this.previewRouteView = previewRouteView;
    }

    @Override
    public void detach() {
        super.detach();
        previewRouteView = null;
    }

    @Override
    public void previewRoute(String routeId, LoadMode loadMode) {
        disposeObserver(previewRouteObserver);
        previewRouteObserver = createPreviewRouteObserver();
        addDisposable(previewRouteObserver);

        previewRouteView.showProgress();

        GetRouteUseCase.Params params = GetRouteUseCase.buildParams(routeId, loadMode);
        getRouteUseCase.execute(previewRouteObserver, params);
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
