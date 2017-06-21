package com.edulog.driverportal.login;

import com.edulog.driverportal.login.domain.interactors.DriverAuthenticateUseCase;
import com.edulog.driverportal.login.domain.interactors.LoginValidateUseCase;
import com.edulog.driverportal.login.domain.services.AuthenticateServiceImplement;
import com.edulog.driverportal.login.domain.utils.LoginValidateUtils;
import com.edulog.driverportal.login.models.ErrorValidation;

import org.junit.Before;
import org.junit.Test;

import io.reactivex.observers.TestObserver;
import io.reactivex.schedulers.Schedulers;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertNotNull;

/**
 * Created by ntmhanh on 6/21/2017.
 */

public class LoginValidateUseCaseTest {

    private AuthenticateServiceImplement authenticateServiceImplement;
    private LoginValidateUseCase loginValidateUseCase;
    private ErrorValidation errorValidation;
    private LoginValidateUtils loginValidateUtils;

    @Before
    public void init(){
        errorValidation = new ErrorValidation();
        loginValidateUtils = new LoginValidateUtils(errorValidation);
        authenticateServiceImplement = new AuthenticateServiceImplement(loginValidateUtils);
        loginValidateUseCase = new LoginValidateUseCase(Schedulers.trampoline(), authenticateServiceImplement);
    }

    @Test
    public void execute_validateLoginInformation_returnIsValidTrue(){
        //Assert
        DriverAuthenticateUseCase.Params params = new DriverAuthenticateUseCase.Params("1", "2", "123456789");
        TestObserver<ErrorValidation> testObserver = new TestObserver<>();
        //Action
        loginValidateUseCase.execute(testObserver, params);
        //Assert
        assertNotNull(errorValidation);
        testObserver.assertResult(errorValidation);
        assertEquals(true, errorValidation.isValid());
    }
    @Test
    public void execute_inValidateLoginInformationPasswordLessThanEightCharacter_returnIsValidFalse(){
        //Assert
        DriverAuthenticateUseCase.Params params = new DriverAuthenticateUseCase.Params("1", "2", "123");
        TestObserver<ErrorValidation> testObserver = new TestObserver<>();
        //Action
        loginValidateUseCase.execute(testObserver, params);
        //Assert
        assertNotNull(errorValidation);
        testObserver.assertResult(errorValidation);
        assertEquals(false, errorValidation.isValid());
    }
}
