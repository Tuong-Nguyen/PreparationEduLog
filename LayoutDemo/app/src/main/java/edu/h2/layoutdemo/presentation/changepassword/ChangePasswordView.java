package edu.h2.layoutdemo.presentation.changepassword;

import edu.h2.layoutdemo.presentation.BaseView;

public interface ChangePasswordView extends BaseView {
    void showChangePasswordSuccess(String message);

    void showChangePasswordFailure(String message);
}
