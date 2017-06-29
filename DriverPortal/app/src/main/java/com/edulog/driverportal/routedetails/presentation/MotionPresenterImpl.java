package com.edulog.driverportal.routedetails.presentation;

import com.edulog.driverportal.common.device.Session;
import com.edulog.driverportal.common.presentation.DefaultObserver;
import com.edulog.driverportal.routedetails.domain.GetRouteUseCase;
import com.google.android.gms.maps.model.LatLng;

import java.util.List;

import io.reactivex.observers.DisposableObserver;

public class MotionPresenterImpl extends MotionContract.MotionPresenter {
    private MotionContract.MotionView motionView;
    private GetRouteUseCase getRouteUseCase;
    private Session session;
    private DisposableObserver<List<LatLng>> routeDirectionObserver;

    public MotionPresenterImpl(GetRouteUseCase getRouteUseCase, Session session) {
        this.getRouteUseCase = getRouteUseCase;
        this.session = session;
    }

    @Override
    public void attach(MotionContract.MotionView motionView) {
        this.motionView = motionView;
    }

    @Override
    public void detach() {
        super.detach();
    }

    @Override
    public void getActiveRoute() {
        disposeObserver(routeDirectionObserver);
        String routeId = session.getRouteId();
        routeDirectionObserver = getRouteDirectionObserver();
        addDisposable(routeDirectionObserver);

        motionView.showProgress();

        getRouteUseCase.execute(routeDirectionObserver, routeId);
    }

    private DisposableObserver<List<LatLng>> getRouteDirectionObserver() {
        return new DefaultObserver<List<LatLng>>() {
            @Override
            public void onNext(List<LatLng> latLngs) {
                motionView.hideProgress();
                motionView.drawRoute(latLngs);
            }

            @Override
            public void onError(Throwable e) {
                motionView.hideProgress();
                motionView.showError(e.getMessage());
            }
        };
    }
}
