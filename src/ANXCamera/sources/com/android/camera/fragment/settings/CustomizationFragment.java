package com.android.camera.fragment.settings;

import android.app.ActionBar;
import android.content.Intent;
import android.os.Bundle;
import androidx.preference.Preference;
import androidx.preference.PreferenceScreen;
import com.android.camera.ModeEditorActivity;
import com.android.camera.MoreModeActivity;
import com.android.camera.R;
import com.android.camera.customization.ActivityCustomSound;
import com.android.camera.customization.CustomTintActivity;
import com.android.camera.customization.ShutterSound;
import com.android.camera.data.DataRepository;
import com.android.camera.log.Log;
import com.android.camera.statistic.MistatsConstants;
import com.android.camera.statistic.MistatsWrapper;
import com.android.camera.ui.ValuePreference;

public class CustomizationFragment extends BasePreferenceFragment {
    public static final String KEY_CUSTOM_MODE = "pref_custom_feature_layout";
    public static final String KEY_CUSTOM_SHUTTER_SOUND = "custom_shutter_sound_key";
    public static final String KEY_CUSTOM_TINT = "custom_tint_key";
    public static final String KEY_MORE_MODE = "pref_custom_more_mode";
    public static final String TAG = "Customization";
    private ValuePreference mCustomShutterSound;
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
            Log.e(TAG, "fail to init PreferenceGroup");
            getActivity().finish();
        }
        updatePreferences(this.mPreferenceGroup, this.mPreferences);
        this.mCustomShutterSound = (ValuePreference) findPreference(KEY_CUSTOM_SHUTTER_SOUND);
        ValuePreference valuePreference = this.mCustomShutterSound;
        if (valuePreference != null) {
            valuePreference.setOnPreferenceClickListener(this);
            this.mCustomShutterSound.setValue(ShutterSound.readSoundName(getContext()));
            this.mCustomShutterSound.setDefaultValue(ShutterSound.readSoundName(getContext()));
        }
        ValuePreference valuePreference2 = (ValuePreference) findPreference(KEY_CUSTOM_TINT);
        if (valuePreference2 != null) {
            valuePreference2.setOnPreferenceClickListener(this);
        }
        if (DataRepository.dataItemFeature().Xj() || !DataRepository.dataItemGlobal().isNormalIntent()) {
            removePreference(this.mPreferenceGroup, KEY_CUSTOM_MODE);
        } else {
            Preference findPreference = findPreference(KEY_CUSTOM_MODE);
            if (findPreference != null) {
                findPreference.setOnPreferenceClickListener(this);
            }
        }
        if (DataRepository.dataItemFeature().Xj() || !DataRepository.dataItemGlobal().isNormalIntent()) {
            removePreference(this.mPreferenceGroup, KEY_MORE_MODE);
            return;
        }
        Preference findPreference2 = findPreference(KEY_MORE_MODE);
        if (findPreference2 != null) {
            findPreference2.setOnPreferenceClickListener(this);
        }
    }

    /* access modifiers changed from: protected */
    @Override // com.android.camera.fragment.settings.BasePreferenceFragment
    public int getPreferenceXml() {
        return R.xml.camera_preferences_customization;
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
            actionBar.setTitle(R.string.pref_customization_title);
        }
        initializeActivity();
    }

    /* JADX INFO: Can't fix incorrect switch cases order, some code will duplicate */
    @Override // androidx.preference.Preference.OnPreferenceClickListener
    public boolean onPreferenceClick(Preference preference) {
        char c2;
        String key = preference.getKey();
        switch (key.hashCode()) {
            case -1531632123:
                if (key.equals(KEY_CUSTOM_MODE)) {
                    c2 = 0;
                    break;
                }
                c2 = 65535;
                break;
            case -1278534757:
                if (key.equals(KEY_MORE_MODE)) {
                    c2 = 1;
                    break;
                }
                c2 = 65535;
                break;
            case -1172234199:
                if (key.equals(KEY_CUSTOM_TINT)) {
                    c2 = 3;
                    break;
                }
                c2 = 65535;
                break;
            case 1650712591:
                if (key.equals(KEY_CUSTOM_SHUTTER_SOUND)) {
                    c2 = 2;
                    break;
                }
                c2 = 65535;
                break;
            default:
                c2 = 65535;
                break;
        }
        if (c2 == 0) {
            Intent intent = new Intent(getActivity(), ModeEditorActivity.class);
            intent.putExtra("from_where", this.mFromWhere);
            if (getActivity().getIntent().getBooleanExtra("StartActivityWhenLocked", false)) {
                intent.putExtra("StartActivityWhenLocked", true);
            }
            startActivity(intent);
            MistatsWrapper.customizeCameraSettingClick(MistatsConstants.CUSTOMIZE_CAMERA.PREF_KEY_CUSTOM_MODE_SETTING);
        } else if (c2 == 1) {
            Intent intent2 = new Intent(getActivity(), MoreModeActivity.class);
            intent2.putExtra("from_where", this.mFromWhere);
            intent2.putExtra(":miui:starting_window_label", getResources().getString(R.string.pref_more_mode_style_title));
            if (getActivity().getIntent().getBooleanExtra("StartActivityWhenLocked", false)) {
                intent2.putExtra("StartActivityWhenLocked", true);
            }
            startActivity(intent2);
            MistatsWrapper.customizeCameraSettingClick("attr_more_mode");
        } else if (c2 == 2) {
            Intent intent3 = new Intent(getActivity(), ActivityCustomSound.class);
            intent3.putExtra("from_where", this.mFromWhere);
            if (getActivity().getIntent().getBooleanExtra("StartActivityWhenLocked", false)) {
                intent3.putExtra("StartActivityWhenLocked", true);
            }
            startActivity(intent3);
            MistatsWrapper.settingClickEvent("attr_edit_sound", MistatsConstants.Setting.CUSTOM_SOUND_OUTTER);
            MistatsWrapper.customizeCameraSettingClick("attr_edit_sound");
            return true;
        } else if (c2 == 3) {
            Intent intent4 = new Intent(getActivity(), CustomTintActivity.class);
            intent4.putExtra("from_where", this.mFromWhere);
            if (getActivity().getIntent().getBooleanExtra("StartActivityWhenLocked", false)) {
                intent4.putExtra("StartActivityWhenLocked", true);
            }
            startActivity(intent4);
            MistatsWrapper.settingClickEvent("attr_edit_tint", MistatsConstants.Setting.CUSTOM_TINT_OUTTER);
            MistatsWrapper.customizeCameraSettingClick("attr_edit_tint");
            return true;
        }
        return false;
    }

    @Override // com.android.camera.fragment.settings.BasePreferenceFragment
    public void onRestart() {
        initializeActivity();
    }

    /* access modifiers changed from: protected */
    @Override // com.android.camera.fragment.settings.BasePreferenceFragment
    public void updateConflictPreference(Preference preference) {
    }
}
