package com.edulog.driverportal.settings.changepassword.data;

import com.edulog.driverportal.api.HttpStatusCodes;
import com.edulog.driverportal.base.Config;
import com.edulog.driverportal.api.Api;
import com.edulog.driverportal.settings.changepassword.domain.ChangePasswordService;
import com.edulog.driverportal.settings.changepassword.domain.AuthService;

import io.reactivex.Observable;
import okhttp3.ResponseBody;
import retrofit2.Response;

public class AuthServiceImpl implements AuthService {
    @Override
    public Observable<Boolean> changePassword(String driverId, String oldPassword, String newPassword) {
        ChangePasswordService service = new Api()
                .baseUrl(Config.EDULOG_URL)
                .generate(ChangePasswordService.class);
        Observable<Response<ResponseBody>> observable = service.changePassword(driverId, oldPassword, newPassword);
        return observable
                .map(response -> response.code() == HttpStatusCodes.SUCCESS);
    }
}
