package com.edulog.driverportal.login.domain.inreractor;

import com.edulog.driverportal.RxImmediateSchedulerRule;
import com.edulog.driverportal.login.domain.interactors.LoginUseCase;
import com.edulog.driverportal.login.domain.services.AuthenticateService;
import com.edulog.driverportal.login.domain.services.DriverPreferences;
import com.edulog.driverportal.login.domain.services.EventService;
import com.edulog.driverportal.login.domain.utils.ErrorValidationUtil;
import com.edulog.driverportal.login.models.ErrorValidation;
import com.edulog.driverportal.login.models.Events;

import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Test;
import org.mockito.Mockito;

import io.reactivex.Observable;
import io.reactivex.observers.TestObserver;
import io.reactivex.schedulers.Schedulers;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;


public class LoginUseCaseTest {
    @ClassRule
    public static final RxImmediateSchedulerRule schedulers = new RxImmediateSchedulerRule();

    String busId = "1";
    String driverId = "2";
    String password = "123456789";
    boolean isChecked;
    private AuthenticateService authenticateService;
    private ErrorValidationUtil errorValidationUtil;
    private LoginUseCase loginUseCase;
    private ErrorValidation errorValidation;
    private EventService eventService;
    private DriverPreferences driverPreferences;

    @Before
    public void init() {
        authenticateService = Mockito.mock(AuthenticateService.class);
        errorValidationUtil = Mockito.mock(ErrorValidationUtil.class);
        errorValidation = Mockito.mock(ErrorValidation.class);
        eventService = Mockito.mock(EventService.class);
        driverPreferences = Mockito.mock(DriverPreferences.class);
        loginUseCase = new LoginUseCase(authenticateService, errorValidationUtil,eventService, driverPreferences);
    }

    @Test
    public void execute_validateLoginInformation_returnAssertComplete() {
        // Arrange
        isChecked = true;
        LoginUseCase.Params params = new LoginUseCase.Params(busId, driverId, password,isChecked);
        when(errorValidation.isValid()).thenReturn(true);
        when(errorValidationUtil.validateLogin(anyString(), anyString(), anyString())).thenReturn(errorValidation);
        when(authenticateService.login(anyString(), anyString())).thenReturn(Observable.just(true));
        when(eventService.sendEvent(Events.LOG_IN)).thenReturn(Observable.just(true));
        TestObserver<Boolean> testObserver = new TestObserver<>();
        // Action
        loginUseCase.execute(testObserver, params);
        // Assert
        testObserver.assertComplete();
    }
    @Test
    public void execute_inValidateLoginInformation_returnAssertError() {
        // Arrange
        isChecked = true;
        LoginUseCase.Params params = new LoginUseCase.Params(busId, driverId, password,isChecked);
        when(errorValidation.isValid()).thenReturn(false);
        when(errorValidationUtil.validateLogin(anyString(), anyString(), anyString())).thenReturn(errorValidation);
        when(authenticateService.login(anyString(), anyString())).thenReturn(Observable.just(true));
        when(eventService.sendEvent(Events.LOG_IN)).thenReturn(Observable.just(true));
        TestObserver<Boolean> testObserver = new TestObserver<>();
        // Action
        loginUseCase.execute(testObserver, params);
        // Assert
        testObserver.assertError(Throwable.class);
    }
    @Test
    public void execute_authenticateFailure_returnAssertError() {
        // Arrange
        isChecked = true;
        LoginUseCase.Params params = new LoginUseCase.Params(busId, driverId, password,isChecked);
        when(errorValidation.isValid()).thenReturn(true);
        when(errorValidationUtil.validateLogin(anyString(), anyString(), anyString())).thenReturn(errorValidation);
        when(authenticateService.login(anyString(), anyString())).thenReturn(Observable.just(false));
        when(eventService.sendEvent(Events.LOG_IN)).thenReturn(Observable.just(true));
        TestObserver<Boolean> testObserver = new TestObserver<>();
        // Action
        loginUseCase.execute(testObserver, params);
        // Assert
        verify(authenticateService).login(params.driverId, params.password);
        testObserver.assertError(Throwable.class);
    }
    @Test
    public void execute_sendEventFailure_returnAssertComplete() {
        // Arrange
        isChecked = true;
        LoginUseCase.Params params = new LoginUseCase.Params(busId, driverId, password,isChecked);
        when(errorValidation.isValid()).thenReturn(true);
        when(errorValidationUtil.validateLogin(anyString(), anyString(), anyString())).thenReturn(errorValidation);
        when(authenticateService.login(anyString(), anyString())).thenReturn(Observable.just(true));
        when(eventService.sendEvent(Events.LOG_IN)).thenReturn(Observable.just(false));
        TestObserver<Boolean> testObserver = new TestObserver<>();
        // Action
        loginUseCase.execute(testObserver, params);
        // Assert
        verify(authenticateService).login(params.driverId, params.password);
        testObserver.assertComplete();
    }
    @Test
    public void execute_rememberDriverIdChecked_returnAssertCompleteAndSetValuePreferencesWasCalled() {
        // Arrange
        isChecked = true;
        LoginUseCase.Params params = new LoginUseCase.Params(busId, driverId, password,isChecked);
        when(errorValidation.isValid()).thenReturn(true);
        when(errorValidationUtil.validateLogin(anyString(), anyString(), anyString())).thenReturn(errorValidation);
        when(authenticateService.login(anyString(), anyString())).thenReturn(Observable.just(true));
        when(eventService.sendEvent(Events.LOG_IN)).thenReturn(Observable.just(true));
        TestObserver<Boolean> testObserver = new TestObserver<>();
        // Action
        loginUseCase.execute(testObserver, params);
        // Assert
        verify(driverPreferences).setValuePreferences(driverId);
        testObserver.assertComplete();
    }
    @Test
    public void execute_rememberDriverIdNotChecked_returnAssertCompleteAndRemoveValueItemWasCalled() {
        // Arrange
        isChecked = false;
        LoginUseCase.Params params = new LoginUseCase.Params(busId, driverId, password,isChecked);
        when(errorValidation.isValid()).thenReturn(true);
        when(errorValidationUtil.validateLogin(anyString(), anyString(), anyString())).thenReturn(errorValidation);
        when(authenticateService.login(anyString(), anyString())).thenReturn(Observable.just(true));
        when(eventService.sendEvent(Events.LOG_IN)).thenReturn(Observable.just(true));
        TestObserver<Boolean> testObserver = new TestObserver<>();
        // Action
        loginUseCase.execute(testObserver, params);
        // Assert
        verify(driverPreferences).removeValueItem();
        testObserver.assertComplete();
    }

}
