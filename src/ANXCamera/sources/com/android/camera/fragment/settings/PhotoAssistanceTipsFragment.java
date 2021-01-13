package com.android.camera.fragment.settings;

import android.app.ActionBar;
import android.content.SharedPreferences;
import android.os.Bundle;
import androidx.preference.CheckBoxPreference;
import androidx.preference.Preference;
import androidx.preference.PreferenceGroup;
import androidx.preference.PreferenceScreen;
import com.android.camera.CameraSettings;
import com.android.camera.R;
import com.android.camera.data.DataRepository;
import com.android.camera.log.Log;

public class PhotoAssistanceTipsFragment extends BasePreferenceFragment {
    public static final String TAG = "PhotoAssistanceTipsFragment";
    protected PreferenceScreen mPreferenceGroup;

    private void filterByFeature() {
        if (!DataRepository.dataItemFeature()._j()) {
            removePreference(this.mPreferenceGroup, CameraSettings.KEY_PICTURE_FLAW_TIP);
        }
        if (!DataRepository.dataItemFeature().Ij()) {
            removePreference(this.mPreferenceGroup, CameraSettings.KEY_LENS_DIRTY_TIP);
        } else {
            CheckBoxPreference checkBoxPreference = (CheckBoxPreference) this.mPreferenceGroup.findPreference(CameraSettings.KEY_LENS_DIRTY_TIP);
            boolean gh = DataRepository.dataItemFeature().gh();
            if (checkBoxPreference != null) {
                checkBoxPreference.setChecked(gh);
            }
        }
        if (!DataRepository.dataItemFeature().Jl()) {
            removePreference(this.mPreferenceGroup, CameraSettings.KEY_CAMERA_LYING_TIP_SWITCH);
        }
    }

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
        filterByFeature();
        registerListener(this.mPreferenceGroup, this);
        updatePreferences(this.mPreferenceGroup, this.mPreferences);
    }

    /* access modifiers changed from: protected */
    @Override // com.android.camera.fragment.settings.BasePreferenceFragment
    public int getPreferenceXml() {
        return R.xml.camera_preferences_photo_assistance_tips;
    }

    @Override // com.android.camera.fragment.settings.BasePreferenceFragment
    public void onBackPressed() {
    }

    @Override // androidx.preference.PreferenceFragment
    public void onCreate(Bundle bundle) {
        ActionBar actionBar;
        super.onCreate(bundle);
        if (getActivity().getIntent().getBooleanExtra("StartActivityWhenLocked", false)) {
            getActivity().setShowWhenLocked(true);
        }
        if (!(getActivity().getIntent().getCharSequenceExtra(":miui:starting_window_label") == null || (actionBar = getActivity().getActionBar()) == null)) {
            actionBar.setTitle(R.string.photo_assistance_tips_title);
        }
        initializeActivity();
    }

    @Override // com.android.camera.fragment.settings.BasePreferenceFragment, androidx.preference.Preference.OnPreferenceChangeListener
    public boolean onPreferenceChange(Preference preference, Object obj) {
        return super.onPreferenceChange(preference, obj);
    }

    @Override // androidx.preference.Preference.OnPreferenceClickListener
    public boolean onPreferenceClick(Preference preference) {
        return false;
    }

    @Override // com.android.camera.fragment.settings.BasePreferenceFragment
    public void onRestart() {
        updatePreferences(this.mPreferenceGroup, this.mPreferences);
    }

    /* access modifiers changed from: protected */
    @Override // com.android.camera.fragment.settings.BasePreferenceFragment
    public void updateConflictPreference(Preference preference) {
    }

    @Override // com.android.camera.fragment.settings.BasePreferenceFragment
    public void updatePreferences(PreferenceGroup preferenceGroup, SharedPreferences sharedPreferences) {
        if (preferenceGroup != null) {
            int preferenceCount = preferenceGroup.getPreferenceCount();
            for (int i = 0; i < preferenceCount; i++) {
                Preference preference = preferenceGroup.getPreference(i);
                if (preference instanceof CheckBoxPreference) {
                    CheckBoxPreference checkBoxPreference = (CheckBoxPreference) preference;
                    checkBoxPreference.setChecked(sharedPreferences.getBoolean(checkBoxPreference.getKey(), checkBoxPreference.isChecked()));
                    preference.setPersistent(false);
                } else if (preference instanceof PreferenceGroup) {
                    updatePreferences((PreferenceGroup) preference, sharedPreferences);
                } else {
                    Log.v(TAG, "no need update preference for " + preference.getKey());
                }
            }
        }
    }
}
