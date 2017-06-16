package edu.h2.layoutdemo.login.domain.services;

import io.reactivex.Observable;

/**
 * Define interface for AuthenticateService
 */

public interface AuthenticateService {
    Observable<Boolean> authenticate(String driverId, String password);
}
