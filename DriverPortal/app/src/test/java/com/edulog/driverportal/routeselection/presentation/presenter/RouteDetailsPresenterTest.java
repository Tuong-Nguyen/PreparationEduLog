package com.edulog.driverportal.routeselection.presentation.presenter;

import com.edulog.driverportal.routeselection.domain.interactor.SetActiveRouteUseCase;
import com.edulog.driverportal.routeselection.presentation.view.RouteDetailsView;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import io.reactivex.Observer;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class)
public class RouteDetailsPresenterTest {
    private RouteDetailsPresenter routeDetailsPresenter;
    @Mock
    private SetActiveRouteUseCase mockSetActiveRouteUseCase;
    @Mock
    private RouteDetailsView mockRouteDetailsView;

    @Before
    public void setUp() throws Exception {
        routeDetailsPresenter = new RouteDetailsPresenterImpl(mockSetActiveRouteUseCase);
        routeDetailsPresenter.attach(mockRouteDetailsView);
    }

    @Test
    public void setActiveRoute_useCaseExecute() throws Exception {
        routeDetailsPresenter.setActiveRoute("");

        verify(mockSetActiveRouteUseCase).execute(any(Observer.class), anyString());
    }

    @Test
    public void setActiveRoute_viewShowProgress() throws Exception {
        routeDetailsPresenter.setActiveRoute("");

        verify(mockRouteDetailsView).showProgress();
    }
}