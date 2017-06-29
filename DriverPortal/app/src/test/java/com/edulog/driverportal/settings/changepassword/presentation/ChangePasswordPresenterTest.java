package com.edulog.driverportal.settings.changepassword.presentation;

import com.edulog.driverportal.settings.changepassword.domain.ChangePasswordUseCase;
import com.edulog.driverportal.settings.changepassword.domain.ValidationUseCase;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import io.reactivex.observers.DisposableObserver;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class)
public class ChangePasswordPresenterTest {
    private ChangePasswordContract.ChangePasswordPresenter changePasswordPresenter;

    @Mock
    private ChangePasswordUseCase mockChangePasswordUseCase;

    @Mock
    private ValidationUseCase mockValidationUseCase;

    @Mock
    private ChangePasswordContract.ChangePasswordView mockChangePasswordView;

    @Before
    public void setUp() {
        changePasswordPresenter = new ChangePasswordPresenterImpl(mockChangePasswordUseCase, mockValidationUseCase);
        changePasswordPresenter.attach(mockChangePasswordView);
    }

    @Test
    public void changePassword_useCaseExecuted() {
        changePasswordPresenter.changePassword("1", "2", "3");

        verify(mockChangePasswordUseCase).execute(any(DisposableObserver.class), any(ChangePasswordUseCase.Params.class));
    }

    @Test
    public void changePassword_showProgress() {
        changePasswordPresenter.changePassword("1", "2", "3");

        verify(mockChangePasswordView).showProgress();
    }

    @Test
    public void validateUserInputs_useCaseExecuted() {
        changePasswordPresenter.validateUserInputs("1", "2", "3", "3");

        verify(mockValidationUseCase).execute(any(DisposableObserver.class), any(ValidationUseCase.Params.class));
    }
}
