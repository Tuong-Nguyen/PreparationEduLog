package com.edulog.driverportal.routeselection.presentation.presenter;

import com.edulog.driverportal.common.presentation.CompositeDisposableObserver;
import com.edulog.driverportal.common.presentation.DefaultObserver;
import com.edulog.driverportal.routeselection.domain.interactor.RouteIdSuggestionsUseCase;
import com.edulog.driverportal.routeselection.presentation.view.NewRouteView;

import java.util.List;

import io.reactivex.observers.DisposableObserver;

public class NewRoutePresenterImpl implements NewRoutePresenter {
    private NewRouteView newRouteView;
    private RouteIdSuggestionsUseCase routeIdSuggestionsUseCase;
    private CompositeDisposableObserver disposables;

    public NewRoutePresenterImpl(RouteIdSuggestionsUseCase routeIdSuggestionsUseCase) {
        this.routeIdSuggestionsUseCase = routeIdSuggestionsUseCase;
        disposables = new CompositeDisposableObserver();
    }

    @Override
    public void attach(NewRouteView newRouteView) {
        this.newRouteView = newRouteView;
    }

    @Override
    public void detach() {
        newRouteView = null;
        disposables.dispose();
    }

    @Override
    public void onError(String message) {

    }

    @Override
    public void suggestRouteIds(String query) {
        DisposableObserver<List<String>> observer = createRouteIdSuggestionsObserver();
        disposables.add(observer);

        routeIdSuggestionsUseCase.execute(observer, query);

        newRouteView.showProgress();
    }

    private DisposableObserver<List<String>> createRouteIdSuggestionsObserver() {
        return new DefaultObserver<List<String>>() {
            @Override
            public void onNext(List<String> ids) {
                newRouteView.showRouteIdSuggestions(ids);
            }
        };
    }
}
