package com.edulog.driverportal.settings.changepassword.domain;

import io.reactivex.Observable;

public interface AuthService {
    Observable<Boolean> changePassword(String driverId, String oldPassword, String newPassword);
}
