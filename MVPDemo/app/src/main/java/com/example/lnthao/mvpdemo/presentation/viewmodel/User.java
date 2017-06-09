package com.example.lnthao.mvpdemo.presentation.viewmodel;

import android.databinding.BaseObservable;
import android.databinding.Bindable;

import com.example.lnthao.mvpdemo.BR;

/**
 * Created by lnthao on 6/7/2017.
 */

public class User extends BaseObservable {
    private String username;
    private String password;

    public User(){
        this.username = "Test User";
        this.password = "123456";
    }

    @Bindable
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
        notifyPropertyChanged(BR.username);
    }

    @Bindable
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
        notifyPropertyChanged(BR.password);
    }
}
