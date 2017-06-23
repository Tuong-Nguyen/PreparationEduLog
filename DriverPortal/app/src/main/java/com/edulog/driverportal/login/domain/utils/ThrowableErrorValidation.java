package com.edulog.driverportal.login.domain.utils;

import com.edulog.driverportal.login.models.ErrorValidation;

/**
 * Created by ntmhanh on 6/22/2017.
 */

// TODO: Use common.validation.exception.ValidationException in place of this class
public class ThrowableErrorValidation extends RuntimeException {
    private ErrorValidation errorValidation;

    public ThrowableErrorValidation(ErrorValidation errorValidation) {
        this.errorValidation = errorValidation;
    }

    public ErrorValidation getErrorValidationUtil() {
        return errorValidation;
    }
}
