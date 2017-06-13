package com.edulog.driverportal.presentation.settings.changepassword;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.edulog.driverportal.R;
import com.edulog.driverportal.domain.executor.PostExecutionThread;
import com.edulog.driverportal.domain.interactor.ChangePasswordUseCase;
import com.edulog.driverportal.domain.service.ChangePasswordService;
import com.edulog.driverportal.presentation.BaseFragment;
import com.edulog.driverportal.presentation.BasePresenter;
import com.edulog.driverportal.presentation.BaseView;
import com.edulog.driverportal.presentation.UIThread;
import com.jakewharton.rxbinding2.widget.RxTextView;

import io.reactivex.Observable;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class ChangePasswordFragment extends BaseFragment implements ChangePasswordView {
    private TextInputLayout driverIdWrapper;
    private TextInputLayout oldPasswordWrapper;
    private TextInputLayout newPasswordWrapper;
    private TextInputLayout confirmNewPasswordWrapper;
    private Button changePasswordButton;
    private ProgressBar progressBar;

    private ChangePasswordPresenter changePasswordPresenter;

    public static ChangePasswordFragment newInstance() {
        return new ChangePasswordFragment();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://obscure-mesa-13550.herokuapp.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();
        ChangePasswordService changePasswordService = retrofit.create(ChangePasswordService.class);
        PostExecutionThread uiThread = new UIThread();
        changePasswordPresenter = new ChangePasswordPresenterImpl(new ChangePasswordUseCase(uiThread, changePasswordService));
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_change_password, container, false);

        driverIdWrapper = (TextInputLayout) root.findViewById(R.id.driverIdWrapper);
        oldPasswordWrapper = (TextInputLayout) root.findViewById(R.id.oldPasswordWrapper);
        newPasswordWrapper = (TextInputLayout) root.findViewById(R.id.newPasswordWrapper);
        confirmNewPasswordWrapper = (TextInputLayout) root.findViewById(R.id.confirmNewPasswordWrapper);

        changePasswordButton = (Button) root.findViewById(R.id.btnChangePassword);
        disableRequestChangePassword();

        progressBar = (ProgressBar)root.findViewById(R.id.progressBar);

        final EditText driverIdEditText = (EditText) root.findViewById(R.id.etDriverId);
        final EditText oldPasswordEditText = (EditText) root.findViewById(R.id.etOldPassword);
        final EditText newPasswordEditText = (EditText) root.findViewById(R.id.etNewPassword);
        final EditText confirmNewPasswordEditText = (EditText) root.findViewById(R.id.etConfirmNewPassword);

        Observable<CharSequence> driverIdObservable = RxTextView.textChanges(driverIdEditText).skip(1);
        Observable<CharSequence> oldPasswordObservable = RxTextView.textChanges(oldPasswordEditText).skip(1);
        Observable<CharSequence> newPasswordObservable = RxTextView.textChanges(newPasswordEditText).skip(1);
        Observable<CharSequence> confirmNewPasswordObservable = RxTextView.textChanges(confirmNewPasswordEditText).skip(1);
        changePasswordPresenter.validateUserInput(driverIdObservable,
                oldPasswordObservable,
                newPasswordObservable,
                confirmNewPasswordObservable);

        Button changePasswordButton = (Button) root.findViewById(R.id.btnChangePassword);
        changePasswordButton.setOnClickListener(v -> {
            String driverId = driverIdEditText.getText().toString();
            String oldPassword = oldPasswordEditText.getText().toString();
            String newPassword = newPasswordEditText.getText().toString();
            changePasswordPresenter.changePassword(driverId, oldPassword, newPassword);
        });

        return root;
    }

    @Override
    public void showProgress() {
        progressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideProgress() {
        progressBar.setVisibility(View.INVISIBLE);
    }

    @Override
    public void showError(String message) {
        Toast.makeText(getActivity(), message, Toast.LENGTH_SHORT).show();
    }

    @Override
    protected BasePresenter getPresenter() {
        return changePasswordPresenter;
    }

    @Override
    protected BaseView getViewLayer() {
        return this;
    }

    @Override
    public void showChangePasswordSuccess(String message) {
        Toast.makeText(getActivity(), message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showInvalidDriverId(String message) {
        driverIdWrapper.setError(message);
    }

    @Override
    public void hideInvalidDriverId() {
        driverIdWrapper.setErrorEnabled(false);
    }

    @Override
    public void showInvalidOldPassword(String message) {
        oldPasswordWrapper.setError(message);
    }

    @Override
    public void hideInvalidOldPassword() {
        oldPasswordWrapper.setErrorEnabled(false);
    }

    @Override
    public void showInvalidNewPassword(String message) {
        newPasswordWrapper.setError(message);
    }

    @Override
    public void hideInvalidNewPassword() {
        newPasswordWrapper.setErrorEnabled(false);
    }

    @Override
    public void showPasswordDoesNotMatch() {
        confirmNewPasswordWrapper.setError("Your password does not match.");
    }

    @Override
    public void hidePasswordDoesNotMatch() {
        confirmNewPasswordWrapper.setErrorEnabled(false);
    }

    @Override
    public void enableRequestChangePassword() {
        changePasswordButton.setEnabled(true);
        changePasswordButton.setClickable(true);
        changePasswordButton.setAlpha(1.0f);
    }

    @Override
    public void disableRequestChangePassword() {
        changePasswordButton.setEnabled(false);
        changePasswordButton.setClickable(false);
        changePasswordButton.setAlpha(0.5f);
    }
}
