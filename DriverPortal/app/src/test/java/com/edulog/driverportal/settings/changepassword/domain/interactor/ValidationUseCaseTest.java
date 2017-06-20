package com.edulog.driverportal.settings.changepassword.domain.interactor;

import com.edulog.driverportal.RxImmediateSchedulerRule;
import com.edulog.driverportal.settings.changepassword.model.ValidationResult;

import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Test;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.observers.TestObserver;

public class ValidationUseCaseTest {
    @ClassRule
    public static final RxImmediateSchedulerRule schedulers = new RxImmediateSchedulerRule();

    private ValidationUseCase validationUseCase;

    @Before
    public void setUp() {
        validationUseCase = new ValidationUseCase(AndroidSchedulers.mainThread());
    }

    @Test
    public void execute_validInformation_success() {
        TestObserver<ValidationResult> observer = new TestObserver<>();
        ValidationUseCase.Params params = ValidationUseCase.buildParams("driver_id", "old_pass", "new_pass", "new_pass");

        validationUseCase.execute(observer, params);

        observer.assertValueAt(0, ValidationResult::isValid);
        observer.assertValueAt(1, ValidationResult::isValid);
        observer.assertValueAt(2, ValidationResult::isValid);
        observer.assertValueAt(3, ValidationResult::isValid);
        observer.assertValueAt(4, ValidationResult::isValid);

    }

    @Test
    public void execute_invalidDriverId_failure() {
        TestObserver<ValidationResult> observer = new TestObserver<>();
        ValidationUseCase.Params params = ValidationUseCase.buildParams("", "old_pass", "new_pass", "new_pass");

        validationUseCase.execute(observer, params);

        observer.assertValueAt(0, validationResult -> !validationResult.isValid());
        observer.assertValueAt(4, validationResult -> !validationResult.isValid());

    }

    @Test
    public void execute_invalidOldPassword_failure() {
        TestObserver<ValidationResult> observer = new TestObserver<>();
        ValidationUseCase.Params params = ValidationUseCase.buildParams("driver_id", "", "new_pass", "new_pass");

        validationUseCase.execute(observer, params);

        observer.assertValueAt(1, validationResult -> !validationResult.isValid());
        observer.assertValueAt(4, validationResult -> !validationResult.isValid());
    }

    @Test
    public void execute_invalidNewPassword_failure() {
        TestObserver<ValidationResult> observer = new TestObserver<>();
        ValidationUseCase.Params params = ValidationUseCase.buildParams("driver_id", "old_password", "aa", "aa");

        validationUseCase.execute(observer, params);

        observer.assertValueAt(2, validationResult -> !validationResult.isValid());
        observer.assertValueAt(4, validationResult -> !validationResult.isValid());
    }

    @Test
    public void execute_passwordDoesNotMatch_failure() {
        TestObserver<ValidationResult> observer = new TestObserver<>();
        ValidationUseCase.Params params = ValidationUseCase.buildParams("driver_id", "old_password", "new_pass", "abcxxxxx");

        validationUseCase.execute(observer, params);

        observer.assertValueAt(3, validationResult -> !validationResult.isValid());
        observer.assertValueAt(4, validationResult -> !validationResult.isValid());
    }
}
