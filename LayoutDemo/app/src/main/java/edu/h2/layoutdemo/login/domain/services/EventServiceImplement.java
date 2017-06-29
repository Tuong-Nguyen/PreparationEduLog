package edu.h2.layoutdemo.login.domain.services;


import edu.h2.layoutdemo.login.models.Events;
import io.reactivex.Observable;

/**
 * Handle ServiceEvent, which observe Event request result
 */

public class EventServiceImplement implements EventService {
    @Override
    public Observable<Boolean> sendEvent(Events events) {
        return Observable.just(true);
    }
}
