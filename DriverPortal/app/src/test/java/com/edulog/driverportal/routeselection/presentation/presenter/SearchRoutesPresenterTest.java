package com.edulog.driverportal.routeselection.presentation.presenter;

import com.edulog.driverportal.routeselection.domain.interactor.SearchRoutesUseCase;
import com.edulog.driverportal.routeselection.domain.interactor.RouteIdSuggestionsUseCase;
import com.edulog.driverportal.routeselection.presentation.view.SearchRoutesView;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class)
public class SearchRoutesPresenterTest {
    private SearchRoutesPresenter searchRoutesPresenter;

    @Mock
    private SearchRoutesUseCase mockSearchRoutesUseCase;

    @Mock
    private RouteIdSuggestionsUseCase mockRouteIdSuggestionsUseCase;

    @Mock
    private SearchRoutesView mockSearchRoutesView;

    @Before
    public void setUp() {
        searchRoutesPresenter = new SearchRoutesPresenterImpl(mockSearchRoutesUseCase, mockRouteIdSuggestionsUseCase);
        searchRoutesPresenter.attach(mockSearchRoutesView);
    }

    @Test
    public void suggestRoutesId_showProgress() {
        searchRoutesPresenter.suggestRouteIds("0");

        verify(mockSearchRoutesView).showProgress();
    }
}
