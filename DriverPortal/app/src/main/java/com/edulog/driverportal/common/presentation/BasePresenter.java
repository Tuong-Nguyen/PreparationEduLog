package com.edulog.driverportal.common.presentation;

public interface BasePresenter<V extends BaseView> {
    void attach(V view);

    void detach();

    // TODO: This method is not used. Please confirm and remove if Yes
    void onError(String message);
}
