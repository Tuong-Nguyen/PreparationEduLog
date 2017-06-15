package edu.h2.layoutdemo.login.usecase;

import edu.h2.layoutdemo.login.domain.AuthenticateServiceImplement;
import io.reactivex.Observable;
import io.reactivex.observers.DisposableObserver;

/**
 * Created by ntmhanh on 6/12/2017.
 */

public class DriverAuthenticateUseCase {

    public AuthenticateServiceImplement mAuthenticateServiceImplement;

    public DriverAuthenticateUseCase(AuthenticateServiceImplement authenticateServiceImplement) {
        this.mAuthenticateServiceImplement = authenticateServiceImplement;
    }


    public Observable<Boolean> buildUseCaseObservable(Params params) {
        String driverId = params.driverId;
        String password = params.password;
        return mAuthenticateServiceImplement.authenticate(driverId, password);
    }

    public void execute(DisposableObserver<Boolean> observer, Params params) {
        final Observable<Boolean> observable = this.buildUseCaseObservable(params);
        observable.subscribeWith(observer).dispose();
    }


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
