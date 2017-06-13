package com.edulog.driverportal.domain.service;

import io.reactivex.Observable;
import okhttp3.ResponseBody;
import retrofit2.Response;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface ChangePasswordService {
    @FormUrlEncoded
    @POST("/api/change_password")
    Observable<ResponseBody> changePassword(@Field("driver_id") String driverId, @Field("old_password") String oldPassword, @Field("new_password") String newPassword);
}
