package edu.h2.layoutdemo.presentation;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

public abstract class BaseActivity extends AppCompatActivity {
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

    protected BasePresenter getPresenter() {
        return null;
    }

    protected BaseView getView() {
        return null;
    }
}
