package com.android.camera.fragment.settings;

import android.app.ActionBar;
import android.content.SharedPreferences;
import android.os.Bundle;
import androidx.preference.Preference;
import androidx.preference.PreferenceGroup;
import androidx.preference.PreferenceScreen;
import com.android.camera.CameraSettings;
import com.android.camera.R;
import com.android.camera.log.Log;
import com.android.camera.preferences.MoreModeStylePreference;

public class MoreModeFragment extends BasePreferenceFragment {
    public static final String TAG = "MoreModeFragment";
    private boolean mLocked = false;
    protected PreferenceScreen mPreferenceGroup;
    private MoreModeStylePreference mStylePreference;

    private void initializeActivity() {
        this.mPreferenceGroup = getPreferenceScreen();
        PreferenceScreen preferenceScreen = this.mPreferenceGroup;
        if (preferenceScreen != null) {
            preferenceScreen.removeAll();
        }
        addPreferencesFromResource(getPreferenceXml());
        this.mPreferenceGroup = getPreferenceScreen();
        if (this.mPreferenceGroup == null) {
            Log.e(TAG, "fail to init PreferenceGroup");
            getActivity().finish();
        }
        updatePreferences(this.mPreferenceGroup, this.mPreferences);
    }

    /* access modifiers changed from: protected */
    @Override // com.android.camera.fragment.settings.BasePreferenceFragment
    public int getPreferenceXml() {
        return R.xml.camera_preferences_more_mode;
    }

    @Override // com.android.camera.fragment.settings.BasePreferenceFragment
    public void onBackPressed() {
    }

    @Override // androidx.preference.PreferenceFragment
    public void onCreate(Bundle bundle) {
        ActionBar actionBar;
        super.onCreate(bundle);
        this.mLocked = getActivity().getIntent().getBooleanExtra("StartActivityWhenLocked", false);
        if (this.mLocked) {
            getActivity().setShowWhenLocked(true);
        }
        this.mFromWhere = getActivity().getIntent().getIntExtra("from_where", 0);
        if (!(getActivity().getIntent().getCharSequenceExtra(":miui:starting_window_label") == null || (actionBar = getActivity().getActionBar()) == null)) {
            actionBar.setTitle(R.string.pref_more_mode_style_title);
        }
        initializeActivity();
        this.mStylePreference = (MoreModeStylePreference) this.mPreferenceGroup.findPreference(CameraSettings.KEY_CAMERA_MORE_MODE_STYLE);
    }

    public void onPause() {
        super.onPause();
        MoreModeStylePreference moreModeStylePreference = this.mStylePreference;
        if (moreModeStylePreference != null) {
            moreModeStylePreference.onPause();
        }
    }

    @Override // androidx.preference.Preference.OnPreferenceClickListener
    public boolean onPreferenceClick(Preference preference) {
        return false;
    }

    @Override // com.android.camera.fragment.settings.BasePreferenceFragment
    public void onRestart() {
    }

    public void onResume() {
        super.onResume();
        MoreModeStylePreference moreModeStylePreference = this.mStylePreference;
        if (moreModeStylePreference != null) {
            moreModeStylePreference.onResume();
        }
    }

    /* access modifiers changed from: protected */
    @Override // com.android.camera.fragment.settings.BasePreferenceFragment
    public void updateConflictPreference(Preference preference) {
    }

    @Override // com.android.camera.fragment.settings.BasePreferenceFragment
    public void updatePreferences(PreferenceGroup preferenceGroup, SharedPreferences sharedPreferences) {
        super.updatePreferences(preferenceGroup, sharedPreferences);
    }
}
