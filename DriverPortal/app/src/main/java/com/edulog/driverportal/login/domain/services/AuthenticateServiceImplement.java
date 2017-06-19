package com.edulog.driverportal.login.domain.services;

import io.reactivex.Observable;

/**
 *Handle AuthenticateService, which observe authenticate result
 */

public class AuthenticateServiceImplement implements AuthenticateService {
    @Override
    public Observable<Boolean> authenticate(String driverId, String password) {
        return Observable.just(true);
    }
}
