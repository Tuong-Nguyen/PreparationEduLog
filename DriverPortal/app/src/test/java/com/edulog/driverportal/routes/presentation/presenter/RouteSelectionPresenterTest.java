package com.edulog.driverportal.routes.presentation.presenter;

import com.edulog.driverportal.routes.domain.interactor.SearchRoutesUseCase;
import com.edulog.driverportal.routes.domain.interactor.RouteIdSuggestionsUseCase;
import com.edulog.driverportal.routes.presentation.view.RouteSelectionView;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import io.reactivex.observers.DisposableObserver;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class)
public class RouteSelectionPresenterTest {
    private RouteSelectionPresenter routeSelectionPresenter;

    @Mock
    private RouteIdSuggestionsUseCase mockRouteIdSuggestionsUseCase;

    @Mock
    private RouteSelectionView mockRouteSelectionView;

    @Before
    public void setUp() {
        routeSelectionPresenter = new RouteSelectionPresenterImpl(mockRouteIdSuggestionsUseCase);
        routeSelectionPresenter.attach(mockRouteSelectionView);
    }

    @Test
    public void suggestRouteIds_useCaseExecuted() {
        routeSelectionPresenter.suggestRouteIds("0");

        verify(mockRouteIdSuggestionsUseCase).execute(any(DisposableObserver.class), anyString());
    }

    @Test
    public void suggestRouteIds_viewShowProgress() {
        routeSelectionPresenter.suggestRouteIds("0");

        verify(mockRouteSelectionView).showProgress();
    }
}
