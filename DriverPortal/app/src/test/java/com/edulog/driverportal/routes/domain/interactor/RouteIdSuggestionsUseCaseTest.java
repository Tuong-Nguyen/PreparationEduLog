package com.edulog.driverportal.routes.domain.interactor;

import com.edulog.driverportal.routes.domain.service.RouteService;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;

import java.util.List;

import io.reactivex.Scheduler;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.observers.TestObserver;

import static org.junit.Assert.*;

public class RouteIdSuggestionsUseCaseTest {
    private RouteIdSuggestionsUseCase routeIdSuggestionsUseCase;
    @Mock RouteService mockRouteService;

    @Before
    public void setUp() throws Exception {
        Scheduler postExecutionScheduler = AndroidSchedulers.mainThread();
        routeIdSuggestionsUseCase = new RouteIdSuggestionsUseCase(postExecutionScheduler, mockRouteService);
    }

    @Test
    public void execute_niceQuery() {
        TestObserver<List<String>> observer = new TestObserver<>();
        routeIdSuggestionsUseCase.execute(observer, "nice");
    }
}