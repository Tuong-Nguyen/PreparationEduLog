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

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.Scheduler;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.observers.TestObserver;

import static org.mockito.Mockito.verify;
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
        routeIdSuggestionsUseCase = new RouteIdSuggestionsUseCase(mockRouteService);
    }

    @Test
    public void execute_findRoutes() {
        when(mockRouteService.findRoutes("query")).thenReturn(Observable.just(new ArrayList<>()));

        routeIdSuggestionsUseCase.execute(new TestObserver<>(), "query");

        verify(mockRouteService).findRoutes("query");
    }
}