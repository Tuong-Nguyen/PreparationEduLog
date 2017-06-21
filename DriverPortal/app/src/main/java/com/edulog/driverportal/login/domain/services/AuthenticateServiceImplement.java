package com.edulog.driverportal.login.domain.services;

import com.edulog.driverportal.login.domain.utils.ErrorValidateUtils;
import com.edulog.driverportal.login.models.ErrorValidation;

import io.reactivex.Observable;

/**
 *Handle AuthenticateService, which observe login result
 */

public class AuthenticateServiceImplement implements AuthenticateService {
    private ErrorValidateUtils mErrorValidateUtils;

    public AuthenticateServiceImplement(ErrorValidateUtils errorValidateUtils) {
            this.mErrorValidateUtils = errorValidateUtils;
    }

    @Override
    public Observable<Boolean> login(String driverId, String password) {
        return Observable.just(true);
    }

    @Override
    public Observable<ErrorValidation> validate(String busId, String driverId, String password) {
       ErrorValidation errorValidation = mErrorValidateUtils.validateLogin( busId, driverId, password);
        return Observable.just(errorValidation);
    }



}
