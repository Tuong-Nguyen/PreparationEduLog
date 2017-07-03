package com.edulog.driverportal.routes.presentation;

import com.edulog.driverportal.routes.domain.SearchRoutesUseCase;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import io.reactivex.observers.DisposableObserver;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class)
public class SearchRoutesPresenterTest {
    private SearchRoutesContract.Presenter searchRoutesPresenter;
    @Mock
    private SearchRoutesUseCase mockSearchRoutesUseCase;
    @Mock
    private SearchRoutesContract.View mockSearchRoutesView;

    @Before
    public void setUp() throws Exception {
        searchRoutesPresenter = new SearchRoutesPresenterImpl(mockSearchRoutesUseCase);
        searchRoutesPresenter.attach(mockSearchRoutesView);
    }

    @Test
    public void searchRoutes_useCaseExecuted() throws Exception {
        searchRoutesPresenter.searchRoutes("0");

        verify(mockSearchRoutesUseCase).execute(any(DisposableObserver.class), anyString());
    }

    @Test
    public void searchRoutes_viewShowProgress() throws Exception {
        searchRoutesPresenter.searchRoutes("0");

        verify(mockSearchRoutesView).showProgress();
    }
}