package edu.h2.layoutdemo.login.usecase;

import edu.h2.layoutdemo.login.Driver;
import edu.h2.layoutdemo.login.repositories.DriverRepository;
import io.reactivex.Observable;
import io.reactivex.observers.DisposableObserver;

/**
 * Created by ntmhanh on 6/12/2017.
 */

public class DriverAuthenticateUseCase {

    public DriverRepository mDriverRepository;

    public DriverAuthenticateUseCase(DriverRepository driverRepository) {
        this.mDriverRepository = driverRepository;
    }


    public Observable<Driver> buildUseCaseObservable(Params params) {
        String driverId = params.driverId;
        return mDriverRepository.getDriverById(driverId);
    }

    public void execute(DisposableObserver<Driver> observer, Params params) {
        final Observable<Driver> observable = this.buildUseCaseObservable(params);
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
