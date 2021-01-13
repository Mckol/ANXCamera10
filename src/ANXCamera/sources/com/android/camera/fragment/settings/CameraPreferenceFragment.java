package com.android.camera.fragment.settings;

import android.app.ActionBar;
import android.app.AlertDialog;
import android.app.Fragment;
import android.app.KeyguardManager;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.Settings;
import android.text.TextUtils;
import android.view.MenuItem;
import android.view.ViewGroup;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.preference.CheckBoxPreference;
import androidx.preference.Preference;
import androidx.preference.PreferenceGroup;
import androidx.preference.PreferenceScreen;
import b.c.a.c;
import com.android.camera.ActivityLauncher;
import com.android.camera.CameraSettings;
import com.android.camera.PhotoAssistanceTipsActivity;
import com.android.camera.R;
import com.android.camera.RotateDialogController;
import com.android.camera.SettingUiState;
import com.android.camera.ThermalHelper;
import com.android.camera.Util;
import com.android.camera.VideoDenoiseActivity;
import com.android.camera.WatermarkActivity;
import com.android.camera.customization.CustomizationActivity;
import com.android.camera.customization.ShutterSound;
import com.android.camera.data.DataRepository;
import com.android.camera.data.data.config.DataItemConfig;
import com.android.camera.data.data.global.DataItemGlobal;
import com.android.camera.lib.compatibility.util.CompatibilityUtils;
import com.android.camera.log.Log;
import com.android.camera.permission.PermissionManager;
import com.android.camera.statistic.MistatsConstants;
import com.android.camera.statistic.MistatsWrapper;
import com.android.camera.storage.PriorityStorageBroadcastReceiver;
import com.android.camera.storage.Storage;
import com.android.camera.ui.PreviewListPreference;
import com.android.camera.ui.ValuePreference;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

public class CameraPreferenceFragment extends BasePreferenceFragment {
    public static final String INTENT_TYPE = "intent_type";
    public static final String INTENT_VIDEO_QUALITY = "intent_video_quality";
    public static final String PREF_KEY_POPUP_CAMERA = "pref_popup_camera";
    public static final String PREF_KEY_PRIVACY = "pref_privacy";
    public static final String PREF_KEY_RESTORE = "pref_restore";
    public static final String REMOVE_KEYS = "remove_keys";
    public static final String TAG = "CameraPreferenceFragment";
    private AlertDialog mAlertDialog;
    private boolean mAllowLocationAccess = false;
    private CompatibilityUtils.PackageInstallerListener mAppInstalledListener = new CompatibilityUtils.PackageInstallerListener() {
        /* class com.android.camera.fragment.settings.CameraPreferenceFragment.AnonymousClass12 */

        /* JADX WARNING: Code restructure failed: missing block: B:3:0x000a, code lost:
            r2 = (androidx.preference.CheckBoxPreference) r1.this$0.mPreferenceGroup.findPreference(com.android.camera.CameraSettings.KEY_SCAN_QRCODE);
         */
        @Override // com.android.camera.lib.compatibility.util.CompatibilityUtils.PackageInstallerListener
        public void onPackageInstalled(String str, boolean z) {
            final CheckBoxPreference checkBoxPreference;
            if (z && TextUtils.equals(str, "com.xiaomi.scanner") && checkBoxPreference != null) {
                CameraPreferenceFragment.this.getActivity().runOnUiThread(new Runnable() {
                    /* class com.android.camera.fragment.settings.CameraPreferenceFragment.AnonymousClass12.AnonymousClass1 */

                    public void run() {
                        checkBoxPreference.setChecked(true);
                        CameraPreferenceFragment.this.onPreferenceChange(checkBoxPreference, Boolean.TRUE);
                    }
                });
            }
        }
    };
    private Preference mCustomization;
    private AlertDialog mDoubleConfirmActionChooseDialog = null;
    private int mFromWhere;
    private boolean mGoToActivity;
    private boolean mHasReset;
    private boolean mIsFrontCamera;
    private boolean mKeyguardSecureLocked = false;
    private AlertDialog mLocationCTADialog = null;
    private AlertDialog mPermissionNotAskDialog = null;
    private Preference mPhotoAssistanceTips;
    protected PreferenceScreen mPreferenceGroup;
    private AlertDialog mScanAlertDialog;
    private Preference mVideoDenoise;
    private int mVideoIntentQuality;
    private Preference mWatermark;

