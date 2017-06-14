package com.edulog.driverportal.settings.base;

public interface BaseView {
    void showProgress();

    void hideProgress();

    void showError(String message);
}
