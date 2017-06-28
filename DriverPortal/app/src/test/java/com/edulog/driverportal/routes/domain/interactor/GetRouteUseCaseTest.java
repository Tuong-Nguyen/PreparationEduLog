package com.edulog.driverportal.routes.domain.interactor;

import com.edulog.driverportal.RxImmediateSchedulerRule;
import com.edulog.driverportal.routes.data.entity.RouteEntity;
import com.edulog.driverportal.routes.domain.repository.RouteRepository;
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
import static org.mockito.Mockito.mock;
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

    @Before
    public void setUp() throws Exception {
        getRouteUseCase = new GetRouteUseCase(mockRouteService, mockRouteRepository);
    }

    @Test
    public void execute_getRoute() {
        when(mockRouteService.getRoute("route_id")).thenReturn(Observable.just(new RouteEntity()));

        getRouteUseCase.execute(new TestObserver<>(), GetRouteUseCase.buildParams("route_id", LoadMode.REMOTE));

        verify(mockRouteService).getRoute("route_id");
    }

    @Test
    public void execute_findOne() {
        when(mockRouteRepository.findOne("route_id")).thenReturn(new RouteEntity());

        getRouteUseCase.execute(new TestObserver<>(), GetRouteUseCase.buildParams("route_id", LoadMode.LOCAL));

        verify(mockRouteRepository).findOne("route_id");
    }
}