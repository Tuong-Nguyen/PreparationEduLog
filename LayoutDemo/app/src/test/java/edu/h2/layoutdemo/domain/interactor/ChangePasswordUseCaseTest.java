package edu.h2.layoutdemo.domain.interactor;

import org.junit.Test;

import edu.h2.layoutdemo.presentation.changepassword.ChangePasswordPresenter;
import edu.h2.layoutdemo.presentation.changepassword.ChangePasswordPresenterImpl;
import io.reactivex.observers.DisposableObserver;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class ChangePasswordUseCaseTest {
    @Test
    public void execute_shouldBeCalled() {
        ChangePasswordUseCase changePasswordUseCaseMock = mock(ChangePasswordUseCase.class);
        when(changePasswordUseCaseMock.buildParams(any(String.class), any(String.class), any(String.class), any(String.class))).thenReturn(new ChangePasswordUseCase.Params());

        ChangePasswordPresenter presenter = new ChangePasswordPresenterImpl(changePasswordUseCaseMock);
        presenter.changePassword("1", "2", "3", "3");

        verify(changePasswordUseCaseMock).execute(any(DisposableObserver.class), any(ChangePasswordUseCase.Params.class));
    }
}
