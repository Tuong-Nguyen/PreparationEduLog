package com.example.lnthao.mvpdemo;

import com.example.lnthao.mvpdemo.presentation.presenter.LoginPresenter;
import com.example.lnthao.mvpdemo.presentation.presenter.LoginPresenterImpl;

import org.junit.Rule;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;

import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;

/**
 * Created by lnthao on 6/9/2017.
 */

public class LoginPresenterTest {
    @Rule
    public MockitoRule mockitoRule = MockitoJUnit.rule();

    @Mock
    private LoginPresenter.View mockView;

    @InjectMocks
    private LoginPresenterImpl presenter;

    @Test
    public void validateCredential_validUsernamePassword_showSuccessWasCalled(){
        String userName = "Test";
        String password = "Test";
        presenter.validateCredentials(userName, password);
        verify(mockView).showSucess();
    }

    @Test
    public void validateCredential_invalidUsernamePassword_showSuccessWasNotCalled(){
        String userName = "Test1";
        String password = "Test";
        presenter.validateCredentials(userName, password);
        verify(mockView, never()).showSucess();
    }
}
