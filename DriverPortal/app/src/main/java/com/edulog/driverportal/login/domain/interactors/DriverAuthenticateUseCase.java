package com.edulog.driverportal.login.domain.interactors;

import com.edulog.driverportal.common.base.UseCase;
import com.edulog.driverportal.login.domain.services.AuthenticateService;
import com.edulog.driverportal.login.domain.utils.ErrorValidateUtils;
import com.edulog.driverportal.login.models.ErrorValidation;

import io.reactivex.Observable;
import io.reactivex.Scheduler;


/**
 * DriverAuthenticateUseCase, which receive observable from login result
 */

public class DriverAuthenticateUseCase extends UseCase<Boolean, DriverAuthenticateUseCase.Params> {

    private AuthenticateService authenticateService;
    private ErrorValidateUtils errorValidateUtils;

    public DriverAuthenticateUseCase(Scheduler postExecutionScheduler, AuthenticateService authenticateService, ErrorValidateUtils errorValidateUtils) {
        super(postExecutionScheduler);
        this.authenticateService = authenticateService;
        this.errorValidateUtils = errorValidateUtils;
    }

    /**
     * Build driver observable
     * @param params
     * @return
     */
    public Observable<Boolean> buildUseCaseObservable(Params params) {
        String busId = params.busId;
        String driverId = params.driverId;
        String password = params.password;
        ErrorValidation errorValidation = errorValidateUtils.validateLogin(busId, driverId, password);
        return Observable.just(errorValidation.isValid())
                .doOnNext(isValid -> {
                    if (!isValid) throw new RuntimeException("Validation was failed ");
                }).zipWith(isLogin(params),(isValid, isSuccess) -> {
                    return isSuccess;
                });
    }

    /**
     * Build Login Observable
     * @param params
     * @return
     */
    public Observable<Boolean> isLogin(Params params){
        String driverId = params.driverId;
        String password = params.password;
        return authenticateService.login(driverId, password)
                .doOnNext(isSuccess -> {
                    if (!isSuccess) throw new RuntimeException("Login failed.");
                });
    }

    /**
     * Define Params to inject for use case
     */
    public static class Params {
        public String busId;
        public String driverId;
        public String password;

        public Params(String busId, String driverId, String password) {
            this.busId = busId;
            this.driverId = driverId;
            this.password = password;
        }
    }
}
