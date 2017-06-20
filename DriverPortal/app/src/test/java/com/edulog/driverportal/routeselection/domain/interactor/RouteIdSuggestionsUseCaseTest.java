package com.edulog.driverportal.routeselection.domain.interactor;

import com.edulog.driverportal.RxImmediateSchedulerRule;
import com.edulog.driverportal.routeselection.data.entity.RouteEntity;
import com.edulog.driverportal.routeselection.domain.service.RouteService;

import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.Scheduler;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.observers.TestObserver;

import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class RouteIdSuggestionsUseCaseTest {
    @ClassRule
    public static final RxImmediateSchedulerRule schedulers = new RxImmediateSchedulerRule();
    private RouteIdSuggestionsUseCase routeIdSuggestionsUseCase;
    @Mock
    private RouteService mockRouteService;

    @Before
    public void setUp() throws Exception {
        Scheduler postExecutionScheduler = AndroidSchedulers.mainThread();
        routeIdSuggestionsUseCase = new RouteIdSuggestionsUseCase(postExecutionScheduler, mockRouteService);
    }

    @Test
    public void execute_niceQuery_niceIdSuggestions() throws Exception {
        List<RouteEntity> routeEntities = prepareTestEntities();
        when(mockRouteService.findRoutes("nice")).thenReturn(Observable.just(routeEntities));
        TestObserver<List<String>> observer = new TestObserver<>();

        routeIdSuggestionsUseCase.execute(observer, "nice");

        List<String> expectedValues = Arrays.asList("1", "2");
        observer.assertValue(expectedValues);
    }

    @Test
    public void execute_badQuery_emptyList() throws Exception {
        List<RouteEntity> routeEntities = prepareTestEntities();
        when(mockRouteService.findRoutes("bad")).thenReturn(Observable.just(Collections.emptyList()));
        TestObserver<List<String>> observer = new TestObserver<>();

        routeIdSuggestionsUseCase.execute(observer, "bad");

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