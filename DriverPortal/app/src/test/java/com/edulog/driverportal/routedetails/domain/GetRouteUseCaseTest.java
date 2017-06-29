package com.edulog.driverportal.routedetails.domain;

import com.edulog.driverportal.RxImmediateSchedulerRule;
import com.edulog.driverportal.routedetails.model.Polyline;
import com.edulog.driverportal.routes.data.RouteRepository;
import com.edulog.driverportal.routes.domain.RouteService;
import com.edulog.driverportal.routes.model.Route;

import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import io.reactivex.Observable;
import io.reactivex.observers.TestObserver;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class GetRouteUseCaseTest {
    @ClassRule
    public static final RxImmediateSchedulerRule schedulers = new RxImmediateSchedulerRule();
    private GetRouteUseCase getRouteUseCase;

    @Mock
    private RouteService mockRouteService;
    @Mock
    private RouteRepository mockRouteRepository;
    @Mock
    private MapService mockMapService;
    private String apiKey = "12340";
    @Before
    public void setUp() {
        getRouteUseCase = new GetRouteUseCase(mockRouteService, mockRouteRepository, mockMapService, apiKey);
    }

    @Test
    public void execute_getRouteFromServerSuccess() {
        TestObserver observer = new TestObserver();
        Route route = new Route();
        route.setOrigin("toronto");
        route.setDestination("montreal");
        when(mockRouteService.getRoute("1")).thenReturn(Observable.just(route));
        when(mockMapService.getDirection("toronto", "montreal", apiKey)).thenReturn(Observable.just(new Polyline()));

        getRouteUseCase.execute(observer, "1");
        observer.awaitTerminalEvent();

        verify(mockRouteService).getRoute("1");
        verify(mockMapService).getDirection("toronto", "montreal", apiKey);
    }

    @Test
    public void execute_getRouteFromServerFailed() {
        TestObserver observer = new TestObserver();
        Route route = new Route();
        route.setOrigin("toronto");
        route.setDestination("montreal");
        when(mockRouteService.getRoute("1")).thenReturn(Observable.error(new RuntimeException()));
        when(mockRouteRepository.findOne("1")).thenReturn(route);
        when(mockMapService.getDirection("toronto", "montreal", apiKey)).thenReturn(Observable.just(new Polyline()));

        getRouteUseCase.execute(observer, "1");
        observer.awaitTerminalEvent();

        verify(mockRouteService).getRoute("1");
        verify(mockRouteRepository).findOne("1");
        verify(mockMapService).getDirection("toronto", "montreal", apiKey);
    }
}
