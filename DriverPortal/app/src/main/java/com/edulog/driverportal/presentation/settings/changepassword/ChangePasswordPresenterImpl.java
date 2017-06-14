package com.edulog.driverportal.presentation.settings.changepassword;

import com.edulog.driverportal.domain.interactor.ChangePasswordUseCase;

import java.io.IOException;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.observers.DisposableObserver;
import okhttp3.ResponseBody;
import retrofit2.Response;

public class ChangePasswordPresenterImpl implements ChangePasswordPresenter {
    private ChangePasswordView changePasswordView;
    private ChangePasswordUseCase changePasswordUseCase;

    public ChangePasswordPresenterImpl(ChangePasswordUseCase changePasswordUseCase) {
        this.changePasswordUseCase = changePasswordUseCase;
    }

    @Override
    public void changePassword(String driverId, String oldPassword, String newPassword) {
        DisposableObserver<Response<ResponseBody>> observer = createChangePasswordObserver();
        ChangePasswordUseCase.Params params = ChangePasswordUseCase.buildParams(driverId, oldPassword, newPassword);
        changePasswordUseCase.execute(observer, params);

        changePasswordView.showProgress();
        changePasswordView.disableRequestChangePassword();
    }

    @Override
    public void validateUserInput(Observable<CharSequence> driverIdObservable,
                                  Observable<CharSequence> oldPasswordObservable,
                                  Observable<CharSequence> newPasswordObservable,
                                  Observable<CharSequence> confirmNewPasswordObservable) {
        Observable<Boolean> observable = Observable.combineLatest(driverIdObservable,
                oldPasswordObservable,
                newPasswordObservable,
                confirmNewPasswordObservable,
                (driverIdOrigin, oldPasswordOrigin, newPasswordOrigin, confirmNewPasswordOrigin) -> {
                    boolean driverIdValid = checkDriverIdValid(driverIdOrigin);
                    boolean oldPasswordValid = checkOldPasswordValid(oldPasswordOrigin);
                    boolean newPasswordValid = checkNewPasswordValid(newPasswordOrigin);
                    boolean confirmNewPasswordValid = checkConfirmNewPasswordValid(newPasswordOrigin, confirmNewPasswordOrigin);

                    return driverIdValid && oldPasswordValid && newPasswordValid && confirmNewPasswordValid;
                });
        observable.subscribe(getUserInputValidationObserver());
    }

    @Override
    public void attach(ChangePasswordView changePasswordView) {
        this.changePasswordView = changePasswordView;
    }

    @Override
    public void detach() {
        changePasswordView = null;
        changePasswordUseCase.dispose();
    }

    @Override
    public void onError(String message) {
        // on error
    }

    private DisposableObserver<Response<ResponseBody>> createChangePasswordObserver() {
        return new DisposableObserver<Response<ResponseBody>>() {
            @Override
            public void onNext(Response<ResponseBody> response) {
                if (response.code() == 200) {
                    changePasswordView.showChangePasswordSuccess("Change password successfully!");
                } else {
                    try {
                        changePasswordView.showError(response.errorBody().string());
                    } catch (IOException ex) {
                        onChangePasswordError(ex.getMessage());
                    }
                }
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

    private Observer<Boolean> getUserInputValidationObserver() {
        return new DisposableObserver<Boolean>() {
            @Override
            public void onNext(Boolean isValid) {
                if (isValid) {
                    changePasswordView.enableRequestChangePassword();
                } else {
                    changePasswordView.disableRequestChangePassword();
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

    private boolean checkDriverIdValid(CharSequence driverIdOrigin) {
        String driverId = driverIdOrigin.toString();
        boolean driverIdValid = driverId.length() >= 2;
        if (!driverIdValid) {
            changePasswordView.showInvalidDriverId("Driver id must be at least 2 characters.");
        } else {
            changePasswordView.hideInvalidDriverId();
        }
        return driverIdValid;
    }

    private boolean checkOldPasswordValid(CharSequence oldPasswordOrigin) {
        String oldPassword = oldPasswordOrigin.toString();
        boolean oldPasswordValid = oldPassword.length() >= 4;
        if (!oldPasswordValid) {
            changePasswordView.showInvalidOldPassword("Password must be at least 4 characters");
        } else {
            changePasswordView.hideInvalidOldPassword();
        }
        return oldPasswordValid;
    }

    private boolean checkNewPasswordValid(CharSequence newPasswordOrigin) {
        String newPassword = newPasswordOrigin.toString();
        boolean newPasswordValid = newPassword.length() >= 4;
        if (!newPasswordValid) {
            changePasswordView.showInvalidNewPassword("Password must be at least 4 characters");
        } else {
            changePasswordView.hideInvalidNewPassword();
        }
        return newPasswordValid;
    }

    private boolean checkConfirmNewPasswordValid(CharSequence newPasswordOrigin, CharSequence confirmNewPasswordOrigin) {
        String newPassword = newPasswordOrigin.toString();
        String confirmNewPassword = confirmNewPasswordOrigin.toString();
        boolean confirmNewPasswordValid = newPassword.equals(confirmNewPassword);
        if (!confirmNewPasswordValid) {
            changePasswordView.showPasswordDoesNotMatch();
        } else {
            changePasswordView.hidePasswordDoesNotMatch();
        }
        return confirmNewPasswordValid;
    }

    private void onChangePasswordError(String message) {
        changePasswordView.showError(message);
        changePasswordView.hideProgress();
        changePasswordView.enableRequestChangePassword();
    }
}
