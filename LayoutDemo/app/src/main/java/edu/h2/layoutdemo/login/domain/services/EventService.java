package edu.h2.layoutdemo.login.domain.services;

import edu.h2.layoutdemo.login.models.Events;
import io.reactivex.Observable;

/**
 * Service for events, which define methods for events request
 */

public interface EventService {
    Observable<Boolean> sendEvent(Events events);
}
