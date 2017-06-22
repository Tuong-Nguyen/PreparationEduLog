package com.edulog.driverportal.login.domain.interactors;

/**
 * Created by ntmhanh on 6/22/2017.
 */

import com.edulog.driverportal.common.base.UseCase;
import com.edulog.driverportal.login.domain.services.AuthenticateService;
import com.edulog.driverportal.login.domain.services.DriverPreferences;
import com.edulog.driverportal.login.domain.services.EventService;
import com.edulog.driverportal.login.domain.utils.ErrorValidationUtil;
import com.edulog.driverportal.login.models.Events;

import io.reactivex.Observable;
import io.reactivex.Scheduler;


/**
 * DriverAuthenticateUseCase, which receive observable from login result
 */

public class LoginUseCase extends UseCase<Boolean, LoginUseCase.Params> {

    private AuthenticateService authenticateService;
    private EventService eventService;
    private ErrorValidationUtil errorValidationUtil;
    private DriverPreferences driverPreferences;

    public LoginUseCase(Scheduler postExecutionScheduler, AuthenticateService authenticateService, ErrorValidationUtil errorValidationUtil, EventService eventService, DriverPreferences driverPreferences) {
        super(postExecutionScheduler);
        this.authenticateService = authenticateService;
        this.errorValidationUtil = errorValidationUtil;
        this.eventService = eventService;
        this.driverPreferences = driverPreferences;
    }

    /**
     * Build driver observable
     * @param params
     * @return
     */
    @Override
    public Observable<Boolean> buildUseCaseObservable(Params params) {
        String busId = params.busId;
        String driverId = params.driverId;
        String password = params.password;
        boolean isRemember = params.rememberDriverId;
        com.edulog.driverportal.login.models.ErrorValidation errorValidation = this.errorValidationUtil.validateLogin(busId, driverId, password);
        return Observable.just(errorValidation.isValid())
                .doOnNext(isValid -> {
                    rememberDriverId(isRemember, params);
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
        boolean isRemember = params.rememberDriverId;
        return authenticateService.login(driverId, password)
                .doOnNext(isSuccess -> {
                   // rememberDriverId(isRemember, params);
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
                .onErrorResumeNext(Observable.empty());
    }

    public void rememberDriverId(boolean isRememberChecked, Params params){
        if (isRememberChecked) {
            driverPreferences.setValuePreferences(params.driverId);
        } else {
            driverPreferences.removeValueItem();
        }
    }


    /**
     * Define Params to inject for use case
     */
    public static class Params {
        public String busId;
        public String driverId;
        public String password;
        public boolean rememberDriverId;

        public Params(String busId, String driverId, String password, boolean rememberDriverId) {
            this.busId = busId;
            this.driverId = driverId;
            this.password = password;
            this.rememberDriverId = rememberDriverId;
        }
    }
}
