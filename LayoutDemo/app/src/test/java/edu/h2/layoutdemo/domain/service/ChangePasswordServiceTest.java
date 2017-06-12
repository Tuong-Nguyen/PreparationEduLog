package edu.h2.layoutdemo.domain.service;

import org.junit.Test;

import edu.h2.layoutdemo.domain.interactor.ChangePasswordUseCase;
import edu.h2.layoutdemo.presentation.changepassword.ChangePasswordPresenter;
import edu.h2.layoutdemo.presentation.changepassword.ChangePasswordPresenterImpl;
import edu.h2.layoutdemo.presentation.changepassword.ChangePasswordView;
import io.reactivex.Observable;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class ChangePasswordServiceTest {
    @Test
    public void requestChangePassword_shouldBeCalled() {
        ChangePasswordService changePasswordServiceMock = mock(ChangePasswordService.class);
        ChangePasswordUseCase changePasswordUseCase = new ChangePasswordUseCase(changePasswordServiceMock);
        when(changePasswordServiceMock.requestChangePassword(any(String.class), any(String.class), any(String.class), any(String.class))).thenReturn(Observable.just(true));

        ChangePasswordView changePasswordViewMock = mock(ChangePasswordView.class);

        ChangePasswordPresenter changePasswordPresenter = new ChangePasswordPresenterImpl(changePasswordUseCase);
        changePasswordPresenter.attach(changePasswordViewMock);
        changePasswordPresenter.changePassword("1", "2", "3", "3");

        verify(changePasswordServiceMock).requestChangePassword("1", "2", "3", "3");
    }
}
