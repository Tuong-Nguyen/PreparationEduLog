package com.edulog.driverportal.data.service;

import com.edulog.driverportal.data.net.RetrofitServiceGenerator;
import com.edulog.driverportal.domain.service.AuthService;
import com.edulog.driverportal.data.net.ChangePasswordService;

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
