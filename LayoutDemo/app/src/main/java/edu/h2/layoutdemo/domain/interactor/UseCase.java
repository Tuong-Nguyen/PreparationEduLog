package edu.h2.layoutdemo.domain.interactor;

import io.reactivex.observers.DisposableObserver;

public abstract class UseCase<T, Params> {
    public abstract void execute(DisposableObserver<T> observer, Params params);
}
