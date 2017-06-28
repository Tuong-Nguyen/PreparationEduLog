package com.edulog.driverportal;

import org.junit.Test;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.functions.Function;
import io.reactivex.observers.TestObserver;
import io.reactivex.schedulers.Schedulers;

public class RxThreadTest {
    @Test
    public void test() throws Exception {
        TestObserver<Integer> observer = new TestObserver<>();

        Observable.just(12)
                .doOnNext(integer -> System.out.println(Thread.currentThread().getName()))
                .map(num -> {
                    System.out.println(Thread.currentThread().getName());
                    return num + 2;
                })
                .subscribeOn(Schedulers.computation())
                .subscribe(observer);

        observer.awaitTerminalEvent();
    }
}
