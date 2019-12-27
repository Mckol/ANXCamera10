package com.android.camera.fragment.bottom;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import com.android.camera.CameraAppImpl;
import com.android.camera.CameraSettings;
import com.android.camera.R;
import com.android.camera.constant.ColorConstant;
import com.android.camera.data.DataRepository;
import com.android.camera.data.data.global.DataItemGlobal;
import com.android.camera.fragment.beauty.MenuItem;
import com.android.camera.ui.ColorActivateTextView;
import com.mi.config.b;

public class FrontBeautyMenu extends AbBottomMenu implements View.OnClickListener {
    private SparseArray mFrontBeautyMenuTabList;
    private int mLastCamerId = -1;
    private SparseArray<ColorActivateTextView> mMenuTextViewList;

    public FrontBeautyMenu(Context context, LinearLayout linearLayout, BeautyMenuAnimator beautyMenuAnimator) {
        super(context, linearLayout, beautyMenuAnimator);
    }

    private boolean isCameraSwitch() {
        int currentCameraId = ((DataItemGlobal) DataRepository.provider().dataGlobal()).getCurrentCameraId();
        if (this.mLastCamerId != currentCameraId) {
            return true;
        }
        this.mLastCamerId = currentCameraId;
        return false;
    }

    private boolean isJustBeautyTab() {
        if (DataRepository.dataItemFeature().db()) {
            return true;
        }
        int currentMode = ((DataItemGlobal) DataRepository.provider().dataGlobal()).getCurrentMode();
        return currentMode == 162 || currentMode == 161 || currentMode == 174 || currentMode == 171 || currentMode == 176;
    }

    public void addAllView() {
        this.mMenuTextViewList = new SparseArray<>();
        SparseArray<MenuItem> menuData = getMenuData();
        for (int i = 0; i < menuData.size(); i++) {
            MenuItem valueAt = menuData.valueAt(i);
            if (!isJustBeautyTab() || valueAt.type == 1) {
                ColorActivateTextView colorActivateTextView = (ColorActivateTextView) LayoutInflater.from(this.mContext).inflate(R.layout.beauty_menu_select_item, this.mContainerView, false);
                colorActivateTextView.setNormalCor(ColorConstant.WHITE_ALPHA_99);
                if (isJustBeautyTab()) {
                    colorActivateTextView.setActivateColor(ColorConstant.COLOR_COMMON_NORMAL);
                } else {
                    colorActivateTextView.setActivateColor(ColorConstant.COLOR_COMMON_SELECTED);
                }
                colorActivateTextView.setText(valueAt.text);
                colorActivateTextView.setTag(Integer.valueOf(valueAt.type));
                colorActivateTextView.setOnClickListener(this);
                if (valueAt.redDot) {
                    Drawable drawable = this.mContext.getResources().getDrawable(R.drawable.ic_dot_hint);
                    colorActivateTextView.setCompoundDrawablePadding(this.mContext.getResources().getDimensionPixelOffset(R.dimen.beautycamera_beauty_fragment_tab_dot_hint_padding));
                    colorActivateTextView.setCompoundDrawablesWithIntrinsicBounds((Drawable) null, (Drawable) null, drawable, (Drawable) null);
                }
                if (1 == valueAt.type) {
                    colorActivateTextView.setActivated(true);
                    this.mCurrentBeautyTextView = colorActivateTextView;
                } else {
                    colorActivateTextView.setActivated(false);
                }
                this.mMenuTextViewList.put(valueAt.type, colorActivateTextView);
                this.mContainerView.addView(colorActivateTextView);
            }
        }
    }

    /* access modifiers changed from: package-private */
    public SparseArray<ColorActivateTextView> getChildMenuViewList() {
        return this.mMenuTextViewList;
    }

    /* access modifiers changed from: package-private */
    public int getDefaultType() {
        return 1;
    }

    /* access modifiers changed from: package-private */
    public SparseArray<MenuItem> getMenuData() {
        SparseArray sparseArray = this.mFrontBeautyMenuTabList;
        if (sparseArray != null && sparseArray.size() > 0) {
            return this.mFrontBeautyMenuTabList;
        }
        this.mFrontBeautyMenuTabList = new SparseArray();
        String string = CameraAppImpl.getAndroidContext().getString(R.string.beauty_fragment_tab_name_beauty);
        String string2 = CameraAppImpl.getAndroidContext().getString(R.string.beauty_fragment_tab_name_makeup);
        String string3 = CameraAppImpl.getAndroidContext().getString(R.string.beauty_fragment_tab_name_3d_makeup);
        if (b.Pi()) {
            string = CameraAppImpl.getAndroidContext().getString(R.string.beauty_fragment_tab_name_3d_beauty);
            string2 = CameraAppImpl.getAndroidContext().getString(R.string.beauty_fragment_tab_name_3d_remodeling);
        }
        MenuItem menuItem = new MenuItem();
        menuItem.type = 1;
        menuItem.text = string;
        menuItem.number = 0;
        MenuItem menuItem2 = new MenuItem();
        menuItem2.type = 2;
        menuItem2.text = string2;
        menuItem2.number = 1;
        this.mFrontBeautyMenuTabList.put(1, menuItem);
        this.mFrontBeautyMenuTabList.put(2, menuItem2);
        if (DataRepository.dataItemFeature().Kc() && CameraSettings.isSupportBeautyMakeup()) {
            MenuItem menuItem3 = new MenuItem();
            menuItem3.type = 3;
            menuItem3.text = string3;
            menuItem3.number = 2;
            this.mFrontBeautyMenuTabList.put(3, menuItem3);
        }
        return this.mFrontBeautyMenuTabList;
    }

    public boolean isRefreshUI() {
        return isCameraSwitch() || isJustBeautyTab();
    }

    public void onClick(View view) {
        if (isClickEnable()) {
            int intValue = ((Integer) view.getTag()).intValue();
            selectBeautyType(intValue);
            if (intValue == 3 && !CameraSettings.isBeautyMakeupClicked()) {
                CameraSettings.setBeautyMakeupClicked();
                this.mCurrentBeautyTextView.setCompoundDrawablesWithIntrinsicBounds((Drawable) null, (Drawable) null, (Drawable) null, (Drawable) null);
            }
        }
    }

    /* access modifiers changed from: package-private */
    public void switchMenu() {
        BeautyMenuAnimator beautyMenuAnimator = this.mBeautyMenuAnimator;
        if (beautyMenuAnimator != null) {
            beautyMenuAnimator.resetAll();
        }
        this.mContainerView.removeAllViews();
        addAllView();
        selectBeautyType(getDefaultType());
        CameraSettings.getFaceBeautifyLevel();
    }
}
