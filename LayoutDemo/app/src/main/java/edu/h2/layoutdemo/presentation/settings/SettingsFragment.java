package edu.h2.layoutdemo.presentation.settings;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import edu.h2.layoutdemo.R;
import edu.h2.layoutdemo.presentation.BaseFragment;
import edu.h2.layoutdemo.presentation.BasePresenter;

public class SettingsFragment extends BaseFragment implements SettingsView {
    SettingsPresenter settingsPresenter;

    public static SettingsFragment newInstance() {
        return new SettingsFragment();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_settings, container, false);
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
        return settingsPresenter;
    }
}
