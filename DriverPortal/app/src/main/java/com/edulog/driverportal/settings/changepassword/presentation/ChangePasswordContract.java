package com.edulog.driverportal.settings.changepassword.presentation;

import com.edulog.driverportal.common.presentation.BasePresenter;
import com.edulog.driverportal.common.presentation.BaseView;
import com.edulog.driverportal.common.validation.model.ValidationResult;

import java.util.List;

public interface ChangePasswordContract {
    abstract class ChangePasswordPresenter extends BasePresenter<ChangePasswordView> {
        public abstract void changePassword(String driverId, String oldPassword, String newPassword);

        public abstract void validateUserInputs(String driverId, String oldPassword, String newPassword, String confirmNewPassword);
    }


    interface ChangePasswordView extends BaseView {
        void showValidationResult(List<ValidationResult> validationResults);

        void showSuccess(String message);
    }
}
