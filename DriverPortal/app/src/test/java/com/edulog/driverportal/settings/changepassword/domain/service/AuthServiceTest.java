package com.edulog.driverportal.settings.changepassword.domain.service;

import com.edulog.driverportal.common.util.RetrofitServiceGenerator;
import com.edulog.driverportal.settings.changepassword.data.service.AuthServiceImpl;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import io.reactivex.Observable;

public class AuthServiceTest {
    private AuthService service;

    @Before
    public void setup() {
        service = new AuthServiceImpl();
        RetrofitServiceGenerator.baseUrl = "https://obscure-mesa-13550.herokuapp.com/";
    }

    @Test
    public void changePassword_validDriverInfo_success() {
        String driverId = "driver01";
        String oldPassword = "driver01";
        String newPassword = "1234";
        Observable<Boolean> observable = service.changePassword(driverId, oldPassword, newPassword);

        boolean result = observable.blockingFirst();

        Assert.assertTrue(result);
    }

    @Test
    public void changePassword_invalidCredential_forbidden() {
        String driverId = "invalid_id";
        String oldPassword = "driver01";
        String newPassword = "1234";
        Observable<Boolean> observable = service.changePassword(driverId, oldPassword, newPassword);

        boolean result = observable.blockingFirst();

        Assert.assertFalse(result);
    }
}
