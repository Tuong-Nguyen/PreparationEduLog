package com.edulog.driverportal.login.domain.interactors;

import com.edulog.driverportal.common.base.UseCase;
import com.edulog.driverportal.login.domain.services.AuthenticateService;
import io.reactivex.Observable;
import io.reactivex.Scheduler;


/**
 * DriverAuthenticateUseCase, which receive observable from authenticate result
 */

public class DriverAuthenticateUseCase extends UseCase<Boolean, DriverAuthenticateUseCase.Params> {

    private AuthenticateService authenticateService;

    public DriverAuthenticateUseCase(Scheduler postExecutionScheduler, AuthenticateService authenticateService) {
        super(postExecutionScheduler);
        this.authenticateService = authenticateService;
    }

    /**
     * Build driver usecase observable
     * @param params
     * @return
     */
    public Observable<Boolean> buildUseCaseObservable(Params params) {
        String driverId = params.driverId;
        String password = params.password;
        return authenticateService.authenticate(driverId, password);
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
