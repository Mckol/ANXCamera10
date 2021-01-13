package com.android.camera.data.data.runing;

import android.util.SparseBooleanArray;
import androidx.annotation.MainThread;
import androidx.collection.SimpleArrayMap;
import b.c.a.c;
import com.android.camera.CameraSettings;
import com.android.camera.R;
import com.android.camera.Util;
import com.android.camera.data.DataRepository;
import com.android.camera.data.data.ComponentDataItem;
import com.android.camera.data.data.DataItemBase;
import com.android.camera.data.data.config.ComponentConfigAmbilight;
import com.android.camera.data.data.config.ComponentConfigFilter;
import com.android.camera.data.data.config.ComponentConfigRatio;
import com.android.camera.data.data.config.ComponentRunning8KVideoQuality;
import com.android.camera.data.data.config.ComponentRunningCinematicAspectRatio;
import com.android.camera.data.data.config.ComponentRunningMacroMode;
import com.android.camera.data.data.config.ComponentRunningUltraPixel;
import com.android.camera.log.Log;
import com.android.camera.protocol.ModeCoordinatorImpl;
import com.android.camera.protocol.ModeProtocol;
import com.android.camera2.CameraCapabilities;
import java.util.ArrayList;

public class DataItemRunning extends DataItemBase {
    private static final String BACKUP_KEY = "camera_running_backup";
    public static final String DATA_CONFIG_ULTRA_PIXEL_PORTRAIT = "pref_camera_ultra_pixel_portrait_mode_key";
    public static final String DATA_RUNING_AI_108_STATUS = "AI_108_SR_UPSCALE";
    public static final String DATA_RUNING_HAND_GESTURE = "pref_hand_gesture";
    public static final String DATA_RUNING_HAND_GESTURE_STATUS = "pref_hand_gesture_status";
    public static final String DATA_RUNING_SPEECH_SHUTTER = "pref_speech_shutter";
    public static final String DATA_RUNING_SPEECH_SHUTTER_STATUS = "pref_speech_shutter_status";
    public static final String DATA_RUNNING_AI_AUDIO = "pref_ai_audio";
    public static final String DATA_RUNNING_AI_WATERMARK = "pref_watermark_key";
    public static final String DATA_RUNNING_AUTO_ZOOM = "pref_camera_auto_zoom";
    public static final String DATA_RUNNING_COLOR_ENHANCE = "pref_color_enhance";
    public static final String DATA_RUNNING_DOCUMENT_MODE = "pref_document_mode_key";
    public static final String DATA_RUNNING_DOCUMENT_MODE_VALUE = "pref_document_mode_value_key";
    public static final String DATA_RUNNING_EXPOSURE_FEEDBACK = "pref_camera_exposure_feedback";
    public static final String DATA_RUNNING_EYE_LIGHT_TYPE = "pref_eye_light_type_key";
    public static final String DATA_RUNNING_FAST_MOTION = "pref_fast_motion_key";
    public static final String DATA_RUNNING_FILTER_INDEX = "pref_camera_shader_coloreffect_key";
    public static final String DATA_RUNNING_FOCUS_PEAK = "pref_camera_peak_key";
    public static final String DATA_RUNNING_GENDER_AGE = "pref_camera_show_gender_age_key";
    public static final String DATA_RUNNING_GROUP_SELFIES = "pref_camera_groupshot_mode_key";
    public static final String DATA_RUNNING_HHT = "pref_camera_hand_night_key";
    public static final String DATA_RUNNING_KALEIDOSCOPE = "pref_kaleidoscope";
    public static final String DATA_RUNNING_LIVE_MUSIC_FIRST_REQUEST_TIME = "pref_key_live_music_first_request_time";
    public static final String DATA_RUNNING_MAGIC_FOCUS = "pref_camera_ubifocus_key";
    public static final String DATA_RUNNING_MAGIC_MIRROR = "pref_camera_magic_mirror_key";
    public static final String DATA_RUNNING_MANUALLY = "pref_camera_manual_mode_key";
    public static final String DATA_RUNNING_MODULE_ULTRA_PIXEL_TIP = "pref_module_ultra_pixel_tip";
    public static final String DATA_RUNNING_MOON_MODE_VALUE = "pref_moon_mode_value_key";
    public static final String DATA_RUNNING_PANORAMA_MOVE_DIRECTION = "pref_panorana_move_direction_key";
    public static final String DATA_RUNNING_PORTRAIT_LIGHTING = "pref_portrait_lighting";
    public static final String DATA_RUNNING_PORTRAIT_MODE = "pref_camera_portrait_mode_key";
    public static final String DATA_RUNNING_PRO_VIDEO_RECORDING_SIMPLE = "pref_pro_video_recording_simple";
    public static final String DATA_RUNNING_SCENE = "pref_camera_scenemode_setting_key";
    public static final String DATA_RUNNING_SCENE_VALUE = "pref_camera_scenemode_key";
    public static final String DATA_RUNNING_SQUARE_MODE = "pref_camera_square_mode_key";
    public static final String DATA_RUNNING_SUPER_EIS = "pref_camera_super_eis";
    public static final String DATA_RUNNING_SUPER_RESOLUTION = "pref_camera_super_resolution_key";
    public static final String DATA_RUNNING_TILT = "pref_camera_tilt_shift_mode";
    public static final String DATA_RUNNING_TILT_VALUE = "pref_camera_tilt_shift_key";
    public static final String DATA_RUNNING_TIMER = "pref_delay_capture_mode";
    public static final String DATA_RUNNING_ULTRA_PIXEL = "pref_ultra_pixel";
    public static final String DATA_RUNNING_ULTRA_WIDE_BOKEH = "pref_ultra_wide_bokeh_enabled";
    public static final String DATA_RUNNING_VIDEO_FAST = "pref_video_speed_fast_key";
    public static final String DATA_RUNNING_VIDEO_SUBTITLE = "pref_video_subtitle_key";
    private static final String KEY = "camera_running";
    private static final String TAG = "DataItemRunning";
    private ComponentRunningFastMotion ComponentRunningFastMotion;
    private ComponentRunningTiltValue componentRunningTiltValue;
    private ComponentRunningCinematicAspectRatio mCinematicAspectRatio;
    private ComponentRunning8KVideoQuality mComponentConfig8KVideoQuality;
    private ComponentConfigAmbilight mComponentConfigAmbilight;
    private ComponentConfigFilter mComponentConfigFilter;
    private ComponentRunningAIWatermark mComponentRunningAIWatermark;
    private ComponentRunningAIWatermark mComponentRunningAIWatermarkExtend;
    private ComponentRunningAiAudio mComponentRunningAiAudio;
    private ComponentRunningAutoZoom mComponentRunningAutoZoom;
    private ComponentRunningColorEnhance mComponentRunningColorEnhance;
    private ComponentRunningDocument mComponentRunningDocument;
    private ComponentRunningDualVideo mComponentRunningDualVideo;
    private ComponentRunningEisPro mComponentRunningEisPro;
    private ComponentRunningEyeLight mComponentRunningEyeLight;
    private ComponentRunningFastMotionDuration mComponentRunningFastMotionDuration;
    private ComponentRunningFastMotionSpeed mComponentRunningFastMotionSpeed;
    private ComponentRunningKaleidoscope mComponentRunningKaleidoscope;
    private ComponentRunningLighting mComponentRunningLighting;
    private ComponentRunningMacroMode mComponentRunningMacroMode;
    private ComponentRunningMoon mComponentRunningMoon;
    private ComponentRunningSceneValue mComponentRunningSceneValue;
    private ComponentRunningShine mComponentRunningShine = new ComponentRunningShine(this);
    private ComponentRunningSubtitle mComponentRunningSubtitle;
    private ComponentRunningSuperEIS mComponentRunningSuperEIS;
    private ComponentRunningTimer mComponentRunningTimer;
    private ComponentRunningUltraPixel mComponentUltraPixel = new ComponentRunningUltraPixel(this);
    private SparseBooleanArray mCurrentSupported;
    private SimpleArrayMap<String, Object> mDawnValues;
    private int mLastUiStyle;
    private int[] mRecordingClosedElements;
    private boolean mSupportHandGesture;
    private boolean mSupportMacroMode;
    private boolean mSupportSpeechShutter;
    private boolean mSupportUltraPixelPortrait;
    private int mUiStyle;

