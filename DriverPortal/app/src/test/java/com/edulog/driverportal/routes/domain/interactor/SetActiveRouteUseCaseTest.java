package com.edulog.driverportal.routes.domain.interactor;

import com.edulog.driverportal.RxImmediateSchedulerRule;
import com.edulog.driverportal.routes.data.entity.RouteEntity;
import com.edulog.driverportal.common.device.Session;
import com.edulog.driverportal.routes.domain.repository.RouteRepository;
import com.edulog.driverportal.routes.domain.service.RouteService;
import com.edulog.driverportal.routes.model.LoadMode;

import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import io.reactivex.Observable;
import io.reactivex.observers.TestObserver;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class SetActiveRouteUseCaseTest {
    @ClassRule
    public static final RxImmediateSchedulerRule schedulers = new RxImmediateSchedulerRule();
    @Mock
    private RouteService mockRouteService;
    @Mock
    private RouteRepository mockRouteRepository;
    @Mock
    private Session mockSession;
    private SetActiveRouteUseCase setActiveRouteUseCase;

    @Before
    public void setUp() throws Exception {
        setActiveRouteUseCase = new SetActiveRouteUseCase(mockRouteService, mockRouteRepository, mockSession);
    }

    @Test
    public void execute_local() throws Exception {
        RouteEntity routeEntity = new RouteEntity();
        routeEntity.setId("route_id");
        when(mockRouteRepository.findOne("route_id")).thenReturn(routeEntity);
        when(mockSession.getDriverId()).thenReturn("driver_id");

        setActiveRouteUseCase.execute(new TestObserver<>(), SetActiveRouteUseCase.buildParams("route_id", LoadMode.LOCAL));

        verify(mockSession).putRouteId("route_id");
    }

    @Test
    public void execute_remote() throws Exception {
        RouteEntity routeEntity = new RouteEntity();
        routeEntity.setId("route_id");
        when(mockRouteService.getRoute("route_id")).thenReturn(Observable.just(routeEntity));
        when(mockSession.getDriverId()).thenReturn("driver_id");

        setActiveRouteUseCase.execute(new TestObserver<>(), SetActiveRouteUseCase.buildParams("route_id", LoadMode.REMOTE));

        verify(mockRouteRepository).upsert(any(RouteEntity.class));
        verify(mockSession).putRouteId("route_id");
    }
}