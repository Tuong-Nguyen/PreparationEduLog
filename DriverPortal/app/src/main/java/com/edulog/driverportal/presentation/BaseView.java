package com.edulog.driverportal.presentation;

public interface BaseView {
    void showProgress();

    void hideProgress();

    void showError(String message);
}
