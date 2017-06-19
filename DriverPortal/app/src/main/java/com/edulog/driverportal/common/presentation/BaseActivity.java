package com.edulog.driverportal.common.presentation;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;

import com.edulog.driverportal.R;

public abstract class BaseActivity extends AppCompatActivity implements BaseView {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void onStart() {
        super.onStart();

        if (getPresenter() != null && getView() != null) {
            // noinspection unchecked
            getPresenter().attach(getView());
        }
    }

    @Override
    protected void onStop() {
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

    public void moveToFragment(Fragment fragment) {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.contentFrame, fragment)
                .addToBackStack(null)
                .commit();
    }

    public void openAsRoot(Fragment fragment) {
        getSupportFragmentManager().beginTransaction()
                .add(R.id.contentFrame, fragment)
                .commit();
    }

    protected BasePresenter getPresenter() {
        return null;
    }

    protected BaseView getView() {
        return null;
    }
}
