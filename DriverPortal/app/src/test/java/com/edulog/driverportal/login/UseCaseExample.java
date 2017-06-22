package com.edulog.driverportal.login;

import org.junit.Test;

import io.reactivex.Observable;
import io.reactivex.observers.TestObserver;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by ntmhanh on 6/22/2017.
 */

public class UseCaseExample {
    public Observable<Boolean> isLogin(boolean b){

        return Observable.just(b).doOnNext(isSuccess -> {
            if (!isSuccess) throw new RuntimeException("Login failed ");
        });
    }
    public Observable<Boolean> isValidate(boolean validate, boolean login, boolean send){

        return Observable.just(validate).doOnNext(isValidate -> {
            if (!isValidate) throw new RuntimeException(" Validate failed");
                 }).concatWith(isLogin(login)
                .concatWith(isSent(send)));
    }
    public Observable<Boolean> isSent(boolean b){

        return Observable.just(b).doOnNext(isSent -> {
            if (!isSent) throw new RuntimeException("Sending event failed ");
        });
    }
    public void execute(TestObserver testObserver, boolean validate, boolean login, boolean send) {
        Observable observable = isValidate(validate, login, send);
        observable
                .observeOn(Schedulers.trampoline())
                .subscribeWith(testObserver);
    }

    @Test
    public void execute_onComplete(){
        TestObserver testObserver = new TestObserver();
        execute(testObserver, true, true, true);
        testObserver.assertComplete();
    }
    @Test
    public void execute_onError(){
        TestObserver testObserver = new TestObserver();
        execute(testObserver, true, false, true);
        testObserver.assertError(Throwable.class);
    }

}
