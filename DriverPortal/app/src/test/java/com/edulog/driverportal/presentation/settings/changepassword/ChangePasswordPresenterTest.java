package com.edulog.driverportal.presentation.settings.changepassword;

import com.edulog.driverportal.domain.interactor.ChangePasswordUseCase;

import org.junit.Test;

import io.reactivex.observers.DisposableObserver;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

public class ChangePasswordPresenterTest {
    @Test
    public void changePassword_executeShouldBeCalled() {
        String driverId = "1234";
        String oldPassword = "old_pass";
        String newPassword = "new_pass";
        String confirmNewPassword = "new_pass";

        ChangePasswordUseCase useCaseMock = mock(ChangePasswordUseCase.class);
        ChangePasswordView viewMock = mock(ChangePasswordView.class);
        ChangePasswordPresenter presenter = new ChangePasswordPresenterImpl(useCaseMock);
        presenter.attach(viewMock);

        presenter.changePassword(driverId, oldPassword, newPassword, confirmNewPassword);

        verify(useCaseMock).execute(mock(DisposableObserver.class), ChangePasswordUseCase.buildParams(driverId, oldPassword, newPassword));
    }
}
