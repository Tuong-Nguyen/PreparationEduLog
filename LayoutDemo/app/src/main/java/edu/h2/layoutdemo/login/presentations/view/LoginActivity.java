package edu.h2.layoutdemo.login.presentations.view;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import edu.h2.layoutdemo.R;
import edu.h2.layoutdemo.login.domain.interactors.DriverAuthenticateUseCase;
import edu.h2.layoutdemo.login.domain.services.AuthenticateServiceImplement;
import edu.h2.layoutdemo.login.domain.services.EventServiceImplement;
import edu.h2.layoutdemo.login.models.Event;
import edu.h2.layoutdemo.login.presentations.presenter.DriverPreferences;
import edu.h2.layoutdemo.login.presentations.presenter.LoginPresenter;
import edu.h2.layoutdemo.login.presentations.presenter.LoginPresenterImplement;
import edu.h2.layoutdemo.login.tracking.EventTracking;

public class LoginActivity extends AppCompatActivity implements LoginPresenter.RequireViewOptions{
    private EditText etBusId;
    private EditText etDriverId;
    private EditText etPassword;
    private Button btnLogin;
    private LoginPresenter.LoginPresenterOptions presenter;
    private String busId;
    private String driverId;
    private String password;
    private CheckBox saveLoginCheckBox;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        etBusId = (EditText)findViewById(R.id.busId);

        etDriverId = (EditText)findViewById(R.id.driverId);

        etPassword = (EditText)findViewById(R.id.password);

        btnLogin = (Button)findViewById(R.id.login);

        DriverAuthenticateUseCase driverAuthenticateUseCase = new DriverAuthenticateUseCase(new AuthenticateServiceImplement());

        DriverPreferences driverPreferences = new DriverPreferences(this);


        EventTracking eventTracking = new EventTracking(new EventServiceImplement());

        presenter = new LoginPresenterImplement(this, driverAuthenticateUseCase, driverPreferences, eventTracking);

        saveLoginCheckBox = (CheckBox)findViewById(R.id.rememberMe);

        presenter.doRememberDriverId();

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                busId = etBusId.getText().toString();
                driverId = etDriverId.getText().toString();
                password = etPassword.getText().toString();
                // Sent event login
                presenter.sendEventLogin(Event.LOG_IN);
                presenter.validateCredentials(busId, driverId, password);
            }
        });

    }

    @Override
    public void setTextRememberDriverId(String driverId) {
        etDriverId.setText(driverId);
    }

    @Override
    public void onLogged() {
        Toast.makeText(this, "Login is successful", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showEmptyCredentials(String field) {
        Toast.makeText(this, field + " is empty", Toast.LENGTH_SHORT).show();
    }


    @Override
    public void onNotLogged() {
        Toast.makeText(this, "Login is not successful", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void rememberDriverIdCheckbox(boolean isChecked) {
        saveLoginCheckBox.setChecked(isChecked);
    }

    @Override
    public boolean isRememberChecked() {
        return saveLoginCheckBox.isChecked();
    }

    @Override
    public void showFailedOverThreeTimesLogin() {
        Toast.makeText(this, "Login over three times, Your account was locked", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showSentEventSuccess() {
        Toast.makeText(this, "Sending event logIn success", Toast.LENGTH_SHORT).show();

    }

}
