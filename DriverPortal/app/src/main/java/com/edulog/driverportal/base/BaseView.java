package com.edulog.driverportal.base;

public interface BaseView {
    void showProgress();

    void hideProgress();

    void showError(String message);
}
