package edu.h2.layoutdemo.login.domain.services;


import edu.h2.layoutdemo.login.models.Events;
import io.reactivex.Observable;

/**
 * Implement EventService
 */

public class EventServiceImplement implements EventService {
    @Override
    public Observable<Boolean> sendEvent(Events events) {
        return Observable.just(true);
    }
}
