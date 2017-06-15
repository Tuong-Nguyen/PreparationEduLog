package edu.h2.layoutdemo.login.domain.services;

import io.reactivex.Observable;

/**
 * Created by ntmhanh on 6/15/2017.
 */

public interface AuthenticateService {
    Observable<Boolean> authenticate(String driverId, String password);
}
