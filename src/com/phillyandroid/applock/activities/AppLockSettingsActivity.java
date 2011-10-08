package com.phillyandroid.applock.activities;

import android.os.Bundle;
import android.preference.PreferenceActivity;

import com.phillyandroid.applock.R;
import com.phillyandroid.applock.lockpattern.LockPatternUtils;

public class AppLockSettingsActivity extends PreferenceActivity {
	private final static String TAG = "AppLockSettingsActivity";
	private final static int DISABLE_LOCKPATTERN = 1;
	private final static int ENABLE_LOCKPATTERN = 2;
	private final static int CHANGE_LOCKPATTERN = 3;
	private LockPatternUtils mLockPatternUtils;
	
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.settings);
    }
}