    private void bringUpDoubleConfirmDlg(final Preference preference, final String str) {
        if (this.mDoubleConfirmActionChooseDialog == null) {
            final boolean snapBoolValue = getSnapBoolValue(str);
            this.mDoubleConfirmActionChooseDialog = RotateDialogController.showSystemAlertDialog(getContext(), getString(R.string.title_snap_double_confirm), getString(R.string.message_snap_double_confirm), getString(R.string.snap_confirmed), new Runnable() {
                /* class com.android.camera.fragment.settings.CameraPreferenceFragment.AnonymousClass3 */

                public void run() {
                    MistatsWrapper.settingClickEvent(CameraSettings.KEY_CAMERA_SNAP, str);
                    Preference preference = preference;
                    if (preference instanceof CheckBoxPreference) {
                        ((CheckBoxPreference) preference).setChecked(snapBoolValue);
                    } else if (preference instanceof PreviewListPreference) {
                        ((PreviewListPreference) preference).setValue(str);
                    }
                    Settings.Secure.putString(CameraPreferenceFragment.this.getActivity().getContentResolver(), "key_long_press_volume_down", CameraSettings.getMiuiSettingsKeyForStreetSnap(str));
                }
            }, null, null, getString(R.string.snap_cancel), new Runnable() {
                /* class com.android.camera.fragment.settings.CameraPreferenceFragment.AnonymousClass4 */

                public void run() {
                    CameraPreferenceFragment.this.mDoubleConfirmActionChooseDialog.dismiss();
                }
            });
            this.mDoubleConfirmActionChooseDialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
                /* class com.android.camera.fragment.settings.CameraPreferenceFragment.AnonymousClass5 */

                public void onDismiss(DialogInterface dialogInterface) {
                    CameraPreferenceFragment.this.mDoubleConfirmActionChooseDialog = null;
                }
            });
        }
    }

    /* access modifiers changed from: private */
    /* access modifiers changed from: public */
    private void closeLocationPreference() {
        ((CheckBoxPreference) this.mPreferenceGroup.findPreference(CameraSettings.KEY_RECORD_LOCATION)).setChecked(false);
        CameraSettings.updateRecordLocationPreference(false);
    }

    private void filterByDeviceCapability() {
    }

    private void filterByDeviceID() {
        int i;
        if (CameraSettings.getRetainCameraModeSettingNeedRemove(this.mFromWhere, this.mIsFrontCamera)) {
            removePreference(this.mPreferenceGroup, CameraSettings.KEY_RETAIN_CAMERA_MODE);
        }
        if (CameraSettings.getFocusShootSettingNeedRemove(this.mFromWhere, this.mIsFrontCamera)) {
            removePreference(this.mPreferenceGroup, "pref_camera_focus_shoot_key");
        }
        dealPreferenceUiState(this.mPreferenceGroup, CameraSettings.KEY_MOVIE_SOLID, CameraSettings.getMovieSolidSettingNeedRemove(this.mFromWhere, this.mIsFrontCamera, this.mVideoIntentQuality));
        dealPreferenceUiState(this.mPreferenceGroup, CameraSettings.KEY_WIND_DENOISE, CameraSettings.getWindDenoiseSettingNeedRemove(this.mFromWhere, this.mIsFrontCamera));
        dealPreferenceUiState(this.mPreferenceGroup, CameraSettings.KEY_VIDEO_DENOISE, CameraSettings.getVideoDenoiseSettingNeedRemove(this.mFromWhere, this.mIsFrontCamera));
        dealPreferenceUiState(this.mPreferenceGroup, CameraSettings.KEY_AI_SHUTTER, CameraSettings.getAiShutterSettingNeedRemove(this.mFromWhere, this.mIsFrontCamera));
        SettingUiState videoTagSettingNeedRemove = CameraSettings.getVideoTagSettingNeedRemove(this.mFromWhere, this.mIsFrontCamera);
        videoTagSettingNeedRemove.toString("videoTagSetting");
        dealPreferenceUiState(this.mPreferenceGroup, CameraSettings.KEY_VIDEO_TAG, videoTagSettingNeedRemove);
        int i2 = 0;
        this.mPreferenceGroup.findPreference(CameraSettings.KEY_VIDEO_DYNAMIC_FRAME_RATE).setSummary(String.format(getResources().getString(R.string.pref_camera_dynamic_frame_rate_summary), 60, 33));
        dealPreferenceUiState(this.mPreferenceGroup, CameraSettings.KEY_VIDEO_DYNAMIC_FRAME_RATE, CameraSettings.getVideoDynamic60fpsSettingNeedRemove(this.mFromWhere, this.mIsFrontCamera));
        boolean dualCameraWaterMarkState = CameraSettings.getDualCameraWaterMarkState(this.mFromWhere, this.mIsFrontCamera);
        boolean timeWaterMarkState = CameraSettings.getTimeWaterMarkState(this.mFromWhere, this.mIsFrontCamera);
        boolean customWaterMarkState = CameraSettings.getCustomWaterMarkState(this.mFromWhere, this.mIsFrontCamera);
        int i3 = this.mFromWhere;
        boolean z = i3 == 186 || i3 == 166 || CameraSettings.isInAllRecordModeSet(i3) || (i = this.mFromWhere) == 210 || i == 185 || i == 187 || i == 205;
        if ((timeWaterMarkState && dualCameraWaterMarkState) || z) {
            removePreference(this.mPreferenceGroup, "pref_watermark_key");
            removePreference(this.mPreferenceGroup, "pref_dualcamera_watermark_key");
            removePreference(this.mPreferenceGroup, "pref_time_watermark_key");
        } else if (!timeWaterMarkState && dualCameraWaterMarkState) {
            removePreference(this.mPreferenceGroup, "pref_watermark_key");
            removePreference(this.mPreferenceGroup, "pref_dualcamera_watermark_key");
        } else if (!timeWaterMarkState || !customWaterMarkState || dualCameraWaterMarkState) {
            removePreference(this.mPreferenceGroup, "pref_dualcamera_watermark_key");
            removePreference(this.mPreferenceGroup, "pref_time_watermark_key");
            SettingUiState waterMarkSettingNeedRemove = CameraSettings.getWaterMarkSettingNeedRemove(this.mFromWhere, this.mIsFrontCamera);
            waterMarkSettingNeedRemove.toString("pref_watermark_key");
            dealPreferenceUiState(this.mPreferenceGroup, "pref_watermark_key", waterMarkSettingNeedRemove);
        } else {
            removePreference(this.mPreferenceGroup, "pref_watermark_key");
            removePreference(this.mPreferenceGroup, "pref_time_watermark_key");
        }
        if (!c.On()) {
            removePreference(this.mPreferenceGroup, CameraSettings.KEY_CAMERA_SOUND);
        }
        if (!c.Kn()) {
            removePreference(this.mPreferenceGroup, CameraSettings.KEY_RECORD_LOCATION);
        }
        if (!Storage.hasSecondaryStorage()) {
            removePreference(this.mPreferenceGroup, CameraSettings.KEY_PRIORITY_STORAGE);
        }
        if (!c.In()) {
            removePreference(this.mPreferenceGroup, CameraSettings.KEY_AUTO_CHROMA_FLASH);
        }
        if (CameraSettings.getLongPressShutterSettingNeedRemove(this.mFromWhere, this.mIsFrontCamera)) {
            removePreference(this.mPreferenceGroup, CameraSettings.KEY_LONG_PRESS_SHUTTER_FEATURE);
        }
        if (!c.Qn()) {
            removePreference(this.mPreferenceGroup, CameraSettings.KEY_CAPTURE_WHEN_STABLE);
        }
        removePreference(this.mPreferenceGroup, CameraSettings.KEY_CAMERA_ASD_NIGHT);
        if (CameraSettings.getCameraSnapSettingNeedRemove(this.mFromWhere, this.mIsFrontCamera)) {
            removePreference(this.mPreferenceGroup, CameraSettings.KEY_CAMERA_SNAP);
        }
        removePreference(this.mPreferenceGroup, CameraSettings.KEY_CAMERA_GROUPSHOT_PRIMITIVE);
        if (!CameraSettings.isSupportedPortrait()) {
            removePreference(this.mPreferenceGroup, CameraSettings.KEY_CAMERA_PORTRAIT_WITH_FACEBEAUTY);
        }
        if (!c.isSupportedOpticalZoom() && !DataRepository.dataItemFeature().wk()) {
            removePreference(this.mPreferenceGroup, CameraSettings.KEY_CAMERA_DUAL_ENABLE);
        }
        if (!c.isSupportedOpticalZoom()) {
            removePreference(this.mPreferenceGroup, CameraSettings.KEY_CAMERA_DUAL_SAT_ENABLE);
        }
        if (!c.isSupportSuperResolution()) {
            removePreference(this.mPreferenceGroup, "pref_camera_super_resolution_key");
        }
        if (!DataRepository.dataItemFeature().kl()) {
            removePreference(this.mPreferenceGroup, CameraSettings.KEY_CAMERA_PARALLEL_PROCESS_ENABLE);
        }
        if (!CameraSettings.isSupportQuickShot()) {
            removePreference(this.mPreferenceGroup, CameraSettings.KEY_CAMERA_QUICK_SHOT_ENABLE);
        }
        if (c.co()) {
            removePreference(this.mPreferenceGroup, CameraSettings.KEY_FACE_DETECTION);
            removePreference(this.mPreferenceGroup, CameraSettings.KEY_CAMERA_FACE_DETECTION_AUTO_HIDDEN);
            removePreference(this.mPreferenceGroup, CameraSettings.KEY_CAMERA_PARALLEL_PROCESS_ENABLE);
            removePreference(this.mPreferenceGroup, CameraSettings.KEY_CAMERA_QUICK_SHOT_ANIM_ENABLE);
            removePreference(this.mPreferenceGroup, CameraSettings.KEY_CAMERA_TOUCH_FOCUS_DELAY_ENABLE);
            removePreference(this.mPreferenceGroup, CameraSettings.KEY_CAMERA_QUICK_SHOT_ENABLE);
            removePreference(this.mPreferenceGroup, CameraSettings.KEY_CAMERA_DUAL_ENABLE);
            removePreference(this.mPreferenceGroup, CameraSettings.KEY_CAMERA_DUAL_SAT_ENABLE);
            removePreference(this.mPreferenceGroup, CameraSettings.KEY_QC_SHARPNESS);
            removePreference(this.mPreferenceGroup, CameraSettings.KEY_QC_CONTRAST);
            removePreference(this.mPreferenceGroup, CameraSettings.KEY_QC_SATURATION);
            removePreference(this.mPreferenceGroup, CameraSettings.KEY_AUTOEXPOSURE);
        }
        if (CameraSettings.getCameraProximityLockSettingNeedRemove(this.mFromWhere, this.mIsFrontCamera)) {
            removePreference(this.mPreferenceGroup, CameraSettings.KEY_CAMERA_PROXIMITY_LOCK);
        }
        if (CameraSettings.getFingerprintCaptureSettingNeedRemove(this.mFromWhere, this.mIsFrontCamera)) {
            removePreference(this.mPreferenceGroup, CameraSettings.KEY_FINGERPRINT_CAPTURE);
        }
        SettingUiState normalWideLDCNeedRemove = CameraSettings.getNormalWideLDCNeedRemove(this.mFromWhere, this.mIsFrontCamera);
        normalWideLDCNeedRemove.toString("KEY_NORMAL_WIDE_LDC");
        dealPreferenceUiState(this.mPreferenceGroup, CameraSettings.KEY_NORMAL_WIDE_LDC, normalWideLDCNeedRemove);
        dealPreferenceUiState(this.mPreferenceGroup, CameraSettings.KEY_ULTRA_WIDE_LDC, CameraSettings.getUltraWideLDCNeedRemove(this.mFromWhere, this.mIsFrontCamera));
        dealPreferenceUiState(this.mPreferenceGroup, CameraSettings.KEY_CAMERA_HIGH_QUALITY_PREFERRED, CameraSettings.getQualityPreferredNeedRemove(this.mFromWhere, this.mIsFrontCamera));
        dealPreferenceUiState(this.mPreferenceGroup, CameraSettings.KEY_JPEG_QUALITY, CameraSettings.getJpegQualityNeedRemove(this.mFromWhere, this.mIsFrontCamera));
        if (!CameraSettings.shouldUltraWideVideoLDCBeVisibleInMode(this.mFromWhere) || !CameraSettings.isUltraWideConfigOpen(this.mFromWhere)) {
            removePreference(this.mPreferenceGroup, CameraSettings.KEY_ULTRA_WIDE_VIDEO_LDC);
        }
        if (CameraSettings.getScanQrcodeSettingNeedRemove(this.mFromWhere, this.mIsFrontCamera)) {
            removePreference(this.mPreferenceGroup, CameraSettings.KEY_SCAN_QRCODE);
        }
        if (!DataRepository.dataItemFeature().zi()) {
            removePreference(this.mPreferenceGroup, PREF_KEY_POPUP_CAMERA);
        }
        removeIncompatibleAdvancePreference();
        if (DataRepository.dataItemFeature()._j()) {
            i2 = 1;
        }
        if (DataRepository.dataItemFeature().Jl()) {
            i2++;
        }
        if (DataRepository.dataItemFeature().Ij()) {
            i2++;
        }
        int i4 = this.mFromWhere;
        if ((i4 != 163 && i4 != 165) || this.mIsFrontCamera) {
            removePreference(this.mPreferenceGroup, CameraSettings.KEY_PHOTO_ASSISTANCE_TIPS);
            removePreference(this.mPreferenceGroup, CameraSettings.KEY_PICTURE_FLAW_TIP);
            removePreference(this.mPreferenceGroup, CameraSettings.KEY_LENS_DIRTY_TIP);
            removePreference(this.mPreferenceGroup, CameraSettings.KEY_CAMERA_LYING_TIP_SWITCH);
        } else if (i2 <= 1) {
            removePreference(this.mPreferenceGroup, CameraSettings.KEY_PHOTO_ASSISTANCE_TIPS);
            if (!DataRepository.dataItemFeature()._j()) {
                removePreference(this.mPreferenceGroup, CameraSettings.KEY_PICTURE_FLAW_TIP);
            }
            if (!DataRepository.dataItemFeature().Ij()) {
                removePreference(this.mPreferenceGroup, CameraSettings.KEY_LENS_DIRTY_TIP);
            }
            if (!DataRepository.dataItemFeature().Jl() && !this.mIsFrontCamera) {
                removePreference(this.mPreferenceGroup, CameraSettings.KEY_CAMERA_LYING_TIP_SWITCH);
            }
        } else {
            removePreference(this.mPreferenceGroup, CameraSettings.KEY_PICTURE_FLAW_TIP);
            removePreference(this.mPreferenceGroup, CameraSettings.KEY_LENS_DIRTY_TIP);
            removePreference(this.mPreferenceGroup, CameraSettings.KEY_CAMERA_LYING_TIP_SWITCH);
        }
        if (!DataRepository.dataItemFeature().qm()) {
            removePreference(this.mPreferenceGroup, CameraSettings.KEY_CAMERA_VIDEO_SAT_ENABLE);
        }
        dealPreferenceUiState(this.mPreferenceGroup, CameraSettings.KEY_VIDEO_ENCODER, CameraSettings.isH265EncoderNeedRemove(this.mFromWhere, this.mIsFrontCamera));
    }

    private void filterByFrom() {
        SettingUiState isHeicImageFormatSelectable = CameraSettings.isHeicImageFormatSelectable(this.mFromWhere, this.mIsFrontCamera);
        isHeicImageFormatSelectable.toString(CameraSettings.KEY_HEIC_FORMAT);
        dealPreferenceUiState(this.mPreferenceGroup, CameraSettings.KEY_HEIC_FORMAT, isHeicImageFormatSelectable);
        if (CameraSettings.getVolumeCameraSettingNeedRemove(this.mFromWhere, this.mIsFrontCamera)) {
            removePreference(this.mPreferenceGroup, CameraSettings.KEY_VOLUME_CAMERA_FUNCTION);
        }
        if (CameraSettings.getVolumeVideoSettingNeedRemove(this.mFromWhere, this.mIsFrontCamera)) {
            removePreference(this.mPreferenceGroup, CameraSettings.KEY_VOLUME_VIDEO_FUNCTION);
        }
        if (CameraSettings.getVolumeProVideoSettingNeedRemove(this.mFromWhere, this.mIsFrontCamera)) {
            removePreference(this.mPreferenceGroup, CameraSettings.KEY_VOLUME_PRO_VIDEO_FUNCTION);
        }
        if (CameraSettings.getVolumeLiveSettingNeedRemove(this.mFromWhere, this.mIsFrontCamera)) {
            removePreference(this.mPreferenceGroup, CameraSettings.KEY_VOLUME_LIVE_FUNCTION);
        }
        if (CameraSettings.getVideoTimeLapseFrameIntervalNeedRemove(this.mFromWhere, this.mIsFrontCamera)) {
            removeFromGroup(this.mPreferenceGroup, CameraSettings.KEY_VIDEO_TIME_LAPSE_FRAME_INTERVAL);
        }
        if (CameraSettings.getMirrorSettingUiNeedRemove(this.mFromWhere, this.mIsFrontCamera)) {
            removeFromGroup(this.mPreferenceGroup, CameraSettings.KEY_FRONT_MIRROR);
        }
        if (CameraSettings.isNearRangeUiNeedRemove(this.mFromWhere, this.mIsFrontCamera)) {
            removeFromGroup(this.mPreferenceGroup, CameraSettings.KEY_CAMERA_NEAR_RANGE);
        }
        dealPreferenceUiState(this.mPreferenceGroup, CameraSettings.KEY_HDR10_VIDEO_ENCODER, CameraSettings.getHDR10UiNeedRemove(this.mFromWhere, this.mIsFrontCamera));
    }

    private void filterByPreference() {
        if (!Util.isLabOptionsVisible()) {
            removePreference(this.mPreferenceGroup, CameraSettings.KEY_FACE_DETECTION);
            removePreference(this.mPreferenceGroup, CameraSettings.KEY_CAMERA_PORTRAIT_WITH_FACEBEAUTY);
            removePreference(this.mPreferenceGroup, CameraSettings.KEY_CAMERA_FACE_DETECTION_AUTO_HIDDEN);
            removePreference(this.mPreferenceGroup, CameraSettings.KEY_CAMERA_DUAL_ENABLE);
            removePreference(this.mPreferenceGroup, CameraSettings.KEY_CAMERA_DUAL_SAT_ENABLE);
            removePreference(this.mPreferenceGroup, CameraSettings.KEY_CAMERA_MFNR_SAT_ENABLE);
            removePreference(this.mPreferenceGroup, CameraSettings.KEY_CAMERA_SR_ENABLE);
            removePreference(this.mPreferenceGroup, CameraSettings.KEY_CAMERA_PARALLEL_PROCESS_ENABLE);
            removePreference(this.mPreferenceGroup, CameraSettings.KEY_CAMERA_QUICK_SHOT_ANIM_ENABLE);
            removePreference(this.mPreferenceGroup, CameraSettings.KEY_CAMERA_TOUCH_FOCUS_DELAY_ENABLE);
            removePreference(this.mPreferenceGroup, CameraSettings.KEY_CAMERA_QUICK_SHOT_ENABLE);
            removePreference(this.mPreferenceGroup, CameraSettings.KEY_LIVE_STICKER_INTERNAL);
            removePreference(this.mPreferenceGroup, CameraSettings.KEY_AUTOEXPOSURE);
            removePreference(this.mPreferenceGroup, CameraSettings.KEY_VIDEO_AUTOEXPOSURE);
            removePreference(this.mPreferenceGroup, CameraSettings.KEY_CAMERA_VIDEO_SAT_ENABLE);
            removePreference(this.mPreferenceGroup, CameraSettings.KEY_VIDEO_SHOW_FACE_VIEW);
        }
        if (CameraSettings.getLongPressViewFinderSettingNeedRemove(this.mFromWhere, this.mIsFrontCamera, this.mKeyguardSecureLocked)) {
            removePreference(this.mPreferenceGroup, CameraSettings.KEY_LONG_PRESS_VIEWFINDER);
        }
        if (CameraSettings.getGoogleLensSuggestionsSettingNeedRemove(this.mFromWhere, this.mIsFrontCamera)) {
            removePreference(this.mPreferenceGroup, CameraSettings.KEY_GOOGLE_LENS_SUGGESTIONS);
        }
    }

    private void filterGroup() {
        filterGroupIfEmpty(CameraSettings.KEY_CATEGORY_DEVICE_SETTING);
        filterGroupIfEmpty(CameraSettings.KEY_CATEGORY_ADVANCE_SETTING);
    }

    private void filterGroupIfEmpty(String str) {
        Preference findPreference = this.mPreferenceGroup.findPreference(str);
        if (findPreference != null && (findPreference instanceof PreferenceGroup) && ((PreferenceGroup) findPreference).getPreferenceCount() == 0) {
            removePreference(this.mPreferenceGroup, str);
        }
    }

    private String getFilterValue(PreviewListPreference previewListPreference, SharedPreferences sharedPreferences) {
        String value = previewListPreference.getValue();
        if (sharedPreferences == null) {
            return value;
        }
        if (value == null) {
            value = previewListPreference.getStoredDefaultValue();
        }
        String key = previewListPreference.getKey();
        char c2 = 65535;
        int hashCode = key.hashCode();
        if (hashCode != -44500048) {
            if (hashCode == 2006116105 && key.equals(CameraSettings.KEY_VOLUME_LIVE_FUNCTION)) {
                c2 = 1;
            }
        } else if (key.equals(CameraSettings.KEY_VOLUME_CAMERA_FUNCTION)) {
            c2 = 0;
        }
        String string = c2 != 0 ? c2 != 1 ? sharedPreferences.getString(previewListPreference.getKey(), value) : CameraSettings.getKeyVolumeLiveFunction(value) : CameraSettings.getVolumeCameraFunction(value);
        if (Util.isStringValueContained(string, previewListPreference.getEntryValues())) {
            return string;
        }
        SharedPreferences.Editor edit = sharedPreferences.edit();
        edit.putString(previewListPreference.getKey(), value);
        edit.apply();
        return value;
    }

    private boolean getSnapBoolValue(String str) {
        return str.equals(getString(R.string.pref_camera_snap_value_take_picture)) || str.equals(getString(R.string.pref_camera_snap_value_take_movie));
    }

    private String getSnapStringValue(boolean z) {
        return z ? getString(R.string.pref_camera_snap_value_take_picture) : getString(R.string.pref_camera_snap_value_off);
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
        registerListener();
        filterByPreference();
        filterByFrom();
        filterByDeviceID();
        filterByIntent();
        filterGroup();
        Preference findPreference = this.mPreferenceGroup.findPreference(CameraSettings.KEY_CATEGORY_QUICK_SETTING);
        Preference findPreference2 = this.mPreferenceGroup.findPreference(CameraSettings.KEY_CATEGORY_MODULE_SETTING);
        if (findPreference2 != null && (findPreference2 instanceof PreferenceGroup) && ((PreferenceGroup) findPreference2).getPreferenceCount() == 0) {
            removePreference(this.mPreferenceGroup, CameraSettings.KEY_CATEGORY_MODULE_SETTING);
            if (findPreference != null && (findPreference instanceof PreferenceGroup)) {
                findPreference.setTitle(R.string.pref_module_settings);
            }
        } else if (findPreference != null && (findPreference instanceof PreferenceGroup)) {
            findPreference.setTitle("");
        }
        filterGroupIfEmpty(CameraSettings.KEY_CATEGORY_QUICK_SETTING);
        updateEntries();
        updatePreferences(this.mPreferenceGroup, this.mPreferences);
        updateConflictPreference(null);
    }

    /* access modifiers changed from: private */
    public void installQRCodeReceiver() {
        new AsyncTask<Void, Void, Void>() {
            /* class com.android.camera.fragment.settings.CameraPreferenceFragment.AnonymousClass11 */

            /* access modifiers changed from: protected */
            public Void doInBackground(Void... voidArr) {
                Log.v(CameraPreferenceFragment.TAG, "install...");
                Util.installPackage(CameraPreferenceFragment.this.getActivity(), "com.xiaomi.scanner", CameraPreferenceFragment.this.mAppInstalledListener, false, true);
                return null;
            }
        }.execute(new Void[0]);
    }

    private static HashMap<String, Boolean> readKeptValues(boolean z) {
        HashMap<String, Boolean> hashMap = new HashMap<>(6);
        hashMap.put(CameraSettings.KEY_CAMERA_FIRST_USE_PERMISSION_SHOWN, Boolean.valueOf(DataRepository.dataItemGlobal().getBoolean(CameraSettings.KEY_CAMERA_FIRST_USE_PERMISSION_SHOWN, true)));
        if (z) {
            for (String str : DataItemGlobal.sUseHints) {
                if (DataRepository.dataItemGlobal().contains(str)) {
                    hashMap.put(str, Boolean.valueOf(DataRepository.dataItemGlobal().getBoolean(str, false)));
                }
            }
        }
        return hashMap;
    }

    private void registerListener() {
        registerListener(this.mPreferenceGroup, this);
        Preference findPreference = this.mPreferenceGroup.findPreference(PREF_KEY_RESTORE);
        if (findPreference != null) {
            findPreference.setOnPreferenceClickListener(this);
        }
        Preference findPreference2 = this.mPreferenceGroup.findPreference(PREF_KEY_PRIVACY);
        if (findPreference2 != null) {
            findPreference2.setOnPreferenceClickListener(this);
        }
        Preference findPreference3 = this.mPreferenceGroup.findPreference(PREF_KEY_POPUP_CAMERA);
        if (findPreference3 != null) {
            findPreference3.setOnPreferenceClickListener(this);
        }
        Preference findPreference4 = this.mPreferenceGroup.findPreference(CameraSettings.KEY_PRIORITY_STORAGE);
        if (findPreference4 != null) {
            findPreference4.setOnPreferenceChangeListener(this);
        }
        Preference findPreference5 = this.mPreferenceGroup.findPreference(CameraSettings.KEY_FACE_DETECTION);
        if (findPreference5 != null) {
            findPreference5.setOnPreferenceClickListener(this);
        }
        Preference findPreference6 = this.mPreferenceGroup.findPreference(CameraSettings.KEY_SCAN_QRCODE);
        if (findPreference6 != null) {
            findPreference6.setOnPreferenceClickListener(this);
        }
        this.mWatermark = this.mPreferenceGroup.findPreference("pref_watermark_key");
        Preference preference = this.mWatermark;
        if (preference != null) {
            preference.setOnPreferenceClickListener(this);
        }
        this.mCustomization = this.mPreferenceGroup.findPreference(CameraSettings.KEY_CUSTOMIZATION);
        Preference preference2 = this.mCustomization;
        if (preference2 != null) {
            preference2.setOnPreferenceClickListener(this);
        }
        this.mPhotoAssistanceTips = this.mPreferenceGroup.findPreference(CameraSettings.KEY_PHOTO_ASSISTANCE_TIPS);
        Preference preference3 = this.mPhotoAssistanceTips;
        if (preference3 != null) {
            preference3.setOnPreferenceClickListener(this);
        }
        this.mVideoDenoise = this.mPreferenceGroup.findPreference(CameraSettings.KEY_VIDEO_DENOISE);
        Preference preference4 = this.mVideoDenoise;
        if (preference4 != null) {
            preference4.setOnPreferenceClickListener(this);
        }
    }

    private void removeIncompatibleAdvancePreference() {
        removePreference(this.mPreferenceGroup, CameraSettings.KEY_QC_CONTRAST);
        removePreference(this.mPreferenceGroup, CameraSettings.KEY_QC_SATURATION);
        removePreference(this.mPreferenceGroup, CameraSettings.KEY_QC_SHARPNESS);
    }

    private void removeNonVideoPreference() {
        removePreference(this.mPreferenceGroup, CameraSettings.KEY_CATEGORY_CAPTURE_SETTING);
    }

    public static void resetPreferences(boolean z) {
        HashMap<String, Boolean> readKeptValues = readKeptValues(z);
        DataItemGlobal dataItemGlobal = DataRepository.dataItemGlobal();
        int intentType = dataItemGlobal.getIntentType();
        dataItemGlobal.resetAll();
        ((DataItemConfig) DataRepository.provider().dataConfig(0, intentType)).resetAll();
        ((DataItemConfig) DataRepository.provider().dataConfig(1, intentType)).resetAll();
        DataRepository.dataItemLive().resetAll();
        DataRepository.dataItemRunning().clearArrayMap();
        DataRepository.getInstance().backUp().clearBackUp();
        rewriteKeptValues(readKeptValues);
        Util.generateWatermark2File();
    }

    private void resetSnapSetting() {
        String string = Settings.Secure.getString(getActivity().getContentResolver(), "key_long_press_volume_down");
        if ("Street-snap-picture".equals(string) || "Street-snap-movie".equals(string)) {
            Settings.Secure.putString(getActivity().getContentResolver(), "key_long_press_volume_down", "none");
        }
    }

    private void resetTimeOutFlag() {
        if (!this.mHasReset) {
            DataRepository.dataItemGlobal().resetTimeOut();
        }
    }

    /* access modifiers changed from: private */
    public void restorePreferences() {
        this.mHasReset = true;
        resetPreferences(false);
        resetSnapSetting();
        ShutterSound.release();
        initializeActivity();
        PriorityStorageBroadcastReceiver.setPriorityStorage(getResources().getBoolean(R.bool.priority_storage));
        onSettingChanged(3);
    }

    private static void rewriteKeptValues(HashMap<String, Boolean> hashMap) {
        for (String str : hashMap.keySet()) {
            DataRepository.dataItemGlobal().putBoolean(str, hashMap.get(str).booleanValue());
        }
    }

    private void showCTADialog(final Fragment fragment) {
        if (this.mLocationCTADialog == null) {
            this.mLocationCTADialog = RotateDialogController.showCTADialog(getActivity(), getActivity().getString(R.string.user_agree), new Runnable() {
                /* class com.android.camera.fragment.settings.CameraPreferenceFragment.AnonymousClass6 */

                public void run() {
                    CameraPreferenceFragment.this.mAllowLocationAccess = true;
                    CameraSettings.updateCTAPreference(true);
                }
            }, getActivity().getString(R.string.user_disagree), new Runnable() {
                /* class com.android.camera.fragment.settings.CameraPreferenceFragment.AnonymousClass7 */

                public void run() {
                    CameraPreferenceFragment.this.mAllowLocationAccess = false;
                    CameraSettings.updateCTAPreference(false);
                }
            }, getActivity().getLayoutInflater().inflate(R.layout.dialog_cta, (ViewGroup) null));
            this.mLocationCTADialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
                /* class com.android.camera.fragment.settings.CameraPreferenceFragment.AnonymousClass8 */

                public void onDismiss(DialogInterface dialogInterface) {
                    if (!CameraPreferenceFragment.this.mAllowLocationAccess) {
                        CameraPreferenceFragment.this.closeLocationPreference();
                    } else if (!PermissionManager.checkCameraLocationPermissions()) {
                        PermissionManager.requestCameraLocationPermissionsByFragment(fragment);
                    }
                    CameraPreferenceFragment.this.mLocationCTADialog.setOnDismissListener(null);
                    CameraPreferenceFragment.this.mLocationCTADialog = null;
                }
            });
        }
    }

    private void updateCameraSound() {
        CheckBoxPreference checkBoxPreference = (CheckBoxPreference) findPreference(CameraSettings.KEY_CAMERA_SOUND);
        if (checkBoxPreference != null) {
            checkBoxPreference.setChecked(DataRepository.dataItemGlobal().getBoolean(CameraSettings.KEY_CAMERA_SOUND, true));
        }
    }

    private void updateEntries() {
        int i;
        int i2;
        int i3;
        int i4;
        PreviewListPreference previewListPreference = (PreviewListPreference) this.mPreferenceGroup.findPreference(CameraSettings.KEY_ANTIBANDING);
        CheckBoxPreference checkBoxPreference = (CheckBoxPreference) this.mPreferenceGroup.findPreference(CameraSettings.KEY_AUTO_CHROMA_FLASH);
        CheckBoxPreference checkBoxPreference2 = (CheckBoxPreference) this.mPreferenceGroup.findPreference(CameraSettings.KEY_CAMERA_SNAP);
        PreviewListPreference previewListPreference2 = (PreviewListPreference) this.mPreferenceGroup.findPreference(CameraSettings.KEY_VOLUME_CAMERA_FUNCTION);
        PreviewListPreference previewListPreference3 = (PreviewListPreference) this.mPreferenceGroup.findPreference(CameraSettings.KEY_VOLUME_VIDEO_FUNCTION);
        PreviewListPreference previewListPreference4 = (PreviewListPreference) this.mPreferenceGroup.findPreference(CameraSettings.KEY_VOLUME_LIVE_FUNCTION);
        if (previewListPreference != null) {
            String defaultValueByKey = CameraSettings.getDefaultValueByKey(CameraSettings.KEY_ANTIBANDING);
            previewListPreference.setValue(defaultValueByKey);
            previewListPreference.setDefaultValue(defaultValueByKey);
        }
        if (checkBoxPreference != null) {
            checkBoxPreference.setChecked(getResources().getBoolean(R.bool.pref_camera_auto_chroma_flash_default));
        }
        if (checkBoxPreference2 != null && c.Sn()) {
            checkBoxPreference2.setChecked(false);
            String string = Settings.Secure.getString(getActivity().getContentResolver(), "key_long_press_volume_down");
            if ("public_transportation_shortcuts".equals(string) || "none".equals(string)) {
                checkBoxPreference2.setChecked(false);
            } else {
                String string2 = DataRepository.dataItemGlobal().getString(CameraSettings.KEY_CAMERA_SNAP, null);
                if (string2 != null) {
                    Settings.Secure.putString(getActivity().getContentResolver(), "key_long_press_volume_down", CameraSettings.getMiuiSettingsKeyForStreetSnap(string2));
                    DataRepository.dataItemGlobal().editor().remove(CameraSettings.KEY_CAMERA_SNAP).apply();
                    checkBoxPreference2.setChecked(getSnapBoolValue(string2));
                } else if ("Street-snap-picture".equals(string) || "Street-snap-movie".equals(string)) {
                    checkBoxPreference2.setChecked(true);
                }
            }
        }
        if (previewListPreference3 != null && ((this.mFromWhere == 162 && this.mIsFrontCamera) || ((this.mFromWhere == 184 && this.mIsFrontCamera) || ((this.mFromWhere == 177 && this.mIsFrontCamera) || ((this.mFromWhere == 168 && this.mIsFrontCamera) || ((this.mFromWhere == 172 && this.mIsFrontCamera) || ((this.mFromWhere == 169 && this.mIsFrontCamera) || (this.mFromWhere == 161 && this.mIsFrontCamera)))))))) {
            String string3 = getString(R.string.pref_camera_volumekey_function_entry_shutter);
            String string4 = getString(R.string.pref_camera_volumekey_function_entry_volume);
            String string5 = getString(R.string.pref_camera_volumekey_function_entryvalue_shutter);
            String string6 = getString(R.string.pref_camera_volumekey_function_entryvalue_volume);
            previewListPreference3.setEntries(new CharSequence[]{string3, string4});
            previewListPreference3.setEntryValues(new CharSequence[]{string5, string6});
            previewListPreference3.setDefaultValue(string5);
            previewListPreference3.setValue(string5);
        }
        if (previewListPreference4 != null && ((i4 = this.mFromWhere) == 183 || i4 == 161 || i4 == 174 || i4 == 179 || i4 == 209 || (i4 == 184 && !DataRepository.dataItemLive().getMimojiStatusManager2().isInMimojiPhoto()))) {
            String[] stringArray = this.mIsFrontCamera ? getResources().getStringArray(R.array.pref_camera_volumekey_function_front_entries) : getResources().getStringArray(R.array.pref_camera_volumekey_function_night_entries);
            String[] stringArray2 = this.mIsFrontCamera ? getResources().getStringArray(R.array.pref_camera_volumekey_function_front_entryvalues) : getResources().getStringArray(R.array.pref_camera_volumekey_function_night_entryvalues);
            previewListPreference4.setEntries(stringArray);
            previewListPreference4.setEntryValues(stringArray2);
            previewListPreference4.setValue(CameraSettings.getKeyVolumeLiveFunction(getString(R.string.pref_camera_volumekey_function_entryvalue_volume)));
        }
        if (previewListPreference2 != null && ((i3 = this.mFromWhere) == 176 || i3 == 166 || ((i3 == 162 && this.mIsFrontCamera) || ((this.mFromWhere == 184 && this.mIsFrontCamera) || ((this.mFromWhere == 177 && this.mIsFrontCamera) || (this.mFromWhere == 173 && this.mIsFrontCamera)))))) {
            String string7 = getString(R.string.pref_camera_volumekey_function_entry_shutter);
            String string8 = getString(R.string.pref_camera_volumekey_function_entry_volume);
            String string9 = getString(R.string.pref_camera_volumekey_function_entryvalue_shutter);
            String string10 = getString(R.string.pref_camera_volumekey_function_entryvalue_volume);
            previewListPreference2.setEntries(new CharSequence[]{string7, string8});
            previewListPreference2.setEntryValues(new CharSequence[]{string9, string10});
            previewListPreference2.setDefaultValue(string9);
            previewListPreference2.setValue(string9);
        }
        if (previewListPreference2 != null && ((this.mFromWhere == 177 && !this.mIsFrontCamera) || ((this.mFromWhere == 184 && !this.mIsFrontCamera) || ((this.mFromWhere == 173 && !this.mIsFrontCamera) || (i2 = this.mFromWhere) == 186 || i2 == 182)))) {
            String[] stringArray3 = getResources().getStringArray(R.array.pref_camera_volumekey_function_night_entries);
            String[] stringArray4 = getResources().getStringArray(R.array.pref_camera_volumekey_function_night_entryvalues);
            previewListPreference2.setEntries(stringArray3);
            previewListPreference2.setEntryValues(stringArray4);
            previewListPreference2.setValue(CameraSettings.getVolumeCameraFunction(getString(R.string.pref_camera_volumekey_function_entryvalue_shutter)));
        }
        if ((previewListPreference2 != null && this.mFromWhere == 163) || (i = this.mFromWhere) == 175 || i == 167 || i == 165 || i == 205 || i == 188) {
            if (this.mIsFrontCamera) {
                String[] stringArray5 = getResources().getStringArray(R.array.pref_camera_volumekey_function_portrait_entries);
                String[] stringArray6 = getResources().getStringArray(R.array.pref_camera_volumekey_function_portrait_entryvalues);
                previewListPreference2.setEntries(stringArray5);
                previewListPreference2.setEntryValues(stringArray6);
            }
            previewListPreference2.setValue(CameraSettings.getVolumeCameraFunction(getString(R.string.pref_camera_volumekey_function_default)));
        }
        if (previewListPreference2 != null && this.mFromWhere == 171) {
            String[] stringArray7 = getResources().getStringArray(R.array.pref_camera_volumekey_function_portrait_entries);
            String[] stringArray8 = getResources().getStringArray(R.array.pref_camera_volumekey_function_portrait_entryvalues);
            previewListPreference2.setEntries(stringArray7);
            previewListPreference2.setEntryValues(stringArray8);
            previewListPreference2.setValue(CameraSettings.getVolumeCameraFunction(""));
        }
        CheckBoxPreference checkBoxPreference3 = (CheckBoxPreference) this.mPreferenceGroup.findPreference("pref_dualcamera_watermark_key");
        if (checkBoxPreference3 != null) {
            checkBoxPreference3.setDefaultValue(Boolean.valueOf(c.N(DataRepository.dataItemFeature().fh())));
            checkBoxPreference3.setChecked(c.N(DataRepository.dataItemFeature().fh()));
        }
        PreviewListPreference previewListPreference5 = (PreviewListPreference) this.mPreferenceGroup.findPreference(CameraSettings.KEY_LONG_PRESS_SHUTTER_FEATURE);
        if (previewListPreference5 == null) {
            return;
        }
        if (!c.Ln() || !DataRepository.dataItemFeature().Oj()) {
            String string11 = getString(R.string.pref_camera_long_pressing_burst_shooting_entry_record);
            String string12 = getString(R.string.pref_camera_long_pressing_burst_shooting_entry_burst);
            String string13 = getString(R.string.pref_camera_long_pressing_burst_shooting_entry_focus);
            String string14 = getString(R.string.pref_camera_long_pressing_burst_shooting_entryvalue_record);
            String string15 = getString(R.string.pref_camera_long_pressing_burst_shooting_entryvalue_burst);
            String string16 = getString(R.string.pref_camera_long_pressing_burst_shooting_entryvalue_focus);
            boolean Ln = c.Ln();
            boolean Oj = DataRepository.dataItemFeature().Oj();
            if (!Ln && Oj) {
                previewListPreference5.setEntries(new String[]{string11, string13});
                previewListPreference5.setEntryValues(new String[]{string14, string16});
                previewListPreference5.setDefaultValue(string14);
            } else if (!Ln || Oj) {
                previewListPreference5.setEntries(new String[]{string13});
                previewListPreference5.setEntryValues(new String[]{string16});
                previewListPreference5.setDefaultValue(string16);
            } else {
                previewListPreference5.setEntries(new String[]{string12, string13});
                previewListPreference5.setEntryValues(new String[]{string15, string16});
                previewListPreference5.setDefaultValue(string15);
            }
        }
    }

    private void updatePhotoAssistanceTips(SharedPreferences sharedPreferences, ValuePreference valuePreference) {
        if (sharedPreferences != null && valuePreference != null) {
            if (DataRepository.dataItemFeature().Jl() && sharedPreferences.getBoolean(CameraSettings.KEY_CAMERA_LYING_TIP_SWITCH, false)) {
                valuePreference.setValue(getString(R.string.pref_photo_assistance_tips_on));
            } else if (DataRepository.dataItemFeature()._j() && sharedPreferences.getBoolean(CameraSettings.KEY_PICTURE_FLAW_TIP, getResources().getBoolean(R.bool.pref_pic_flaw_tip_default))) {
                valuePreference.setValue(getString(R.string.pref_photo_assistance_tips_on));
            } else if (!DataRepository.dataItemFeature().Ij() || !sharedPreferences.getBoolean(CameraSettings.KEY_LENS_DIRTY_TIP, DataRepository.dataItemFeature().gh())) {
                valuePreference.setValue(getString(R.string.pref_photo_assistance_tips_off));
            } else {
                valuePreference.setValue(getString(R.string.pref_photo_assistance_tips_on));
                valuePreference.setDefaultValue(getString(R.string.pref_photo_assistance_tips_on));
            }
        }
    }

    private void updateQRCodeEntry() {
        CheckBoxPreference checkBoxPreference = (CheckBoxPreference) this.mPreferenceGroup.findPreference(CameraSettings.KEY_SCAN_QRCODE);
        if (checkBoxPreference != null && this.mPreferences.getBoolean(CameraSettings.KEY_SCAN_QRCODE, checkBoxPreference.isChecked()) && !CameraSettings.isQRCodeReceiverAvailable(getContext())) {
            Log.v(TAG, "disable QRCodeScan");
            SharedPreferences.Editor edit = this.mPreferences.edit();
            edit.putBoolean(CameraSettings.KEY_SCAN_QRCODE, false);
            edit.apply();
            checkBoxPreference.setChecked(false);
        }
    }

    private void updateVideoDenoise(SharedPreferences sharedPreferences, ValuePreference valuePreference) {
        if (DataRepository.dataItemFeature().cj()) {
            if (CameraSettings.isWindDenoiseOn() || CameraSettings.isFrontDenoiseOn()) {
                valuePreference.setValue(getString(R.string.pref_video_denoise_on));
            } else {
                valuePreference.setValue(getString(R.string.pref_video_denoise_off));
            }
        }
    }

    private void updateWaterMark(SharedPreferences sharedPreferences, ValuePreference valuePreference) {
        if ((!CameraSettings.isSupportedDualCameraWaterMark() || !sharedPreferences.getBoolean("pref_dualcamera_watermark_key", c.N(DataRepository.dataItemFeature().fh()))) && !sharedPreferences.getBoolean("pref_time_watermark_key", false)) {
            valuePreference.setValue(getString(R.string.pref_watermark_off));
        } else {
            valuePreference.setValue(getString(R.string.pref_watermark_on));
        }
    }

    /* access modifiers changed from: protected */
    public void filterByIntent() {
        ArrayList<String> stringArrayListExtra = getActivity().getIntent().getStringArrayListExtra(REMOVE_KEYS);
        if (stringArrayListExtra != null) {
            Iterator<String> it = stringArrayListExtra.iterator();
            while (it.hasNext()) {
                removePreference(this.mPreferenceGroup, it.next());
            }
        }
    }

    /* access modifiers changed from: protected */
    @Override // com.android.camera.fragment.settings.BasePreferenceFragment
    public int getPreferenceXml() {
        return R.xml.camera_other_preferences_new;
    }

    @Override // com.android.camera.fragment.settings.BasePreferenceFragment
    public void onBackPressed() {
        resetTimeOutFlag();
    }

    @Override // androidx.preference.PreferenceFragment
    public void onCreate(Bundle bundle) {
        ActionBar actionBar;
        super.onCreate(bundle);
        this.mIsFrontCamera = CameraSettings.isFrontCamera();
        this.mFromWhere = getActivity().getIntent().getIntExtra("from_where", 0);
        this.mVideoIntentQuality = getActivity().getIntent().getIntExtra(INTENT_VIDEO_QUALITY, -1);
        if (getActivity().getIntent().getBooleanExtra("StartActivityWhenLocked", false)) {
            getActivity().setShowWhenLocked(true);
            this.mKeyguardSecureLocked = ((KeyguardManager) getActivity().getSystemService("keyguard")).isKeyguardSecure();
        } else {
            this.mKeyguardSecureLocked = false;
        }
        CameraSettings.upgradeGlobalPreferences();
        Storage.initStorage(getContext());
        initializeActivity();
        if (getActivity().getIntent().getCharSequenceExtra(":miui:starting_window_label") != null && (actionBar = getActivity().getActionBar()) != null) {
            actionBar.setTitle(R.string.pref_camera_settings_category);
        }
    }

    public boolean onOptionsItemSelected(MenuItem menuItem) {
        if (menuItem.getItemId() != 16908332) {
            return super.onOptionsItemSelected(menuItem);
        }
        resetTimeOutFlag();
        getActivity().finish();
        return true;
    }

    public void onPause() {
        super.onPause();
        if (c.ko()) {
            ThermalHelper.updateDisplayFrameRate(false, getActivity().getApplication());
        }
    }

    @Override // com.android.camera.fragment.settings.BasePreferenceFragment, androidx.preference.Preference.OnPreferenceChangeListener
    public boolean onPreferenceChange(Preference preference, Object obj) {
        String key = preference.getKey();
        if (TextUtils.isEmpty(key)) {
            return true;
        }
        char c2 = 65535;
        int hashCode = key.hashCode();
        String str = CameraSettings.KEY_MOVIE_SOLID;
        switch (hashCode) {
            case -1153050370:
                if (key.equals(str)) {
                    c2 = 2;
                    break;
                }
                break;
            case -44500048:
                if (key.equals(CameraSettings.KEY_VOLUME_CAMERA_FUNCTION)) {
                    c2 = 3;
                    break;
                }
                break;
            case 829778300:
                if (key.equals(CameraSettings.KEY_PRIORITY_STORAGE)) {
                    c2 = 5;
                    break;
                }
                break;
            case 852574760:
                if (key.equals(CameraSettings.KEY_CAMERA_SNAP)) {
                    c2 = 0;
                    break;
                }
                break;
            case 2006116105:
                if (key.equals(CameraSettings.KEY_VOLUME_LIVE_FUNCTION)) {
                    c2 = 4;
                    break;
                }
                break;
            case 2069752292:
                if (key.equals(CameraSettings.KEY_RECORD_LOCATION)) {
                    c2 = 1;
                    break;
                }
                break;
        }
        if (c2 != 0) {
            if (c2 == 1) {
                Log.d(TAG, "onPreferenceChange: KEY_RECORD_LOCATION " + obj);
                if (((Boolean) obj).booleanValue()) {
                    if (!CameraSettings.isAllowCTA()) {
                        showCTADialog(this);
                    } else if (!PermissionManager.checkCameraLocationPermissions()) {
                        PermissionManager.requestCameraLocationPermissionsByFragment(this);
                    }
                }
            } else if (c2 == 2) {
                if (DataRepository.dataItemGlobal().getCurrentMode() == 180) {
                    str = CameraSettings.KEY_PRO_MODE_MOVIE_SOLID;
                }
                DataRepository.dataItemGlobal().putBoolean(str, ((Boolean) obj).booleanValue());
            } else if (c2 == 3) {
                int i = this.mFromWhere;
                if (i == 0) {
                    i = 163;
                }
                if (i != 163 && i != 165 && i != 167 && i != 171 && i != 175) {
                    CameraSettings.setVolumeCameraFunction(i, (String) obj);
                } else if (obj.equals(getString(R.string.pref_camera_volumekey_function_entryvalue_shutter)) || obj.equals(getString(R.string.pref_camera_volumekey_function_entryvalue_volume)) || obj.equals(getString(R.string.pref_camera_volumekey_function_entryvalue_timer))) {
                    String str2 = (String) obj;
                    CameraSettings.setVolumeCameraFunction(163, str2);
                    CameraSettings.setVolumeCameraFunction(171, str2);
                } else if (obj.equals(getString(R.string.pref_camera_volumekey_function_entryvalue_zoom))) {
                    CameraSettings.setVolumeCameraFunction(163, (String) obj);
                }
            } else if (c2 == 4) {
                CameraSettings.setVolumeLiveCameraFunction((String) obj);
            } else if (c2 == 5) {
                PriorityStorageBroadcastReceiver.setPriorityStorage(((Boolean) obj).booleanValue());
                return true;
            }
        } else if (obj != null) {
            String string = getString(R.string.pref_camera_snap_value_off);
            if (obj instanceof Boolean) {
                string = getSnapStringValue(((Boolean) obj).booleanValue());
            } else if (obj instanceof String) {
                string = (String) obj;
            }
            if ((string.equals(getString(R.string.pref_camera_snap_value_take_picture)) || string.equals(getString(R.string.pref_camera_snap_value_take_movie))) && "public_transportation_shortcuts".equals(Settings.Secure.getString(getActivity().getContentResolver(), "key_long_press_volume_down"))) {
                bringUpDoubleConfirmDlg(preference, string);
                return false;
            }
            Settings.Secure.putString(getActivity().getContentResolver(), "key_long_press_volume_down", CameraSettings.getMiuiSettingsKeyForStreetSnap(string));
            MistatsWrapper.settingClickEvent(MistatsConstants.Setting.PARAM_CAMERA_SNAP, string);
            return true;
        }
        return super.onPreferenceChange(preference, obj);
    }

    /* JADX INFO: Can't fix incorrect switch cases order, some code will duplicate */
    @Override // androidx.preference.Preference.OnPreferenceClickListener
    public boolean onPreferenceClick(Preference preference) {
        char c2;
        String key = preference.getKey();
        if (TextUtils.isEmpty(key)) {
            return true;
        }
        switch (key.hashCode()) {
            case -1717659284:
                if (key.equals(PREF_KEY_PRIVACY)) {
                    c2 = 1;
                    break;
                }
                c2 = 65535;
                break;
            case -1620641004:
                if (key.equals(CameraSettings.KEY_SCAN_QRCODE)) {
                    c2 = 7;
                    break;
                }
                c2 = 65535;
                break;
            case -1590444841:
                if (key.equals(CameraSettings.KEY_CUSTOMIZATION)) {
                    c2 = 5;
                    break;
                }
                c2 = 65535;
                break;
            case -305641358:
                if (key.equals(PREF_KEY_RESTORE)) {
                    c2 = 0;
                    break;
                }
                c2 = 65535;
                break;
            case 76287668:
                if (key.equals(PREF_KEY_POPUP_CAMERA)) {
                    c2 = 2;
                    break;
                }
                c2 = 65535;
                break;
            case 1069539048:
                if (key.equals("pref_watermark_key")) {
                    c2 = 3;
                    break;
                }
                c2 = 65535;
                break;
            case 1578520153:
                if (key.equals(CameraSettings.KEY_VIDEO_DENOISE)) {
                    c2 = 4;
                    break;
                }
                c2 = 65535;
                break;
            case 2047422134:
                if (key.equals(CameraSettings.KEY_PHOTO_ASSISTANCE_TIPS)) {
                    c2 = 6;
                    break;
                }
                c2 = 65535;
                break;
            default:
                c2 = 65535;
                break;
        }
        switch (c2) {
            case 0:
                if (this.mAlertDialog != null) {
                    return true;
                }
                MistatsWrapper.settingClickEvent(MistatsConstants.Setting.PREF_KEY_RESTORE, null);
                this.mAlertDialog = RotateDialogController.showSystemAlertDialog(getContext(), getString(R.string.confirm_restore_title), getString(R.string.confirm_restore_message), getString(17039370), new a(this), null, null, getString(17039360), null);
                this.mAlertDialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
                    /* class com.android.camera.fragment.settings.CameraPreferenceFragment.AnonymousClass9 */

                    public void onDismiss(DialogInterface dialogInterface) {
                        CameraPreferenceFragment.this.mAlertDialog = null;
                    }
                });
                return true;
            case 1:
                ActivityLauncher.launchPrivacyPolicyWebpage(getActivity());
                MistatsWrapper.settingClickEvent(MistatsConstants.Setting.PREF_KEY_PRIVACY, null);
                return true;
            case 2:
                this.mGoToActivity = true;
                ActivityLauncher.launchPopupCameraSetting(getActivity());
                MistatsWrapper.settingClickEvent(MistatsConstants.Setting.PREF_KEY_POPUP_CAMERA, null);
                return true;
            case 3:
                Intent intent = new Intent(getActivity(), WatermarkActivity.class);
                intent.putExtra("from_where", this.mFromWhere);
                intent.putExtra(":miui:starting_window_label", getResources().getString(R.string.pref_watermark_title));
                if (getActivity().getIntent().getBooleanExtra("StartActivityWhenLocked", false)) {
                    intent.putExtra("StartActivityWhenLocked", true);
                }
                this.mGoToActivity = true;
                startActivity(intent);
                return true;
            case 4:
                Intent intent2 = new Intent(getActivity(), VideoDenoiseActivity.class);
                intent2.putExtra("from_where", this.mFromWhere);
                intent2.putExtra(":miui:starting_window_label", getResources().getString(R.string.pref_audio_denoise_title));
                if (getActivity().getIntent().getBooleanExtra("StartActivityWhenLocked", false)) {
                    intent2.putExtra("StartActivityWhenLocked", true);
                }
                this.mGoToActivity = true;
                startActivity(intent2);
                return true;
            case 5:
                Intent intent3 = new Intent(getActivity(), CustomizationActivity.class);
                intent3.putExtra("from_where", this.mFromWhere);
                intent3.putExtra(":miui:starting_window_label", getResources().getString(R.string.pref_customization_title));
                if (getActivity().getIntent().getBooleanExtra("StartActivityWhenLocked", false)) {
                    intent3.putExtra("StartActivityWhenLocked", true);
                }
                this.mGoToActivity = true;
                startActivity(intent3);
                MistatsWrapper.settingClickEvent("attr_custom_camera", null);
                MistatsWrapper.customizeCameraSettingClick("attr_custom_camera");
                return true;
            case 6:
                Intent intent4 = new Intent(getActivity(), PhotoAssistanceTipsActivity.class);
                intent4.putExtra(":miui:starting_window_label", getResources().getString(R.string.photo_assistance_tips_title));
                try {
                    if (getActivity().getIntent().getBooleanExtra("StartActivityWhenLocked", false)) {
                        intent4.putExtra("StartActivityWhenLocked", true);
                    }
                    this.mGoToActivity = true;
                    startActivity(intent4);
                    HashMap hashMap = new HashMap();
                    hashMap.put(MistatsConstants.FeatureName.VALUE_PHOTO_ASSISTANCE_TIP_CLICK, 1);
                    MistatsWrapper.mistatEvent(MistatsConstants.FeatureName.KEY_COMMON_TIPS, hashMap);
                } catch (Exception unused) {
                }
                return true;
            case 7:
                if (!CameraSettings.isQRCodeReceiverAvailable(getActivity())) {
                    if (this.mScanAlertDialog != null) {
                        return true;
                    }
                    this.mScanAlertDialog = RotateDialogController.showSystemAlertDialog(getActivity(), getString(R.string.confirm_install_scanner_title), getString(R.string.confirm_install_scanner_message), getString(R.string.install_confirmed), new b(this), null, null, getString(17039360), null);
                    this.mScanAlertDialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
                        /* class com.android.camera.fragment.settings.CameraPreferenceFragment.AnonymousClass10 */

                        public void onDismiss(DialogInterface dialogInterface) {
                            CameraPreferenceFragment.this.mScanAlertDialog = null;
                        }
                    });
                    return true;
                }
                break;
        }
        return false;
    }

    public void onRequestPermissionsResult(int i, @NonNull String[] strArr, @NonNull int[] iArr) {
        super.onRequestPermissionsResult(i, strArr, iArr);
        String str = TAG;
        Log.d(str, "onRequestPermissionsResult: requestCode = " + i);
        if (i == PermissionManager.getCameraLocationPermissionRequestCode() && !PermissionManager.checkCameraLocationPermissions()) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(getActivity(), "android.permission.ACCESS_FINE_LOCATION") || ActivityCompat.shouldShowRequestPermissionRationale(getActivity(), "android.permission.ACCESS_COARSE_LOCATION")) {
                closeLocationPreference();
                return;
            }
            Log.d(TAG, "onRequestPermissionsResult: not ask again!");
            if (this.mPermissionNotAskDialog == null) {
                this.mPermissionNotAskDialog = RotateDialogController.showSystemAlertDialog(getContext(), null, getString(R.string.location_permission_not_ask_again), getString(R.string.location_permission_not_ask_again_go_to_settings), new Runnable() {
                    /* class com.android.camera.fragment.settings.CameraPreferenceFragment.AnonymousClass1 */

                    public void run() {
                        CameraPreferenceFragment cameraPreferenceFragment = CameraPreferenceFragment.this;
                        cameraPreferenceFragment.startActivity(new Intent("android.settings.APPLICATION_DETAILS_SETTINGS", Uri.parse("package:" + CameraPreferenceFragment.this.getActivity().getPackageName())));
                    }
                }, null, null, getString(17039360), new Runnable() {
                    /* class com.android.camera.fragment.settings.CameraPreferenceFragment.AnonymousClass2 */

                    public void run() {
                        CameraPreferenceFragment.this.closeLocationPreference();
                    }
                });
            }
        }
    }

    @Override // com.android.camera.fragment.settings.BasePreferenceFragment
    public void onRestart() {
        if (this.mGoToActivity) {
            updateWaterMark(this.mPreferences, (ValuePreference) this.mWatermark);
            updatePhotoAssistanceTips(this.mPreferences, (ValuePreference) this.mPhotoAssistanceTips);
            updateVideoDenoise(this.mPreferences, (ValuePreference) this.mVideoDenoise);
            this.mGoToActivity = false;
        } else if (getActivity().getReferrer() == null || !TextUtils.equals(getActivity().getReferrer().getHost(), getActivity().getPackageName())) {
            initializeActivity();
        } else {
            getActivity().finish();
        }
    }

    public void onResume() {
        super.onResume();
        if (c.ko()) {
            ThermalHelper.updateDisplayFrameRate(true, getActivity().getApplication());
        }
        updateQRCodeEntry();
        updateCameraSound();
        if (Util.isLabOptionsVisible()) {
            Toast.makeText(getContext(), (int) R.string.camera_facedetection_sub_option_hint, 1).show();
        }
    }

    @Override // com.android.camera.fragment.settings.BasePreferenceFragment
    public void updateConflictPreference(Preference preference) {
        dealPreferenceUiState(this.mPreferenceGroup, CameraSettings.KEY_MOVIE_SOLID, CameraSettings.getMovieSolidSettingNeedRemove(this.mFromWhere, this.mIsFrontCamera, this.mVideoIntentQuality));
        dealPreferenceUiState(this.mPreferenceGroup, CameraSettings.KEY_VIDEO_ENCODER, CameraSettings.isH265EncoderNeedRemove(this.mFromWhere, this.mIsFrontCamera));
    }

    @Override // com.android.camera.fragment.settings.BasePreferenceFragment
    public void updatePreferences(PreferenceGroup preferenceGroup, SharedPreferences sharedPreferences) {
        if (preferenceGroup != null) {
            int preferenceCount = preferenceGroup.getPreferenceCount();
            for (int i = 0; i < preferenceCount; i++) {
                Preference preference = preferenceGroup.getPreference(i);
                if (preference instanceof ValuePreference) {
                    if (preference.getKey().equals("pref_watermark_key")) {
                        updateWaterMark(sharedPreferences, (ValuePreference) preference);
                    }
                    if (preference.getKey().equals(CameraSettings.KEY_PHOTO_ASSISTANCE_TIPS)) {
                        updatePhotoAssistanceTips(sharedPreferences, (ValuePreference) preference);
                    }
                    if (preference.getKey().equals(CameraSettings.KEY_VIDEO_DENOISE)) {
                        updateVideoDenoise(sharedPreferences, (ValuePreference) preference);
                    }
                } else if (preference instanceof PreviewListPreference) {
                    PreviewListPreference previewListPreference = (PreviewListPreference) preference;
                    previewListPreference.setValue(getFilterValue(previewListPreference, sharedPreferences));
                    preference.setPersistent(false);
                } else if (preference instanceof CheckBoxPreference) {
                    CheckBoxPreference checkBoxPreference = (CheckBoxPreference) preference;
                    String key = checkBoxPreference.getKey();
                    boolean isChecked = checkBoxPreference.isChecked();
                    if (CameraSettings.KEY_MOVIE_SOLID.equals(key)) {
                        boolean isMovieSolidOn = CameraSettings.isMovieSolidOn();
                        if (CameraSettings.isVideoEisBeautyMeanwhileEnable()) {
                            isMovieSolidOn = true;
                        }
                        checkBoxPreference.setChecked(isMovieSolidOn);
                    } else {
                        checkBoxPreference.setChecked(sharedPreferences.getBoolean(key, isChecked));
                    }
                    preference.setPersistent(false);
                    if (CameraSettings.KEY_RECORD_LOCATION.equals(key)) {
                        preference.setEnabled(!this.mKeyguardSecureLocked);
                        if (!PermissionManager.checkCameraLocationPermissions() && CameraSettings.isRecordLocation()) {
                            checkBoxPreference.setChecked(false);
                            CameraSettings.updateRecordLocationPreference(false);
                        }
                    }
                    if (CameraSettings.KEY_FRONT_MIRROR.equals(key) && c.Xm()) {
                        checkBoxPreference.setChecked(false);
                    }
                } else if (preference instanceof PreferenceGroup) {
                    updatePreferences((PreferenceGroup) preference, sharedPreferences);
                } else {
                    Log.v(TAG, "no need update preference for " + preference.getKey());
                }
            }
        }
    }
}
