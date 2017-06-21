package com.edulog.driverportal.login.domain.services;

import com.edulog.driverportal.login.models.ErrorValidation;

import io.reactivex.Observable;

/**
 * Service for login driver, which define methods for login
 */

public interface AuthenticateService {
    Observable<Boolean> login(String driverId, String password);

    Observable<ErrorValidation> validate(String busId, String driverId, String password);

}
