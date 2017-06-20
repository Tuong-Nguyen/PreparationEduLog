package com.edulog.driverportal.login.domain.interactors;

import com.edulog.driverportal.common.base.UseCase;
import com.edulog.driverportal.login.domain.services.EventService;
import com.edulog.driverportal.login.models.Events;

import io.reactivex.Observable;
import io.reactivex.Scheduler;

/**
 * Created by ntmhanh on 6/20/2017.
 */

public class SendEventUseCase extends UseCase<Boolean, Events> {
    private EventService mEventService;

    public SendEventUseCase(Scheduler postExecutionScheduler, EventService eventService) {
        super(postExecutionScheduler);
        this.mEventService = eventService;
    }

    @Override
    protected Observable<Boolean> buildUseCaseObservable(Events events) {
        return mEventService.sendEvent(events)
                .doOnNext(isSuccess -> {
                    if (!isSuccess) throw new RuntimeException("Sending event was failed ");
                });
    }
}
