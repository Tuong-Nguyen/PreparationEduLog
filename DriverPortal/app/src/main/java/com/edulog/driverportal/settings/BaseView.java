package com.edulog.driverportal.settings;

public interface BaseView {
    void showProgress();

    void hideProgress();

    void showError(String message);
}
