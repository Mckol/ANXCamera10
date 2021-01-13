package com.android.camera.data.data.runing;

import androidx.annotation.DrawableRes;
import androidx.annotation.NonNull;
import b.c.a.c;
import com.android.camera.CameraSettings;
import com.android.camera.R;
import com.android.camera.data.DataRepository;
import com.android.camera.data.data.ComponentData;
import com.android.camera.data.data.ComponentDataItem;
import com.android.camera.effect.FilterInfo;
import com.android.camera.fragment.beauty.BeautyValues;
import com.android.camera.log.Log;
import com.android.camera2.CameraCapabilities;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public class ComponentRunningShine extends ComponentData {
    public static final int ENTRY_NONE = -1;
    public static final int ENTRY_POPUP_BEAUTY = 5;
    public static final int ENTRY_POPUP_SHINE = 4;
    public static final int ENTRY_TOP_BEAUTY = 2;
    public static final int ENTRY_TOP_FILTER = 3;
    public static final int ENTRY_TOP_SHINE = 1;
    public static final int FILTER_NATIVE_NONE_ID = 0;
    public static final String SHINE_BEAUTY_LEVEL_SMOOTH = "2";
    public static final String SHINE_BEAUTY_LEVEL_SWITCH = "1";
    public static final String SHINE_EYE_LIGHT = "9";
    public static final String SHINE_FIGURE = "6";
    public static final String SHINE_FILTER = "7";
    public static final String SHINE_FRONT_SUPER_NIGHT_BEAUTY = "17";
    public static final String SHINE_KALEIDOSCOPE = "16";
    public static final String SHINE_LIGHTING = "8";
    public static final String SHINE_LIVE_BEAUTY = "11";
    public static final String SHINE_LIVE_FILTER = "10";
    public static final String SHINE_LIVE_SPEED = "13";
    public static final String SHINE_LIVE_STICKER = "12";
    public static final String SHINE_MAKEUP = "5";
    public static final String SHINE_MI_LIVE_BEAUTY = "15";
    public static final String SHINE_MODEL_ADVANCE = "3";
    public static final String SHINE_MODEL_REMODELING = "4";
    public static final String SHINE_VIDEO_BOKEH_LEVEL = "14";
    private static final String TAG = "ComponentRunningShine";
    private boolean isFrontCamera;
    private BeautyValues mBeautyValues;
    private int mBeautyVersion;
    private boolean mCurrentStatus;
    private String mCurrentTipType;
    @ShineType
    private String mCurrentType;
    @ShineType
    private String mDefaultType;
    private boolean mIsClosed;
    private HashMap<String, Boolean> mIsVideoBeautySwitchedOnMap = new HashMap<>();
    @ShineEntry
    private int mShineEntry;
    private boolean mSupportBeautyBody;
    private boolean mSupportBeautyLevel;
    private boolean mSupportBeautyMakeUp;
    private boolean mSupportBeautyMiLive;
    private boolean mSupportBeautyModel;
    private boolean mSupportFrontNightBeauty;
    private boolean mSupportHalColorRententionBack;
    private boolean mSupportHalColorRententionFront;
    private boolean mSupportHalVideoBokehColorRetentionBack;
    private boolean mSupportHalVideoBokehColorRetentionFront;
    private boolean mSupportHalVideoBokehLevel;
    private boolean mSupportHalVideoFilter;
    private boolean mSupportSmoothLevel;
    private boolean mTargetShow;
    private TypeElementsBeauty mTypeElementsBeauty = new TypeElementsBeauty(this);

    public @interface ShineEntry {
    }

    public @interface ShineType {
    }

    public ComponentRunningShine(DataItemRunning dataItemRunning) {
        super(dataItemRunning);
    }

    private ComponentDataItem generateBeautyLevelItem(boolean z) {
        return c.Bn() ? new ComponentDataItem((int) R.drawable.ic_beauty_off, (int) R.drawable.ic_beauty_on, (int) R.string.beauty_fragment_tab_name_3d_beauty, "1") : new ComponentDataItem((int) R.drawable.ic_beauty_off, (int) R.drawable.ic_beauty_on, (int) R.string.beauty_fragment_tab_name_beauty, "1");
    }

    private ComponentDataItem generateFigureItem() {
        return new ComponentDataItem((int) R.drawable.ic_beauty_off, (int) R.drawable.ic_beauty_on, isSmoothDependBeautyVersion() ? R.string.beauty_fragment_tab_name_3d_beauty : R.string.beauty_body, "6");
    }

    private ComponentDataItem generateFilterItem() {
        return new ComponentDataItem((int) R.drawable.ic_new_effect_button_normal, (int) R.drawable.ic_new_effect_button_selected, (int) R.string.pref_camera_coloreffect_title, "7");
    }

    private ComponentDataItem generateFrontSuperNightBeauty() {
        return new ComponentDataItem((int) R.drawable.ic_beauty_off, (int) R.drawable.ic_beauty_on, (int) R.string.beauty_fragment_tab_name_3d_beauty, "17");
    }

    private ComponentDataItem generateKaleidoscopeItem() {
        return new ComponentDataItem((int) R.drawable.ic_vector_kaleidoscope, (int) R.drawable.ic_vector_kaleidoscope, (int) R.string.kaleidoscope_fragment_tab_name, "16");
    }

    private ComponentDataItem generateMakeupItem() {
        return new ComponentDataItem((int) R.drawable.ic_beauty_off, (int) R.drawable.ic_beauty_on, (int) R.string.beauty_fragment_tab_name_3d_makeup, "5");
    }

    private ComponentDataItem generateMiLiveItem() {
        return new ComponentDataItem((int) R.drawable.ic_beauty_off, (int) R.drawable.ic_beauty_on, (int) R.string.beauty_fragment_tab_name_3d_beauty, "15");
    }

    private ComponentDataItem generateModelItem() {
        if (!c.Bn()) {
            return new ComponentDataItem((int) R.drawable.ic_beauty_off, (int) R.drawable.ic_beauty_on, (int) R.string.beauty_fragment_tab_name_makeup, "3");
        }
        return new ComponentDataItem((int) R.drawable.ic_beauty_off, (int) R.drawable.ic_beauty_on, isSmoothDependBeautyVersion() ? R.string.beauty_fragment_tab_name_3d_beauty : R.string.beauty_fragment_tab_name_3d_remodeling, "4");
    }

    private ComponentDataItem generateSmoothLevelItem(boolean z) {
        return new ComponentDataItem((int) R.drawable.ic_beauty_off, (int) R.drawable.ic_beauty_on, (int) R.string.beauty_fragment_tab_name_3d_beauty, "2");
    }

    private ComponentDataItem generateVideoBokeh() {
        return new ComponentDataItem((int) R.drawable.ic_beauty_off, (int) R.drawable.ic_beauty_on, (int) R.string.fragment_tab_name_bokeh, "14");
    }

    public void clearArrayMap() {
        this.mIsVideoBeautySwitchedOnMap.clear();
    }

    /* JADX INFO: Can't fix incorrect switch cases order, some code will duplicate */
    /* JADX WARNING: Removed duplicated region for block: B:106:0x002b A[SYNTHETIC] */
    /* JADX WARNING: Removed duplicated region for block: B:62:0x00e3  */
    /* JADX WARNING: Removed duplicated region for block: B:64:0x00f3 A[ADDED_TO_REGION] */
    /* JADX WARNING: Removed duplicated region for block: B:68:0x0101  */
    /* JADX WARNING: Removed duplicated region for block: B:72:0x0110  */
    /* JADX WARNING: Removed duplicated region for block: B:80:0x0128  */
    /* JADX WARNING: Removed duplicated region for block: B:82:0x0130  */
    /* JADX WARNING: Removed duplicated region for block: B:84:0x013a  */
    /* JADX WARNING: Removed duplicated region for block: B:91:0x014f  */
    public boolean determineStatus(int i) {
        char c2;
        boolean z = false;
        if (this.mItems == null) {
            return false;
        }
        if (this.mBeautyValues == null) {
            this.mBeautyValues = new BeautyValues();
        }
        if (isClosed() || this.mItems == null) {
            this.mCurrentStatus = false;
        } else {
            boolean isVideoShineForceOn = isVideoShineForceOn(i);
            Iterator<ComponentDataItem> it = this.mItems.iterator();
            boolean z2 = false;
            boolean z3 = false;
            boolean z4 = false;
            boolean z5 = false;
            while (true) {
                boolean z6 = true;
                if (it.hasNext()) {
                    ComponentDataItem next = it.next();
                    if (next != null) {
                        String str = next.mValue;
                        int hashCode = str.hashCode();
                        if (hashCode != 1567) {
                            if (hashCode != 1568) {
                                switch (hashCode) {
                                    case 49:
                                        if (str.equals("1")) {
                                            c2 = 0;
                                            break;
                                        }
                                        break;
                                    case 50:
                                        if (str.equals("2")) {
                                            c2 = 7;
                                            break;
                                        }
                                        break;
                                    case 51:
                                        if (str.equals("3")) {
                                            c2 = 1;
                                            break;
                                        }
                                        break;
                                    case 52:
                                        if (str.equals("4")) {
                                            c2 = 2;
                                            break;
                                        }
                                        break;
                                    case 53:
                                        if (str.equals("5")) {
                                            c2 = 6;
                                            break;
                                        }
                                        break;
                                    case 54:
                                        if (str.equals("6")) {
                                            c2 = 4;
                                            break;
                                        }
                                        break;
                                    case 55:
                                        if (str.equals("7")) {
                                            c2 = '\t';
                                            break;
                                        }
                                        break;
                                    default:
                                        switch (hashCode) {
                                            case 1571:
                                                if (str.equals("14")) {
                                                    c2 = 11;
                                                    break;
                                                }
                                                break;
                                            case 1572:
                                                if (str.equals("15")) {
                                                    c2 = 3;
                                                    break;
                                                }
                                                break;
                                            case 1573:
                                                if (str.equals("16")) {
                                                    c2 = '\f';
                                                    break;
                                                }
                                                break;
                                            case 1574:
                                                if (str.equals("17")) {
                                                    c2 = 5;
                                                    break;
                                                }
                                                break;
                                        }
                                }
                                switch (c2) {
                                    case 0:
                                    case 1:
                                    case 2:
                                    case 3:
                                    case 4:
                                    case 5:
                                        if (z2) {
                                            break;
                                        } else {
                                            z2 = CameraSettings.isFaceBeautyOn(i, this.mBeautyValues);
                                            break;
                                        }
                                    case 6:
                                        if (!z2) {
                                            if (!CameraSettings.isFaceBeautyOn(i, this.mBeautyValues) && !CameraSettings.isEyeLightOpen()) {
                                                z6 = false;
                                            }
                                            z2 = z6;
                                            break;
                                        } else {
                                            break;
                                        }
                                    case 7:
                                        if (z2) {
                                            break;
                                        } else {
                                            z2 = CameraSettings.isFaceBeautyOn(i, this.mBeautyValues);
                                            break;
                                        }
                                    case '\b':
                                        if (z2) {
                                            break;
                                        } else {
                                            z2 = CameraSettings.isLiveBeautyOpen();
                                            break;
                                        }
                                    case '\t':
                                        if (z3) {
                                            break;
                                        } else {
                                            int shaderEffect = CameraSettings.getShaderEffect();
                                            if (!supportVideoFilter() ? shaderEffect == FilterInfo.FILTER_ID_NONE || shaderEffect <= 0 : shaderEffect == 0) {
                                                z6 = z3;
                                            }
                                            z3 = z6;
                                            break;
                                        }
                                    case '\n':
                                        if (!z3) {
                                            if (DataRepository.dataItemLive().getLiveFilter() == 0) {
                                                break;
                                            }
                                            z3 = z6;
                                            break;
                                        } else {
                                            break;
                                        }
                                    case 11:
                                        if (!z4 && CameraSettings.getVideoBokehRatio() != 0.0f) {
                                            z4 = true;
                                            break;
                                        }
                                    case '\f':
                                        if (z5) {
                                            break;
                                        } else {
                                            z5 = DataRepository.dataItemRunning().getComponentRunningKaleidoscope().isSwitchOn();
                                            break;
                                        }
                                }
                            } else if (str.equals("11")) {
                                c2 = '\b';
                                switch (c2) {
                                }
                            }
                        } else if (str.equals("10")) {
                            c2 = '\n';
                            switch (c2) {
                            }
                        }
                        c2 = 65535;
                        switch (c2) {
                        }
                    }
                } else {
                    if (isVideoShineForceOn || z2 || z3 || z4 || z5) {
                        z = true;
                    }
                    this.mCurrentStatus = z;
                }
            }
        }
        return this.mCurrentStatus;
    }

    public int getBeautyVersion() {
        return this.mBeautyVersion;
    }

    @DrawableRes
    public int getBottomEntryRes(int i) {
        this.mCurrentStatus = determineStatus(i);
        int i2 = this.mShineEntry;
        if (i2 != 4) {
            return i2 != 5 ? R.drawable.ic_shine_off : this.mCurrentStatus ? R.drawable.ic_beauty_on : R.drawable.ic_beauty_off;
        }
        boolean z = this.mCurrentStatus;
        return R.drawable.ic_vector_shine;
    }

    public boolean getCurrentStatus() {
        return this.mCurrentStatus;
    }

    public String getCurrentTipType() {
        return this.mCurrentTipType;
    }

    @ShineType
    public String getCurrentType() {
        return this.mCurrentType;
    }

    @Override // com.android.camera.data.data.ComponentData
    @NonNull
    public String getDefaultValue(int i) {
        return this.mDefaultType;
    }

    @Override // com.android.camera.data.data.ComponentData
    public int getDisplayTitleString() {
        return 0;
    }

    @Override // com.android.camera.data.data.ComponentData
    public List<ComponentDataItem> getItems() {
        return this.mItems;
    }

    @Override // com.android.camera.data.data.ComponentData
    public String getKey(int i) {
        return null;
    }

    @DrawableRes
    public int getTopConfigEntryRes(int i) {
        this.mCurrentStatus = determineStatus(i);
        int i2 = this.mShineEntry;
        if (i2 == 1) {
            boolean z = this.mCurrentStatus;
            return R.drawable.ic_shine_off;
        } else if (i2 != 2) {
            return i2 != 3 ? R.drawable.ic_shine_off : this.mCurrentStatus ? R.drawable.ic_new_effect_button_selected : R.drawable.ic_new_effect_button_normal;
        } else {
            boolean z2 = this.mCurrentStatus;
            return R.drawable.ic_vector_beauty_off;
        }
    }

    @DrawableRes
    public int getTopConfigEntryShadowRes(int i) {
        switch (i) {
            case R.drawable.ic_new_effect_button_normal:
            case R.drawable.ic_new_effect_button_selected:
                return R.drawable.ic_new_effect_button_normal_shadow;
            case R.drawable.ic_shine_off:
                return R.drawable.ic_shine_off_shadow;
            case R.drawable.ic_vector_beauty_off:
                return R.drawable.ic_vector_beauty_off_shadow;
            default:
                return -1;
        }
    }

    public int getTopConfigItem() {
        int i = this.mShineEntry;
        if (i == 1 || i == 2 || i == 3) {
            return 212;
        }
        throw new RuntimeException("unknown Shine" + this.mShineEntry);
    }

    public TypeElementsBeauty getTypeElementsBeauty() {
        return this.mTypeElementsBeauty;
    }

    public boolean isClosed() {
        return this.mIsClosed;
    }

    public boolean isLegacyBeautyVersion() {
        return this.mBeautyVersion == 1;
    }

    public boolean isSmoothDependBeautyVersion() {
        return this.mBeautyVersion == 3;
    }

    public boolean isTargetShow() {
        return this.mTargetShow;
    }

    public boolean isTopBeautyEntry() {
        return this.mShineEntry == 2;
    }

    public boolean isTopFilterEntry() {
        return this.mShineEntry == 3;
    }

    public boolean isTopShineEntry() {
        return this.mShineEntry == 1;
    }

    public boolean isVideoShineForceOn(int i) {
        if (i != 162 && i != 169 && i != 180) {
            return false;
        }
        String str = this.isFrontCamera ? "front" : "back";
        return this.mIsVideoBeautySwitchedOnMap.getOrDefault(i + str, Boolean.FALSE).booleanValue();
    }

    public void reInit() {
        if (this.mItems == null) {
            this.mItems = new CopyOnWriteArrayList();
        } else {
            this.mItems.clear();
        }
    }

    public void reInit(int i, int i2, CameraCapabilities cameraCapabilities) {
        boolean z = true;
        this.isFrontCamera = i2 == 1;
        reInit();
        this.mBeautyVersion = cameraCapabilities.getBeautyVersion();
        if (this.mBeautyVersion < 0) {
            if (c.Bn()) {
                this.mBeautyVersion = 2;
            } else {
                this.mBeautyVersion = 1;
            }
        }
        this.mShineEntry = -1;
        this.mDefaultType = null;
        this.mSupportBeautyLevel = false;
        this.mSupportSmoothLevel = false;
        this.mSupportBeautyModel = false;
        this.mSupportBeautyMakeUp = false;
        this.mSupportBeautyBody = false;
        this.mSupportBeautyMiLive = false;
        this.mSupportFrontNightBeauty = false;
        this.mSupportHalVideoFilter = false;
        this.mSupportHalColorRententionFront = false;
        this.mSupportHalColorRententionBack = false;
        this.mSupportHalVideoBokehColorRetentionFront = false;
        this.mSupportHalVideoBokehColorRetentionBack = false;
        if (i != 165) {
            if (i != 167) {
                if (i != 169) {
                    if (i == 171) {
                        this.mShineEntry = 4;
                        if (DataRepository.dataItemFeature().gj()) {
                            if (!isSmoothDependBeautyVersion()) {
                                this.mSupportBeautyLevel = true;
                                List<ComponentDataItem> list = this.mItems;
                                if (i2 != 1) {
                                    z = false;
                                }
                                list.add(generateBeautyLevelItem(z));
                            } else {
                                this.mSupportSmoothLevel = true;
                                List<ComponentDataItem> list2 = this.mItems;
                                if (i2 != 1) {
                                    z = false;
                                }
                                list2.add(generateSmoothLevelItem(z));
                            }
                        }
                        this.mItems.add(generateFilterItem());
                    } else if (i != 180) {
                        if (i != 183) {
                            if (i != 184) {
                                if (i != 204) {
                                    if (i != 205) {
                                        switch (i) {
                                            case 161:
                                                if (cameraCapabilities.isSupportVideoBeauty()) {
                                                    this.mShineEntry = 4;
                                                    if (i2 == 0) {
                                                        this.mDefaultType = "7";
                                                        if (!isSmoothDependBeautyVersion()) {
                                                            this.mSupportBeautyLevel = true;
                                                            this.mItems.add(generateBeautyLevelItem(i2 == 1));
                                                            if (DataRepository.dataItemFeature().isSupportShortVideoBeautyBody()) {
                                                                this.mSupportBeautyBody = true;
                                                                this.mItems.add(generateFigureItem());
                                                            }
                                                        } else {
                                                            this.mSupportSmoothLevel = true;
                                                            if (DataRepository.dataItemFeature().isSupportShortVideoBeautyBody()) {
                                                                this.mSupportBeautyBody = true;
                                                                this.mItems.add(generateFigureItem());
                                                            } else {
                                                                List<ComponentDataItem> list3 = this.mItems;
                                                                if (i2 != 1) {
                                                                    z = false;
                                                                }
                                                                list3.add(generateSmoothLevelItem(z));
                                                            }
                                                        }
                                                    } else if (!isSmoothDependBeautyVersion()) {
                                                        this.mSupportBeautyLevel = true;
                                                        List<ComponentDataItem> list4 = this.mItems;
                                                        if (i2 != 1) {
                                                            z = false;
                                                        }
                                                        list4.add(generateBeautyLevelItem(z));
                                                    } else {
                                                        this.mSupportSmoothLevel = true;
                                                        List<ComponentDataItem> list5 = this.mItems;
                                                        if (i2 != 1) {
                                                            z = false;
                                                        }
                                                        list5.add(generateSmoothLevelItem(z));
                                                    }
                                                    this.mItems.add(generateFilterItem());
                                                } else {
                                                    this.mShineEntry = 3;
                                                    this.mItems.add(generateFilterItem());
                                                }
                                                if (DataRepository.dataItemFeature().Qk()) {
                                                    this.mItems.add(generateKaleidoscopeItem());
                                                    break;
                                                }
                                                break;
                                            case 162:
                                                break;
                                            case 163:
                                                break;
                                            default:
                                                switch (i) {
                                                    case 173:
                                                        if (i2 == 1 && DataRepository.dataItemFeature().Cj()) {
                                                            this.mShineEntry = 4;
                                                            this.mSupportSmoothLevel = true;
                                                            this.mSupportFrontNightBeauty = true;
                                                            this.mItems.add(generateFrontSuperNightBeauty());
                                                            break;
                                                        }
                                                    case 174:
                                                        this.mShineEntry = 4;
                                                        this.mDefaultType = "10";
                                                        this.mItems.add(new ComponentDataItem((int) R.drawable.ic_beauty_off, (int) R.drawable.ic_beauty_on, (int) R.string.beauty_fragment_tab_name_3d_beauty, "11"));
                                                        this.mItems.add(new ComponentDataItem((int) R.drawable.ic_new_effect_button_normal, (int) R.drawable.ic_new_effect_button_selected, (int) R.string.pref_camera_coloreffect_title, "10"));
                                                        break;
                                                    case 176:
                                                        this.mShineEntry = 4;
                                                        if (isSmoothDependBeautyVersion()) {
                                                            this.mSupportSmoothLevel = true;
                                                            List<ComponentDataItem> list6 = this.mItems;
                                                            if (i2 != 1) {
                                                                z = false;
                                                            }
                                                            list6.add(generateSmoothLevelItem(z));
                                                            break;
                                                        } else {
                                                            this.mSupportBeautyLevel = true;
                                                            List<ComponentDataItem> list7 = this.mItems;
                                                            if (i2 != 1) {
                                                                z = false;
                                                            }
                                                            list7.add(generateBeautyLevelItem(z));
                                                            break;
                                                        }
                                                }
                                                break;
                                        }
                                    }
                                }
                                if (cameraCapabilities.isSupportVideoBeauty()) {
                                    this.mCurrentTipType = "2";
                                    if (!isSmoothDependBeautyVersion()) {
                                        this.mShineEntry = 2;
                                        this.mSupportBeautyLevel = true;
                                        this.mItems.add(generateBeautyLevelItem(i2 == 1));
                                    } else {
                                        this.mShineEntry = 1;
                                        this.mSupportSmoothLevel = true;
                                        this.mItems.add(generateSmoothLevelItem(i2 == 1));
                                    }
                                }
                                boolean isSupportVideoFilter = cameraCapabilities.isSupportVideoFilter();
                                Log.i(TAG, "isSupportVideoFilter: " + isSupportVideoFilter);
                                if (isSupportVideoFilter) {
                                    this.mSupportHalVideoFilter = true;
                                    if (i2 == 0) {
                                        this.mDefaultType = "7";
                                        this.mCurrentTipType = "7";
                                    }
                                    this.mItems.add(generateFilterItem());
                                }
                                boolean isSupportVideoBokehAdjust = cameraCapabilities.isSupportVideoBokehAdjust();
                                Log.i(TAG, "isSupportVideoBokehLevel:" + isSupportVideoBokehAdjust);
                                if (isSupportVideoBokehAdjust) {
                                    this.mSupportHalVideoBokehLevel = true;
                                    this.mItems.add(generateVideoBokeh());
                                }
                                boolean isSupportVideoBokehColorRetention = cameraCapabilities.isSupportVideoBokehColorRetention(this.isFrontCamera);
                                Log.i(TAG, "isSupportVideoBokehColorRetention:" + isSupportVideoBokehColorRetention);
                                if (isSupportVideoBokehColorRetention) {
                                    if (this.isFrontCamera) {
                                        this.mSupportHalVideoBokehColorRetentionFront = true;
                                    } else {
                                        this.mSupportHalVideoBokehColorRetentionBack = true;
                                    }
                                }
                                this.mSupportHalColorRententionFront = cameraCapabilities.isSupportVideoFilterColorRetentionFront();
                                this.mSupportHalColorRententionBack = cameraCapabilities.isSupportVideoFilterColorRetentionBack();
                                Log.i(TAG, "mSupportColorRententionFront:" + this.mSupportHalColorRententionFront + "  mSupportColorRententionBack:" + this.mSupportHalColorRententionBack);
                                if (this.mItems.size() > 1) {
                                    this.mShineEntry = 1;
                                }
                            }
                            this.mSupportSmoothLevel = true;
                        } else {
                            if (cameraCapabilities.isSupportBeauty()) {
                                this.mShineEntry = 4;
                                if (!isSmoothDependBeautyVersion()) {
                                    this.mSupportBeautyLevel = true;
                                    this.mItems.add(generateBeautyLevelItem(i2 == 1));
                                } else {
                                    this.mSupportSmoothLevel = true;
                                }
                                if (i2 == 0) {
                                    if (!isSmoothDependBeautyVersion()) {
                                        if (DataRepository.dataItemFeature().isSupportBeautyBody()) {
                                            this.mSupportBeautyBody = true;
                                            this.mItems.add(generateFigureItem());
                                        }
                                    } else if (!DataRepository.dataItemFeature().isSupportBeautyBody() || DataRepository.dataItemFeature().Lk()) {
                                        this.mItems.add(generateSmoothLevelItem(false));
                                    } else {
                                        this.mSupportBeautyBody = true;
                                        this.mItems.add(generateFigureItem());
                                    }
                                } else if (!isSmoothDependBeautyVersion()) {
                                    if (DataRepository.dataItemFeature().yi() || !DataRepository.dataItemFeature().isSupportShortVideoBeautyBody()) {
                                        this.mSupportBeautyLevel = true;
                                        this.mItems.add(generateBeautyLevelItem(false));
                                    } else {
                                        this.mSupportBeautyMiLive = true;
                                        this.mItems.add(generateMiLiveItem());
                                    }
                                } else if (DataRepository.dataItemFeature().yi() || !DataRepository.dataItemFeature().isSupportShortVideoBeautyBody()) {
                                    this.mSupportSmoothLevel = true;
                                    this.mItems.add(generateSmoothLevelItem(false));
                                } else {
                                    this.mSupportBeautyMiLive = true;
                                    this.mItems.add(generateMiLiveItem());
                                }
                            } else {
                                this.mShineEntry = 3;
                            }
                            this.mDefaultType = "7";
                            this.mItems.add(new ComponentDataItem((int) R.drawable.ic_new_effect_button_normal, (int) R.drawable.ic_new_effect_button_selected, (int) R.string.pref_camera_coloreffect_title, "7"));
                            if (DataRepository.dataItemFeature().Qk()) {
                                this.mItems.add(generateKaleidoscopeItem());
                            }
                        }
                    } else if (cameraCapabilities.isSupportVideoFilter()) {
                        this.mShineEntry = 3;
                        this.mItems.add(generateFilterItem());
                        this.mSupportHalVideoFilter = true;
                        this.mSupportHalColorRententionBack = cameraCapabilities.isSupportVideoFilterColorRetentionBack();
                        Log.i(TAG, "mSupportColorRententionFront:" + this.mSupportHalColorRententionFront + "  mSupportColorRententionBack:" + this.mSupportHalColorRententionBack);
                    }
                } else if (cameraCapabilities.isSupportVideoFilter() && DataRepository.dataItemFeature().uj()) {
                    this.mShineEntry = 3;
                    this.mItems.add(generateFilterItem());
                    this.mSupportHalVideoFilter = true;
                    this.mSupportHalColorRententionBack = cameraCapabilities.isSupportVideoFilterColorRetentionBack();
                    Log.i(TAG, "mSupportColorRententionFront:" + this.mSupportHalColorRententionFront + "  mSupportColorRententionBack:" + this.mSupportHalColorRententionBack);
                }
                if (this.mDefaultType == null && !this.mItems.isEmpty()) {
                    this.mDefaultType = this.mItems.get(0).mValue;
                }
                this.mCurrentType = this.mDefaultType;
            }
            this.mShineEntry = 3;
            this.mItems.add(generateFilterItem());
            this.mDefaultType = this.mItems.get(0).mValue;
            this.mCurrentType = this.mDefaultType;
        }
        if (!CameraSettings.isUltraPixelRearOn()) {
            if (isSmoothDependBeautyVersion() || c.sh) {
                this.mSupportSmoothLevel = true;
            } else {
                this.mSupportBeautyLevel = true;
                this.mItems.add(generateBeautyLevelItem(i2 == 1));
            }
            if (i2 == 0) {
                this.mShineEntry = 1;
                this.mDefaultType = "7";
                if (DataRepository.dataItemFeature().isSupportBeautyBody()) {
                    this.mSupportBeautyBody = true;
                    this.mItems.add(generateFigureItem());
                } else if (isSmoothDependBeautyVersion()) {
                    this.mItems.add(generateSmoothLevelItem(false));
                }
            } else {
                this.mShineEntry = 4;
                if (!DataRepository.dataItemFeature().yi()) {
                    this.mSupportBeautyModel = true;
                    this.mItems.add(generateModelItem());
                    if (DataRepository.dataItemFeature().rl() && cameraCapabilities.isSupportBeautyMakeup()) {
                        this.mSupportBeautyMakeUp = true;
                    }
                } else if (isSmoothDependBeautyVersion()) {
                    this.mItems.add(generateSmoothLevelItem(true));
                }
            }
        } else if (i2 == 0) {
            this.mShineEntry = 3;
        } else {
            this.mShineEntry = 4;
        }
        this.mItems.add(generateFilterItem());
        this.mDefaultType = this.mItems.get(0).mValue;
        this.mCurrentType = this.mDefaultType;
    }

    public void setClosed(boolean z) {
        this.mIsClosed = z;
    }

    public void setCurrentTipType(String str) {
        this.mCurrentTipType = str;
    }

    public void setCurrentType(@ShineType String str) {
        this.mCurrentType = str;
    }

    public void setTargetShow(boolean z) {
        this.mTargetShow = z;
    }

    public void setVideoShineForceOn(int i, boolean z) {
        String str = this.isFrontCamera ? "front" : "back";
        this.mIsVideoBeautySwitchedOnMap.put(i + str, Boolean.valueOf(z));
    }

    public boolean supportBeautyBody() {
        return this.mSupportBeautyBody;
    }

    public boolean supportBeautyLevel() {
        return this.mSupportBeautyLevel;
    }

    public boolean supportBeautyMakeUp() {
        return this.mSupportBeautyMakeUp;
    }

    public boolean supportBeautyMiLive() {
        return this.mSupportBeautyMiLive;
    }

    public boolean supportBeautyModel() {
        return this.mSupportBeautyModel;
    }

    public boolean supportColorRentention() {
        return this.isFrontCamera ? this.mSupportHalColorRententionFront : this.mSupportHalColorRententionBack;
    }

    public boolean supportFrontNightBeauty() {
        return this.mSupportFrontNightBeauty;
    }

    public boolean supportPopUpEntry() {
        int i = this.mShineEntry;
        return i == 4 || i == 5;
    }

    public boolean supportSmoothLevel() {
        return this.mSupportSmoothLevel;
    }

    public boolean supportTopConfigEntry() {
        int i = this.mShineEntry;
        return i == 1 || i == 2 || i == 3;
    }

    public boolean supportVideoBokehColorRetention() {
        return this.isFrontCamera ? this.mSupportHalVideoBokehColorRetentionFront : this.mSupportHalVideoBokehColorRetentionBack;
    }

    public boolean supportVideoFilter() {
        return this.mSupportHalVideoFilter;
    }
}
