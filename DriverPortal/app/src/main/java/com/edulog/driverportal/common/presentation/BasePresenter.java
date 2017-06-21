package com.edulog.driverportal.common.presentation;

import io.reactivex.disposables.Disposable;

public abstract class BasePresenter<V extends BaseView> {
    private CompositeDisposableObserver disposables;

    public BasePresenter() {
        disposables = new CompositeDisposableObserver();
    }

    public abstract void attach(V view);

    public void detach() {
        disposables.dispose();
    }

    protected void addDisposable(Disposable disposable) {
        disposables.add(disposable);
    }
}
