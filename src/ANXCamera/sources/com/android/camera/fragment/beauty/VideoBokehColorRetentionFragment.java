package com.android.camera.fragment.beauty;

import com.android.camera.CameraSettings;
import com.android.camera.data.data.TypeItem;
import com.android.camera.protocol.ModeCoordinatorImpl;
import com.android.camera.protocol.ModeProtocol;

public class VideoBokehColorRetentionFragment extends BaseBeautyMakeupFragment {
    private int getPositionByMode(int i) {
        switch (i) {
            case 0:
            case 1:
            default:
                return 0;
            case 2:
                return 5;
            case 3:
                return 4;
            case 4:
                return 2;
            case 5:
                return 3;
            case 6:
                return 1;
        }
    }

    /* access modifiers changed from: protected */
    @Override // com.android.camera.fragment.beauty.BaseBeautyMakeupFragment
    public String getClassString() {
        return VideoBokehColorRetentionFragment.class.getSimpleName();
    }

    /* access modifiers changed from: protected */
    @Override // com.android.camera.fragment.beauty.BaseBeautyMakeupFragment
    public String getShineType() {
        return "14";
    }

    /* access modifiers changed from: protected */
    @Override // com.android.camera.fragment.beauty.BaseBeautyMakeupFragment
    public void initExtraType() {
        this.mAlphaElement = -1;
        this.mBetaElement = -1;
    }

    /* access modifiers changed from: protected */
    @Override // com.android.camera.fragment.beauty.BaseBeautyMakeupFragment
    public void onAdapterItemClick(TypeItem typeItem) {
        ModeProtocol.MakeupProtocol makeupProtocol = (ModeProtocol.MakeupProtocol) ModeCoordinatorImpl.getInstance().getAttachProtocol(180);
        if (makeupProtocol != null) {
            makeupProtocol.onMakeupItemSelected(typeItem.mKeyOrType, true);
        }
    }

    /* access modifiers changed from: protected */
    @Override // com.android.camera.fragment.beauty.BaseBeautyMakeupFragment
    public void onClearClick() {
    }

    /* access modifiers changed from: protected */
    @Override // com.android.camera.fragment.beauty.BaseBeautyMakeupFragment
    public void onResetClick() {
    }

    /* access modifiers changed from: protected */
    @Override // com.android.camera.fragment.beauty.BaseBeautyFragment, com.android.camera.fragment.beauty.BaseBeautyMakeupFragment, com.android.camera.fragment.BaseViewPagerFragment
    public void onViewCreatedAndVisibleToUser(boolean z) {
        this.mSelectedParam = getPositionByMode(CameraSettings.getVideoBokehColorRetentionMode());
        this.mSelectedPosition = this.mSelectedParam;
        this.mMakeupAdapter.setSelectedPosition(this.mSelectedPosition);
        super.onViewCreatedAndVisibleToUser(z);
    }
}
