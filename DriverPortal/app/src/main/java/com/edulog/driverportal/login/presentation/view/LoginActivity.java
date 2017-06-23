package com.edulog.driverportal.login.presentation.view;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import com.edulog.driverportal.R;
import com.edulog.driverportal.login.domain.interactors.LoginUseCase;
import com.edulog.driverportal.login.domain.services.AuthenticateServiceImplement;
import com.edulog.driverportal.login.domain.services.DriverPreferences;
import com.edulog.driverportal.login.domain.services.EventServiceImplement;
import com.edulog.driverportal.login.domain.utils.ErrorValidationUtil;
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

        com.edulog.driverportal.login.models.ErrorValidation errorValidation = new com.edulog.driverportal.login.models.ErrorValidation();

        ErrorValidationUtil errorValidateUtils = new ErrorValidationUtil(errorValidation);

        AuthenticateServiceImplement authenticateServiceImplement = new AuthenticateServiceImplement(errorValidateUtils);

        EventServiceImplement eventServiceImplement = new EventServiceImplement();

        DriverPreferences driverPreferences = new DriverPreferences(this);

        LoginUseCase loginUseCase = new LoginUseCase(authenticateServiceImplement,errorValidateUtils, eventServiceImplement, driverPreferences);
        presenter = new LoginPresenterImplement(this, driverPreferences, loginUseCase);
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
