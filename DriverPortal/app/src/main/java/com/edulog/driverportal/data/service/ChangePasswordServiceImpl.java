package com.edulog.driverportal.data.service;

import com.edulog.driverportal.domain.service.ChangePasswordService;

import io.reactivex.Observable;

public class ChangePasswordServiceImpl implements ChangePasswordService {
    @Override
    public Observable<Boolean> changePassword(String driverId, String oldPassword, String newPassword) {
        return Observable.just(true);
    }
}
