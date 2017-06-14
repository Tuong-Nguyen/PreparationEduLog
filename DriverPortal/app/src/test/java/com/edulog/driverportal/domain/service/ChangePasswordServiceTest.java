package com.edulog.driverportal.domain.service;

import com.edulog.driverportal.data.net.RetrofitServiceGenerator;

import org.junit.Before;
import org.junit.Test;

import io.reactivex.Observable;
import okhttp3.ResponseBody;

public class ChangePasswordServiceTest {
    private ChangePasswordService service;

    @Before
    public void setup() {
        service = RetrofitServiceGenerator.generate(ChangePasswordService.class);
    }

    @Test
    public void changePassword() {
        String driverId = "driver0";
        String oldPassword = "driver01";
        String newPassword = "1234";
        Observable<ResponseBody> observable = service.changePassword(driverId, oldPassword, newPassword);
        ResponseBody body = observable.blockingFirst();
        int a = 12;
    }
}
