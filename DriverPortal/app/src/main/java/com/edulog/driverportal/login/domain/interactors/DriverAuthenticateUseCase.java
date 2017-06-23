package com.edulog.driverportal.login.domain.interactors;

import com.edulog.driverportal.common.domain.UseCase;
import com.edulog.driverportal.login.domain.services.AuthenticateService;
import com.edulog.driverportal.login.domain.services.EventService;
import com.edulog.driverportal.login.domain.utils.ErrorValidationUtil;
import com.edulog.driverportal.login.models.Events;

import io.reactivex.Observable;
import io.reactivex.Scheduler;


/**
 * DriverAuthenticateUseCase, which receive observable from login result
 */

public class DriverAuthenticateUseCase extends UseCase<Boolean, DriverAuthenticateUseCase.Params> {

    private AuthenticateService authenticateService;
    private EventService eventService;
    private ErrorValidationUtil errorValidationUtil;

    public DriverAuthenticateUseCase(AuthenticateService authenticateService) {
        this.authenticateService = authenticateService;
        this.errorValidationUtil = errorValidationUtil;
        this.eventService = eventService;
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
        com.edulog.driverportal.login.models.ErrorValidation errorValidation = this.errorValidationUtil.validateLogin(busId, driverId, password);
        return Observable.just(errorValidation.isValid())
                .doOnNext(isValid -> {
                    if (!isValid) throw new RuntimeException("Validation was failed ");
                }).concatWith(loginObservable(params))
                .concatWith(sendEventsObservable(Events.LOG_IN));
    }

    /**
     * Build Login Observable
     * @param params
     * @return
     */
    public Observable<Boolean> loginObservable(Params params){
        String driverId = params.driverId;
        String password = params.password;
        return authenticateService.login(driverId, password)
                .doOnNext(isSuccess -> {
                    if (!isSuccess) throw new RuntimeException("Login failed.");
                });
    }

    /**
     * Build Event Observable
     * @param events
     * @return
     */
    public Observable<Boolean> sendEventsObservable(Events events) {
        return eventService.sendEvent(events)
                .doOnNext(isSuccess -> {
                    if (!isSuccess) throw new RuntimeException("Sending event was failed ");
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
