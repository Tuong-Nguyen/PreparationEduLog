package com.edulog.driverportal.login.domain.inreractor;

import com.edulog.driverportal.login.domain.interactors.DriverAuthenticateUseCase;
import com.edulog.driverportal.login.domain.services.AuthenticateService;
import com.edulog.driverportal.login.domain.utils.ErrorValidateUtils;
import com.edulog.driverportal.login.models.ErrorValidation;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import io.reactivex.Observable;
import io.reactivex.observers.TestObserver;
import io.reactivex.schedulers.Schedulers;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * Created by ntmhanh on 6/13/2017.
 */

public class DriverAuthenticateUseCaseTest {

    String busId = "1";
    String driverId = "2";
    String password = "123456789";
    private AuthenticateService authenticateService;
    private ErrorValidateUtils errorValidateUtils;
    private DriverAuthenticateUseCase driverAuthenticateUseCase;
    private ErrorValidation errorValidation;

    @Before
    public void init() {
        authenticateService = Mockito.mock(AuthenticateService.class);
        errorValidateUtils = Mockito.mock(ErrorValidateUtils.class);
        errorValidation = Mockito.mock(ErrorValidation.class);
        driverAuthenticateUseCase = new DriverAuthenticateUseCase(Schedulers.trampoline(), authenticateService, errorValidateUtils);
    }

    @Test
    public void execute_validateLoginInformation_returnAuthenticateWasCalledAssertValidateAndLoginTrue() {
        // Arrange
        DriverAuthenticateUseCase.Params params = new DriverAuthenticateUseCase.Params(busId, driverId, password);
        when(errorValidation.isValid()).thenReturn(true);
        when(errorValidateUtils.validateLogin(anyString(), anyString(), anyString())).thenReturn(errorValidation);
        when(authenticateService.login(anyString(), anyString())).thenReturn(Observable.just(true));
        TestObserver<Boolean> testObserver = new TestObserver<>();
        // Action
        driverAuthenticateUseCase.execute(testObserver, params);
        // Assert
        verify(authenticateService).login(params.driverId, params.password);
        testObserver.assertValue(true);
    }
    @Test
    public void execute_inValidateLoginInformation_returnAuthenticateWasCalledAssertError() {
        // Arrange
        DriverAuthenticateUseCase.Params params = new DriverAuthenticateUseCase.Params(busId, driverId, password);
        when(errorValidation.isValid()).thenReturn(false);
        when(errorValidateUtils.validateLogin(anyString(), anyString(), anyString())).thenReturn(errorValidation);
        when(authenticateService.login(anyString(), anyString())).thenReturn(Observable.just(true));
        TestObserver<Boolean> testObserver = new TestObserver<>();
        // Action
        driverAuthenticateUseCase.execute(testObserver, params);
        // Assert
        verify(authenticateService).login(params.driverId, params.password);
        testObserver.assertError(Throwable.class);
    }
}
