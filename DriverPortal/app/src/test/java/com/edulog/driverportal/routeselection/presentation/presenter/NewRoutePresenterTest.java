package com.edulog.driverportal.routeselection.presentation.presenter;

import com.edulog.driverportal.routeselection.domain.interactor.RouteIdSuggestionsUseCase;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentMatcher;
import org.mockito.ArgumentMatchers;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import io.reactivex.observers.DisposableObserver;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyChar;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class)
public class NewRoutePresenterTest {
    private NewRouteContract.NewRoutePresenter newRoutePresenter;

    @Mock
    private RouteIdSuggestionsUseCase mockRouteIdSuggestionsUseCase;

    @Mock
    private NewRouteContract.NewRouteView mockNewRouteView;

    @Before
    public void setUp() throws Exception {
        newRoutePresenter = new NewRoutePresenterImpl(mockRouteIdSuggestionsUseCase);
        newRoutePresenter.attach(mockNewRouteView);
    }

    @Test
    public void suggestRouteIds_useCaseExecuted() throws Exception {
        newRoutePresenter.suggestRouteIds("0");

        verify(mockRouteIdSuggestionsUseCase).execute(any(DisposableObserver.class), anyString());
    }

    @Test
    public void suggestRouteIds_viewShowProgress() throws Exception {
        newRoutePresenter.suggestRouteIds("0");

        verify(mockNewRouteView).showProgress();
    }
}
