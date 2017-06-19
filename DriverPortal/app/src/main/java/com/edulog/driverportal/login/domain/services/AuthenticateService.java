package com.edulog.driverportal.login.domain.services;

import io.reactivex.Observable;

/**
 * Service for authenticate driver, which define methods for authenticate
 */

public interface AuthenticateService {
    Observable<Boolean> authenticate(String driverId, String password);
}
