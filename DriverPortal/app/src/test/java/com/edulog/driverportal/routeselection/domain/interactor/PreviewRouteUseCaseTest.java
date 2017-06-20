package com.edulog.driverportal.routeselection.domain.interactor;

import com.edulog.driverportal.RxImmediateSchedulerRule;
import com.edulog.driverportal.routeselection.data.entity.RouteEntity;
import com.edulog.driverportal.routeselection.domain.service.RouteService;
import com.edulog.driverportal.routeselection.model.RouteModel;

import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.observers.TestObserver;

import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class PreviewRouteUseCaseTest {
    @ClassRule
    public static final RxImmediateSchedulerRule schedulers = new RxImmediateSchedulerRule();
    private PreviewRouteUseCase previewRouteUseCase;
    @Mock
    private RouteService mockRouteService;

    @Before
    public void setUp() throws Exception {
        previewRouteUseCase = new PreviewRouteUseCase(AndroidSchedulers.mainThread(), mockRouteService);
    }

    @Test
    public void execute_routeExists_returnRouteModel() throws Exception {
        RouteEntity routeEntity = new RouteEntity();
        routeEntity.setId("exists_route");
        routeEntity.setName("exists_route_name");
        routeEntity.setStopCount(3);
        TestObserver<RouteModel> observer = new TestObserver<>();
        when(mockRouteService.getRoute("exists_route")).thenReturn(Observable.just(routeEntity));

        previewRouteUseCase.execute(observer, "exists_route");

        observer.assertNoErrors();
        observer.assertValue(routeModel -> routeModel.getId().equals(routeEntity.getId()));
    }

    @Test
    public void execute_routeNotExists_error() throws Exception {
        when(mockRouteService.getRoute("not_exists_route")).thenReturn(Observable.error(new RuntimeException()));
        TestObserver<RouteModel> observer = new TestObserver<>();

        previewRouteUseCase.execute(observer, "not_exists_route");

        observer.assertError(RuntimeException.class);
    }
}