    private ArrayList<ComponentDataItem> getAIWatermarkData(int i) {
        ArrayList<ComponentDataItem> arrayList = new ArrayList<>();
        if (i != 188) {
            arrayList.add(new ComponentDataItem(-1, -1, (int) R.string.watermark_tab_general, String.valueOf(0)));
            arrayList.add(new ComponentDataItem(-1, -1, (int) R.string.watermark_tab_spots, String.valueOf(1)));
            arrayList.add(new ComponentDataItem(-1, -1, (int) R.string.watermark_tab_festival, String.valueOf(2)));
            arrayList.add(new ComponentDataItem(-1, -1, (int) R.string.watermark_tab_scene, String.valueOf(3)));
            if (DataRepository.dataItemFeature().ci() == 1) {
                arrayList.add(new ComponentDataItem(-1, -1, (int) R.string.watermark_tab_city, String.valueOf(4)));
            }
        } else {
            arrayList.add(new ComponentDataItem(-1, -1, (int) R.string.watermark_tab_super_moon_silhouette, String.valueOf(11)));
            arrayList.add(new ComponentDataItem(-1, -1, (int) R.string.watermark_tab_super_moon_text, String.valueOf(12)));
        }
        return arrayList;
    }

    private ArrayList<ComponentDataItem> getFastMotionData() {
        ArrayList<ComponentDataItem> arrayList = new ArrayList<>();
        arrayList.add(new ComponentDataItem(-1, -1, (int) R.string.pref_camera_fastmotion_speed, String.valueOf(1)));
        arrayList.add(new ComponentDataItem(-1, -1, (int) R.string.pref_camera_fastmotion_duration, String.valueOf(2)));
        return arrayList;
    }

