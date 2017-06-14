package com.edulog.driverportal.domain.interactor;

import com.edulog.driverportal.domain.service.ChangePasswordService;

import org.junit.Test;

import io.reactivex.Observable;
import io.reactivex.observers.DisposableObserver;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class ChangePasswordUseCaseTest {
    @Test
    public void execute_changePasswordShouldBeCalled() {
        String driverId = "1234";
        String oldPassword = "old_pass";
        String newPassword = "new_pass";

    }
}
