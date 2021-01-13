package com.android.camera.data.data.global;

import b.c.a.b;
import b.c.a.c;
import com.android.camera.CameraAppImpl;
import com.android.camera.CameraSettings;
import com.android.camera.R;
import com.android.camera.data.DataRepository;
import com.android.camera.data.data.ComponentData;
import com.android.camera.data.data.ComponentDataItem;
import com.android.camera.data.data.config.ComponentRunningUltraPixel;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import miui.telephony.phonenumber.CountryCodeConverter;

public class ComponentModuleList extends ComponentData {
    private boolean isChanged;
    private List<ComponentDataItem> mFavoriteItems = new CopyOnWriteArrayList();
    private List<ComponentDataItem> mInMoreItems = new CopyOnWriteArrayList();
    private int mIntentType;
    private int mLastCameraId;

    public ComponentModuleList(DataItemGlobal dataItemGlobal) {
        super(dataItemGlobal);
    }

    private int getMoreItemIndex(List<ComponentDataItem> list) {
        if (this.mIntentType != 0 || list.isEmpty()) {
            return -1;
        }
        int i = 0;
        while (list.size() > i && Integer.parseInt(list.get(i).mValue) != 254) {
            i++;
        }
        if (i == list.size()) {
            return -1;
        }
        return i;
    }

    public static final int getTransferredMode(int i) {
        if (i == 165) {
            return 163;
        }
        if (i == 176) {
            return 166;
        }
        if (i == 185) {
            return 210;
        }
        if (i == 179) {
            return 209;
        }
        if (i != 180) {
            return i;
        }
        return 167;
    }

    private void initImageItems(List<ComponentDataItem> list) {
        list.add(new ComponentDataItem((int) R.drawable.ic_mode_capture, -1, (int) R.string.module_name_capture, String.valueOf(163)));
    }

    private synchronized List<ComponentDataItem> initItems() {
        ArrayList arrayList;
        if (this.mIntentType != -1) {
            arrayList = new ArrayList();
            int i = this.mIntentType;
            if (i == 0) {
                initNormalItems(arrayList);
            } else if (i == 1) {
                initImageItems(arrayList);
            } else if (i == 2) {
                initVideoItems(arrayList);
            } else if (i == 3) {
                initQRItems(arrayList);
            }
            this.mFavoriteItems.clear();
            this.mInMoreItems.clear();
            int moreItemIndex = getMoreItemIndex(arrayList);
            if (moreItemIndex == -1) {
                this.mFavoriteItems.addAll(arrayList);
            } else {
                if (CameraSettings.getMoreModeStyle() == 1) {
                    this.mFavoriteItems.addAll(arrayList.subList(0, moreItemIndex));
                } else if (CameraSettings.getMoreModeStyle() == 0) {
                    this.mFavoriteItems.addAll(arrayList.subList(0, moreItemIndex + 1));
                }
                this.mInMoreItems.addAll(arrayList.subList(moreItemIndex + 1, arrayList.size()));
            }
        } else {
            throw new RuntimeException("parse intent first!");
        }
        return arrayList;
    }

