package com.edulog.driverportal.settings.base;

public interface BasePresenter<V extends BaseView> {
    void attach(V view);

    void detach();

    void onError(String message);
}
