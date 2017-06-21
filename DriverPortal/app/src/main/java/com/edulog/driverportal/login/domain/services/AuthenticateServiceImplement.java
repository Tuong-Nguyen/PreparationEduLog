package com.edulog.driverportal.login.domain.services;

import com.edulog.driverportal.login.domain.utils.LoginValidateUtils;
import com.edulog.driverportal.login.models.ErrorValidation;

import io.reactivex.Observable;

/**
 *Handle AuthenticateService, which observe login result
 */

public class AuthenticateServiceImplement implements AuthenticateService {
    private LoginValidateUtils mLoginValidateUtils;

    public AuthenticateServiceImplement(LoginValidateUtils loginValidateUtils) {
            this.mLoginValidateUtils = loginValidateUtils;
    }

    @Override
    public Observable<Boolean> login(String driverId, String password) {
        return Observable.just(true);
    }

    @Override
    public Observable<ErrorValidation> validate(String busId, String driverId, String password) {
       ErrorValidation errorValidation = mLoginValidateUtils.validateLogin( busId, driverId, password);
        return Observable.just(errorValidation);
    }



}
