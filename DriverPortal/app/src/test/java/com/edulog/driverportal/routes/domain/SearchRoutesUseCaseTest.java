package com.edulog.driverportal.routes.domain;

import com.edulog.driverportal.RxImmediateSchedulerRule;
import com.edulog.driverportal.routes.domain.SearchRoutesUseCase;

import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.ArrayList;

import io.reactivex.Observable;
import io.reactivex.observers.TestObserver;

import static org.mockito.Mockito.verify;
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
        searchRoutesUseCase = new SearchRoutesUseCase(mockRouteService);
    }

    @Test
    public void execute_findRoutes() {
        when(mockRouteService.findRoutes("query")).thenReturn(Observable.just(new ArrayList<>()));

        searchRoutesUseCase.execute(new TestObserver<>(), "query");

        verify(mockRouteService).findRoutes("query");
    }
}