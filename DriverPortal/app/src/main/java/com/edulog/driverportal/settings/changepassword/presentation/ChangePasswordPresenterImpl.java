package com.edulog.driverportal.settings.changepassword.presentation;

import com.edulog.driverportal.common.presentation.DefaultObserver;
import com.edulog.driverportal.settings.changepassword.domain.exception.ValidationException;
import com.edulog.driverportal.settings.changepassword.domain.interactor.ChangePasswordUseCase;
import com.edulog.driverportal.settings.changepassword.domain.interactor.ValidationUseCase;
import com.edulog.driverportal.settings.changepassword.presentation.model.ValidationResult;

import java.util.List;

import io.reactivex.observers.DisposableObserver;

public class ChangePasswordPresenterImpl extends ChangePasswordContract.ChangePasswordPresenter {
    private ChangePasswordContract.ChangePasswordView changePasswordView;
    private ChangePasswordUseCase changePasswordUseCase;
    private ValidationUseCase validationUseCase;
    private DisposableObserver<List<ValidationResult>> validationObserver;
    private DisposableObserver<Boolean> changePasswordObserver;

    public ChangePasswordPresenterImpl(ChangePasswordUseCase changePasswordUseCase, ValidationUseCase validationUseCase) {
        this.changePasswordUseCase = changePasswordUseCase;
        this.validationUseCase = validationUseCase;
    }

    @Override
    public void changePassword(String driverId, String oldPassword, String newPassword) {
        disposeObserver(changePasswordObserver);
        changePasswordObserver = createChangePasswordObserver();
        addDisposable(changePasswordObserver);

        ChangePasswordUseCase.Params params = ChangePasswordUseCase.buildParams(driverId, oldPassword, newPassword);

        changePasswordView.showProgress();
        changePasswordUseCase.execute(changePasswordObserver, params);
    }

    @Override
    public void validateUserInputs(String driverId, String oldPassword, String newPassword, String confirmNewPassword) {
        disposeObserver(validationObserver);
        validationObserver = createValidationObserver();
        addDisposable(validationObserver);

        ValidationUseCase.Params params = ValidationUseCase.buildParams(driverId, oldPassword, newPassword, confirmNewPassword);

        validationUseCase.execute(validationObserver, params);
    }

    @Override
    public void attach(ChangePasswordContract.ChangePasswordView changePasswordView) {
        this.changePasswordView = changePasswordView;
    }

    @Override
    public void detach() {
        super.detach();
        changePasswordView = null;
    }

    private DisposableObserver<Boolean> createChangePasswordObserver() {
        return new DefaultObserver<Boolean>() {
            @Override
            public void onNext(Boolean isValid) {
                changePasswordView.hideProgress();
                changePasswordView.showSuccess("Change password successfully!");
            }

            @Override
            public void onError(Throwable e) {
                onChangePasswordError(e.getMessage());
            }
        };
    }

    private void onChangePasswordError(String message) {
        changePasswordView.showError(message);
        changePasswordView.hideProgress();
    }

    private DisposableObserver<List<ValidationResult>> createValidationObserver() {
        return new DefaultObserver<List<ValidationResult>>() {
            @Override
            public void onNext(List<ValidationResult> validationResult) {
                changePasswordView.showValidationResult(validationResult);
            }

            @Override
            public void onError(Throwable e) {
                ValidationException exception = (ValidationException)e;
                changePasswordView.showValidationResult(exception.getValidationResults());
            }
        };
    }
}
