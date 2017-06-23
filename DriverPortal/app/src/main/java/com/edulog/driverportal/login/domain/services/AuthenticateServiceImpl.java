package com.edulog.driverportal.login.domain.services;

import com.edulog.driverportal.login.domain.interactors.LoginValidator;

import io.reactivex.Observable;

/**
 *Handle AuthenticateService, which observe login result
 */

// TODO: rename to AuthenticateServiceImpl
public class AuthenticateServiceImpl implements AuthenticateService {
    // TODO: do not prefix with 'm'
    private LoginValidator loginValidator;

    public AuthenticateServiceImpl(LoginValidator loginValidator) {
            this.loginValidator = loginValidator;
    }

    @Override
    public Observable<Boolean> login(String driverId, String password) {
        return Observable.just(true);
    }
}
