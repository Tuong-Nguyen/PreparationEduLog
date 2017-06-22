package com.edulog.driverportal.login.domain.inreractor.utils;

import com.edulog.driverportal.login.domain.utils.ErrorValidationUtil;
import com.edulog.driverportal.login.models.ErrorValidation;

import org.junit.Before;
import org.junit.Test;

import static junit.framework.Assert.assertFalse;
import static junit.framework.Assert.assertNotNull;
import static junit.framework.Assert.assertTrue;


public class ErrorValidateUtilsTest {
    private ErrorValidationUtil errorValidationUtil;
    private ErrorValidation errorValidation;

    @Before
    public void init(){
        errorValidation = new ErrorValidation();
        errorValidationUtil = new ErrorValidationUtil(errorValidation);
    }
    @Test
    public void validateLogin_inputValidLoginInformation_returnErrorValidateObjectWithIsValidTrue(){
        //Action
        errorValidation = errorValidationUtil.validateLogin("1", "2", "123456789");
        //Assert
        assertNotNull(errorValidationUtil);
        assertTrue(errorValidation.isValid());
    }
    @Test
    public void validateLogin_inputInValidLoginInformation_returnErrorValidateObjectWithIsValidFalse(){
        //Action
        errorValidation = errorValidationUtil.validateLogin("1", "2", "123");
        //Assert
        assertNotNull(errorValidationUtil);
        assertFalse(errorValidation.isValid());
    }
}
