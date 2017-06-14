package edu.h2.layoutdemo.login.view;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import edu.h2.layoutdemo.R;
import edu.h2.layoutdemo.login.presenter.LoginPresenter;
import edu.h2.layoutdemo.login.presenter.LoginPresenterImplement;
import edu.h2.layoutdemo.login.repositories.DriverRepository;
import edu.h2.layoutdemo.login.usecase.DriverAuthenticateUseCase;

public class LoginActivity extends AppCompatActivity implements LoginPresenter.RequireViewOptions{
    private EditText etBusId;
    private EditText etDriverId;
    private EditText etPassword;
    private Button btnLogin;
    private LoginPresenter.LoginPresenterOptions presenter;
    private String busId;
    private String driverId;
    private String password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        etBusId = (EditText)findViewById(R.id.busId);

        etDriverId = (EditText)findViewById(R.id.driverId);

        etPassword = (EditText)findViewById(R.id.password);

        btnLogin = (Button)findViewById(R.id.login);

        DriverAuthenticateUseCase driverAuthenticateUseCase = new DriverAuthenticateUseCase(new DriverRepository());

        presenter = new LoginPresenterImplement(this, driverAuthenticateUseCase);

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                busId = etBusId.getText().toString();
                driverId = etDriverId.getText().toString();
                password = etPassword.getText().toString();
                presenter.validateCredentials(busId, driverId, password);
            }
        });

    }

    @Override
    public void showLoginSuccess() {
        Toast.makeText(this, "Login is successful", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showEmptyCredentials(String field) {
        Toast.makeText(this, field + " is empty", Toast.LENGTH_SHORT).show();
    }


    @Override
    public void showLoginFail() {
        Toast.makeText(this, "Login is not successful", Toast.LENGTH_SHORT).show();
    }

}
