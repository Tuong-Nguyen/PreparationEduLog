package com.example.lnthao.mvpdemo.presentation.presenter;

/**
 * Created by lnthao on 6/8/2017.
 */

public class LoginPresenterImpl implements LoginPresenter {
    private View view;

    public LoginPresenterImpl(View view) {
        this.view = view;
    }

    @Override
    public boolean validateCredentials(String username, String password) {
        if (username.equals(password)) {
            view.showSucess();
            return true;
        }
        return false;
    }
}
