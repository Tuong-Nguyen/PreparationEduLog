package com.edulog.driverportal.settings.changepassword.domain.interactor;

import io.reactivex.Observable;
import io.reactivex.Scheduler;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.observers.DisposableObserver;

public abstract class UseCase<T, Params> {
    private CompositeDisposable disposables;
    private Scheduler postExecutionScheduler;

    public UseCase(Scheduler postExecutionScheduler) {
        this.postExecutionScheduler = postExecutionScheduler;
        disposables = new CompositeDisposable();
    }

    public abstract Observable<T> buildUseCaseObservable(Params params);

    public void execute(DisposableObserver<T> observer, Params params) {
        Observable<T> observable = buildUseCaseObservable(params);

        Disposable disposable = observable
                .observeOn(postExecutionScheduler)
                .subscribeWith(observer);

        disposables.add(disposable);
    }

    public void dispose() {
        if (!disposables.isDisposed()) {
            disposables.dispose();
        }
    }
}
