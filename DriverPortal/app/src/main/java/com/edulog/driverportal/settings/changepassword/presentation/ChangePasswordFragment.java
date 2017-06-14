package com.edulog.driverportal.settings.changepassword.presentation;

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
import com.edulog.driverportal.settings.base.BaseFragment;
import com.edulog.driverportal.settings.base.BasePresenter;
import com.edulog.driverportal.settings.base.BaseView;
import com.edulog.driverportal.settings.changepassword.data.service.AuthServiceImpl;
import com.edulog.driverportal.settings.changepassword.domain.interactor.ChangePasswordUseCase;
import com.edulog.driverportal.settings.changepassword.domain.service.AuthService;
import com.jakewharton.rxbinding2.widget.RxTextView;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.observers.DisposableObserver;

public class ChangePasswordFragment extends BaseFragment implements ChangePasswordView {
    private TextInputLayout driverIdWrapper;
    private TextInputLayout oldPasswordWrapper;
    private TextInputLayout newPasswordWrapper;
    private TextInputLayout confirmNewPasswordWrapper;
    private EditText driverIdEditText;
    private EditText oldPasswordEditText;
    private EditText newPasswordEditText;
    private EditText confirmNewPasswordEditText;
    private Button changePasswordButton;
    private ProgressBar progressBar;

    private ChangePasswordPresenter changePasswordPresenter;

    public static ChangePasswordFragment newInstance() {
        return new ChangePasswordFragment();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        AuthService authService = new AuthServiceImpl();
        changePasswordPresenter = new ChangePasswordPresenterImpl(new ChangePasswordUseCase(AndroidSchedulers.mainThread(), authService));
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_change_password, container, false);

        driverIdWrapper = (TextInputLayout) root.findViewById(R.id.driverIdWrapper);
        oldPasswordWrapper = (TextInputLayout) root.findViewById(R.id.oldPasswordWrapper);
        newPasswordWrapper = (TextInputLayout) root.findViewById(R.id.newPasswordWrapper);
        confirmNewPasswordWrapper = (TextInputLayout) root.findViewById(R.id.confirmNewPasswordWrapper);

        driverIdEditText = (EditText) root.findViewById(R.id.etDriverId);
        oldPasswordEditText = (EditText) root.findViewById(R.id.etOldPassword);
        newPasswordEditText = (EditText) root.findViewById(R.id.etNewPassword);
        confirmNewPasswordEditText = (EditText) root.findViewById(R.id.etConfirmNewPassword);

        changePasswordButton = (Button) root.findViewById(R.id.btnChangePassword);
        changePasswordButton.setOnClickListener(v -> requestChangePassword());

        progressBar = (ProgressBar) root.findViewById(R.id.progressBar);

        return root;
    }

    @Override
    public void onStart() {
        super.onStart();

        monitorUserInput();
        disableRequestChangePassword();
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

    private void requestChangePassword() {
        String driverId = driverIdEditText.getText().toString();
        String oldPassword = oldPasswordEditText.getText().toString();
        String newPassword = newPasswordEditText.getText().toString();
        changePasswordPresenter.changePassword(driverId, oldPassword, newPassword);
    }

    private void monitorUserInput() {
        Observable<CharSequence> driverIdObservable = RxTextView.textChanges(driverIdEditText).skip(1);
        Observable<CharSequence> oldPasswordObservable = RxTextView.textChanges(oldPasswordEditText).skip(1);
        Observable<CharSequence> newPasswordObservable = RxTextView.textChanges(newPasswordEditText).skip(1);
        Observable<CharSequence> confirmNewPasswordObservable = RxTextView.textChanges(confirmNewPasswordEditText).skip(1);
        Observable<Boolean> observable = Observable.combineLatest(driverIdObservable,
                oldPasswordObservable,
                newPasswordObservable,
                confirmNewPasswordObservable,
                (driverIdOrigin, oldPasswordOrigin, newPasswordOrigin, confirmNewPasswordOrigin) -> {
                    String driverId = driverIdOrigin.toString();
                    String oldPassword = oldPasswordOrigin.toString();
                    String newPassword = newPasswordOrigin.toString();
                    String confirmNewPassword = confirmNewPasswordOrigin.toString();
                    return changePasswordPresenter.validateUserInput(driverId, oldPassword, newPassword, confirmNewPassword);
                });
        observable.subscribe(createUserInputValidationObserver());
    }

    private Observer<Boolean> createUserInputValidationObserver() {
        return new DisposableObserver<Boolean>() {
            @Override
            public void onNext(Boolean isValid) {
                changePasswordPresenter.validateUserInput(isValid);
            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onComplete() {

            }
        };
    }
}
