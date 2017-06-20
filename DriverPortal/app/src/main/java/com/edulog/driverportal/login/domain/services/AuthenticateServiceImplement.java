package com.edulog.driverportal.login.domain.services;

import com.edulog.driverportal.login.domain.utils.LoginValidateUtils;
import com.edulog.driverportal.login.models.LoginValidation;

import io.reactivex.Observable;

/**
 *Handle AuthenticateService, which observe authenticate result
 */

public class AuthenticateServiceImplement implements AuthenticateService {
    private LoginValidateUtils mLoginValidateUtils;

    public AuthenticateServiceImplement(LoginValidateUtils loginValidateUtils) {
            this.mLoginValidateUtils = loginValidateUtils;
    }

    @Override
    public Observable<Boolean> authenticate(String driverId, String password) {
        return Observable.just(true);
    }

    @Override
    public Observable<LoginValidation> validate(String busId, String driverId, String password) {
       LoginValidation loginValidation = mLoginValidateUtils.makeLoginValidation( busId, driverId, password);
        return Observable.just(loginValidation);
    }



}
