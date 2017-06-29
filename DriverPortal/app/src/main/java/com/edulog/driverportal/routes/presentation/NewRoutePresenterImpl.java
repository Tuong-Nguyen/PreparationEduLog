package com.edulog.driverportal.routes.presentation;

import com.edulog.driverportal.common.presentation.DefaultObserver;
import com.edulog.driverportal.routes.domain.RouteIdSuggestionsUseCase;

import java.util.List;

import io.reactivex.observers.DisposableObserver;

public class NewRoutePresenterImpl extends NewRouteContract.NewRoutePresenter {
    private NewRouteContract.NewRouteView newRouteView;
    private RouteIdSuggestionsUseCase routeIdSuggestionsUseCase;
    private DisposableObserver<List<String>> routeIdSuggestionsObserver;

    public NewRoutePresenterImpl(RouteIdSuggestionsUseCase routeIdSuggestionsUseCase) {
        this.routeIdSuggestionsUseCase = routeIdSuggestionsUseCase;
    }

    @Override
    public void attach(NewRouteContract.NewRouteView newRouteView) {
        this.newRouteView = newRouteView;
    }

    @Override
    public void detach() {
        super.detach();
        newRouteView = null;
    }

    @Override
    public void suggestRouteIds(String query) {
        disposeObserver(routeIdSuggestionsObserver);
        routeIdSuggestionsObserver = createRouteIdSuggestionsObserver();
        addDisposable(routeIdSuggestionsObserver);

        newRouteView.showProgress();

        routeIdSuggestionsUseCase.execute(routeIdSuggestionsObserver, query);
    }

    private DisposableObserver<List<String>> createRouteIdSuggestionsObserver() {
        return new DefaultObserver<List<String>>() {
            @Override
            public void onNext(List<String> ids) {
                newRouteView.hideProgress();
                newRouteView.showRouteIdSuggestions(ids);
            }

            @Override
            public void onError(Throwable e) {
                newRouteView.hideProgress();
                newRouteView.showError(e.getMessage());
            }
        };
    }
}
