package com.edulog.driverportal.login.domain.utils;

import com.edulog.driverportal.login.models.ErrorValidation;

/**
 * Created by ntmhanh on 6/20/2017.
 */

public  class LoginValidateUtils {
    private ErrorValidation mloginValidation;

    public LoginValidateUtils(ErrorValidation errorValidation) {
        this.mloginValidation = errorValidation;
    }

    public ErrorValidation validateLogin(String busId, String driverId, String password){
        if (busId.isEmpty()){
            mloginValidation.setErrorMessage("Bus ID mustn't empty");
        } else if(driverId.isEmpty()){
            mloginValidation.setErrorMessage("Driver ID mustn't empty");
        } else {
            if (password.isEmpty()){
                mloginValidation.setErrorMessage("Password mustn't empty");
            } else if (password.length() <= 7){
                mloginValidation.setErrorMessage("Password must be at least 8 characters");
            }
        }
        return mloginValidation;
    }
}
