package com.android.camera.fragment.beauty;

import android.text.TextUtils;
import com.android.camera.CameraSettings;
import com.android.camera.constant.VideoBokehConstant;
import com.android.camera.data.DataRepository;
import com.android.camera.data.data.TypeItem;
import com.android.camera.data.data.global.DataItemGlobal;
import com.android.camera.data.data.runing.ComponentRunningShine;
import com.android.camera.data.data.runing.TypeElementsBeauty;
import com.android.camera.module.loader.camera2.Camera2DataContainer;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class VideoBokehSettingBusiness implements IBeautySettingBusiness {
    private static final String TAG = "VideoBokehSettingBusiness";
    @ComponentRunningShine.ShineType
    private String mBeautyType;
    private String mCurrentBeautyItemType;
    private Map<String, Integer> mExtraTable = new HashMap();
    private List<TypeItem> mSupportedTypeItems;

    public VideoBokehSettingBusiness(@ComponentRunningShine.ShineType String str, TypeElementsBeauty typeElementsBeauty) {
        this.mBeautyType = str;
        initBeauty(this.mBeautyType, typeElementsBeauty);
    }

    private String getBeautyType() {
        return this.mBeautyType;
    }

    private void initBeauty(String str, TypeElementsBeauty typeElementsBeauty) {
        DataItemGlobal dataItemGlobal = DataRepository.dataItemGlobal();
        int currentCameraId = dataItemGlobal.getCurrentCameraId();
        this.mSupportedTypeItems = typeElementsBeauty.initAndGetSupportItems(currentCameraId, Camera2DataContainer.getInstance().getCapabilitiesByBogusCameraId(currentCameraId, dataItemGlobal.getCurrentMode()), str);
        List<TypeItem> supportedTypeArray = getSupportedTypeArray(getBeautyType());
        if (!supportedTypeArray.isEmpty()) {
            this.mCurrentBeautyItemType = supportedTypeArray.get(0).mKeyOrType;
        }
    }

    @Override // com.android.camera.fragment.beauty.IBeautySettingBusiness
    public void clearBeauty() {
        for (TypeItem typeItem : getSupportedTypeArray(getBeautyType())) {
            String str = typeItem.mKeyOrType;
            CameraSettings.setVideoBokehRatio(str, 0);
            this.mExtraTable.put(str, 0);
        }
        ShineHelper.onVideoBokehWithRetentionChanged();
    }

    @Override // com.android.camera.fragment.beauty.IBeautySettingBusiness
    public int getDefaultProgressByCurrentItem() {
        return getProgressDefValue(this.mCurrentBeautyItemType);
    }

    @Override // com.android.camera.fragment.beauty.IBeautySettingBusiness
    public int getProgressByCurrentItem() {
        return this.mExtraTable.get(this.mCurrentBeautyItemType) == null ? getProgressDefValue(this.mCurrentBeautyItemType) : this.mExtraTable.get(this.mCurrentBeautyItemType).intValue();
    }

    /* access modifiers changed from: protected */
    public int getProgressDefValue(String str) {
        return VideoBokehConstant.getDefaultValueByKey(str);
    }

    @Override // com.android.camera.fragment.beauty.IBeautySettingBusiness
    public List<TypeItem> getSupportedTypeArray(String str) {
        return this.mSupportedTypeItems;
    }

    @Override // com.android.camera.fragment.beauty.IBeautySettingBusiness
    public void onStateChanged() {
        resetBeauty();
        CameraSettings.setVideoBokehColorRetentionMode(0);
    }

    @Override // com.android.camera.fragment.beauty.IBeautyBusiness
    public Object operate(Object obj) {
        return null;
    }

    @Override // com.android.camera.fragment.beauty.IBeautySettingBusiness
    public void resetBeauty() {
        for (TypeItem typeItem : getSupportedTypeArray(getBeautyType())) {
            String str = typeItem.mKeyOrType;
            int progressDefValue = getProgressDefValue(str);
            this.mExtraTable.put(str, Integer.valueOf(progressDefValue));
            CameraSettings.setVideoBokehRatio(str, progressDefValue);
        }
        ShineHelper.onVideoBokehWithRetentionChanged();
    }

    @Override // com.android.camera.fragment.beauty.IBeautySettingBusiness
    public void setCurrentType(String str) {
        if (!TextUtils.equals(str, this.mCurrentBeautyItemType) && VideoBokehConstant.getVideoBokehMode(str) >= 0) {
            Integer num = this.mExtraTable.get(str);
            int videoBokehMode = VideoBokehConstant.getVideoBokehMode(str);
            if (videoBokehMode >= 0 && num != null) {
                CameraSettings.setVideoBokehRatio(num.floatValue());
                CameraSettings.setVideoBokehColorRetentionMode(videoBokehMode);
                ShineHelper.onVideoBokehWithRetentionChanged();
            }
            if (videoBokehMode == 0) {
                resetBeauty();
            }
        }
        this.mCurrentBeautyItemType = str;
    }

    @Override // com.android.camera.fragment.beauty.IBeautySettingBusiness
    public void setProgressForCurrentItem(int i) {
        int videoBokehMode;
        int intValue = this.mExtraTable.get(this.mCurrentBeautyItemType) == null ? 0 : this.mExtraTable.get(this.mCurrentBeautyItemType).intValue();
        this.mExtraTable.put(this.mCurrentBeautyItemType, Integer.valueOf(i));
        String str = this.mCurrentBeautyItemType;
        if ((intValue != i || i == getProgressDefValue(str)) && (videoBokehMode = VideoBokehConstant.getVideoBokehMode(this.mCurrentBeautyItemType)) >= 0) {
            CameraSettings.setVideoBokehRatio((float) i);
            CameraSettings.setVideoBokehRatio(str, i);
            CameraSettings.setVideoBokehColorRetentionMode(videoBokehMode);
            ShineHelper.onVideoBokehWithRetentionChanged();
        }
    }

    @Override // com.android.camera.fragment.beauty.IBeautySettingBusiness
    public void updateExtraTable() {
        for (TypeItem typeItem : getSupportedTypeArray(getBeautyType())) {
            int i = 0;
            String str = typeItem.mKeyOrType;
            if (!TextUtils.isEmpty(str)) {
                i = CameraSettings.getVideoBokehRatio(str);
            }
            this.mExtraTable.put(str, Integer.valueOf(i));
        }
    }
}