    private void initNormalItems(List<ComponentDataItem> list) {
        b dataItemFeature = DataRepository.dataItemFeature();
        if (dataItemFeature.Xj()) {
            if (DataRepository.dataItemFeature().Ej()) {
                list.add(new ComponentDataItem((int) R.drawable.ic_mode_mimoji, -1, (int) R.string.module_name_fun_ar, String.valueOf(177)));
            }
            if (DataRepository.dataItemFeature().Sj()) {
                list.add(new ComponentDataItem((int) R.drawable.ic_mode_mimoji, -1, (int) R.string.module_name_fun_ar, String.valueOf(184)));
                return;
            }
            return;
        }
        HashMap hashMap = new HashMap();
        if (DataRepository.dataItemGlobal().getDisplayMode() == 1) {
            hashMap.put(167, new ComponentDataItem((int) R.drawable.ic_mode_pro, -1, (int) R.string.module_name_professional, String.valueOf(167)));
        }
        if (dataItemFeature.wk()) {
            hashMap.put(171, new ComponentDataItem((int) R.drawable.ic_mode_portrait, -1, (int) R.string.module_name_portrait, String.valueOf(171)));
        }
        hashMap.put(163, new ComponentDataItem((int) R.drawable.ic_mode_capture, -1, (int) R.string.module_name_capture, String.valueOf(163)));
        if (DataRepository.dataItemFeature().Qh() > 0 && DataRepository.dataItemGlobal().getDisplayMode() == 1) {
            hashMap.put(175, new ComponentDataItem(ComponentRunningUltraPixel.getUltraPixelIcon(), -1, ultraPixelModuleName(), String.valueOf(175)));
        }
        hashMap.put(162, new ComponentDataItem((int) R.drawable.ic_mode_video, -1, (int) R.string.module_name_video, String.valueOf(162)));
        hashMap.put(254, new ComponentDataItem(-1, -1, (int) R.string.module_name_more, String.valueOf(254)));
        if ((dataItemFeature.Ek() || dataItemFeature.Zl()) && DataRepository.dataItemGlobal().getDisplayMode() == 1) {
            hashMap.put(173, new ComponentDataItem((int) R.drawable.ic_mode_night, -1, (int) R.string.pref_camera_scenemode_entry_night, String.valueOf(173)));
        }
        if (dataItemFeature.Rj() && !dataItemFeature.Kj()) {
            hashMap.put(183, new ComponentDataItem((int) R.drawable.ic_mode_live, -1, (int) R.string.module_name_fun, String.valueOf(183)));
        }
        if (dataItemFeature.Kj() && !dataItemFeature.Rj()) {
            hashMap.put(174, new ComponentDataItem((int) R.drawable.ic_mode_live, -1, (int) R.string.module_name_fun, String.valueOf(174)));
        }
        if (!dataItemFeature.Kj() && !dataItemFeature.Rj()) {
            hashMap.put(161, new ComponentDataItem((int) R.drawable.ic_mode_live, -1, (int) R.string.module_name_fun, String.valueOf(161)));
        }
        if (DataRepository.dataItemFeature().Ej()) {
            hashMap.put(177, new ComponentDataItem((int) R.drawable.ic_mode_mimoji, -1, (int) R.string.module_name_fun_ar, String.valueOf(177)));
        }
        if (DataRepository.dataItemFeature().Sj()) {
            hashMap.put(184, new ComponentDataItem((int) R.drawable.ic_mode_mimoji, -1, (int) R.string.module_name_fun_ar, String.valueOf(184)));
        }
        if (dataItemFeature.Dk() && DataRepository.dataItemGlobal().getDisplayMode() == 1) {
            hashMap.put(166, new ComponentDataItem((int) R.drawable.ic_mode_panorama, -1, (int) R.string.module_name_panorama, String.valueOf(166)));
        }
        if ((dataItemFeature.qj() || dataItemFeature.rj()) && DataRepository.dataItemGlobal().getDisplayMode() == 1) {
            hashMap.put(186, new ComponentDataItem((int) R.drawable.ic_mode_doc, -1, (int) R.string.pref_document_mode, String.valueOf(186)));
        }
        if (dataItemFeature.mk() && DataRepository.dataItemGlobal().getDisplayMode() == 1) {
            hashMap.put(188, new ComponentDataItem((int) R.drawable.ic_mode_super_moon, -1, (int) R.string.module_name_super_moon, String.valueOf(188)));
        }
        if (dataItemFeature.H() && DataRepository.dataItemGlobal().getDisplayMode() == 1) {
            hashMap.put(209, new ComponentDataItem((int) R.drawable.ic_mode_vlog, -1, (int) R.string.module_name_vlog, String.valueOf(209)));
        }
        if (dataItemFeature.wm() && DataRepository.dataItemGlobal().getDisplayMode() == 1) {
            hashMap.put(172, new ComponentDataItem((int) R.drawable.ic_mode_slow_motion, -1, (int) R.string.module_name_new_slow_motion, String.valueOf(172)));
        }
        hashMap.put(169, new ComponentDataItem((int) R.drawable.ic_mode_time_lapse, -1, (int) R.string.pref_video_speed_fast_title, String.valueOf(169)));
        if (dataItemFeature.hj()) {
            hashMap.put(210, new ComponentDataItem((int) R.drawable.ic_mode_clone, -1, (int) R.string.module_name_clone, String.valueOf(210)));
        }
        if (dataItemFeature.sj()) {
            hashMap.put(204, new ComponentDataItem((int) R.drawable.ic_mode_dual_video, -1, dataItemFeature.tj() ? R.string.module_name_multi_camera : R.string.pref_dual_camera_video, String.valueOf(204)));
        }
        if (dataItemFeature.il()) {
            hashMap.put(205, new ComponentDataItem((int) R.drawable.ic_mode_more_ai_watermark, -1, (int) R.string.ai_watermark_title, String.valueOf(205)));
        }
        if (dataItemFeature.sm()) {
            hashMap.put(187, new ComponentDataItem((int) R.drawable.ic_mode_ambilight, -1, (int) R.string.parameter_ambilight_title, String.valueOf(187)));
        }
        int[] sortModes = DataRepository.dataItemGlobal().getSortModes();
        int[] iArr = new int[hashMap.size()];
        int i = 0;
        for (int i2 = 0; i2 < sortModes.length; i2++) {
            ComponentDataItem componentDataItem = (ComponentDataItem) hashMap.get(Integer.valueOf(sortModes[i2]));
            if (componentDataItem != null) {
                list.add(componentDataItem);
                hashMap.remove(Integer.valueOf(sortModes[i2]));
                iArr[i] = sortModes[i2];
                i++;
            }
        }
        if (!hashMap.isEmpty()) {
            for (Integer num : hashMap.keySet()) {
                list.add((ComponentDataItem) hashMap.get(num));
                iArr[i] = num.intValue();
                i++;
            }
        }
        list.add(new ComponentDataItem((int) R.drawable.ic_mode_edit, -1, (int) R.string.module_name_edit, String.valueOf(255)));
        DataRepository.dataItemGlobal().setSortModes(iArr);
    }

