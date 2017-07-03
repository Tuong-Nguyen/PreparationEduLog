package com.edulog.driverportal.base;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.Scheduler;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public abstract class UseCase<T, Params> {
    private Scheduler jobExecutionScheduler;
    private Scheduler postExecutionScheduler;

    public UseCase() {
        this.jobExecutionScheduler = Schedulers.io();
        this.postExecutionScheduler = AndroidSchedulers.mainThread();
    }

    public abstract Observable<T> buildUseCaseObservable(Params params);

    public void execute(Observer<T> observer, Params params) {
        Observable<T> observable = buildUseCaseObservable(params);

        observable
                .subscribeOn(jobExecutionScheduler)
                .observeOn(postExecutionScheduler)
                .subscribe(observer);
    }

    public void setPostExecutionScheduler(Scheduler scheduler) {
        this.postExecutionScheduler = scheduler;
    }

    public void setJobExecutionScheduler(Scheduler scheduler) {
        this.jobExecutionScheduler = scheduler;
    }
}
