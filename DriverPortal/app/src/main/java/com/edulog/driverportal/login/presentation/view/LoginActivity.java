package com.edulog.driverportal.login.presentation.view;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import com.edulog.driverportal.R;
import com.edulog.driverportal.login.domain.interactors.DriverAuthenticateUseCase;
import com.edulog.driverportal.login.domain.interactors.LoginValidateUseCase;
import com.edulog.driverportal.login.domain.interactors.SendEventUseCase;
import com.edulog.driverportal.login.domain.services.AuthenticateServiceImplement;
import com.edulog.driverportal.login.domain.services.EventServiceImplement;
import com.edulog.driverportal.login.domain.utils.ErrorValidateUtils;
import com.edulog.driverportal.login.models.DriverPreferences;
import com.edulog.driverportal.login.models.Events;
import com.edulog.driverportal.login.models.ErrorValidation;
import com.edulog.driverportal.login.presentation.presenter.LoginPresenter;
import com.edulog.driverportal.login.presentation.presenter.LoginPresenterImplement;
import com.edulog.driverportal.login.presentation.presenter.LoginView;

import io.reactivex.android.schedulers.AndroidSchedulers;

public class LoginActivity extends AppCompatActivity implements LoginView {
    private EditText etBusId;
    private EditText etDriverId;
    private EditText etPassword;
    private Button btnLogin;
    private LoginPresenter presenter;
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

        ErrorValidation errorValidation = new ErrorValidation();
        ErrorValidateUtils errorValidateUtils = new ErrorValidateUtils(errorValidation);

        AuthenticateServiceImplement authenticateServiceImplement = new AuthenticateServiceImplement(errorValidateUtils);

        DriverAuthenticateUseCase driverAuthenticateUseCase = new DriverAuthenticateUseCase(AndroidSchedulers.mainThread(),authenticateServiceImplement, errorValidateUtils);

        LoginValidateUseCase loginValidateUseCase = new LoginValidateUseCase(AndroidSchedulers.mainThread(), authenticateServiceImplement);

        EventServiceImplement eventServiceImplement = new EventServiceImplement();

        SendEventUseCase sendEventUseCase = new SendEventUseCase(AndroidSchedulers.mainThread(), eventServiceImplement);

        DriverPreferences driverPreferences = new DriverPreferences(this);

        presenter = new LoginPresenterImplement(this, driverAuthenticateUseCase, driverPreferences, sendEventUseCase, loginValidateUseCase);

        saveLoginCheckBox = (CheckBox)findViewById(R.id.rememberMe);

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                busId = etBusId.getText().toString();
                driverId = etDriverId.getText().toString();
                password = etPassword.getText().toString();
                presenter.doLogin(busId, driverId, password, Events.LOG_IN);
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
    public void rememberDriverIdCheckbox(boolean isChecked) {
        saveLoginCheckBox.setChecked(isChecked);
    }

    @Override
    public boolean isRememberChecked() {
        return saveLoginCheckBox.isChecked();
    }

    @Override
    public void onLoginError(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showLoginValidation(ErrorValidation errorValidation) {
        if (!errorValidation.isValid()){
         showErrorValidate(errorValidation.getErrorMessage());
        }
    }

    private void showErrorValidate(String errorMessage){
        Toast.makeText(this, errorMessage, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showSentEventSuccess() {
        Toast.makeText(this, "Sending login event is success", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showSentEventFailure(String message) {
        Toast.makeText(this, "Sending login event is failed", Toast.LENGTH_SHORT).show();
    }

}
