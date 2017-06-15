package edu.h2.layoutdemo.login.domain.services;


import edu.h2.layoutdemo.login.models.Event;
import io.reactivex.Observable;

/**
 * Created by ntmhanh on 6/14/2017.
 */

public class EventServiceImplement implements EventService {
    @Override
    public Observable<Boolean> sendEvent(Event event) {
        return Observable.just(true);
    }
}
