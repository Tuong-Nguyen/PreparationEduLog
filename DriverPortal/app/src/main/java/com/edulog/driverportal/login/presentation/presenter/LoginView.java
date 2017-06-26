package com.edulog.driverportal.login.presentation.presenter;

import com.edulog.driverportal.common.presentation.BaseView;

/**
 * LoginPresenter access to View(LoginActivity)
 */

public interface LoginView extends BaseView{
    void rememberDriverId(String driverId, boolean isChecked);
}
