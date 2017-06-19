package com.edulog.driverportal.common.base;

public interface BasePresenter<V extends BaseView> {
    void attach(V view);

    void detach();

    void onError(String message);
}
