package edu.h2.layoutdemo.presentation.changepassword;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import edu.h2.layoutdemo.R;
import edu.h2.layoutdemo.data.service.ChangePasswordServiceImpl;
import edu.h2.layoutdemo.domain.interactor.ChangePasswordUseCase;
import edu.h2.layoutdemo.presentation.BaseFragment;
import edu.h2.layoutdemo.presentation.BasePresenter;
import edu.h2.layoutdemo.presentation.BaseView;

public class ChangePasswordFragment extends BaseFragment implements ChangePasswordView {
    private static final String TAG = ChangePasswordFragment.class.getSimpleName();

    private ChangePasswordPresenter changePasswordPresenter;

    public static ChangePasswordFragment newInstance() {
        return new ChangePasswordFragment();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        changePasswordPresenter = new ChangePasswordPresenterImpl(new ChangePasswordUseCase(new ChangePasswordServiceImpl()));
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_change_password, container, false);

        final EditText driverIdEditText = (EditText) root.findViewById(R.id.etDriverId);
        final EditText oldPasswordEditText = (EditText) root.findViewById(R.id.etOldPassword);
        final EditText newPasswordEditText = (EditText) root.findViewById(R.id.etNewPassword);
        final EditText confirmNewPasswordEditText = (EditText) root.findViewById(R.id.etConfirmNewPassword);

        Button changePasswordButton = (Button) root.findViewById(R.id.btnChangePassword);
        changePasswordButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String driverId = driverIdEditText.getText().toString();
                String oldPassword = oldPasswordEditText.getText().toString();
                String newPassword = newPasswordEditText.getText().toString();
                String confirmNewPassword = confirmNewPasswordEditText.getText().toString();
                changePasswordPresenter.changePassword(driverId, oldPassword, newPassword, confirmNewPassword);
            }
        });

        return root;
    }

    @Override
    public void showProgress() {

    }

    @Override
    public void hideProgress() {

    }

    @Override
    public void showError(String message) {

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
    public void showChangePasswordFailure(String message) {
        Toast.makeText(getActivity(), message, Toast.LENGTH_SHORT).show();
    }
}
