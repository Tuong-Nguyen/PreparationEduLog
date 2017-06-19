package com.edulog.driverportal.routes.presentation.presenter;

import com.edulog.driverportal.routes.domain.interactor.SearchRoutesUseCase;
import com.edulog.driverportal.routes.domain.interactor.RouteIdSuggestionsUseCase;
import com.edulog.driverportal.routes.presentation.view.RouteSelectionView;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class)
public class RouteSelectionPresenterTest {
    private RouteSelectionPresenter routeSelectionPresenter;

    @Mock
    private SearchRoutesUseCase mockSearchRoutesUseCase;

    @Mock
    private RouteIdSuggestionsUseCase mockRouteIdSuggestionsUseCase;

    @Mock
    private RouteSelectionView mockRouteSelectionView;

    @Before
    public void setUp() {
        routeSelectionPresenter = new RouteSelectionPresenterImpl(mockSearchRoutesUseCase, mockRouteIdSuggestionsUseCase);
        routeSelectionPresenter.attach(mockRouteSelectionView);
    }

    @Test
    public void suggestRoutesId_showProgress() {
        routeSelectionPresenter.suggestRouteIds("0");

        verify(mockRouteSelectionView).showProgress();
    }
}
