package com.edulog.driverportal.common.presentation;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;

import com.edulog.driverportal.R;

import static android.support.v4.app.FragmentManager.POP_BACK_STACK_INCLUSIVE;

public abstract class BaseActivity extends AppCompatActivity implements BaseView {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void onStart() {
        super.onStart();

        if (getPresenter() != null) {
            // noinspection unchecked
            getPresenter().attach(this);
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

    public void setTitle(String title) {
        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle(title);
        }
    }

    protected BasePresenter getPresenter() {
        return null;
    }

    /**
     * Pops all the queued fragments
     */
    private void popEveryFragment() {
        // Clear all back stack.
        int backStackCount = getSupportFragmentManager().getBackStackEntryCount();
        for (int i = 0; i < backStackCount; i++) {
            // Get the back stack fragment id.
            int backStackId = getSupportFragmentManager().getBackStackEntryAt(i).getId();
            getSupportFragmentManager().popBackStack(backStackId, POP_BACK_STACK_INCLUSIVE);
        }
    }
}
