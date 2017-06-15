package com.edulog.driverportal.settings.changepassword.domain.service;

import io.reactivex.Observable;

public interface AuthService {
    Observable<Boolean> changePassword(String driverId, String oldPassword, String newPassword);
}
