package com.edulog.driverportal.common.base;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;

import com.edulog.driverportal.R;

public abstract class Navigator {
    protected FragmentManager fragmentManager;

    public Navigator(FragmentManager fragmentManager) {
        this.fragmentManager = fragmentManager;
    }

    public void addRootContent() {
        Fragment fragment = fragmentManager.findFragmentById(R.id.contentFrame);
        if (fragment == null) {
            fragment = createRootContent();
            fragmentManager.beginTransaction()
                    .add(R.id.contentFrame, fragment)
                    .commit();
        }
    }

    protected abstract Fragment createRootContent();
}
