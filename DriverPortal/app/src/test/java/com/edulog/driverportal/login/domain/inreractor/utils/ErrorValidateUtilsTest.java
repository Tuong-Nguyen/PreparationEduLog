package com.edulog.driverportal.login.domain.inreractor.utils;

import com.edulog.driverportal.login.domain.utils.ErrorValidateUtils;
import com.edulog.driverportal.login.models.ErrorValidation;

import org.junit.Before;
import org.junit.Test;

import static junit.framework.Assert.assertFalse;
import static junit.framework.Assert.assertNotNull;
import static junit.framework.Assert.assertTrue;


public class ErrorValidateUtilsTest {
    private ErrorValidateUtils errorValidateUtils;
    private ErrorValidation errorValidation;

    @Before
    public void init(){
        errorValidation = new ErrorValidation();
        errorValidateUtils = new ErrorValidateUtils(errorValidation);
    }
    @Test
    public void validateLogin_inputValidLoginInformation_returnErrorValidateObjectWithIsValidTrue(){
        //Action
        errorValidation = errorValidateUtils.validateLogin("1", "2", "123456789");
        //Assert
        assertNotNull(errorValidation);
        assertTrue(errorValidation.isValid());
    }
    @Test
    public void validateLogin_inputInValidLoginInformation_returnErrorValidateObjectWithIsValidFalse(){
        //Action
        errorValidation = errorValidateUtils.validateLogin("1", "2", "123");
        //Assert
        assertNotNull(errorValidation);
        assertFalse(errorValidation.isValid());
    }
}
