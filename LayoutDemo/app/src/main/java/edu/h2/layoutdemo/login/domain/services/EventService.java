package edu.h2.layoutdemo.login.domain.services;

import edu.h2.layoutdemo.login.models.Events;
import io.reactivex.Observable;

/**
 * Define interface for EnventService
 */

public interface EventService {
    Observable<Boolean> sendEvent(Events events);
}
