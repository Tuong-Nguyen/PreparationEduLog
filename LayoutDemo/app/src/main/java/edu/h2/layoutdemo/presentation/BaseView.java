package edu.h2.layoutdemo.presentation;

public interface BaseView {
    void showProgress();

    void hideProgress();

    void showError(String message);
}
