package com.edulog.driverportal.common.base;

import android.support.v4.app.Fragment;

public abstract class BaseFragment extends Fragment implements BaseView {
    @Override
    public void onStart() {
        super.onStart();

        if (getPresenter() != null && getViewLayer() != null) {
            // noinspection unchecked
            getPresenter().attach(getViewLayer());
        }
    }

    @Override
    public void onStop() {
        super.onStop();

        if (getPresenter() != null) {
            getPresenter().detach();
        }
    }

    @Override
    public void showProgress() {

    }

    @Override
    public void hideProgress() {

    }

    @Override
    public void showError(String message) {

    }

    protected BasePresenter getPresenter() {
        return null;
    }

    protected BaseView getViewLayer() {
        return null;
    }
}
