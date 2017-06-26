package com.edulog.driverportal.login.domain.services;

import io.reactivex.Observable;

/**
 *Handle AuthenticateService, which observe login result
 */
public class AuthenticateServiceImpl implements AuthenticateService {

    @Override
    public Observable<Boolean> login(String driverId, String password) {
        return Observable.just(true);
    }
}
