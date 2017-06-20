package com.edulog.driverportal.login.domain.interactors;

import com.edulog.driverportal.common.base.UseCase;
import com.edulog.driverportal.login.domain.services.AuthenticateService;
import com.edulog.driverportal.login.models.LoginValidation;

import io.reactivex.Observable;
import io.reactivex.Scheduler;

/**
 * Created by ntmhanh on 6/20/2017.
 */

public class LoginValidateUseCase extends UseCase<LoginValidation, DriverAuthenticateUseCase.Params> {

    private AuthenticateService authenticateService;

    public LoginValidateUseCase(Scheduler postExecutionScheduler, AuthenticateService authenticateService) {
        super(postExecutionScheduler);
        this.authenticateService = authenticateService;
    }

    @Override
    protected Observable<LoginValidation> buildUseCaseObservable(DriverAuthenticateUseCase.Params params) {
        String busId = params.busId;
        String driverId = params.driverId;
        String password = params.password;
        return authenticateService.validate(busId, driverId, password);
    }

}
