package com.android.camera.data.data.config;

import android.content.res.Resources;
import android.util.Size;
import android.util.SparseBooleanArray;
import androidx.annotation.DrawableRes;
import androidx.annotation.NonNull;
import com.android.camera.CameraAppImpl;
import com.android.camera.CameraSettings;
import com.android.camera.R;
import com.android.camera.data.DataRepository;
import com.android.camera.data.data.ComponentData;
import com.android.camera.data.data.ComponentDataItem;
import com.android.camera.data.data.runing.DataItemRunning;
import com.android.camera.log.Log;
import com.android.camera2.CameraCapabilities;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ComponentRunningUltraPixel extends ComponentData {
    private static final String TAG = "ComponentRunningUltraPixel";
    public static final String ULTRA_PIXEL_OFF = "OFF";
    public static final String ULTRA_PIXEL_ON_FRONT_32M = "FRONT_0x1";
    public static final String ULTRA_PIXEL_ON_REAR_108M = "REAR_0x3";
    public static final String ULTRA_PIXEL_ON_REAR_48M = "REAR_0x2";
    public static final String ULTRA_PIXEL_ON_REAR_64M = "REAR_0x1";
    public static final String ULTRA_PIXEL_ON_REAR_AI_108M = "REAR_0x4";
    private String mCloseTipString = null;
    private int mCurrentMode;
    private SparseBooleanArray mIsClosed;
    @DrawableRes
    private int mMenuDrawable = -1;
    private String mMenuString = null;
    private String mOpenTipString = null;

    public @interface UltraPixelSupport {
    }

    public ComponentRunningUltraPixel(DataItemRunning dataItemRunning) {
        super(dataItemRunning);
    }

    private void add108M(List<ComponentDataItem> list) {
        Resources resources = CameraAppImpl.getAndroidContext().getResources();
        list.add(new ComponentDataItem((int) R.drawable.ic_ultra_pixel_photography_108mp, (int) R.drawable.ic_ultra_pixel_photography_108mp, resources.getString(R.string.pref_menu_ultra_pixel_photography, resources.getString(R.string.ultra_pixel_108mp)), "OFF"));
        list.add(new ComponentDataItem((int) R.drawable.ic_ultra_pixel_photography_108mp, (int) R.drawable.ic_ultra_pixel_photography_108mp, resources.getString(R.string.pref_menu_ultra_pixel_photography, resources.getString(R.string.ultra_pixel_108mp)), ULTRA_PIXEL_ON_REAR_108M));
        initUltraPixelResource(ULTRA_PIXEL_ON_REAR_108M);
    }

    private void add48M(List<ComponentDataItem> list) {
        Resources resources = CameraAppImpl.getAndroidContext().getResources();
        list.add(new ComponentDataItem((int) R.drawable.ic_menu_ultra_pixel_photography_48mp, (int) R.drawable.ic_menu_ultra_pixel_photography_48mp, resources.getString(R.string.pref_menu_ultra_pixel_photography, resources.getString(R.string.ultra_pixel_48mp)), "OFF"));
        list.add(new ComponentDataItem((int) R.drawable.ic_menu_ultra_pixel_photography_48mp, (int) R.drawable.ic_menu_ultra_pixel_photography_48mp, resources.getString(R.string.pref_menu_ultra_pixel_photography, resources.getString(R.string.ultra_pixel_48mp)), ULTRA_PIXEL_ON_REAR_48M));
        initUltraPixelResource(ULTRA_PIXEL_ON_REAR_48M);
    }

    private void add64M(List<ComponentDataItem> list) {
        Resources resources = CameraAppImpl.getAndroidContext().getResources();
        list.add(new ComponentDataItem((int) R.drawable.ic_menu_ultra_pixel_photography_64mp, (int) R.drawable.ic_menu_ultra_pixel_photography_64mp, resources.getString(R.string.pref_menu_ultra_pixel_photography, resources.getString(R.string.ultra_pixel_64mp)), "OFF"));
        list.add(new ComponentDataItem((int) R.drawable.ic_menu_ultra_pixel_photography_64mp, (int) R.drawable.ic_menu_ultra_pixel_photography_64mp, resources.getString(R.string.pref_menu_ultra_pixel_photography, resources.getString(R.string.ultra_pixel_64mp)), ULTRA_PIXEL_ON_REAR_64M));
        initUltraPixelResource(ULTRA_PIXEL_ON_REAR_64M);
    }

    private void addAI108M(List<ComponentDataItem> list) {
        Resources resources = CameraAppImpl.getAndroidContext().getResources();
        list.add(new ComponentDataItem((int) R.drawable.ic_ultra_pixel_photography_108mp, (int) R.drawable.ic_ultra_pixel_photography_108mp, resources.getString(R.string.pref_menu_ultra_pixel_photography, resources.getString(R.string.ultra_pixel_108mp)), "OFF"));
        list.add(new ComponentDataItem((int) R.drawable.ic_ultra_pixel_photography_108mp, (int) R.drawable.ic_ultra_pixel_photography_108mp, resources.getString(R.string.pref_menu_ultra_pixel_photography, resources.getString(R.string.ultra_pixel_108mp)), ULTRA_PIXEL_ON_REAR_108M));
        initUltraPixelResource(ULTRA_PIXEL_ON_REAR_AI_108M);
    }

    private List<ComponentDataItem> createItems(int i, int i2, CameraCapabilities cameraCapabilities) {
        int Qh;
        this.mCurrentMode = i;
        ArrayList arrayList = new ArrayList();
        if (cameraCapabilities == null) {
            return arrayList;
        }
        Resources resources = CameraAppImpl.getAndroidContext().getResources();
        if (i == 163 || i == 165 || i == 167) {
            if (i2 == 0) {
                int Qh2 = DataRepository.dataItemFeature().Qh();
                Size Zh = DataRepository.dataItemFeature().Zh();
                if (Qh2 > -1 && cameraCapabilities.isUltraPixelPhotographySupported(Zh)) {
                    if (Qh2 == 1) {
                        arrayList.add(new ComponentDataItem((int) R.drawable.ic_menu_ultra_pixel_photography_48mp, (int) R.drawable.ic_menu_ultra_pixel_photography_48mp, resources.getString(R.string.pref_menu_ultra_pixel_photography, resources.getString(R.string.ultra_pixel_48mp)), "OFF"));
                        arrayList.add(new ComponentDataItem((int) R.drawable.ic_menu_ultra_pixel_photography_48mp, (int) R.drawable.ic_menu_ultra_pixel_photography_48mp, resources.getString(R.string.pref_menu_ultra_pixel_photography, resources.getString(R.string.ultra_pixel_48mp)), ULTRA_PIXEL_ON_REAR_48M));
                        initUltraPixelResource(ULTRA_PIXEL_ON_REAR_48M);
                    } else if (Qh2 == 2) {
                        arrayList.add(new ComponentDataItem((int) R.drawable.ic_menu_ultra_pixel_photography_64mp, (int) R.drawable.ic_menu_ultra_pixel_photography_64mp, resources.getString(R.string.pref_menu_ultra_pixel_photography, resources.getString(R.string.ultra_pixel_64mp)), "OFF"));
                        arrayList.add(new ComponentDataItem((int) R.drawable.ic_menu_ultra_pixel_photography_64mp, (int) R.drawable.ic_menu_ultra_pixel_photography_64mp, resources.getString(R.string.pref_menu_ultra_pixel_photography, resources.getString(R.string.ultra_pixel_64mp)), ULTRA_PIXEL_ON_REAR_64M));
                        initUltraPixelResource(ULTRA_PIXEL_ON_REAR_64M);
                    } else if (Qh2 != 3) {
                        String str = TAG;
                        Log.d(str, "Unknown rearPixel index: " + Qh2);
                    } else {
                        add108M(arrayList);
                    }
                }
            }
        } else if (i == 175 && i2 == 0 && (Qh = DataRepository.dataItemFeature().Qh()) > -1) {
            if (Qh != 1) {
                if (Qh == 2) {
                    add64M(arrayList);
                } else if (Qh == 3) {
                    add108M(arrayList);
                }
            } else if (CameraSettings.isSRTo108mModeOn()) {
                addAI108M(arrayList);
            } else {
                add48M(arrayList);
            }
        }
        return arrayList;
    }

    public static String getNoSupportZoomTip() {
        Resources resources = CameraAppImpl.getAndroidContext().getResources();
        int Qh = DataRepository.dataItemFeature().Qh();
        if (DataRepository.dataItemRunning().getAi108Running()) {
            Qh = 3;
        }
        if (Qh == 1) {
            return resources.getString(R.string.ultra_pixel_zoom_no_support_tip, resources.getString(R.string.ultra_pixel_48mp));
        } else if (Qh == 2) {
            return resources.getString(R.string.ultra_pixel_zoom_no_support_tip, resources.getString(R.string.ultra_pixel_64mp));
        } else if (Qh != 3) {
            return resources.getString(R.string.ultra_pixel_zoom_no_support_tip, resources.getString(R.string.ultra_pixel_48mp));
        } else {
            return resources.getString(R.string.ultra_pixel_zoom_no_support_tip, resources.getString(R.string.ultra_pixel_108mp));
        }
    }

    public static int getUltraPixelIcon() {
        int Qh = DataRepository.dataItemFeature().Qh();
        if (Qh == 0) {
            return R.drawable.ic_mode_32;
        }
        if (Qh == 1) {
            return R.drawable.ic_mode_48;
        }
        if (Qh == 2) {
            return R.drawable.ic_mode_64;
        }
        if (Qh != 3) {
            return 0;
        }
        return R.drawable.ic_mode_108;
    }

    public static String[] getUltraPixelSwitchTipsString() {
        Resources resources = CameraAppImpl.getAndroidContext().getResources();
        int Qh = DataRepository.dataItemFeature().Qh();
        if (Qh == 1) {
            return new String[]{resources.getString(R.string.accessibility_ultra_pixel_photography_off, resources.getString(R.string.ultra_pixel_48mp)), resources.getString(R.string.accessibility_ultra_pixel_photography_on, resources.getString(R.string.ultra_pixel_48mp))};
        } else if (Qh == 2) {
            return new String[]{resources.getString(R.string.accessibility_ultra_pixel_photography_off, resources.getString(R.string.ultra_pixel_64mp)), resources.getString(R.string.accessibility_ultra_pixel_photography_on, resources.getString(R.string.ultra_pixel_64mp))};
        } else if (Qh != 3) {
            return new String[]{resources.getString(R.string.accessibility_ultra_pixel_photography_off, resources.getString(R.string.ultra_pixel_48mp)), resources.getString(R.string.accessibility_ultra_pixel_photography_on, resources.getString(R.string.ultra_pixel_48mp))};
        } else {
            return new String[]{resources.getString(R.string.accessibility_ultra_pixel_photography_off, resources.getString(R.string.ultra_pixel_108mp)), resources.getString(R.string.accessibility_ultra_pixel_photography_on, resources.getString(R.string.ultra_pixel_108mp))};
        }
    }

    public static int[] getUltraPixelTopMenuResources() {
        int Qh = DataRepository.dataItemFeature().Qh();
        return Qh != 1 ? Qh != 2 ? Qh != 3 ? new int[]{R.drawable.ic_menu_ultra_pixel_photography_48mp, R.drawable.ic_menu_ultra_pixel_photography_48mp_shadow} : new int[]{R.drawable.ic_ultra_pixel_photography_108mp, R.drawable.ic_ultra_pixel_photography_108mp_shadow} : new int[]{R.drawable.ic_menu_ultra_pixel_photography_64mp, R.drawable.ic_menu_ultra_pixel_photography_64mp_shadow} : new int[]{R.drawable.ic_menu_ultra_pixel_photography_48mp, R.drawable.ic_menu_ultra_pixel_photography_48mp_shadow};
    }

    /* JADX INFO: Can't fix incorrect switch cases order, some code will duplicate */
    /* JADX WARNING: Removed duplicated region for block: B:21:0x0058  */
    /* JADX WARNING: Removed duplicated region for block: B:31:0x014c  */
    private void initUltraPixelResource(@UltraPixelSupport String str) {
        char c2;
        Resources resources = CameraAppImpl.getAndroidContext().getResources();
        int hashCode = str.hashCode();
        if (hashCode != -1379357773) {
            switch (hashCode) {
                case -70725170:
                    if (str.equals(ULTRA_PIXEL_ON_REAR_64M)) {
                        c2 = 2;
                        break;
                    }
                    break;
                case -70725169:
                    if (str.equals(ULTRA_PIXEL_ON_REAR_48M)) {
                        c2 = 1;
                        break;
                    }
                    break;
                case -70725168:
                    if (str.equals(ULTRA_PIXEL_ON_REAR_108M)) {
                        c2 = 3;
                        break;
                    }
                    break;
                case -70725167:
                    if (str.equals(ULTRA_PIXEL_ON_REAR_AI_108M)) {
                        c2 = 4;
                        break;
                    }
                    break;
            }
            if (c2 == 0) {
                this.mMenuDrawable = R.drawable.ic_menu_ultra_pixel_photography_32mp;
                this.mOpenTipString = resources.getString(R.string.ultra_pixel_photography_open_tip, resources.getString(R.string.ultra_pixel_32mp));
                this.mCloseTipString = resources.getString(R.string.ultra_pixel_photography_close_tip, resources.getString(R.string.ultra_pixel_32mp));
                this.mMenuString = resources.getString(R.string.pref_menu_ultra_pixel_photography, resources.getString(R.string.ultra_pixel_32mp));
                return;
            } else if (c2 == 1) {
                this.mMenuDrawable = R.drawable.ic_menu_ultra_pixel_photography_48mp;
                this.mOpenTipString = resources.getString(R.string.ultra_pixel_photography_open_tip, resources.getString(R.string.ultra_pixel_48mp));
                this.mCloseTipString = resources.getString(R.string.ultra_pixel_photography_close_tip, resources.getString(R.string.ultra_pixel_48mp));
                this.mMenuString = resources.getString(R.string.pref_menu_ultra_pixel_photography, resources.getString(R.string.ultra_pixel_48mp));
                return;
            } else if (c2 == 2) {
                this.mMenuDrawable = R.drawable.ic_menu_ultra_pixel_photography_64mp;
                this.mOpenTipString = resources.getString(R.string.ultra_pixel_photography_open_tip, resources.getString(R.string.ultra_pixel_64mp));
                this.mCloseTipString = resources.getString(R.string.ultra_pixel_photography_close_tip, resources.getString(R.string.ultra_pixel_64mp));
                this.mMenuString = resources.getString(R.string.pref_menu_ultra_pixel_photography, resources.getString(R.string.ultra_pixel_64mp));
                return;
            } else if (c2 == 3) {
                this.mMenuDrawable = R.drawable.ic_ultra_pixel_photography_108mp;
                this.mOpenTipString = resources.getString(R.string.ultra_pixel_photography_open_tip, resources.getString(R.string.ultra_pixel_108mp));
                this.mCloseTipString = resources.getString(R.string.ultra_pixel_photography_close_tip, resources.getString(R.string.ultra_pixel_108mp));
                this.mMenuString = resources.getString(R.string.pref_menu_ultra_pixel_photography, resources.getString(R.string.ultra_pixel_108mp));
                return;
            } else if (c2 != 4) {
                Log.d(TAG, "Unknown ultra pixel size: " + str);
                return;
            } else {
                this.mMenuDrawable = R.drawable.ic_ultra_pixel_photography_108mp;
                this.mOpenTipString = resources.getString(R.string.ultra_ai_pixel_photography_open_tip, resources.getString(R.string.ai_ultra_pixel_108mp));
                this.mCloseTipString = resources.getString(R.string.ultra_ai_pixel_photography_close_tip, resources.getString(R.string.ai_ultra_pixel_108mp));
                this.mMenuString = resources.getString(R.string.pref_menu_ai_ultra_pixel_photography, resources.getString(R.string.ai_ultra_pixel_108mp));
                return;
            }
        } else if (str.equals(ULTRA_PIXEL_ON_FRONT_32M)) {
            c2 = 0;
            if (c2 == 0) {
            }
        }
        c2 = 65535;
        if (c2 == 0) {
        }
    }

    @UltraPixelSupport
    public String getCurrentSupportUltraPixel() {
        return this.mItems.get(1).mValue;
    }

    @Override // com.android.camera.data.data.ComponentData
    @NonNull
    public String getDefaultValue(int i) {
        return "OFF";
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
        return "pref_ultra_pixel";
    }

    @DrawableRes
    public int getMenuDrawable() {
        return this.mMenuDrawable;
    }

    public String getMenuString() {
        return this.mMenuString;
    }

    public String getUltraPixelCloseTip() {
        return this.mCloseTipString;
    }

    public String getUltraPixelOpenTip() {
        return this.mOpenTipString;
    }

    public boolean isClosed() {
        if (this.mIsClosed == null) {
            return false;
        }
        if (this.mCurrentMode == 165) {
            this.mCurrentMode = 163;
        }
        return this.mIsClosed.get(this.mCurrentMode);
    }

    public boolean isFront32MPSwitchOn() {
        return ULTRA_PIXEL_ON_FRONT_32M.equals(getComponentValue(160));
    }

    public boolean isRear108MPSwitchOn() {
        if (isClosed()) {
            return false;
        }
        return ULTRA_PIXEL_ON_REAR_108M.equals(getComponentValue(160));
    }

    public boolean isRear48MPSwitchOn() {
        if (isClosed()) {
            return false;
        }
        return ULTRA_PIXEL_ON_REAR_48M.equals(getComponentValue(160));
    }

    public boolean isRear64MPSwitchOn() {
        if (isClosed()) {
            return false;
        }
        return ULTRA_PIXEL_ON_REAR_64M.equals(getComponentValue(160));
    }

    public boolean isRearSwitchOn() {
        return isRear48MPSwitchOn() || isRear64MPSwitchOn() || isRear108MPSwitchOn();
    }

    public boolean isSwitchOn() {
        if (isClosed()) {
            return false;
        }
        return !"OFF".equals(getComponentValue(160));
    }

    public void reInit(int i, int i2, CameraCapabilities cameraCapabilities) {
        this.mItems = Collections.unmodifiableList(createItems(i, i2, cameraCapabilities));
    }

    public void setClosed(boolean z) {
        if (this.mIsClosed == null) {
            this.mIsClosed = new SparseBooleanArray();
        }
        if (this.mCurrentMode == 165) {
            this.mCurrentMode = 163;
        }
        this.mIsClosed.put(this.mCurrentMode, z);
    }

    public void switchOff() {
        setComponentValue(160, "OFF");
    }

    public void switchOn(@UltraPixelSupport String str) {
        setClosed(false);
        setComponentValue(160, str);
    }

    public void switchOnCurrentSupported(int i, int i2, CameraCapabilities cameraCapabilities) {
        if (isEmpty() || this.mItems.size() < 2) {
            reInit(i, i2, cameraCapabilities);
        }
        if (isEmpty()) {
            Log.e("UltraPixel:", "CameraCapabilities not supported");
            return;
        }
        setClosed(false);
        setComponentValue(160, getCurrentSupportUltraPixel());
    }
}
