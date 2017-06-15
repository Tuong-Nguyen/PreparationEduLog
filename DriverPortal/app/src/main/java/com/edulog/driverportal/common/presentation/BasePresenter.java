package com.edulog.driverportal.common.presentation;

public interface BasePresenter<V extends BaseView> {
    void attach(V view);

    void detach();

    void onError(String message);
}
