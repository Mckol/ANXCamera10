package com.android.camera.data.data.global;

import android.content.Intent;
import androidx.core.util.Pair;
import b.c.a.b;
import b.c.a.c;
import com.android.camera.CameraAppImpl;
import com.android.camera.CameraIntentManager;
import com.android.camera.CameraSettings;
import com.android.camera.Util;
import com.android.camera.data.DataRepository;
import com.android.camera.data.backup.DataBackUp;
import com.android.camera.data.data.DataItemBase;
import com.android.camera.data.data.config.DataItemConfig;
import com.android.camera.data.data.runing.DataItemRunning;
import com.android.camera.data.provider.DataProvider;
import com.android.camera.log.Log;
import com.android.camera.module.ModuleManager;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.regex.PatternSyntaxException;

public class DataItemGlobal extends DataItemBase {
    public static final int BACK_DISPLAY_MODE = 2;
    public static final String CTA_CAN_CONNECT_NETWORK_BY_IMPUNITY = "can_connect_network";
    public static final String DATA_CLONE_MODEL_VERSION = "pref_clone_model_version";
    public static final String DATA_COMMON_AI_SCENE_HINT = "pref_camera_first_ai_scene_use_hint_shown_key";
    public static final String DATA_COMMON_CAMCORDER_TIP_8K_MAX_VIDEO_DURATION_SHOWN = "pref_camcorder_tip_8k_max_video_duration_shown";
    public static final String DATA_COMMON_CURRENT_CAMERA_ID = "pref_camera_id_key";
    public static final String DATA_COMMON_CURRENT_MODE = "pref_camera_mode_key_intent_";
    public static final String DATA_COMMON_CUSTOM_WATERMARK_VERSION = "pref_custom_watermark_version";
    public static final String DATA_COMMON_DEVICE_WATERMARK = "pref_dualcamera_watermark_key";
    public static final String DATA_COMMON_DOCUMENT_MODE_USE_HINT_SHOWN = "pref_document_use_hint_shown";
    public static final String DATA_COMMON_DUALCAMERA_USERDEFINE_WATERMARK = "user_define_watermark_key";
    public static final String DATA_COMMON_FIRST_USE_HINT = "pref_camera_first_use_hint_shown_key";
    public static final String DATA_COMMON_FOCUS_SHOOT = "pref_camera_focus_shoot_key";
    public static final String DATA_COMMON_FRONT_CAM_ROTATE_HINT = "pref_front_camera_first_use_hint_shown_key";
    public static final String DATA_COMMON_ID_CARD_MODE_HINT = "pref_camera_first_id_card_mode_use_hint_shown_key";
    public static final String DATA_COMMON_LPL_SELECTOR_USE_HINT_SHOWN = "pref_lpl_selector_use_hint_shown";
    public static final String DATA_COMMON_MACRO_MODE_HINT = "pref_camera_first_macro_mode_use_hint_shown_key";
    private static final String DATA_COMMON_OPEN_TIME = "pref_camera_open_time";
    public static final String DATA_COMMON_PORTRAIT_HINT = "pref_camera_first_portrait_use_hint_shown_key";
    public static final String DATA_COMMON_SORT_MODES = "pref_camera_sort_modes_key";
    public static final String DATA_COMMON_TIKTOK_MORE_BUTTON_SHOW_APP = "pref_camera_tiktok_more_show_app_key";
    public static final String DATA_COMMON_TIKTOK_MORE_BUTTON_SHOW_MARKET = "pref_camera_tiktok_more_show_market_key";
    public static final String DATA_COMMON_TIME_WATER_MARK = "pref_time_watermark_key";
    public static final String DATA_COMMON_ULTRA_TELE_HINT = "pref_camera_first_ultra_tele_use_hint_shown_key";
    public static final String DATA_COMMON_ULTRA_WIDE_HINT = "pref_camera_first_ultra_wide_use_hint_shown_key";
    public static final String DATA_COMMON_ULTRA_WIDE_SAT_HINT = "pref_camera_first_ultra_wide_sat_use_hint_shown_key";
    public static final String DATA_COMMON_USER_EDIT_MODES = "pref_user_edit_modes";
    public static final String DATA_COMMON_VV_HINT = "pref_camera_first_vv_hint_shown_key";
    private static int[] DEFAULT_SORT_MODES = {167, 162, 163, 171, 254, 173, 175, 183, 174, 161, 166, 176, 186, 209, 172, 169, 204, 205, 187, 188, 177, 184, 185};
    public static final int FRONT_DISPLAY_MODE = 1;
    public static final int INTENT_TYPE_IMAGE = 1;
    public static final int INTENT_TYPE_NORMAL = 0;
    public static final int INTENT_TYPE_SCAN_QR = 3;
    public static final int INTENT_TYPE_UNSPECIFIED = -1;
    public static final int INTENT_TYPE_VIDEO = 2;
    public static final int INTENT_TYPE_VOICE_CONTROL = 4;
    public static final String IS_FIRST_SHOW_VIDEOTAG = "first_show_videotag";
    public static final String IS_FIRST_USE_CLONE_PHOTO = "first_show_clone_photo";
    public static final String IS_FIRST_USE_CLONE_VIDEO = "first_show_clone_video";
    public static final String KEY = "camera_settings_global";
    private static final String TAG = "DataItemGlobal";
    private static final int TIME_OUT = 30000;
    public static List<String> sUseHints = new ArrayList();
    private boolean hasModesSortChanged = false;
    private int[] mAllSortModes;
    private DataBackUp mDataBackUp;
    private b mDataItemFeature;
    private DataItemRunning mDataItemRunning;
    private int mIntentType = 0;
    private int mIntentVideoQuality = -1;
    private boolean mIsForceMainBackCamera;
    private Boolean mIsTimeOut;
    private int mLastCameraId;
    private boolean mMimojiStandAlone;
    private ComponentModuleList mModuleList;
    private boolean mRetriedIfCameraError;
    private boolean mStartFromKeyguard;

