package com.edulog.driverportal.settings.changepassword.presentation;

import com.edulog.driverportal.base.BasePresenter;
import com.edulog.driverportal.base.BaseView;
import com.edulog.driverportal.settings.changepassword.model.ValidationResult;

import java.util.List;

public interface ChangePasswordContract {
    abstract class Presenter extends BasePresenter<View> {
        public abstract void changePassword(String driverId, String oldPassword, String newPassword);

        public abstract void validateUserInputs(String driverId, String oldPassword, String newPassword, String confirmNewPassword);
    }


    interface View extends BaseView {
        void showValidationResult(List<ValidationResult> validationResults);

        void showSuccess(String message);
    }
}
