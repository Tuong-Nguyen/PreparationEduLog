package com.edulog.driverportal.login;

import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Test;
import org.mockito.Mockito;

import com.edulog.driverportal.RxImmediateSchedulerRule;
import com.edulog.driverportal.login.domain.services.AuthenticateService;
import com.edulog.driverportal.login.domain.interactors.DriverAuthenticateUseCase;

import io.reactivex.Observable;
import io.reactivex.observers.TestObserver;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * Created by ntmhanh on 6/13/2017.
 */

public class DriverAuthenticateUseCaseTest {
    @ClassRule
    public static final RxImmediateSchedulerRule schedulers = new RxImmediateSchedulerRule();

    private DriverAuthenticateUseCase driverAuthenticateUseCase;
    String busId = "1";
    String driverId = "2";
    String password = "123";
    AuthenticateService authenticateService;


    @Before
    public void init() {
        authenticateService = Mockito.mock(AuthenticateService.class);
        driverAuthenticateUseCase = new DriverAuthenticateUseCase(authenticateService);
    }

    @Test
    public void execute_inputLoginInformation_returnAuthenticateWasCalled() {
        // Arrange
        DriverAuthenticateUseCase.Params params = new DriverAuthenticateUseCase.Params(busId, driverId, password);
        when(authenticateService.authenticate(anyString(), anyString())).thenReturn(Observable.just(true));
        TestObserver<Boolean> testObserver = new TestObserver<>();
        // Action
        driverAuthenticateUseCase.execute(testObserver, params);
        // Assert
        verify(authenticateService).authenticate(params.driverId, params.password);
        testObserver.assertValue(true);
    }
}
