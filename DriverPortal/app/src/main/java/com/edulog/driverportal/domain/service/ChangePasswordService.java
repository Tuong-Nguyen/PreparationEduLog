package com.edulog.driverportal.domain.service;

import io.reactivex.Observable;

public interface ChangePasswordService {
    Observable<Boolean> changePassword(String driverId, String oldPassword, String newPassword);
}
