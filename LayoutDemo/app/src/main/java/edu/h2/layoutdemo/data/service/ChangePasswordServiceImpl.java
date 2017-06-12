package edu.h2.layoutdemo.data.service;

import edu.h2.layoutdemo.domain.service.ChangePasswordService;
import io.reactivex.Observable;

public class ChangePasswordServiceImpl implements ChangePasswordService {
    @Override
    public Observable<Boolean> requestChangePassword(String driverId, String oldPassword, String newPassword, String confirmNewPassword) {
        return Observable.just(true);
    }
}
