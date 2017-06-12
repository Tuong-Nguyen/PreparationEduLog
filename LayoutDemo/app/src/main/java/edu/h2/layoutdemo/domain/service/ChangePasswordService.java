package edu.h2.layoutdemo.domain.service;

import io.reactivex.Observable;

public interface ChangePasswordService {
    Observable<Boolean> requestChangePassword(String driverId, String oldPassword, String newPassword, String confirmNewPassword);
}