    private boolean isModeSupportMacro(int i, int i2) {
        if (!DataRepository.dataItemFeature().isSupportMacroMode() || i != 0) {
            return false;
        }
        return 163 == i2 || 165 == i2 || 162 == i2 || 204 == i2 || 180 == i2 || 169 == i2 || 172 == i2 || 186 == i2 || 167 == i2;
    }

    private void reCheckMutexEarly(int i) {
        ModeProtocol.ConfigChanges configChanges = (ModeProtocol.ConfigChanges) ModeCoordinatorImpl.getInstance().getAttachProtocol(164);
        if (configChanges != null) {
            configChanges.reCheckMutexEarly(i);
        }
    }

    @MainThread
    public void appendDrawnValues(SimpleArrayMap simpleArrayMap) {
        this.mDawnValues = simpleArrayMap;
    }

    @Override // com.android.camera.data.data.DataItemBase
    public void clearArrayMap() {
        super.clearArrayMap();
        ComponentRunningAIWatermark componentRunningAIWatermark = this.mComponentRunningAIWatermarkExtend;
        if (componentRunningAIWatermark != null) {
            componentRunningAIWatermark.resetAIWatermark(true);
        }
    }

    @MainThread
    public void clearDrawnValues() {
        this.mDawnValues = null;
    }

    public boolean getAi108Running() {
        return getBoolean(DATA_RUNING_AI_108_STATUS, false);
    }

    public int getBackupKey() {
        return getInt(BACKUP_KEY, 0);
    }

    public ComponentRunningCinematicAspectRatio getCinematicAspectRatio() {
        if (this.mCinematicAspectRatio == null) {
            this.mCinematicAspectRatio = new ComponentRunningCinematicAspectRatio(this);
        }
        return this.mCinematicAspectRatio;
    }

    public ComponentConfigAmbilight getComponentConfigAmbilight() {
        if (this.mComponentConfigAmbilight == null) {
            this.mComponentConfigAmbilight = new ComponentConfigAmbilight(this);
        }
        return this.mComponentConfigAmbilight;
    }

    public ComponentConfigFilter getComponentConfigFilter() {
        if (this.mComponentConfigFilter == null) {
            this.mComponentConfigFilter = new ComponentConfigFilter(this);
        }
        return this.mComponentConfigFilter;
    }

    public ComponentRunning8KVideoQuality getComponentRunning8KVideoQuality() {
        if (this.mComponentConfig8KVideoQuality == null) {
            this.mComponentConfig8KVideoQuality = new ComponentRunning8KVideoQuality(this);
        }
        return this.mComponentConfig8KVideoQuality;
    }

