package com.edulog.driverportal.settings.changepassword.presentation;

import com.edulog.driverportal.settings.changepassword.domain.interactor.ChangePasswordUseCase;
import com.edulog.driverportal.settings.changepassword.domain.interactor.ValidationUseCase;
import com.edulog.driverportal.settings.changepassword.model.ValidationResult;

import io.reactivex.observers.DisposableObserver;

public class ChangePasswordPresenterImpl implements ChangePasswordPresenter {
    private ChangePasswordView changePasswordView;
    private ChangePasswordUseCase changePasswordUseCase;
    private ValidationUseCase validationUseCase;
    private DisposableObserver<ValidationResult> validationObserver;
    private DisposableObserver<Boolean> changePasswordObserver;

    public ChangePasswordPresenterImpl(ChangePasswordUseCase changePasswordUseCase, ValidationUseCase validationUseCase) {
        this.changePasswordUseCase = changePasswordUseCase;
        this.validationUseCase = validationUseCase;
    }

    @Override
    public void changePassword(String driverId, String oldPassword, String newPassword) {
        changePasswordObserver = createChangePasswordObserver();
        ChangePasswordUseCase.Params params = ChangePasswordUseCase.buildParams(driverId, oldPassword, newPassword);
        changePasswordUseCase.execute(changePasswordObserver, params);

        changePasswordView.showProgress();
    }

    @Override
    public void validateUserInputs(String driverId, String oldPassword, String newPassword, String confirmNewPassword) {
        validationObserver = createValidationObserver();
        ValidationUseCase.Params params = ValidationUseCase.buildParams(driverId, oldPassword, newPassword, confirmNewPassword);
        validationUseCase.execute(validationObserver, params);
    }

    @Override
    public void attach(ChangePasswordView changePasswordView) {
        this.changePasswordView = changePasswordView;
    }

    @Override
    public void detach() {
        changePasswordView = null;

        // TODO: generalize this code and move it to BasePresenter class
        if (validationObserver != null && !validationObserver.isDisposed()) {
            validationObserver.dispose();
        }
        if (changePasswordObserver != null && !changePasswordObserver.isDisposed()) {
            changePasswordObserver.dispose();
        }
    }

    @Override
    public void onError(String message) {
        // on error
    }

    private DisposableObserver<Boolean> createChangePasswordObserver() {
        return new DisposableObserver<Boolean>() {
            @Override
            public void onNext(Boolean isValid) {
                changePasswordView.showSuccess("Change password successfully!");
            }

            @Override
            public void onError(Throwable e) {
                onChangePasswordError(e.getMessage());
            }

            @Override
            public void onComplete() {
                changePasswordView.hideProgress();
            }
        };
    }

    private void onChangePasswordError(String message) {
        changePasswordView.showError(message);
        changePasswordView.hideProgress();
    }

    private DisposableObserver<ValidationResult> createValidationObserver() {
        return new DisposableObserver<ValidationResult>() {
            @Override
            public void onNext(ValidationResult validationResult) {
                changePasswordView.showValidationResult(validationResult);
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
