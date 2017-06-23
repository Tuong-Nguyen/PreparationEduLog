package com.edulog.driverportal.login.domain.utils;

/**
 * Created by ntmhanh on 6/20/2017.
 */

// TODO: Rename as LoginValidator and move it into interactors package
public  class ErrorValidationUtil {
    private com.edulog.driverportal.login.models.ErrorValidation errorValidation;

    public ErrorValidationUtil(com.edulog.driverportal.login.models.ErrorValidation errorValidation) {
        this.errorValidation = errorValidation;
    }

    public com.edulog.driverportal.login.models.ErrorValidation validateLogin(String busId, String driverId, String password){
        if (busId.isEmpty()){
            errorValidation.setErrorMessage("Bus ID mustn't empty");
        } else if(driverId.isEmpty()){
            errorValidation.setErrorMessage("Driver ID mustn't empty");
        } else {
            if (password.isEmpty()){
                errorValidation.setErrorMessage("Password mustn't empty");
            } else if (password.length() <= 7){
                errorValidation.setErrorMessage("Password must be at least 8 characters");
            }
        }
        return errorValidation;
    }
}