    public ComponentRunningAIWatermark getComponentRunningAIWatermark() {
        int currentMode = DataRepository.dataItemGlobal().getCurrentMode();
        if (currentMode == 188) {
            if (this.mComponentRunningAIWatermarkExtend == null) {
                this.mComponentRunningAIWatermarkExtend = new ComponentRunningAIWatermark(this, getAIWatermarkData(currentMode), currentMode);
            }
            return this.mComponentRunningAIWatermarkExtend;
        }
        if (this.mComponentRunningAIWatermark == null) {
            this.mComponentRunningAIWatermark = new ComponentRunningAIWatermark(this, getAIWatermarkData(currentMode), currentMode);
        }
        return this.mComponentRunningAIWatermark;
    }

    public ComponentRunningAiAudio getComponentRunningAiAudio() {
        if (this.mComponentRunningAiAudio == null) {
            this.mComponentRunningAiAudio = new ComponentRunningAiAudio(this);
        }
        return this.mComponentRunningAiAudio;
    }

    public ComponentRunningAutoZoom getComponentRunningAutoZoom() {
        if (this.mComponentRunningAutoZoom == null) {
            this.mComponentRunningAutoZoom = new ComponentRunningAutoZoom(this);
        }
        return this.mComponentRunningAutoZoom;
    }

    public ComponentRunningColorEnhance getComponentRunningColorEnhance() {
        if (this.mComponentRunningColorEnhance == null) {
            this.mComponentRunningColorEnhance = new ComponentRunningColorEnhance(this);
        }
        return this.mComponentRunningColorEnhance;
    }

    public ComponentRunningDocument getComponentRunningDocument() {
        if (this.mComponentRunningDocument == null) {
            this.mComponentRunningDocument = new ComponentRunningDocument(this);
        }
        return this.mComponentRunningDocument;
    }

    public ComponentRunningDualVideo getComponentRunningDualVideo() {
        if (this.mComponentRunningDualVideo == null) {
            this.mComponentRunningDualVideo = new ComponentRunningDualVideo(this);
        }
        return this.mComponentRunningDualVideo;
    }

    public ComponentRunningEisPro getComponentRunningEisPro() {
        if (this.mComponentRunningEisPro == null) {
            this.mComponentRunningEisPro = new ComponentRunningEisPro(this);
        }
        return this.mComponentRunningEisPro;
    }

    public ComponentRunningEyeLight getComponentRunningEyeLight() {
        if (this.mComponentRunningEyeLight == null) {
            this.mComponentRunningEyeLight = new ComponentRunningEyeLight(this);
        }
        return this.mComponentRunningEyeLight;
    }

    public ComponentRunningFastMotion getComponentRunningFastMotion() {
        if (this.ComponentRunningFastMotion == null) {
            this.ComponentRunningFastMotion = new ComponentRunningFastMotion(this, getFastMotionData());
        }
        return this.ComponentRunningFastMotion;
    }

    public ComponentRunningFastMotionDuration getComponentRunningFastMotionDuration() {
        if (this.mComponentRunningFastMotionDuration == null) {
            this.mComponentRunningFastMotionDuration = new ComponentRunningFastMotionDuration(this);
        }
        return this.mComponentRunningFastMotionDuration;
    }

    public ComponentRunningFastMotionSpeed getComponentRunningFastMotionSpeed() {
        if (this.mComponentRunningFastMotionSpeed == null) {
            this.mComponentRunningFastMotionSpeed = new ComponentRunningFastMotionSpeed(this);
        }
        return this.mComponentRunningFastMotionSpeed;
    }

    public ComponentRunningKaleidoscope getComponentRunningKaleidoscope() {
        if (this.mComponentRunningKaleidoscope == null) {
            this.mComponentRunningKaleidoscope = new ComponentRunningKaleidoscope(this);
        }
        return this.mComponentRunningKaleidoscope;
    }

    public ComponentRunningLighting getComponentRunningLighting() {
        if (this.mComponentRunningLighting == null) {
            this.mComponentRunningLighting = new ComponentRunningLighting(this);
        }
        return this.mComponentRunningLighting;
    }

    public ComponentRunningMacroMode getComponentRunningMacroMode() {
        if (this.mComponentRunningMacroMode == null) {
            this.mComponentRunningMacroMode = new ComponentRunningMacroMode(this);
        }
        return this.mComponentRunningMacroMode;
    }

