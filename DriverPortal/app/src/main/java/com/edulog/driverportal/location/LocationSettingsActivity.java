package com.edulog.driverportal.location;

import android.app.Activity;
import android.content.Intent;
import android.content.IntentSender;
import android.os.Bundle;
import android.support.annotation.Nullable;

import com.google.android.gms.common.api.Status;

public class LocationSettingsActivity extends Activity {
    static final String ARG_STATUS = "status";
    static final int REQUEST_LOCATION_PERMISSION = 123;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (savedInstanceState == null) {
            handleIntent();
        }
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        setIntent(intent);
        handleIntent();
    }

    private void handleIntent() {
        Status status = getIntent().getParcelableExtra(ARG_STATUS);
        if (status != null) {
            try {
                status.startResolutionForResult(this, REQUEST_LOCATION_PERMISSION);
            } catch (IntentSender.SendIntentException ex) {
                ex.printStackTrace();
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_LOCATION_PERMISSION) {
            SettingHandlerSingleOnSubscribe.onResolutionResult(resultCode);
            finish();
        }
    }
}
