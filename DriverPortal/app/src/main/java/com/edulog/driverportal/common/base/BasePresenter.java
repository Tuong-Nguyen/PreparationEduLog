package com.edulog.driverportal.common.base;

public interface BasePresenter<V extends BaseView> {
    void attach(V view);

    void detach();

    // TODO: what is this method used for? - Remove it if it is not used
    void onError(String message);
}
