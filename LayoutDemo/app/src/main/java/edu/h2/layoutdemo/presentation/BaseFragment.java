package edu.h2.layoutdemo.presentation;

import android.support.v4.app.Fragment;

public class BaseFragment extends Fragment {
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

    protected BasePresenter getPresenter() {
        return null;
    }

    protected BaseView getViewLayer() {
        return null;
    }
}
