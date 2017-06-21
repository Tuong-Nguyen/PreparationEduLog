package com.edulog.driverportal.common.domain;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.Scheduler;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;

public abstract class UseCase<T, Params> {
    private Scheduler postExecutionScheduler;

    public UseCase() {
        this.postExecutionScheduler = AndroidSchedulers.mainThread();
    }

    protected abstract Observable<T> buildUseCaseObservable(Params params);

    public void execute(Observer<T> observer, Params params) {
        Observable<T> observable = buildUseCaseObservable(params);

        observable
                .observeOn(postExecutionScheduler)
                .subscribe(observer);
    }

    public void setPostExecutionScheduler(Scheduler scheduler) {
        this.postExecutionScheduler = scheduler;
    }
}
