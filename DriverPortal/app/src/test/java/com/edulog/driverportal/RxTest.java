package com.edulog.driverportal;

import org.junit.Test;

import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.functions.BiFunction;
import io.reactivex.observers.TestObserver;

public class RxTest {
    @Test
    public void test() {
        TestObserver<Long> observer = new TestObserver<>();
        Observable<String> just = Observable.just("hehe");
        Observable
                .interval(1, TimeUnit.SECONDS)
                .doOnNext(value -> {
                    System.out.println("DoonNext: " + value);
                })
                .zipWith(just, new BiFunction<Long, String, Long>() {
                    @Override
                    public Long apply(Long aLong, String s) throws Exception {
                        System.out.println("apply: " + aLong + " " + s);
                        return aLong;
                    }
                })
                .subscribeWith(observer);
        observer.awaitTerminalEvent();
    }
}
