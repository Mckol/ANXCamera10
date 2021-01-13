package com.android.camera.fragment.bottom;

import android.content.Context;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import com.android.camera.CameraAppImpl;
import com.android.camera.R;
import com.android.camera.fragment.beauty.MenuItem;
import com.android.camera.ui.ColorActivateTextView;

public class PortraitLightingMenu extends AbBottomMenu implements View.OnClickListener {
    private static final int PORTRAIT_LIGHTING_TYPE = 0;
    private SparseArray<MenuItem> mLiveSpeedMenuTabList;
    private SparseArray<ColorActivateTextView> mMenuTextViewList;
    private int mPortraitLightingVersion;

    public PortraitLightingMenu(Context context, LinearLayout linearLayout, BeautyMenuAnimator beautyMenuAnimator, int i) {
        super(context, linearLayout, beautyMenuAnimator);
        this.mPortraitLightingVersion = i;
    }

    /* access modifiers changed from: package-private */
    @Override // com.android.camera.fragment.bottom.AbBottomMenu
    public void addAllView() {
        this.mMenuTextViewList = new SparseArray<>();
        SparseArray<MenuItem> menuData = getMenuData();
        for (int i = 0; i < menuData.size(); i++) {
            MenuItem valueAt = menuData.valueAt(i);
            ColorActivateTextView colorActivateTextView = (ColorActivateTextView) LayoutInflater.from(this.mContext).inflate(R.layout.beauty_menu_select_item, (ViewGroup) this.mContainerView, false);
            colorActivateTextView.setNormalCor(-1);
            colorActivateTextView.setText(valueAt.text);
            colorActivateTextView.setTag(Integer.valueOf(valueAt.type));
            colorActivateTextView.setOnClickListener(this);
            if (valueAt.type == 0) {
                this.mCurrentBeautyTextView = colorActivateTextView;
            }
            colorActivateTextView.setActivated(false);
            this.mMenuTextViewList.put(valueAt.type, colorActivateTextView);
            this.mContainerView.addView(colorActivateTextView);
        }
    }

    /* access modifiers changed from: package-private */
    @Override // com.android.camera.fragment.bottom.AbBottomMenu
    public SparseArray<ColorActivateTextView> getChildMenuViewList() {
        return this.mMenuTextViewList;
    }

    /* access modifiers changed from: package-private */
    @Override // com.android.camera.fragment.bottom.AbBottomMenu
    public int getDefaultType() {
        return 0;
    }

    /* access modifiers changed from: package-private */
    @Override // com.android.camera.fragment.bottom.AbBottomMenu
    public SparseArray<MenuItem> getMenuData() {
        SparseArray<MenuItem> sparseArray = this.mLiveSpeedMenuTabList;
        if (sparseArray != null) {
            return sparseArray;
        }
        this.mLiveSpeedMenuTabList = new SparseArray<>();
        MenuItem menuItem = new MenuItem();
        menuItem.type = 0;
        if (this.mPortraitLightingVersion >= 2) {
            menuItem.text = CameraAppImpl.getAndroidContext().getString(R.string.lighting_movie_light_effect_title);
        } else {
            menuItem.text = CameraAppImpl.getAndroidContext().getString(R.string.lighting_hint_title);
        }
        menuItem.number = 0;
        this.mLiveSpeedMenuTabList.put(0, menuItem);
        return this.mLiveSpeedMenuTabList;
    }

    /* access modifiers changed from: package-private */
    @Override // com.android.camera.fragment.bottom.AbBottomMenu
    public boolean isRefreshUI() {
        return true;
    }

    public void onClick(View view) {
    }

    /* access modifiers changed from: package-private */
    @Override // com.android.camera.fragment.bottom.AbBottomMenu
    public void switchMenu() {
        this.mContainerView.removeAllViews();
        addAllView();
    }
}
