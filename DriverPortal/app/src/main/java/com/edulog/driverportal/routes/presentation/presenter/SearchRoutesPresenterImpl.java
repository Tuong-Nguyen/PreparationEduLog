package com.edulog.driverportal.routes.presentation.presenter;

import com.edulog.driverportal.common.presentation.CompositeDisposableObserver;
import com.edulog.driverportal.common.presentation.DefaultObserver;
import com.edulog.driverportal.routes.domain.interactor.SaveRouteUseCase;
import com.edulog.driverportal.routes.domain.interactor.SearchRoutesUseCase;
import com.edulog.driverportal.routes.model.RouteModel;
import com.edulog.driverportal.routes.presentation.view.SearchRoutesView;

import java.util.List;

import io.reactivex.observers.DisposableObserver;

public class SearchRoutesPresenterImpl implements SearchRoutesPresenter {
    private SearchRoutesView searchRoutesView;
    private SearchRoutesUseCase searchRoutesUseCase;
    private SaveRouteUseCase saveRouteUseCase;
    private CompositeDisposableObserver disposables;

    public SearchRoutesPresenterImpl(SearchRoutesUseCase searchRoutesUseCase, SaveRouteUseCase saveRouteUseCase) {
        this.searchRoutesUseCase = searchRoutesUseCase;
        this.saveRouteUseCase = saveRouteUseCase;

        disposables = new CompositeDisposableObserver();
    }

    @Override
    public void attach(SearchRoutesView searchRoutesView) {
        this.searchRoutesView = searchRoutesView;
    }

    @Override
    public void detach() {
        this.searchRoutesView = null;
        disposables.dispose();
    }

    @Override
    public void onError(String message) {

    }

    @Override
    public void searchRoutes(String query) {
        DisposableObserver<List<RouteModel>> searchRoutesObserver = createSearchRoutesObserver();
        disposables.add(searchRoutesObserver);

        searchRoutesUseCase.execute(searchRoutesObserver, query);

        searchRoutesView.showProgress();
    }

    @Override
    public void getPreviewRoute(RouteModel routeModel) {
        searchRoutesView.showRoutePreview(routeModel);
    }

    @Override
    public void saveRoute(RouteModel routeModel) {
        DisposableObserver<Boolean> saveRouteObserver = createSaveRouteObserver();
        disposables.add(saveRouteObserver);

        saveRouteUseCase.execute(saveRouteObserver, routeModel);

        searchRoutesView.showRouteDetails(routeModel);
    }

    private DisposableObserver<List<RouteModel>> createSearchRoutesObserver() {
        return new DefaultObserver<List<RouteModel>>() {
            @Override
            public void onNext(List<RouteModel> routeModels) {
                searchRoutesView.showSearchRouteResults(routeModels);
            }
        };
    }

    private DisposableObserver<Boolean> createSaveRouteObserver() {
        return new DefaultObserver<Boolean>() {
            @Override
            public void onNext(Boolean aBoolean) {

            }
        };
    }
}