    public ComponentRunningMoon getComponentRunningMoon() {
        if (this.mComponentRunningMoon == null) {
            this.mComponentRunningMoon = new ComponentRunningMoon(this);
        }
        return this.mComponentRunningMoon;
    }

    public ComponentRunningSceneValue getComponentRunningSceneValue() {
        if (this.mComponentRunningSceneValue == null) {
            this.mComponentRunningSceneValue = new ComponentRunningSceneValue(this);
        }
        return this.mComponentRunningSceneValue;
    }

    public ComponentRunningShine getComponentRunningShine() {
        return this.mComponentRunningShine;
    }

    public ComponentRunningSubtitle getComponentRunningSubtitle() {
        if (this.mComponentRunningSubtitle == null) {
            this.mComponentRunningSubtitle = new ComponentRunningSubtitle(this);
        }
        return this.mComponentRunningSubtitle;
    }

    public ComponentRunningSuperEIS getComponentRunningSuperEIS() {
        if (this.mComponentRunningSuperEIS == null) {
            this.mComponentRunningSuperEIS = new ComponentRunningSuperEIS(this);
        }
        return this.mComponentRunningSuperEIS;
    }

    public ComponentRunningTiltValue getComponentRunningTiltValue() {
        if (this.componentRunningTiltValue == null) {
            this.componentRunningTiltValue = new ComponentRunningTiltValue(this);
        }
        return this.componentRunningTiltValue;
    }

    public ComponentRunningTimer getComponentRunningTimer() {
        if (this.mComponentRunningTimer == null) {
            this.mComponentRunningTimer = new ComponentRunningTimer(this);
        }
        return this.mComponentRunningTimer;
    }

    public ComponentRunningUltraPixel getComponentUltraPixel() {
        return this.mComponentUltraPixel;
    }

    public SimpleArrayMap<String, Object> getDawnValues() {
        return this.mDawnValues;
    }

    public int getDrawnBackupKey() {
        SimpleArrayMap<String, Object> simpleArrayMap = this.mDawnValues;
        if (simpleArrayMap != null) {
            return ((Integer) simpleArrayMap.getOrDefault(BACKUP_KEY, 0)).intValue();
        }
        return -1;
    }

    public boolean getHandGestureRunning() {
        return getBoolean("pref_hand_gesture_status", false);
    }

    public int getLastUiStyle() {
        return this.mLastUiStyle;
    }

    public long getLiveMusicFirstRequestTime() {
        return getLong("pref_key_live_music_first_request_time", -1);
    }

    public boolean getProVideoRecordingSimpleRunning() {
        return getBoolean("pref_pro_video_recording_simple", false);
    }

    public int[] getRecordingClosedElements() {
        return this.mRecordingClosedElements;
    }

    public boolean getSpeechShutterRunning() {
        return getBoolean("pref_speech_shutter_status", false);
    }

    public int getUiStyle() {
        return this.mUiStyle;
    }

    public String getVideoSpeed() {
        return isSwitchOn("pref_video_speed_fast_key") ? CameraSettings.VIDEO_SPEED_FAST : "normal";
    }

    public boolean isSwitchOn(String str) {
        return getBoolean(str, false);
    }

    @Override // com.android.camera.data.provider.DataProvider.ProviderEvent
    public boolean isTransient() {
        return true;
    }

    @Override // com.android.camera.data.provider.DataProvider.ProviderEvent
    public String provideKey() {
        return KEY;
    }

    public boolean reConfigCinematicAspectRatioIfRatioChanged(int i, String str) {
        boolean isSwitchOn = getCinematicAspectRatio().isSwitchOn(i);
        if (ComponentConfigRatio.RATIO_16X9.equals(str) || !isSwitchOn) {
            return false;
        }
        getCinematicAspectRatio().setEnabled(i, false);
        return true;
    }

