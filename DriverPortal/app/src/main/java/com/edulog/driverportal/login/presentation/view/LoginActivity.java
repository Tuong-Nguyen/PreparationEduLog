package com.edulog.driverportal.login.presentation.view;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import com.edulog.driverportal.R;
import com.edulog.driverportal.common.preference.SessionImpl;
import com.edulog.driverportal.login.domain.interactors.DevicePreferenceUseCase;
import com.edulog.driverportal.login.domain.interactors.LoginUseCase;
import com.edulog.driverportal.login.domain.interactors.LoginValidator;
import com.edulog.driverportal.login.domain.services.AuthenticateServiceImpl;
import com.edulog.driverportal.login.domain.services.EventServiceImpl;
import com.edulog.driverportal.login.models.ErrorValidation;
import com.edulog.driverportal.login.presentation.presenter.LoginPresenter;
import com.edulog.driverportal.login.presentation.presenter.LoginPresenterImpl;
import com.edulog.driverportal.login.presentation.presenter.LoginView;

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

        AuthenticateServiceImpl authenticateServiceImpl = new AuthenticateServiceImpl(new LoginValidator());

        EventServiceImpl eventServiceImpl = new EventServiceImpl();

        LoginUseCase loginUseCase = new LoginUseCase(authenticateServiceImpl, eventServiceImpl, new SessionImpl(this));

        DevicePreferenceUseCase devicePreferenceUseCase = new DevicePreferenceUseCase(new SessionImpl(this));

        presenter = new LoginPresenterImpl(this, devicePreferenceUseCase, loginUseCase);

        saveLoginCheckBox = (CheckBox)findViewById(R.id.rememberMe);

        presenter.getRememberDriverId();

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                busId = etBusId.getText().toString();
                driverId = etDriverId.getText().toString();
                password = etPassword.getText().toString();
                presenter.doLogin(busId, driverId, password,saveLoginCheckBox.isChecked());
            }
        });

    }

    @Override
    public void setTextRememberDriverId(String driverId) {
        etDriverId.setText(driverId);
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
    public void showErrorValidationMessage(ErrorValidation errorValidation) {
        if (!errorValidation.isValid()){
         showErrorValidate(errorValidation.getErrorMessage());
        }
    }

    private void showErrorValidate(String errorMessage){
        Toast.makeText(this, errorMessage, Toast.LENGTH_SHORT).show();
    }

}
