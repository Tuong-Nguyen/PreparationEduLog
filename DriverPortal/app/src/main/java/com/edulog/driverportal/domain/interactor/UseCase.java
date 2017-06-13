package com.edulog.driverportal.domain.interactor;

import io.reactivex.Observable;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.observers.DisposableObserver;

public abstract class UseCase<T, Params> {
    private CompositeDisposable disposables;

    public UseCase() {
        disposables = new CompositeDisposable();
    }

    public abstract Observable<T> buildUseCaseObservable(Params params);

    public void execute(DisposableObserver<T> observer, Params params) {
        Observable<T> observable = buildUseCaseObservable(params);
        Disposable disposable = observable.subscribeWith(observer);
        disposables.add(disposable);
    }

    public void dispose() {
        disposables.dispose();
    }
}
