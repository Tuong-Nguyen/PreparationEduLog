package com.edulog.driverportal.routes.domain.interactor;

import com.edulog.driverportal.RxImmediateSchedulerRule;
import com.edulog.driverportal.routes.data.entity.RouteEntity;
import com.edulog.driverportal.routes.domain.service.RouteService;
import com.edulog.driverportal.routes.model.RouteModel;

import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.observers.TestObserver;

import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class SearchRoutesUseCaseTest {
    @ClassRule
    public static final RxImmediateSchedulerRule schedulers = new RxImmediateSchedulerRule();
    @Mock
    private RouteService mockRouteService;
    private SearchRoutesUseCase searchRoutesUseCase;

    @Before
    public void setUp() throws Exception {
        searchRoutesUseCase = new SearchRoutesUseCase(AndroidSchedulers.mainThread(), mockRouteService);
    }

    @Test
    public void execute_niceQuery_niceRouteModels() throws Exception {
        TestObserver<List<RouteModel>> observer = new TestObserver<>();
        when(mockRouteService.findRoutes("nice")).thenReturn(Observable.just(prepareTestEntities()));

        searchRoutesUseCase.execute(observer, "nice");

        observer.assertValue(routeModels -> routeModels.get(0).getId().equals("1") && routeModels.get(1).getId().equals("2"));
    }

    @Test
    public void execute_badQuery_emptyList() throws Exception {
        TestObserver<List<RouteModel>> observer = new TestObserver<>();
        when(mockRouteService.findRoutes("bad")).thenReturn(Observable.just(Collections.emptyList()));

        searchRoutesUseCase.execute(observer, "bad");

        observer.assertValue(Collections.emptyList());
    }

    private List<RouteEntity> prepareTestEntities() {
        List<RouteEntity> routeEntities = new ArrayList<>();

        RouteEntity entity1 = new RouteEntity();
        entity1.setId("1");
        entity1.setName("entity1");
        entity1.setStopCount(1);
        routeEntities.add(entity1);

        RouteEntity entity2 = new RouteEntity();
        entity2.setId("2");
        entity2.setName("entity2");
        entity2.setStopCount(2);
        routeEntities.add(entity2);

        return routeEntities;
    }
}