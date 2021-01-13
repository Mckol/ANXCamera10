package com.android.camera.fragment.settings;

import android.app.ActionBar;
import android.os.Bundle;
import androidx.preference.Preference;
import androidx.preference.PreferenceScreen;
import b.c.a.c;
import com.android.camera.CameraSettings;
import com.android.camera.R;
import com.android.camera.ThermalHelper;
import com.android.camera.log.Log;

public class VideoDenoiseFragment extends BasePreferenceFragment {
    public static final String TAG = "VideoDenoiseFragment";
    private boolean mLocked = false;
    protected PreferenceScreen mPreferenceGroup;

    private void initializeActivity() {
        this.mPreferenceGroup = getPreferenceScreen();
        PreferenceScreen preferenceScreen = this.mPreferenceGroup;
        if (preferenceScreen != null) {
            preferenceScreen.removeAll();
        }
        addPreferencesFromResource(getPreferenceXml());
        this.mPreferenceGroup = getPreferenceScreen();
        if (this.mPreferenceGroup == null) {
            Log.e("VideoDenoiseFragment", "fail to init PreferenceGroup");
            getActivity().finish();
        }
        registerListener();
        updatePreferences(this.mPreferenceGroup, this.mPreferences);
    }

    private void registerListener() {
        registerListener(this.mPreferenceGroup, this);
        Preference findPreference = this.mPreferenceGroup.findPreference(CameraSettings.KEY_FRONT_DENOISE);
        if (findPreference != null) {
            findPreference.setOnPreferenceClickListener(this);
        }
        Preference findPreference2 = this.mPreferenceGroup.findPreference(CameraSettings.KEY_WIND_DENOISE);
        if (findPreference2 != null) {
            findPreference2.setOnPreferenceClickListener(this);
        }
    }

    /* access modifiers changed from: protected */
    @Override // com.android.camera.fragment.settings.BasePreferenceFragment
    public int getPreferenceXml() {
        return R.xml.camera_preferences_video_denoise;
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
            actionBar.setTitle(R.string.pref_audio_denoise_title);
        }
        initializeActivity();
    }

    public void onDestroy() {
        super.onDestroy();
    }

    public void onPause() {
        super.onPause();
        if (c.ko()) {
            ThermalHelper.updateDisplayFrameRate(false, getActivity().getApplication());
        }
    }

    @Override // com.android.camera.fragment.settings.BasePreferenceFragment, androidx.preference.Preference.OnPreferenceChangeListener
    public boolean onPreferenceChange(Preference preference, Object obj) {
        super.onPreferenceChange(preference, obj);
        return true;
    }

    @Override // androidx.preference.Preference.OnPreferenceClickListener
    public boolean onPreferenceClick(Preference preference) {
        return false;
    }

    @Override // com.android.camera.fragment.settings.BasePreferenceFragment
    public void onRestart() {
        updatePreferences(this.mPreferenceGroup, this.mPreferences);
    }

    public void onResume() {
        super.onResume();
        if (c.ko()) {
            ThermalHelper.updateDisplayFrameRate(true, getActivity().getApplication());
        }
    }

    /* access modifiers changed from: protected */
    @Override // com.android.camera.fragment.settings.BasePreferenceFragment
    public void updateConflictPreference(Preference preference) {
    }
}
