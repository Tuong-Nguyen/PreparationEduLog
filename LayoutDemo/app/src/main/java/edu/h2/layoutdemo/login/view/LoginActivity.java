package edu.h2.layoutdemo.login.view;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import edu.h2.layoutdemo.R;
import edu.h2.layoutdemo.login.domain.EventServiceImplement;
import edu.h2.layoutdemo.login.models.Event;
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
    private CheckBox saveLoginCheckBox;
    private SharedPreferences loginPreferences;
    private SharedPreferences.Editor loginPrefsEditor;
    private Boolean saveLogin;
    private int countLoginFail;

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

        saveLoginCheckBox = (CheckBox)findViewById(R.id.rememberMe);

        //Create SharedPreferences
        loginPreferences = getSharedPreferences("loginPrefs", MODE_PRIVATE);

        loginPrefsEditor = loginPreferences.edit();

        saveLogin = loginPreferences.getBoolean("saveLogin", false);

        if (saveLogin == true) {
           // Retrieve data from preference and set text
            etBusId.setText(loginPreferences.getString("busId", ""));
            etDriverId.setText(loginPreferences.getString("driverId", ""));
            etPassword.setText(loginPreferences.getString("password", ""));
            saveLoginCheckBox.setChecked(true);
            onLogged();
        } else {
            onNotLogged();
        }
        countLoginFail = 0;
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                busId = etBusId.getText().toString();
                driverId = etDriverId.getText().toString();
                password = etPassword.getText().toString();
                if (saveLoginCheckBox.isChecked()) {
                    ///Setting values in Preference:
                    loginPrefsEditor.putBoolean("saveLogin", true);
                    loginPrefsEditor.putString("busId", busId);
                    loginPrefsEditor.putString("driverId", driverId);
                    loginPrefsEditor.putString("password", password);
                    // Save the changes in SharedPreferences
                    loginPrefsEditor.commit();  // commit changes
                } else {
                    loginPrefsEditor.clear();
                    loginPrefsEditor.commit(); // commit changes
                }

                // Sent event login
                EventServiceImplement eventServiceImplement = new EventServiceImplement();
                eventServiceImplement.sentEvent(Event.LOG_IN);

                presenter.validateCredentials(busId, driverId, password);
                if (countLoginFail > 3){
                    lockedAccount();
                }
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
        countLoginFail++;
    }

    public void onLogged(){
        Toast.makeText(this, "You logged", Toast.LENGTH_SHORT).show();
    }
    public void onNotLogged(){
        Toast.makeText(this, "You have not yet logged", Toast.LENGTH_SHORT).show();
    }
    public void lockedAccount(){
        Toast.makeText(this, "Your account was locked", Toast.LENGTH_SHORT).show();
    }

}
