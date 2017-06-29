package com.edulog.driverportal.settings.changepassword.domain;

import com.edulog.driverportal.RxImmediateSchedulerRule;
import com.edulog.driverportal.settings.changepassword.domain.AuthService;
import com.edulog.driverportal.settings.changepassword.domain.ChangePasswordUseCase;
import com.edulog.driverportal.settings.changepassword.domain.ValidationUseCase;

import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.ArrayList;

import io.reactivex.Observable;
import io.reactivex.observers.TestObserver;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class ChangePasswordUseCaseTest {
    @ClassRule
    public static final RxImmediateSchedulerRule schedulers = new RxImmediateSchedulerRule();

    @Mock
    private AuthService mockAuthService;
    @Mock
    private ValidationUseCase mockValidationUseCase;

    private ChangePasswordUseCase changePasswordUseCase;

    @Before
    public void setUp() {
        changePasswordUseCase = new ChangePasswordUseCase(mockAuthService, mockValidationUseCase);
        when(mockAuthService.changePassword("driver_id", "old_pass", "new_pass")).thenReturn(Observable.just(true));
        when(mockValidationUseCase.buildUseCaseObservable(any(ValidationUseCase.Params.class)))
                .thenReturn(Observable.just(new ArrayList<>()));
    }

    @Test
    public void execute_buildUseCaseObservable() {
        TestObserver<Boolean> observer = new TestObserver<>();

        changePasswordUseCase.execute(observer, ChangePasswordUseCase.buildParams("driver_id", "old_pass", "new_pass"));

        verify(mockValidationUseCase).buildUseCaseObservable(any(ValidationUseCase.Params.class));
    }

    @Test
    public void execute_changePassword() {
        TestObserver<Boolean> observer = new TestObserver<>();

        changePasswordUseCase.execute(observer, ChangePasswordUseCase.buildParams("driver_id", "old_pass", "new_pass"));

        verify(mockAuthService).changePassword("driver_id", "old_pass", "new_pass");
    }

    @Test
    public void execute_validCredentials_success() {
        TestObserver<Boolean> observer = new TestObserver<>();

        changePasswordUseCase.execute(observer, ChangePasswordUseCase.buildParams("driver_id", "old_pass", "new_pass"));

        observer.assertNoErrors();
        observer.assertResult(true);
    }
}
