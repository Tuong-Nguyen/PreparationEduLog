package edu.h2.layoutdemo.login.domain.services;

import io.reactivex.Observable;

/**
 * Implement AuthenticateService
 */

public class AuthenticateServiceImplement implements AuthenticateService {
    @Override
    public Observable<Boolean> authenticate(String driverId, String password) {
        return Observable.just(true);
    }
}
