package com.edulog.driverportal.settings.changepassword.domain.interactor;

import com.edulog.driverportal.RxImmediateSchedulerRule;
import com.edulog.driverportal.settings.changepassword.domain.service.AuthService;

import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import io.reactivex.Observable;
import io.reactivex.Scheduler;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.observers.TestObserver;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class ChangePasswordUseCaseTest {
    @ClassRule
    public static final RxImmediateSchedulerRule schedulers = new RxImmediateSchedulerRule();

    @Mock
    private AuthService mockAuthService;

    private ChangePasswordUseCase changePasswordUseCase;

    @Before
    public void setUp() {
        changePasswordUseCase = new ChangePasswordUseCase(mockAuthService);
    }

    @Test
    public void execute_validCredentials_success() {
        when(mockAuthService.changePassword(anyString(), anyString(), anyString())).thenReturn(Observable.just(true));

        TestObserver<Boolean> observer = new TestObserver<>();
        changePasswordUseCase.execute(observer, ChangePasswordUseCase.buildParams("driver_id", "old_pass", "new_pass"));

        observer.assertNoErrors();

        observer.assertResult(true);
    }

    @Test
    public void execute_invalidCredentials_failure() {
        when(mockAuthService.changePassword(anyString(), anyString(), anyString())).thenReturn(Observable.just(false));

        TestObserver<Boolean> observer = new TestObserver<>();
        changePasswordUseCase.execute(observer, ChangePasswordUseCase.buildParams("driver_id", "old_pass", "new_pass"));

        observer.assertError(Throwable.class);
    }

    @Test
    public void execute_validCredentialsButNewPasswordNotValid_failure() {
        when(mockAuthService.changePassword(anyString(), anyString(), anyString())).thenReturn(Observable.just(true));

        TestObserver<Boolean> observer = new TestObserver<>();
        changePasswordUseCase.execute(observer, ChangePasswordUseCase.buildParams("driver_id", "old_pass", ""));

        observer.assertError(Throwable.class);
    }
}