    @Retention(RetentionPolicy.SOURCE)
    public @interface IntentType {
    }

    static {
        sUseHints.add("pref_camera_first_use_hint_shown_key");
        sUseHints.add("pref_camera_first_ai_scene_use_hint_shown_key");
        sUseHints.add("pref_camera_first_ultra_wide_use_hint_shown_key");
        sUseHints.add("pref_camera_first_portrait_use_hint_shown_key");
        sUseHints.add("pref_front_camera_first_use_hint_shown_key");
        sUseHints.add(DATA_COMMON_DOCUMENT_MODE_USE_HINT_SHOWN);
        sUseHints.add(DATA_COMMON_LPL_SELECTOR_USE_HINT_SHOWN);
        sUseHints.add(CameraSettings.KEY_RECORD_LOCATION);
    }

    public DataItemGlobal(b bVar, DataItemRunning dataItemRunning, DataBackUp dataBackUp) {
        this.mDataItemFeature = bVar;
        this.mDataItemRunning = dataItemRunning;
        this.mDataBackUp = dataBackUp;
        this.mMimojiStandAlone = this.mDataItemFeature.Xj();
        this.mLastCameraId = getCurrentCameraId();
        this.mModuleList = new ComponentModuleList(this);
        this.mAllSortModes = getSortModes();
        if (getInt(CameraSettings.KEY_CAMERA_MORE_MODE_STYLE, 0) != 0) {
            applyMoreModeStyle();
        }
    }

    private boolean determineTimeOut() {
        return (!CameraSettings.retainCameraMode() || !this.mModuleList.isCommonMode(getCurrentMode())) ? isActualTimeOut() : getCurrentMode() == 254;
    }

    private int[] getConfigCommonModes() {
        boolean z;
        if (this.mMimojiStandAlone) {
            return new int[]{getDefaultMode(this.mIntentType)};
        }
        int[] yh = this.mDataItemFeature.yh();
        if (yh == null) {
            return DEFAULT_SORT_MODES;
        }
        ArrayList arrayList = new ArrayList();
        for (int i : yh) {
            arrayList.add(Integer.valueOf(i));
        }
        arrayList.add(254);
        int[] iArr = DEFAULT_SORT_MODES;
        for (int i2 : iArr) {
            Iterator it = arrayList.iterator();
            while (true) {
                if (it.hasNext()) {
                    if (((Integer) it.next()).intValue() == ComponentModuleList.getTransferredMode(i2)) {
                        z = true;
                        break;
                    }
                } else {
                    z = false;
                    break;
                }
            }
            if (!z) {
                arrayList.add(Integer.valueOf(i2));
            }
        }
        int[] iArr2 = new int[arrayList.size()];
        for (int i3 = 0; i3 < arrayList.size(); i3++) {
            iArr2[i3] = ((Integer) arrayList.get(i3)).intValue();
        }
        return iArr2;
    }

