package com.edulog.driverportal.login.domain.services;

import com.edulog.driverportal.login.models.LoginValidation;

import io.reactivex.Observable;

/**
 * Service for authenticate driver, which define methods for authenticate
 */

public interface AuthenticateService {
    Observable<Boolean> authenticate(String driverId, String password);

    Observable<LoginValidation> validate(String busId, String driverId, String password);

}
