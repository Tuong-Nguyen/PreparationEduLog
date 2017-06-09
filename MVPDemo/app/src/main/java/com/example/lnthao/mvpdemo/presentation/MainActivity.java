package com.example.lnthao.mvpdemo.presentation;

import android.databinding.DataBindingUtil;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import com.example.lnthao.mvpdemo.R;
import com.example.lnthao.mvpdemo.databinding.ActivityMainBinding;
import com.example.lnthao.mvpdemo.presentation.presenter.LoginPresenter;
import com.example.lnthao.mvpdemo.presentation.presenter.LoginPresenterImpl;
import com.example.lnthao.mvpdemo.presentation.viewmodel.User;

public class MainActivity extends AppCompatActivity implements LoginPresenter.View {
    ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        LoginPresenterImpl presenter = new LoginPresenterImpl(this);
        User user = new User();
        binding.setUser(user);
        binding.setPresenter(presenter);
    }

    @Override
    public void showSucess() {
        Toast.makeText(this, "Username and password are the same: " + binding.getUser().getUsername(), Toast.LENGTH_LONG).show();
    }
}