    /* JADX INFO: Can't fix incorrect switch cases order, some code will duplicate */
    private int getCurrentCameraId(int i) {
        if (!(i == 166 || i == 167)) {
            if (i != 169) {
                if (!(i == 180 || i == 204 || i == 175)) {
                    if (i == 176) {
                        return 1;
                    }
                    if (!(i == 209 || i == 210)) {
                        switch (i) {
                            case 171:
                                if (this.mDataItemFeature.Bk()) {
                                    return Integer.parseInt(getString("pref_camera_id_key", String.valueOf(getDefaultCameraId(i))));
                                }
                                return 0;
                            case 172:
                                if (this.mDataItemFeature.Ak()) {
                                    return Integer.parseInt(getString("pref_camera_id_key", String.valueOf(getDefaultCameraId(i))));
                                }
                                return 0;
                            case 173:
                                if (this.mDataItemFeature.yl() || this.mDataItemFeature.zl()) {
                                    return Integer.parseInt(getString("pref_camera_id_key", String.valueOf(getDefaultCameraId(i))));
                                }
                                return 0;
                            default:
                                switch (i) {
                                    case 186:
                                    case 187:
                                    case 188:
                                        break;
                                    default:
                                        return Integer.parseInt(getString("pref_camera_id_key", String.valueOf(getDefaultCameraId(i))));
                                }
                        }
                    }
                }
            } else if (this.mDataItemFeature.Dj()) {
                return Integer.parseInt(getString("pref_camera_id_key", String.valueOf(getDefaultCameraId(i))));
            }
        }
        return 0;
    }

    private int getCurrentMode(int i) {
        return getInt(DATA_COMMON_CURRENT_MODE + i, getDefaultMode(i));
    }

    /* JADX INFO: Can't fix incorrect switch cases order, some code will duplicate */
    private int getCurrentModeForFrontCamera(int i) {
        int currentMode = getCurrentMode(i);
        switch (currentMode) {
            case 166:
            case 167:
            case 173:
            case 175:
                break;
            case 168:
            case 170:
            case 174:
            default:
                return currentMode;
            case 169:
            case 172:
                return 162;
            case 171:
                if (this.mDataItemFeature.Bk()) {
                    return currentMode;
                }
                break;
        }
        return 163;
    }

    private int getDefaultCameraId(int i) {
        return 0;
    }

    private boolean isActualTimeOut() {
        long currentTimeMillis = System.currentTimeMillis();
        return currentTimeMillis - getLong(DATA_COMMON_OPEN_TIME, currentTimeMillis) > 30000 || this.mIsTimeOut == null;
    }

    public void applyMoreModeStyle() {
        int[] sortModes = getSortModes();
        if (!getBoolean(DATA_COMMON_USER_EDIT_MODES, false)) {
            int i = getInt(CameraSettings.KEY_CAMERA_MORE_MODE_STYLE, 0);
            int i2 = -1;
            for (int i3 = 0; i3 < sortModes.length; i3++) {
                if (sortModes[i3] == 254) {
                    i2 = i3;
                }
            }
            if (i2 < 2) {
                Log.w(TAG, "filterByStyle find more index fail.");
                return;
            }
            if (i == 1) {
                if (i2 < sortModes.length - 1) {
                    int i4 = i2 + 1;
                    sortModes[i2] = sortModes[i4];
                    sortModes[i4] = 254;
                } else {
                    return;
                }
            } else if (i == 0) {
                int i5 = i2 - 1;
                sortModes[i2] = sortModes[i5];
                sortModes[i5] = 254;
            }
            this.mAllSortModes = sortModes;
            Log.d(TAG, "filterByStyle " + Arrays.toString(sortModes));
        }
    }

    public boolean getCTACanCollect() {
        return getBoolean(CTA_CAN_CONNECT_NETWORK_BY_IMPUNITY, false);
    }

    public ComponentModuleList getComponentModuleList() {
        return this.mModuleList;
    }

    public int getCurrentCameraId() {
        return getCurrentCameraId(getCurrentMode());
    }

