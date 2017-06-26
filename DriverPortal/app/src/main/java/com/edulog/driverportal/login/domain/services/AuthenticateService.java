package com.edulog.driverportal.login.domain.services;

import io.reactivex.Observable;

/**
 * Service for login driver, which define methods for login
 */

public interface AuthenticateService {
    Observable<Boolean> login(String driverId, String password);
}
