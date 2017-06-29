package com.edulog.driverportal.common.base;

public interface BaseView {
    void showProgress();

    void hideProgress();

    void showError(String message);
}
