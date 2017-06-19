package com.edulog.driverportal.login.domain.services;

import com.edulog.driverportal.login.models.Events;

import io.reactivex.Observable;

/**
 * Service for events, which define methods for events request
 */

public interface EventService {
    Observable<Boolean> sendEvent(Events events);
}
