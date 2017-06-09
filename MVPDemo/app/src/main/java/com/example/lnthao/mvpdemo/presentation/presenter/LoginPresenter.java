package com.example.lnthao.mvpdemo.presentation.presenter;

/**
 * Created by lnthao on 6/8/2017.
 */

public interface LoginPresenter {
    boolean validateCredentials(String username, String password);
    interface View{
        void showSucess();
    }
}
