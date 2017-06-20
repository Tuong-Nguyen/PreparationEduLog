package com.edulog.driverportal.routes.domain.interactor;

import com.edulog.driverportal.RxImmediateSchedulerRule;
import com.edulog.driverportal.routes.data.entity.RouteEntity;
import com.edulog.driverportal.routes.data.session.Session;
import com.edulog.driverportal.routes.domain.repository.RouteRepository;
import com.edulog.driverportal.routes.model.RouteModel;

import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.observers.TestObserver;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class)
public class SaveRouteUseCaseTest {
    @ClassRule
    public static final RxImmediateSchedulerRule schedulers = new RxImmediateSchedulerRule();
    @Mock
    private RouteRepository mockRouteRepository;
    @Mock
    private Session mockSession;

    private SaveRouteUseCase saveRouteUseCase;

    @Before
    public void setUp() throws Exception {
        saveRouteUseCase = new SaveRouteUseCase(AndroidSchedulers.mainThread(), mockRouteRepository, mockSession);
    }

    @Test
    public void execute_saveToSession() throws Exception {
        RouteModel routeModel = new RouteModel();
        routeModel.setId("1");
        saveRouteUseCase.execute(new TestObserver<>(), routeModel);

        verify(mockSession).putRouteId("1");
    }

    @Test
    public void execute_saveToDatabase() throws Exception {
        RouteModel routeModel = new RouteModel();
        routeModel.setId("1");
        saveRouteUseCase.execute(new TestObserver<>(), routeModel);

        verify(mockRouteRepository).insert(any(RouteEntity.class));
    }
}