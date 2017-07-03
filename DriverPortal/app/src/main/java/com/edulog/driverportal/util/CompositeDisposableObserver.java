package com.edulog.driverportal.util;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.disposables.Disposable;

public class CompositeDisposableObserver {
    private List<Disposable> disposables;

    public CompositeDisposableObserver() {
        disposables = new ArrayList<>();
    }

    public void add(Disposable disposable) {
        if (disposable != null) {
            disposables.add(disposable);
        }
    }

    public void dispose() {
        for (Disposable disposable : disposables) {
            if (!disposable.isDisposed()) {
                disposable.dispose();
            }
        }
    }
}
