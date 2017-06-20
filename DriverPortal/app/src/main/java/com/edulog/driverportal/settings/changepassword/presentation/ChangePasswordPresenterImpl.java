package com.edulog.driverportal.settings.changepassword.presentation;

import com.edulog.driverportal.settings.changepassword.domain.interactor.ChangePasswordUseCase;
import com.edulog.driverportal.settings.changepassword.domain.interactor.ValidationUseCase;
import com.edulog.driverportal.settings.changepassword.model.ValidationResult;

import io.reactivex.observers.DisposableObserver;

public class ChangePasswordPresenterImpl implements ChangePasswordPresenter {
    private ChangePasswordView changePasswordView;
    private ChangePasswordUseCase changePasswordUseCase;
    private ValidationUseCase validationUseCase;

    public ChangePasswordPresenterImpl(ChangePasswordUseCase changePasswordUseCase, ValidationUseCase validationUseCase) {
        this.changePasswordUseCase = changePasswordUseCase;
        this.validationUseCase = validationUseCase;
    }

    @Override
    public void changePassword(String driverId, String oldPassword, String newPassword) {
        DisposableObserver<Boolean> observer = createChangePasswordObserver();
        ChangePasswordUseCase.Params params = ChangePasswordUseCase.buildParams(driverId, oldPassword, newPassword);
        changePasswordUseCase.execute(observer, params);

        changePasswordView.showProgress();
        changePasswordView.disableRequestChangePassword();
    }

    @Override
    public void validateUserInputs(String driverId, String oldPassword, String newPassword, String confirmNewPassword) {
        DisposableObserver<ValidationResult> observer = createValidationObserver();
        ValidationUseCase.Params params = ValidationUseCase.buildParams(driverId, oldPassword, newPassword, confirmNewPassword);
        validationUseCase.execute(observer, params);
    }

    @Override
    public void attach(ChangePasswordView changePasswordView) {
        this.changePasswordView = changePasswordView;
    }

    @Override
    public void detach() {
        changePasswordView = null;
    }

    @Override
    public void onError(String message) {
        // on error
    }

    private DisposableObserver<Boolean> createChangePasswordObserver() {
        return new DisposableObserver<Boolean>() {
            @Override
            public void onNext(Boolean isValid) {
                changePasswordView.showChangePasswordSuccess("Change password successfully!");
            }

            @Override
            public void onError(Throwable e) {
                onChangePasswordError(e.getMessage());
            }

            @Override
            public void onComplete() {
                changePasswordView.hideProgress();
                changePasswordView.enableRequestChangePassword();
            }
        };
    }

    private void onChangePasswordError(String message) {
        changePasswordView.showError(message);
        changePasswordView.hideProgress();
        changePasswordView.enableRequestChangePassword();
    }

    private DisposableObserver<ValidationResult> createValidationObserver() {
        return new DisposableObserver<ValidationResult>() {
            @Override
            public void onNext(ValidationResult validationResult) {
                if (validationResult.isValid()) {
                    onValidationResultValid(validationResult);
                } else {
                    onValidationResultInvalid(validationResult);
                }

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onComplete() {

            }
        };
    }

    private void onValidationResultValid(ValidationResult validationResult) {
        changePasswordView.enableRequestChangePassword();
        switch (validationResult.getField()) {
            case DRIVER_ID:
                changePasswordView.hideInvalidDriverId();
                break;
            case OLD_PASSWORD:
                changePasswordView.hideInvalidOldPassword();
                break;
            case NEW_PASSWORD:
                changePasswordView.hideInvalidNewPassword();
                break;
            case ALL:
                changePasswordView.hidePasswordDoesNotMatch();
                break;
        }
    }

    private void onValidationResultInvalid(ValidationResult validationResult) {
        changePasswordView.disableRequestChangePassword();
        switch (validationResult.getField()) {
            case DRIVER_ID:
                changePasswordView.showInvalidDriverId(validationResult.getErrorMessage());
                break;
            case OLD_PASSWORD:
                changePasswordView.showInvalidOldPassword(validationResult.getErrorMessage());
                break;
            case NEW_PASSWORD:
                changePasswordView.showInvalidNewPassword(validationResult.getErrorMessage());
                break;
            case ALL:
                changePasswordView.showPasswordDoesNotMatch();
                break;
        }
    }
}
