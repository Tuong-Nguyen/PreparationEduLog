package com.edulog.driverportal.routeselection.presentation.presenter;

import com.edulog.driverportal.common.presentation.CompositeDisposableObserver;
import com.edulog.driverportal.common.presentation.DefaultObserver;
import com.edulog.driverportal.routeselection.domain.interactor.RouteIdSuggestionsUseCase;
import com.edulog.driverportal.routeselection.presentation.view.NewRouteView;

import java.util.List;

import io.reactivex.observers.DisposableObserver;

public class NewRoutePresenterImpl extends NewRoutePresenter {
    private NewRouteView newRouteView;
    private RouteIdSuggestionsUseCase routeIdSuggestionsUseCase;

    public NewRoutePresenterImpl(RouteIdSuggestionsUseCase routeIdSuggestionsUseCase) {
        this.routeIdSuggestionsUseCase = routeIdSuggestionsUseCase;
    }

    @Override
    public void attach(NewRouteView newRouteView) {
        this.newRouteView = newRouteView;
    }

    @Override
    public void detach() {
        super.detach();
        newRouteView = null;
    }

    @Override
    public void suggestRouteIds(String query) {
        DisposableObserver<List<String>> observer = createRouteIdSuggestionsObserver();
        addDisposable(observer);

        newRouteView.showProgress();

        routeIdSuggestionsUseCase.execute(observer, query);
    }

    private DisposableObserver<List<String>> createRouteIdSuggestionsObserver() {
        return new DefaultObserver<List<String>>() {
            @Override
            public void onNext(List<String> ids) {
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
