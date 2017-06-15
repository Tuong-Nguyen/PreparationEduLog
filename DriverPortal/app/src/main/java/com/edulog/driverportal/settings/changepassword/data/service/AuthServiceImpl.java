package com.edulog.driverportal.settings.changepassword.data.service;

import com.edulog.driverportal.settings.changepassword.data.net.ChangePasswordService;
import com.edulog.driverportal.common.net.RetrofitServiceGenerator;
import com.edulog.driverportal.settings.changepassword.domain.service.AuthService;

import io.reactivex.Observable;
import io.reactivex.schedulers.Schedulers;
import okhttp3.ResponseBody;
import retrofit2.Response;

public class AuthServiceImpl implements AuthService {
    @Override
    public Observable<Response<ResponseBody>> changePassword(String driverId, String oldPassword, String newPassword) {
        ChangePasswordService service = RetrofitServiceGenerator.generate(ChangePasswordService.class);
        Observable<Response<ResponseBody>> observable = service.changePassword(driverId, oldPassword, newPassword);
        return observable.subscribeOn(Schedulers.io());
    }
}
