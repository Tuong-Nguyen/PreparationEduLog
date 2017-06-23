package com.edulog.driverportal.login.domain.services;


import com.edulog.driverportal.login.models.Events;
import io.reactivex.Observable;

/**
 * Handle ServiceEvent, which observe Event request result
 */
public class EventServiceImpl implements EventService {
    @Override
    public Observable<Boolean> sendEvent(Events events) {
        return Observable.just(true);
    }
}
