package edu.h2.layoutdemo.login.domain.services;

import edu.h2.layoutdemo.login.models.Event;

/**
 * Created by ntmhanh on 6/14/2017.
 */

public class EventServiceImplement implements EventService {
    @Override
    public boolean sentEvent(Event event) {
        return true;
    }
}
