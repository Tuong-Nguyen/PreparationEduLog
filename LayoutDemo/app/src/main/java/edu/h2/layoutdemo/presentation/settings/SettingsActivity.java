package edu.h2.layoutdemo.presentation.settings;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.os.Bundle;

import edu.h2.layoutdemo.R;
import edu.h2.layoutdemo.presentation.BaseActivity;

public class SettingsActivity extends BaseActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        FragmentManager fm = getSupportFragmentManager();
        Fragment fragment = fm.findFragmentById(R.id.contentFrame);
        if (fragment == null) {
            fragment = SettingsFragment.newInstance();
            fm.beginTransaction()
                    .add(R.id.contentFrame, fragment)
                    .commit();
        }
    }
}
