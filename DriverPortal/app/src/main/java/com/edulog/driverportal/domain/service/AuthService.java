package com.edulog.driverportal.domain.service;

import io.reactivex.Observable;
import okhttp3.ResponseBody;
import retrofit2.Response;

public interface AuthService {
    Observable<Response<ResponseBody>> changePassword(String driverId, String oldPassword, String newPassword);
}
