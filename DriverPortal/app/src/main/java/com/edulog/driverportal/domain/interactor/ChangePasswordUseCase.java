package com.edulog.driverportal.domain.interactor;

import com.edulog.driverportal.domain.executor.PostExecutionThread;
import com.edulog.driverportal.domain.service.ChangePasswordService;

import io.reactivex.Observable;
import io.reactivex.schedulers.Schedulers;
import okhttp3.ResponseBody;
import retrofit2.Response;

public class ChangePasswordUseCase extends UseCase<Response<ResponseBody>, ChangePasswordUseCase.Params> {
    private ChangePasswordService changePasswordService;

    public ChangePasswordUseCase(PostExecutionThread postExecutionThread, ChangePasswordService changePasswordService) {
        super(postExecutionThread);
        this.changePasswordService = changePasswordService;
    }

    public static Params buildParams(String driverId, String oldPassword, String newPassword) {
        Params params = new Params();
        params.driverId = driverId;
        params.oldPassword = oldPassword;
        params.newPassword = newPassword;
        return params;
    }

    @Override
    public Observable<Response<ResponseBody>> buildUseCaseObservable(Params params) {
        String driverId = params.driverId;
        String oldPassword = params.oldPassword;
        String newPassword = params.newPassword;
        return changePasswordService.changePassword(driverId, oldPassword, newPassword)
                .subscribeOn(Schedulers.io());
    }

    public static class Params {
        public String driverId;
        public String oldPassword;
        public String newPassword;
    }
}
