package com.edulog.driverportal.routeselection.presentation.presenter;

import com.edulog.driverportal.routeselection.domain.interactor.SetActiveRouteUseCase;
import com.edulog.driverportal.routeselection.domain.interactor.ShowRouteHistoryUseCase;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import io.reactivex.Observable;
import io.reactivex.Observer;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class)
public class RouteHistoryPresenterTest {
    private RouteHistoryContract.RouteHistoryPresenter routeHistoryPresenter;
    @Mock
    private ShowRouteHistoryUseCase mockShowRouteHistoryUseCase;
    @Mock
    private RouteHistoryContract.RouteHistoryView mockRouteHistoryView;

    @Before
    public void setUp() throws Exception {
        routeHistoryPresenter = new RouteHistoryPresenterImpl(mockShowRouteHistoryUseCase);
        routeHistoryPresenter.attach(mockRouteHistoryView);
    }

    @Test
    public void getRouteHistory_execute() throws Exception {
        routeHistoryPresenter.getRouteHistory();

        verify(mockShowRouteHistoryUseCase).execute(any(Observer.class), any());
    }
}