package com.edulog.driverportal.login.domain.services;

import com.edulog.driverportal.login.domain.utils.ErrorValidationUtil;

import io.reactivex.Observable;

/**
 *Handle AuthenticateService, which observe login result
 */

public class AuthenticateServiceImplement implements AuthenticateService {
    private ErrorValidationUtil mErrorValidationUtil;

    public AuthenticateServiceImplement(ErrorValidationUtil errorValidationUtil) {
            this.mErrorValidationUtil = errorValidationUtil;
    }

    @Override
    public Observable<Boolean> login(String driverId, String password) {
        return Observable.just(true);
    }

    @Override
    public Observable<com.edulog.driverportal.login.models.ErrorValidation> validate(String busId, String driverId, String password) {
       com.edulog.driverportal.login.models.ErrorValidation errorValidation = mErrorValidationUtil.validateLogin( busId, driverId, password);
        return Observable.just(errorValidation);
    }



}