    private void initQRItems(List<ComponentDataItem> list) {
        list.add(new ComponentDataItem((int) R.drawable.ic_mode_capture, -1, (int) R.string.module_name_capture, String.valueOf(163)));
    }

    private void initVideoItems(List<ComponentDataItem> list) {
        list.add(new ComponentDataItem((int) R.drawable.ic_mode_video, -1, (int) R.string.module_name_video, String.valueOf(162)));
    }

    private String ultraPixelModuleName() {
        if (c.mh && c.isGlobal()) {
            return "64M";
        }
        if (!c.lh) {
            return DataRepository.dataItemFeature().Ok();
        }
        String str = c.Vm() ? CountryCodeConverter.PL : CountryCodeConverter.NZ;
        return str + DataRepository.dataItemFeature().Ok();
    }

    public void clear() {
        this.mItems = null;
    }

    public String geMoreItemName(int i) {
        String valueOf = String.valueOf(getTransferredMode(i));
        for (ComponentDataItem componentDataItem : getMoreItems()) {
            if (valueOf.equals(componentDataItem.mValue)) {
                return componentDataItem.mDisplayNameRes > 0 ? CameraAppImpl.getAndroidContext().getResources().getString(componentDataItem.mDisplayNameRes) : componentDataItem.mDisplayNameStr;
            }
        }
        return "";
    }

    public List<ComponentDataItem> getCommonItems() {
        if (this.mIntentType != 0) {
            return getItems();
        }
        if (this.mItems == null) {
            this.mItems = initItems();
        }
        return this.mFavoriteItems;
    }

    @Override // com.android.camera.data.data.ComponentData
    public String getDefaultValue(int i) {
        return null;
    }

    @Override // com.android.camera.data.data.ComponentData
    public int getDisplayTitleString() {
        return 0;
    }

    @Override // com.android.camera.data.data.ComponentData
    public List<ComponentDataItem> getItems() {
        int currentCameraId = DataRepository.dataItemGlobal().getCurrentCameraId();
        if (DataRepository.dataItemFeature().fk() && this.mLastCameraId != currentCameraId) {
            this.mLastCameraId = currentCameraId;
            this.mItems = initItems();
        }
        if (this.mItems == null) {
            this.mItems = initItems();
        }
        return this.mItems;
    }

    @Override // com.android.camera.data.data.ComponentData
    public String getKey(int i) {
        return DataItemGlobal.DATA_COMMON_CURRENT_MODE + this.mIntentType;
    }

    public int getMode(int i) {
        return Integer.parseInt(getItems().get(i).mValue);
    }

    public List<ComponentDataItem> getMoreItems() {
        if (this.mIntentType != 0) {
            return new ArrayList();
        }
        if (this.mItems == null) {
            this.mItems = initItems();
        }
        return this.mInMoreItems;
    }

    public boolean isCommonMode(int i) {
        int transferredMode = getTransferredMode(i);
        if (this.mFavoriteItems.isEmpty()) {
            this.mItems = initItems();
        }
        for (ComponentDataItem componentDataItem : this.mFavoriteItems) {
            if (componentDataItem != null && transferredMode == Integer.parseInt(componentDataItem.mValue)) {
                return true;
            }
        }
        return false;
    }

    public boolean needShowLiveRedDot() {
        return !CameraSettings.isLiveModuleClicked();
    }

    public void reInit(boolean z) {
        if (z || DataRepository.dataItemGlobal().hasModesChanged()) {
            this.mItems = initItems();
            this.isChanged = true;
        }
    }

    public void runChangeResetCb(Runnable runnable) {
        if (this.isChanged && runnable != null) {
            this.isChanged = false;
            runnable.run();
        }
    }

    public void setIntentType(int i) {
        this.mIntentType = i;
        if (this.mItems != null) {
            this.mItems.clear();
        }
        this.mItems = initItems();
    }
}
