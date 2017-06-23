package com.edulog.driverportal.login.domain.inreractor;

import com.edulog.driverportal.RxImmediateSchedulerRule;
import com.edulog.driverportal.common.preference.SessionImpl;
import com.edulog.driverportal.login.domain.interactors.LoginUseCase;
import com.edulog.driverportal.login.domain.services.AuthenticateService;
import com.edulog.driverportal.login.domain.services.DriverPreferences;
import com.edulog.driverportal.login.domain.services.EventService;
import com.edulog.driverportal.login.models.Events;

import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Test;
import org.mockito.Mockito;

import io.reactivex.Observable;
import io.reactivex.observers.TestObserver;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;


public class LoginUseCaseTest {
    @ClassRule
    public static final RxImmediateSchedulerRule schedulers = new RxImmediateSchedulerRule();

    boolean isChecked;
    private AuthenticateService authenticateService;
    private LoginUseCase loginUseCase;
    private EventService eventService;
    private DriverPreferences driverPreferences;
    private SessionImpl session;

    @Before
    public void init() {
        authenticateService = Mockito.mock(AuthenticateService.class);
        eventService = Mockito.mock(EventService.class);
        driverPreferences = Mockito.mock(DriverPreferences.class);
        session = Mockito.mock(SessionImpl.class);
        loginUseCase = new LoginUseCase(authenticateService,eventService, session);
    }

    @Test
    public void execute_validateLoginInformation_returnAssertComplete() {
        // Arrange
        String busId = "123";
        String driverId = "123";
        String password = "123456789";
        isChecked = true;
        LoginUseCase.Params params = new LoginUseCase.Params(busId, driverId, password,isChecked);
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
        String busId = "1";
        String driverId = "123";
        String password = "123456789";
        isChecked = true;
        LoginUseCase.Params params = new LoginUseCase.Params(busId, driverId, password,isChecked);
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
        String busId = "123";
        String driverId = "123";
        String password = "123456789";
        isChecked = true;
        LoginUseCase.Params params = new LoginUseCase.Params(busId, driverId, password,isChecked);
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
        String busId = "123";
        String driverId = "123";
        String password = "123456789";
        isChecked = true;
        LoginUseCase.Params params = new LoginUseCase.Params(busId, driverId, password,isChecked);
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
        String busId = "123";
        String driverId = "123";
        String password = "123456789";
        isChecked = true;
        LoginUseCase.Params params = new LoginUseCase.Params(busId, driverId, password,isChecked);
        when(authenticateService.login(anyString(), anyString())).thenReturn(Observable.just(true));
        when(eventService.sendEvent(Events.LOG_IN)).thenReturn(Observable.just(true));
        TestObserver<Boolean> testObserver = new TestObserver<>();
        // Action
        loginUseCase.execute(testObserver, params);
        // Assert
        verify(session).putDriverId(driverId);
        testObserver.assertComplete();
    }
    @Test
    public void execute_rememberDriverIdNotChecked_returnAssertCompleteAndRemoveValueItemWasCalled() {
        // Arrange
        String busId = "123";
        String driverId = "123";
        String password = "123456789";
        isChecked = false;
        LoginUseCase.Params params = new LoginUseCase.Params(busId, driverId, password,isChecked);
        when(authenticateService.login(anyString(), anyString())).thenReturn(Observable.just(true));
        when(eventService.sendEvent(Events.LOG_IN)).thenReturn(Observable.just(true));
        TestObserver<Boolean> testObserver = new TestObserver<>();
        // Action
        loginUseCase.execute(testObserver, params);
        // Assert
        verify(session).removeDriverId();
        testObserver.assertComplete();
    }

    @Test
    public void execute_rememberDriverIdNotChecked_returnAssertErrorAndPutDriverIdWasNotCalled() {
        // Arrange
        String busId = "1";
        String driverId = "123";
        String password = "123456789";
        isChecked = true;
        LoginUseCase.Params params = new LoginUseCase.Params(busId, driverId, password,isChecked);
        when(authenticateService.login(anyString(), anyString())).thenReturn(Observable.just(true));
        when(eventService.sendEvent(Events.LOG_IN)).thenReturn(Observable.just(true));
        TestObserver<Boolean> testObserver = new TestObserver<>();
        // Action
        loginUseCase.execute(testObserver, params);
        // Assert
        verify(session,Mockito.times(0)).putDriverId(driverId);
        testObserver.assertError(Throwable.class);
    }
}
