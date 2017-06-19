package com.edulog.driverportal.common.base;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.Scheduler;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.observers.DisposableObserver;

public abstract class UseCase<T, Params> {
    private Scheduler postExecutionScheduler;

    public UseCase(Scheduler postExecutionScheduler) {
        this.postExecutionScheduler = postExecutionScheduler;
    }

    protected abstract Observable<T> buildUseCaseObservable(Params params);

    public void execute(Observer<T> observer, Params params) {
        Observable<T> observable = buildUseCaseObservable(params);

        observable
                .observeOn(postExecutionScheduler)
                .subscribeWith(observer);
    }
}