    public int getCurrentMode() {
        return getCurrentMode(this.mIntentType);
    }

    public int getDataBackUpKey(int i, int i2) {
        if (i == 165 || i == 185) {
            i = ComponentModuleList.getTransferredMode(i);
        }
        int i3 = i | ((this.mIntentType + 2) << 8) | (i2 << 12);
        return this.mStartFromKeyguard ? i3 | 65536 : i3;
    }

    public int getDefaultMode(int i) {
        if (i != 1) {
            if (i != 2) {
                return (i == 3 || !this.mMimojiStandAlone) ? 163 : 177;
            }
            return 162;
        }
        return 163;
    }

    public int getDisplayMode() {
        return (!this.mDataItemFeature.pj() || DataRepository.dataItemGlobal().getCurrentCameraId() != 1) ? 1 : 2;
    }

    public int getFavoriteModeCount() {
        int[] sortModes = getSortModes();
        for (int i = 0; i < sortModes.length; i++) {
            if (sortModes[i] == 254) {
                return i;
            }
        }
        return -1;
    }

    public int getIntentType() {
        return this.mIntentType;
    }

    public int getIntentVideoQuality() {
        return this.mIntentVideoQuality;
    }

    public int getLastCameraId() {
        return this.mLastCameraId;
    }

    public int[] getSortModes() {
        int[] iArr = this.mAllSortModes;
        if (iArr != null) {
            return iArr;
        }
        String string = getString(DATA_COMMON_SORT_MODES, Arrays.toString(getConfigCommonModes()));
        try {
            int[] array = Arrays.stream(string.substring(1, string.length() - 1).split(",")).mapToInt(a.INSTANCE).toArray();
            LinkedHashSet linkedHashSet = new LinkedHashSet();
            for (int i : array) {
                linkedHashSet.add(Integer.valueOf(i));
            }
            if (linkedHashSet.size() != array.length) {
                Log.e(TAG, "found duplicate mode.");
            }
            return linkedHashSet.stream().mapToInt(b.INSTANCE).toArray();
        } catch (NumberFormatException | PatternSyntaxException unused) {
            return DEFAULT_SORT_MODES;
        }
    }

    public boolean getStartFromKeyguard() {
        return this.mStartFromKeyguard;
    }

    public boolean hasModesChanged() {
        if (this.mAllSortModes == null) {
            return true;
        }
        if (!getBoolean(DATA_COMMON_USER_EDIT_MODES, false)) {
            return false;
        }
        return this.hasModesSortChanged;
    }

    public boolean isFirstShowCTAConCollect() {
        return !contains(CTA_CAN_CONNECT_NETWORK_BY_IMPUNITY);
    }

    public boolean isFirstUseClonePhoto() {
        return getBoolean(IS_FIRST_USE_CLONE_PHOTO, true);
    }

    public boolean isFirstUseCloneVideo() {
        return getBoolean(IS_FIRST_USE_CLONE_VIDEO, true);
    }

    public boolean isForceMainBackCamera() {
        return this.mIsForceMainBackCamera;
    }

    public boolean isGlobalSwitchOn(String str) {
        return getBoolean(str, false);
    }

    public boolean isIntentAction() {
        return this.mIntentType != 0;
    }

    public boolean isNormalIntent() {
        return this.mIntentType == 0;
    }

    public boolean isOnSuperNightAlgoUpAndQuickShot() {
        return isOnSuperNightAlgoUpMode() && this.mDataItemFeature.hm();
    }

    public boolean isOnSuperNightAlgoUpMode() {
        if (getCurrentMode() != 173) {
            return false;
        }
        return (DataRepository.dataItemGlobal().getCurrentCameraId() == 0 && this.mDataItemFeature.pl()) || (DataRepository.dataItemGlobal().getCurrentCameraId() == 1 && this.mDataItemFeature.zl());
    }

    public boolean isOnSuperNightHalfAlgoUp() {
        return getCurrentMode() == 173 && DataRepository.dataItemGlobal().getCurrentCameraId() == 0 && this.mDataItemFeature.ql();
    }

    public boolean isRetriedIfCameraError() {
        return this.mRetriedIfCameraError;
    }