    public void reInitComponent(int i, int i2, CameraCapabilities cameraCapabilities, boolean z) {
        this.mComponentUltraPixel.reInit(i, i2, cameraCapabilities);
        this.mComponentRunningShine.reInit(i, i2, cameraCapabilities);
        getComponentRunningColorEnhance().reInit(i, i2, cameraCapabilities);
        getComponentRunningMacroMode().reInit(i2, z);
        getComponentRunningAutoZoom().reInit(i2);
        getComponentRunningSubtitle().reInit(i2, z);
        getComponentRunningDocument().reInit(i, i2, z);
        getComponentRunningSuperEIS().reInit(i2, z);
        getComponentRunningMacroMode().reInit(i2, z);
        getComponentRunning8KVideoQuality().reInit(i, i2, cameraCapabilities);
        getComponentRunningFastMotion().reInit();
        getComponentRunningFastMotionDuration().reInit(i, cameraCapabilities);
        getComponentRunningFastMotionSpeed().reInit(i, cameraCapabilities);
        getComponentRunningLighting().reInit(cameraCapabilities);
        getCinematicAspectRatio().reInit(i);
    }

    public void reInitSupport(int i, int i2) {
        this.mSupportHandGesture = false;
        this.mSupportSpeechShutter = false;
        this.mSupportMacroMode = false;
        this.mSupportUltraPixelPortrait = false;
        if (i2 == 1 && ((i == 163 || i == 165 || i == 171 || i == 173 || i == 205) && c.Fj())) {
            this.mSupportHandGesture = true;
        }
        if (c.hk()) {
            if (i == 163 || i == 165 || i == 171 || i == 177 || i == 173 || ((i == 184 && DataRepository.dataItemLive().getMimojiStatusManager2().isInMimojiPhoto()) || i == 205 || i == 188)) {
                this.mSupportSpeechShutter = true;
            } else if (i2 == 0 && (i == 175 || i == 167 || i == 185 || i == 210 || i == 187)) {
                this.mSupportSpeechShutter = true;
            }
        }
        this.mSupportMacroMode = isModeSupportMacro(i2, i);
        if (i2 != 1) {
            return;
        }
        if ((i == 163 || i == 165) && !Util.isGlobalVersion()) {
            this.mSupportUltraPixelPortrait = c.Zg;
            reCheckMutexEarly(i);
        }
    }

    public void setAi108Running(boolean z) {
        putBoolean(DATA_RUNING_AI_108_STATUS, z);
    }

    public void setBackupKey(int i) {
        putInt(BACKUP_KEY, i);
    }

    public void setHandGestureRunning(boolean z) {
        putBoolean("pref_hand_gesture_status", z);
    }

    public void setLiveMusicFirstRequestTime(long j) {
        putLong("pref_key_live_music_first_request_time", j);
    }

    public void setProVideoRecordingSimpleRunning(boolean z) {
        putBoolean("pref_pro_video_recording_simple", z);
    }

    public void setRecordingClosedElements(int[] iArr) {
        this.mRecordingClosedElements = iArr;
    }

    public void setSpeechShutterRunning(boolean z) {
        putBoolean("pref_speech_shutter_status", z);
    }

    public void setUiStyle(int i) {
        Log.d(TAG, "setUiStyle: " + i);
        this.mLastUiStyle = this.mUiStyle;
        this.mUiStyle = i;
    }

    public boolean supportHandGesture() {
        return this.mSupportHandGesture;
    }

    public boolean supportMacroMode(int i, int i2) {
        return isModeSupportMacro(i, i2) && DataRepository.dataItemGlobal().isNormalIntent();
    }

    public boolean supportPopShineEntry() {
        return this.mComponentRunningShine.supportPopUpEntry();
    }

    public boolean supportSpeechShutter() {
        return this.mSupportSpeechShutter;
    }

    public boolean supportSuperMacroMode() {
        return !c.isGlobal() && DataRepository.dataItemFeature().Wi() && this.mSupportMacroMode && DataRepository.dataItemGlobal().isNormalIntent();
    }

    public boolean supportTopShineEntry() {
        return this.mComponentRunningShine.supportTopConfigEntry();
    }

    public boolean supportUltraPixel() {
        return !this.mComponentUltraPixel.isEmpty();
    }

    public boolean supportUltraPixelPortrait() {
        return this.mSupportUltraPixelPortrait;
    }

    public void switchOff(String str) {
        putBoolean(str, false);
    }

    public void switchOn(String str) {
        putBoolean(str, true);
    }

    public boolean triggerSwitchAndGet(String str) {
        if (isSwitchOn(str)) {
            switchOff(str);
            return false;
        }
        switchOn(str);
        return true;
    }
}
