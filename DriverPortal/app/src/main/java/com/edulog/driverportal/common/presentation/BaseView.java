package com.edulog.driverportal.common.presentation;

public interface BaseView {
    void showProgress();

    void hideProgress();

    void showError(String message);
}
