package com.edulog.driverportal.routes.presentation;

import com.edulog.driverportal.routes.domain.SetActiveRouteUseCase;
import com.edulog.driverportal.routes.model.LoadMode;
import com.edulog.driverportal.routes.presentation.RouteDetailsContract;
import com.edulog.driverportal.routes.presentation.RouteDetailsPresenterImpl;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import io.reactivex.Observer;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class)
public class RouteDetailsPresenterTest {
    private RouteDetailsContract.RouteDetailsPresenter routeDetailsPresenter;
    @Mock
    private SetActiveRouteUseCase mockSetActiveRouteUseCase;
    @Mock
    private RouteDetailsContract.RouteDetailsView mockRouteDetailsView;

    @Before
    public void setUp() throws Exception {
        routeDetailsPresenter = new RouteDetailsPresenterImpl(mockSetActiveRouteUseCase);
        routeDetailsPresenter.attach(mockRouteDetailsView);
    }

    @Test
    public void setActiveRoute_useCaseExecute() throws Exception {
        routeDetailsPresenter.setActiveRoute("", LoadMode.REMOTE);

        verify(mockSetActiveRouteUseCase).execute(any(Observer.class), any(SetActiveRouteUseCase.Params.class));
    }

    @Test
    public void setActiveRoute_viewShowProgress() throws Exception {
        routeDetailsPresenter.setActiveRoute("", LoadMode.REMOTE);

        verify(mockRouteDetailsView).showProgress();
    }
}