    public boolean isTiktokMoreButtonEnabled(boolean z) {
        return getBoolean(z ? DATA_COMMON_TIKTOK_MORE_BUTTON_SHOW_APP : DATA_COMMON_TIKTOK_MORE_BUTTON_SHOW_MARKET, c.Og ? true : z);
    }

    public boolean isTimeOut() {
        Boolean bool = this.mIsTimeOut;
        return bool == null || bool.booleanValue();
    }

    @Override // com.android.camera.data.provider.DataProvider.ProviderEvent
    public boolean isTransient() {
        return false;
    }

    public boolean matchCloneModelVersion(String str) {
        if (!contains(DATA_CLONE_MODEL_VERSION)) {
            return false;
        }
        String string = getString(DATA_CLONE_MODEL_VERSION, "");
        if (string.equals(str)) {
            return true;
        }
        Log.w(TAG, "matchCloneModelVersion, pref version " + string + ", asset version = " + str);
        return false;
    }

    public boolean matchCustomWatermarkVersion() {
        String xh = this.mDataItemFeature.xh();
        if (!contains(DATA_COMMON_CUSTOM_WATERMARK_VERSION)) {
            return false;
        }
        if (arrayMapContainsKey(DATA_COMMON_CUSTOM_WATERMARK_VERSION)) {
            arrayMapRemove(DATA_COMMON_CUSTOM_WATERMARK_VERSION);
        }
        String string = getString(DATA_COMMON_CUSTOM_WATERMARK_VERSION, "");
        int indexOf = string.indexOf(58);
        if (indexOf > 0) {
            String substring = string.substring(0, indexOf);
            String substring2 = string.substring(indexOf + 1);
            if (substring.equals(c.Bg + c.Em()) && substring2.equals(xh)) {
                return true;
            }
        }
        Log.w(TAG, "mismatch custom watermark version: " + string);
        return false;
    }

