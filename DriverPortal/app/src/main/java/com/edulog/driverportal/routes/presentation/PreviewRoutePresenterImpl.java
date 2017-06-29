package com.edulog.driverportal.routes.presentation;

import com.edulog.driverportal.common.presentation.DefaultObserver;
import com.edulog.driverportal.routes.model.Route;
import com.edulog.driverportal.routes.domain.GetRouteUseCase;
import com.edulog.driverportal.routes.model.LoadMode;

import io.reactivex.observers.DisposableObserver;

public class PreviewRoutePresenterImpl extends PreviewRouteContract.PreviewRoutePresenter {
    private PreviewRouteContract.PreviewRouteView previewRouteView;
    private GetRouteUseCase getRouteUseCase;
    private DisposableObserver<Route> previewRouteObserver;

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

    private DisposableObserver<Route> createPreviewRouteObserver() {
        return new DefaultObserver<Route>() {
            @Override
            public void onNext(Route route) {
                previewRouteView.hideProgress();
                previewRouteView.showPreviewRoute(route);
            }

            @Override
            public void onError(Throwable e) {
                previewRouteView.hideProgress();
                previewRouteView.showError(e.getMessage());
            }
        };
    }
}
