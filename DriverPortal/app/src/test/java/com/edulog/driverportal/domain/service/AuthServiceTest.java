package com.edulog.driverportal.domain.service;

import com.edulog.driverportal.data.net.ChangePasswordService;
import com.edulog.driverportal.data.net.RetrofitServiceGenerator;
import com.edulog.driverportal.data.service.AuthServiceImpl;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import io.reactivex.Observable;
import okhttp3.ResponseBody;
import retrofit2.Response;

public class AuthServiceTest {
    private AuthService service;

    @Before
    public void setup() {
        service = new AuthServiceImpl();
    }

    @Test
    public void changePassword_validDriverInfo_success() {
        String driverId = "driver01";
        String oldPassword = "driver01";
        String newPassword = "1234";
        Observable<Response<ResponseBody>> observable = service.changePassword(driverId, oldPassword, newPassword);

        Response<ResponseBody> response = observable.blockingFirst();

        Assert.assertEquals(200, response.code());
    }

    @Test
    public void changePassword_invalidCredential_forbidden() {
        String driverId = "invalid_id";
        String oldPassword = "driver01";
        String newPassword = "1234";
        Observable<Response<ResponseBody>> observable = service.changePassword(driverId, oldPassword, newPassword);

        Response<ResponseBody> response = observable.blockingFirst();

        Assert.assertEquals(403, response.code());
    }
}
