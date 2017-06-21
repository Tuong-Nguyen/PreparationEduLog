package com.edulog.driverportal.routeselection.domain.interactor;

import com.edulog.driverportal.RxImmediateSchedulerRule;
import com.edulog.driverportal.routeselection.data.entity.RouteEntity;
import com.edulog.driverportal.common.preference.Session;
import com.edulog.driverportal.routeselection.domain.repository.RouteRepository;
import com.edulog.driverportal.routeselection.domain.service.RouteService;
import com.edulog.driverportal.routeselection.presentation.model.RouteModel;

import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
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
    public void execute_existsRouteId_returnRouteModel() throws Exception {
        RouteEntity routeEntity = createRouteEntity();
        TestObserver<RouteModel> observer = new TestObserver<>();
        when(mockRouteService.getRoute("exists_route")).thenReturn(Observable.just(routeEntity));

        setActiveRouteUseCase.execute(observer, "exists_route");

        observer.assertNoErrors();
        observer.assertValue(routeModel -> routeModel.getId().equals(routeEntity.getId()));
    }

    @Test
    public void execute_notExistsRouteId_error() throws Exception {
        when(mockRouteService.getRoute("not_exists_route")).thenReturn(Observable.error(new RuntimeException()));
        TestObserver<RouteModel> observer = new TestObserver<>();

        setActiveRouteUseCase.execute(observer, "not_exists_route");

        observer.assertError(RuntimeException.class);
    }

    @Test
    public void execute_existsRouteId_saveToSession() throws Exception {
        RouteEntity routeEntity = createRouteEntity();
        TestObserver<RouteModel> observer = new TestObserver<>();
        when(mockRouteService.getRoute("exists_route")).thenReturn(Observable.just(routeEntity));

        setActiveRouteUseCase.execute(observer, "exists_route");

        observer.awaitTerminalEvent();
        observer.assertNoErrors();
        verify(mockSession).putRouteId("exists_route");
    }

    @Test
    public void execute_existsRouteId_saveToDatabase() throws Exception {
        RouteEntity routeEntity = createRouteEntity();
        TestObserver<RouteModel> observer = new TestObserver<>();
        when(mockRouteService.getRoute("exists_route")).thenReturn(Observable.just(routeEntity));

        setActiveRouteUseCase.execute(observer, "exists_route");

        observer.awaitTerminalEvent();
        observer.assertNoErrors();
        verify(mockRouteRepository).insert(any(RouteEntity.class));
    }

    private RouteEntity createRouteEntity() {
        RouteEntity routeEntity = new RouteEntity();
        routeEntity.setId("exists_route");
        routeEntity.setName("exists_route_name");
        routeEntity.setStopCount(3);
        return routeEntity;
    }
}