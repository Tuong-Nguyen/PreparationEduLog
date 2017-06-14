package edu.h2.layoutdemo.login.domain;

import edu.h2.layoutdemo.login.models.Event;

/**
 * Created by ntmhanh on 6/14/2017.
 */

public interface EventService {
    boolean sentEvent (Event event);
}
