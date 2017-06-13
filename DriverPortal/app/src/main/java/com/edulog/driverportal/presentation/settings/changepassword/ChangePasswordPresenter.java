package com.edulog.driverportal.presentation.settings.changepassword;

import com.edulog.driverportal.presentation.BasePresenter;

import io.reactivex.Observable;

public interface ChangePasswordPresenter extends BasePresenter<ChangePasswordView> {
    void changePassword(String driverId, String oldPassword, String newPassword);
    void validateUserInput(Observable<CharSequence> driverIdObservable,
                           Observable<CharSequence> oldPasswordObservable,
                           Observable<CharSequence> newPasswordObservable,
                           Observable<CharSequence> confirmNewPasswordObservable);
}
