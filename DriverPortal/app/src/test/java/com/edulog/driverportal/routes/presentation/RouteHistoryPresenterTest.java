package com.edulog.driverportal.routes.presentation;

import com.edulog.driverportal.routes.domain.ShowRouteHistoryUseCase;
import com.edulog.driverportal.routes.presentation.RouteHistoryContract;
import com.edulog.driverportal.routes.presentation.RouteHistoryPresenterImpl;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import io.reactivex.Observer;

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