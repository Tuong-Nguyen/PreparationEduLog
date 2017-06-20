package com.edulog.driverportal.routes.presentation.presenter;

import com.edulog.driverportal.routes.domain.interactor.SaveRouteUseCase;
import com.edulog.driverportal.routes.domain.interactor.SearchRoutesUseCase;
import com.edulog.driverportal.routes.presentation.view.SearchRoutesView;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import io.reactivex.observers.DisposableObserver;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class)
public class SearchRoutesPresenterTest {
    private SearchRoutesPresenter searchRoutesPresenter;
    @Mock private SearchRoutesUseCase mockSearchRoutesUseCase;
    @Mock private SaveRouteUseCase mockSaveRouteUseCase;
    @Mock private SearchRoutesView mockSearchRoutesView;

    @Before
    public void setUp() {
        searchRoutesPresenter = new SearchRoutesPresenterImpl(mockSearchRoutesUseCase, mockSaveRouteUseCase);
        searchRoutesPresenter.attach(mockSearchRoutesView);
    }

    @Test
    public void searchRoutes_useCaseExecuted() {
        searchRoutesPresenter.searchRoutes("");

        verify(mockSearchRoutesUseCase).execute(any(DisposableObserver.class), anyString());
    }

    @Test
    public void searchRoutes_viewShowProgress() {
        searchRoutesPresenter.searchRoutes("");

        verify(mockSearchRoutesView).showProgress();
    }
}