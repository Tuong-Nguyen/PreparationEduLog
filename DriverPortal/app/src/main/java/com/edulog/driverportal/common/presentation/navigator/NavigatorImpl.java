package com.edulog.driverportal.common.presentation.navigator;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;

public class NavigatorImpl implements Navigator {
    private FragmentManager fragmentManager;
    private int containerId;

    public NavigatorImpl(FragmentManager fragmentManager, int containerId) {
        this.fragmentManager = fragmentManager;
        this.containerId = containerId;
    }

    @Override
    public void moveToFragment(Fragment fragment) {
        fragmentManager.beginTransaction()
                .replace(containerId, fragment)
                .addToBackStack(null)
                .commit();
    }

    @Override
    public void openAsRoot(Fragment fragment) {
        fragmentManager.beginTransaction()
                .add(containerId, fragment)
                .commit();
    }
}