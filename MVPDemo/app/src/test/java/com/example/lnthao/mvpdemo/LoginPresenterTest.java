package com.example.lnthao.mvpdemo;

import com.example.lnthao.mvpdemo.presentation.presenter.LoginPresenter;
import com.example.lnthao.mvpdemo.presentation.presenter.LoginPresenterImpl;

import org.junit.Before;
import org.junit.Test;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

/**
 * Created by lnthao on 6/9/2017.
 */

public class LoginPresenterTest {
    private LoginPresenter presenter;
    private LoginPresenter.View mockView;

    @Before
    public void setup(){
        mockView = mock(LoginPresenter.View.class);
        presenter = new LoginPresenterImpl(mockView);
    }

    @Test
    public void validateCredential_validUsernamePassword_showSuccessWasCalled(){
        String userName = "Test";
        String password = "Test";
        presenter.validateCredentials(userName, password);
        verify(mockView).showSucess();
    }
}