    /* JADX INFO: Can't fix incorrect switch cases order, some code will duplicate */
    /* JADX WARNING: Code restructure failed: missing block: B:39:0x008e, code lost:
        if (r19.booleanValue() == false) goto L_0x0086;
     */
    /* JADX WARNING: Removed duplicated region for block: B:106:0x01bb  */
    /* JADX WARNING: Removed duplicated region for block: B:119:0x01de  */
    /* JADX WARNING: Removed duplicated region for block: B:123:0x0212  */
    /* JADX WARNING: Removed duplicated region for block: B:69:0x0137  */
    /* JADX WARNING: Removed duplicated region for block: B:71:0x013c  */
    /* JADX WARNING: Removed duplicated region for block: B:77:0x0149  */
    /* JADX WARNING: Removed duplicated region for block: B:83:0x015b  */
    /* JADX WARNING: Removed duplicated region for block: B:85:0x0162  */
    public Pair<Integer, Integer> parseIntent(Intent intent, Boolean bool, boolean z, boolean z2, boolean z3) {
        char c2;
        int i;
        int i2;
        int cameraFacing;
        int i3;
        int i4;
        int currentCameraId;
        int i5;
        setForceMainBackCamera(false);
        if (this.mDataItemFeature.Ri() && Util.isScreenSlideOff(CameraAppImpl.getAndroidContext())) {
            setCameraId(0);
        }
        String action = intent.getAction();
        if (action == null) {
            action = "<unknown>";
        }
        switch (action.hashCode()) {
            case -1960745709:
                if (action.equals("android.media.action.IMAGE_CAPTURE")) {
                    c2 = 0;
                    break;
                }
                c2 = 65535;
                break;
            case -1658348509:
                if (action.equals("android.media.action.IMAGE_CAPTURE_SECURE")) {
                    c2 = 1;
                    break;
                }
                c2 = 65535;
                break;
            case -1528697361:
                if (action.equals(CameraIntentManager.ACTION_VOICE_CONTROL)) {
                    c2 = 7;
                    break;
                }
                c2 = 65535;
                break;
            case -1449841107:
                if (action.equals(CameraIntentManager.ACTION_QR_CODE_ZXING)) {
                    c2 = 4;
                    break;
                }
                c2 = 65535;
                break;
            case 464109999:
                if (action.equals("android.media.action.STILL_IMAGE_CAMERA")) {
                    c2 = 5;
                    break;
                }
                c2 = 65535;
                break;
            case 701083699:
                if (action.equals("android.media.action.VIDEO_CAPTURE")) {
                    c2 = 2;
                    break;
                }
                c2 = 65535;
                break;
            case 1130890360:
                if (action.equals("android.media.action.VIDEO_CAMERA")) {
                    c2 = 6;
                    break;
                }
                c2 = 65535;
                break;
            case 1280056183:
                if (action.equals(CameraIntentManager.ACTION_QR_CODE_CAPTURE)) {
                    c2 = 3;
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
            case 1:
                i = -1;
                i2 = 1;
                cameraFacing = CameraIntentManager.getInstance(intent).getCameraFacing();
                if (cameraFacing != -1) {
                    setCameraIdTransient(cameraFacing);
                }
                boolean z4 = !z3 && determineTimeOut();
                boolean z5 = this.mIntentType == i2 || this.mStartFromKeyguard != z;
                int i6 = 162;
                if (!"android.media.action.STILL_IMAGE_CAMERA".equals(action)) {
                    currentCameraId = getCurrentCameraId(163);
                } else {
                    if ("android.media.action.VIDEO_CAMERA".equals(action)) {
                        i3 = getCurrentCameraId(162);
                        i4 = 162;
                    } else if (CameraIntentManager.getInstance(intent).isQuickLaunch()) {
                        currentCameraId = getCurrentCameraId(163);
                    } else if (z4) {
                        i4 = getDefaultMode(i2);
                        i3 = cameraFacing < 0 ? getDefaultCameraId(i4) : getCurrentCameraId(i4);
                        if (i4 == 163 && ((DataItemConfig) DataRepository.provider().dataConfig(i3, i2)).getComponentConfigRatio().isSquareModule()) {
                            i4 = 165;
                        }
                    } else {
                        i4 = cameraFacing != 1 ? getCurrentMode(i2) : getCurrentModeForFrontCamera(i2);
                        i3 = getCurrentCameraId(i4);
                    }
                    if (i4 != 168 || i4 == 170) {
                        if (this.mDataItemFeature.wm()) {
                            i6 = 172;
                        }
                        i4 = i6;
                    } else if (isActualTimeOut() || z5) {
                        if (i4 == 179) {
                            i4 = 209;
                        } else if (i4 == 185) {
                            i4 = 210;
                        }
                    }
                    Log.d(TAG, String.format("parseIntent timeOut = %s, intentChanged = %s, action = %s, pendingOpenId = %s, pendingOpenModule = %s, intentCameraId = %s", Boolean.valueOf(z4), Boolean.valueOf(z5), action, Integer.valueOf(i3), Integer.valueOf(i4), Integer.valueOf(cameraFacing)));
                    if (!z2) {
                        this.mIsTimeOut = Boolean.valueOf(z4);
                        if (z5) {
                            this.mIntentType = i2;
                            this.mIntentVideoQuality = i;
                            this.mStartFromKeyguard = z;
                            this.mModuleList.setIntentType(this.mIntentType);
                        }
                        if (i4 != getCurrentMode()) {
                            setCurrentMode(i4);
                            ModuleManager.setActiveModuleIndex(i4);
                        }
                        if (i3 != getCurrentCameraId()) {
                            setCameraId(i3);
                        }
                        if (this.mIsTimeOut.booleanValue()) {
                            this.mDataItemRunning.clearArrayMap();
                            this.mDataBackUp.clearBackUp();
                        }
                        this.mDataBackUp.revertOrCreateRunning(this.mDataItemRunning, getDataBackUpKey(i4, i3));
                    }
                    return new Pair<>(Integer.valueOf(i3), Integer.valueOf(i4));
                }
                i3 = currentCameraId;
                i4 = 163;
                if (i4 != 168) {
                }
                if (this.mDataItemFeature.wm()) {
                }
                i4 = i6;
                Log.d(TAG, String.format("parseIntent timeOut = %s, intentChanged = %s, action = %s, pendingOpenId = %s, pendingOpenModule = %s, intentCameraId = %s", Boolean.valueOf(z4), Boolean.valueOf(z5), action, Integer.valueOf(i3), Integer.valueOf(i4), Integer.valueOf(cameraFacing)));
                if (!z2) {
                }
                return new Pair<>(Integer.valueOf(i3), Integer.valueOf(i4));
            case 2:
                i = CameraIntentManager.getInstance(intent).getVideoQuality();
                i2 = 2;
                cameraFacing = CameraIntentManager.getInstance(intent).getCameraFacing();
                if (cameraFacing != -1) {
                }
                if (!z3) {
                    break;
                }
                if (this.mIntentType == i2) {
                    break;
                }
                int i62 = 162;
                if (!"android.media.action.STILL_IMAGE_CAMERA".equals(action)) {
                }
                i3 = currentCameraId;
                i4 = 163;
                if (i4 != 168) {
                }
                if (this.mDataItemFeature.wm()) {
                }
                i4 = i62;
                Log.d(TAG, String.format("parseIntent timeOut = %s, intentChanged = %s, action = %s, pendingOpenId = %s, pendingOpenModule = %s, intentCameraId = %s", Boolean.valueOf(z4), Boolean.valueOf(z5), action, Integer.valueOf(i3), Integer.valueOf(i4), Integer.valueOf(cameraFacing)));
                if (!z2) {
                }
                return new Pair<>(Integer.valueOf(i3), Integer.valueOf(i4));
            case 3:
            case 4:
                i = -1;
                i2 = 3;
                cameraFacing = CameraIntentManager.getInstance(intent).getCameraFacing();
                if (cameraFacing != -1) {
                }
                if (!z3) {
                }
                if (this.mIntentType == i2) {
                }
                int i622 = 162;
                if (!"android.media.action.STILL_IMAGE_CAMERA".equals(action)) {
                }
                i3 = currentCameraId;
                i4 = 163;
                if (i4 != 168) {
                }
                if (this.mDataItemFeature.wm()) {
                }
                i4 = i622;
                Log.d(TAG, String.format("parseIntent timeOut = %s, intentChanged = %s, action = %s, pendingOpenId = %s, pendingOpenModule = %s, intentCameraId = %s", Boolean.valueOf(z4), Boolean.valueOf(z5), action, Integer.valueOf(i3), Integer.valueOf(i4), Integer.valueOf(cameraFacing)));
                if (!z2) {
                }
                return new Pair<>(Integer.valueOf(i3), Integer.valueOf(i4));
            case 5:
            case 6:
                break;
            case 7:
                CameraIntentManager instance = CameraIntentManager.getInstance(intent);
                int cameraModeId = instance.getCameraModeId();
                if (cameraModeId == 160) {
                    cameraModeId = determineTimeOut() ? getDefaultMode(0) : getCurrentMode(0);
                }
                try {
                    i5 = instance.isUseFrontCamera();
                } catch (Exception unused) {
                    if (instance.isOnlyForceOpenMainBackCamera()) {
                        setForceMainBackCamera(true);
                        i5 = 0;
                    } else {
                        i5 = determineTimeOut() ? getDefaultCameraId(cameraModeId) : getCurrentCameraId(cameraModeId);
                    }
                }
                Log.d(TAG, "intent from voice control assist : pendingOpenId = " + (i5 == 1 ? 1 : 0) + ";pendingOpenModule = " + cameraModeId + ",newIntentType = " + 0);
                this.mIntentType = 0;
                this.mStartFromKeyguard = z;
                this.mModuleList.setIntentType(this.mIntentType);
                if (cameraModeId != getCurrentMode()) {
                    setCurrentMode(cameraModeId);
                    ModuleManager.setActiveModuleIndex(cameraModeId);
                }
                if (i5 != getCurrentCameraId()) {
                    setCameraId(i5);
                }
                return new Pair<>(Integer.valueOf(i5), Integer.valueOf(cameraModeId));
            default:
                i2 = 0;
                i = -1;
                cameraFacing = CameraIntentManager.getInstance(intent).getCameraFacing();
                if (cameraFacing != -1) {
                }
                if (!z3) {
                }
                if (this.mIntentType == i2) {
                }
                int i6222 = 162;
                if (!"android.media.action.STILL_IMAGE_CAMERA".equals(action)) {
                }
                i3 = currentCameraId;
                i4 = 163;
                if (i4 != 168) {
                }
                if (this.mDataItemFeature.wm()) {
                }
                i4 = i6222;
                Log.d(TAG, String.format("parseIntent timeOut = %s, intentChanged = %s, action = %s, pendingOpenId = %s, pendingOpenModule = %s, intentCameraId = %s", Boolean.valueOf(z4), Boolean.valueOf(z5), action, Integer.valueOf(i3), Integer.valueOf(i4), Integer.valueOf(cameraFacing)));
                if (!z2) {
                }
                return new Pair<>(Integer.valueOf(i3), Integer.valueOf(i4));
        }
    }

    @Override // com.android.camera.data.provider.DataProvider.ProviderEvent
    public String provideKey() {
        return "camera_settings_global";
    }

    public void reInit() {
        this.mModuleList.reInit(false);
        DataProvider.ProviderEditor editor = editor();
        this.mIsTimeOut = false;
        editor.putLong(DATA_COMMON_OPEN_TIME, System.currentTimeMillis());
        editor.putLong(CameraSettings.KEY_OPEN_CAMERA_FAIL, 0);
        int currentCameraId = getCurrentCameraId(getCurrentMode());
        this.mLastCameraId = currentCameraId;
        editor.putString("pref_camera_id_key", String.valueOf(currentCameraId));
        Log.d(TAG, "reInit: mLastCameraId = " + this.mLastCameraId + ", currentCameraId = " + currentCameraId);
        if (getBoolean(DATA_COMMON_USER_EDIT_MODES, false)) {
            editor().putString(DATA_COMMON_SORT_MODES, Arrays.toString(getSortModes())).apply();
            this.hasModesSortChanged = false;
        }
        editor.apply();
    }

    public void resetAll() {
        this.mIsTimeOut = null;
        this.mAllSortModes = null;
        this.hasModesSortChanged = false;
        this.mModuleList.clear();
        editor().clear().putInt(CameraSettings.KEY_VERSION, 4).apply();
    }

    public void resetTimeOut() {
        this.mIsTimeOut = false;
        editor().putLong(DATA_COMMON_OPEN_TIME, System.currentTimeMillis()).apply();
    }

    public void setCTACanCollect(boolean z) {
        editor().putBoolean(CTA_CAN_CONNECT_NETWORK_BY_IMPUNITY, z).apply();
    }

    public void setCameraId(int i) {
        this.mLastCameraId = getCurrentCameraId(getCurrentMode());
        editor().putString("pref_camera_id_key", String.valueOf(i)).apply();
        Log.d(TAG, "setCameraId: mLastCameraId = " + this.mLastCameraId + ", cameraId = " + i);
    }

    public void setCameraIdTransient(int i) {
        this.mLastCameraId = getCurrentCameraId(getCurrentMode());
        putString("pref_camera_id_key", String.valueOf(i));
        Log.d(TAG, "setCameraIdTransient: mLastCameraId = " + this.mLastCameraId + ", cameraId = " + i);
    }

    public void setCurrentMode(int i) {
        DataProvider.ProviderEditor editor = editor();
        editor.putInt(DATA_COMMON_CURRENT_MODE + this.mIntentType, i).apply();
    }

    public void setFirstUseClonePhoto(boolean z) {
        editor().putBoolean(IS_FIRST_USE_CLONE_PHOTO, z).apply();
    }

    public void setFirstUseCloneVideo(boolean z) {
        editor().putBoolean(IS_FIRST_USE_CLONE_VIDEO, z).apply();
    }

    public void setForceMainBackCamera(boolean z) {
        this.mIsForceMainBackCamera = z;
    }

    public void setRetriedIfCameraError(boolean z) {
        this.mRetriedIfCameraError = z;
    }

    public void setSortModes(int[] iArr) {
        if (!Arrays.equals(this.mAllSortModes, iArr)) {
            this.mAllSortModes = iArr;
            this.hasModesSortChanged = true;
            Log.d(TAG, "setSortModes " + Arrays.toString(iArr));
        }
    }

    public void setStartFromKeyguard(boolean z) {
        this.mStartFromKeyguard = z;
    }

    public void updateCloneModelVersion(String str) {
        editor().putString(DATA_CLONE_MODEL_VERSION, str).apply();
        Log.d(TAG, "updateCloneModelVersion " + str);
    }

    public void updateCustomWatermarkVersion() {
        String str = c.Bg + c.Em() + ":" + this.mDataItemFeature.xh();
        editor().putString(DATA_COMMON_CUSTOM_WATERMARK_VERSION, str).apply();
        Log.i(TAG, "custom watermark version updated: " + str);
    }
}
