package com.edulog.driverportal.login.domain.interactors;

/**
 * Created by ntmhanh on 6/22/2017.
 */

import com.edulog.driverportal.common.domain.UseCase;
import com.edulog.driverportal.common.preference.Session;
import com.edulog.driverportal.common.validation.exception.ValidationException;
import com.edulog.driverportal.common.validation.model.ValidationResult;
import com.edulog.driverportal.login.domain.services.AuthenticateService;
import com.edulog.driverportal.login.domain.services.EventService;
import com.edulog.driverportal.login.models.Events;

import java.util.Arrays;
import java.util.List;

import io.reactivex.Observable;

import static com.edulog.driverportal.login.domain.interactors.LoginValidator.validateAll;
import static com.edulog.driverportal.login.domain.interactors.LoginValidator.validateBusId;
import static com.edulog.driverportal.login.domain.interactors.LoginValidator.validateDriverId;
import static com.edulog.driverportal.login.domain.interactors.LoginValidator.validatePassword;

/**
 * DriverAuthenticateUseCase, which receive observable from login result
 */

public class LoginUseCase extends UseCase<Boolean, LoginUseCase.Params> {

    private AuthenticateService authenticateService;
    private EventService eventService;
    private Session session;

    public LoginUseCase(AuthenticateService authenticateService, EventService eventService, Session session) {
        this.authenticateService = authenticateService;
        this.eventService = eventService;
        this.session = session;
    }

    /**
     * Build driver observable
     * @param params
     * @return
     */

    @Override
    public Observable<Boolean> buildUseCaseObservable(Params params) {
        return validate(params)
                .zipWith(loginObservable(params),
                        ((validationResults, isSuccess) -> isSuccess));
    }

    /**
     *
     * @param params
     * @return
     */
    public Observable<List<ValidationResult>> validate(Params params) {
        ValidationResult busIdValidate = validateBusId(params.busId);
        ValidationResult driverIdValidate = validateDriverId(params.driverId);
        ValidationResult passwordValidate = validatePassword(params.password);
        ValidationResult allResult = validateAll(busIdValidate, driverIdValidate, passwordValidate);

        List<ValidationResult> validationResults = Arrays.asList(busIdValidate, driverIdValidate, passwordValidate);
        return Observable.just(validationResults)
                .doOnNext(results -> {
                    if (!allResult.isValid())
                        throw new ValidationException(results);
                });
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
                    rememberDriverId(isRemember, params);
                    if (!isSuccess) throw new RuntimeException("Login failed.");
                })
                .concatWith(sendEventsObservable(Events.LOG_IN));
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
            session.putDriverId(params.driverId);
        } else {
            session.removeDriverId();
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
