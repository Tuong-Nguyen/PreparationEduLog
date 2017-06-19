package com.edulog.driverportal.routes.presentation.presenter;

import com.edulog.driverportal.routes.domain.interactor.SaveRouteUseCase;
import com.edulog.driverportal.routes.domain.interactor.SearchRoutesUseCase;
import com.edulog.driverportal.routes.model.RouteModel;
import com.edulog.driverportal.routes.presentation.device.Session;
import com.edulog.driverportal.routes.presentation.view.SearchRoutesView;

import java.util.List;

import io.reactivex.observers.DisposableObserver;

public class SearchRoutesPresenterImpl implements SearchRoutesPresenter {
    private SearchRoutesView searchRoutesView;
    private SearchRoutesUseCase searchRoutesUseCase;
    private SaveRouteUseCase saveRouteUseCase;
    private Session session;

    public SearchRoutesPresenterImpl(SearchRoutesUseCase searchRoutesUseCase, SaveRouteUseCase saveRouteUseCase, Session session) {
        this.searchRoutesUseCase = searchRoutesUseCase;
        this.saveRouteUseCase = saveRouteUseCase;
        this.session = session;
    }

    @Override
    public void attach(SearchRoutesView searchRoutesView) {
        this.searchRoutesView = searchRoutesView;
    }

    @Override
    public void detach() {
        this.searchRoutesView = null;
    }

    @Override
    public void onError(String message) {

    }

    @Override
    public void searchRoutes(String query) {
        searchRoutesUseCase.execute(createSearchRoutesObserver(), query);
    }

    @Override
    public void getPreviewRoute(RouteModel routeModel) {
        searchRoutesView.showRoutePreview(routeModel);
    }

    private DisposableObserver<List<RouteModel>> createSearchRoutesObserver() {
        return new DisposableObserver<List<RouteModel>>() {
            @Override
            public void onNext(List<RouteModel> routeModels) {
                searchRoutesView.showSearchRouteResults(routeModels);
            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onComplete() {

            }
        };
    }

    @Override
    public void saveRoute(RouteModel routeModel) {
        session.putRouteId(routeModel.getId());
    }
}
