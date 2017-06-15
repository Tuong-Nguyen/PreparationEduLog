package com.edulog.driverportal.common.presentation.navigator;

import android.support.v4.app.Fragment;

public interface Navigator {
    void moveToFragment(Fragment fragment);
    void openAsRoot(Fragment fragment);
}
