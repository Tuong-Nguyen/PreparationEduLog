package com.edulog.driverportal.login;

import org.junit.Test;

import io.reactivex.Observable;
import io.reactivex.observers.TestObserver;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by ntmhanh on 6/22/2017.
 */
// TODO: Rename to ReactiveXTest
public class UseCaseExample {
    // TODO: 3 methods isLogin - isValidate - isSent are very similar except the Error message. Refactor to use a single method
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

    // TODO: rename to 'concat3observables_allsucceeds_returnComplete'
    @Test
    public void execute_onComplete(){
        TestObserver testObserver = new TestObserver();
        execute(testObserver, true, true, true);
        testObserver.assertComplete();
    }
    // TODO: rename to 'concat3observables_the2ndgiveserror_returnError'
    @Test
    public void execute_onError(){
        TestObserver testObserver = new TestObserver();
        execute(testObserver, true, false, true);
        // TODO: Assert the error message to ensure that the correct error is received
        testObserver.assertError(Throwable.class);
    }

    // TODO: Add tests when the first observable fail and the third observable fail

}
