package com.edulog.driverportal.settings.changepassword.presentation;

import com.edulog.driverportal.common.presentation.BaseView;
import com.edulog.driverportal.settings.changepassword.model.ValidationResult;

public interface ChangePasswordView extends BaseView {
    void showValidationResult(ValidationResult validationResult);

    void showSuccess(String message);
}
