package com.edulog.driverportal.login.domain.inreractor;

import com.edulog.driverportal.login.domain.interactors.SendEventUseCase;
import com.edulog.driverportal.login.domain.services.EventService;
import com.edulog.driverportal.login.models.Events;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import io.reactivex.Observable;
import io.reactivex.observers.TestObserver;
import io.reactivex.schedulers.Schedulers;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;


public class SendEventUseCaseTest {
    private SendEventUseCase sendEventUseCase;
    private EventService eventService;

    @Before
    public void init(){
        eventService = Mockito.mock(EventService.class);
        sendEventUseCase = new SendEventUseCase(Schedulers.trampoline(), eventService);
    }
    @Test
    public void execute_sendEventLoginSuccess_returnSendEventWasCalledAndObserverAssertResultTrue(){
        //Arrange
        when(eventService.sendEvent(Events.LOG_IN)).thenReturn(Observable.just(true));
        TestObserver<Boolean> testObserver = new TestObserver<>();
        //Action
        sendEventUseCase.execute(testObserver, Events.LOG_IN);
        //Assert
        verify(eventService).sendEvent(Events.LOG_IN);
        testObserver.assertResult(true);
    }
    @Test
    public void execute_sendEventLoginFailure_returnSendEventWasCalledAndObserverAssertResultError(){
        //Arrange
        when(eventService.sendEvent(Events.LOG_IN)).thenReturn(Observable.just(false));
        TestObserver<Boolean> testObserver = new TestObserver<>();
        //Action
        sendEventUseCase.execute(testObserver, Events.LOG_IN);
        //Assert
        verify(eventService).sendEvent(Events.LOG_IN);
        testObserver.assertError(Throwable.class);
    }
}
