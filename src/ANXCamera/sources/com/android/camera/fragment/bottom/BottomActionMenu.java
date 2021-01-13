package com.android.camera.fragment.bottom;

import android.content.Context;
import android.text.TextUtils;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import androidx.annotation.NonNull;
import androidx.core.view.ViewCompat;
import com.android.camera.CameraSettings;
import com.android.camera.R;
import com.android.camera.animation.type.AlphaInOnSubscribe;
import com.android.camera.constant.BeautyConstant;
import com.android.camera.constant.ColorConstant;
import com.android.camera.data.data.ComponentDataItem;
import com.android.camera.data.data.runing.ComponentRunningAIWatermark;
import com.android.camera.data.data.runing.ComponentRunningFastMotion;
import com.android.camera.data.data.runing.ComponentRunningShine;
import com.android.camera.fragment.beauty.MenuItem;
import com.android.camera.log.Log;
import com.android.camera.module.ModuleManager;
import com.android.camera.protocol.ModeCoordinatorImpl;
import com.android.camera.protocol.ModeProtocol;
import com.android.camera.statistic.CameraStatUtils;
import com.android.camera.statistic.MistatsConstants;
import com.android.camera.statistic.MistatsWrapper;
import com.android.camera.ui.ColorActivateTextView;
import io.reactivex.Completable;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import miui.view.animation.QuinticEaseInInterpolator;
import miui.view.animation.QuinticEaseOutInterpolator;

public class BottomActionMenu implements View.OnClickListener {
    public static final int ANIM_EXPAND = 160;
    public static final int ANIM_SHRINK = 161;
    public static final int BEAUTY_BOTTOM_MENU = 1;
    public static final int CAMERA_OPERATE_BOTTOM_MENU = 0;
    public static final int KALEIDOSCOPE_BOTTOM_MENU = 3;
    public static final int LIVE_BOTTOM_MENU = 2;
    public static final int MIMOJI_BOTTOM_MENU = 4;
    private static final String TAG = "BottomActionMenu";
    private LinearLayout beautyOperateMenuView;
    private BeautyMenuGroupManager mBeautyOperateMenuViewWrapper;
    private FrameLayout mContainerView;
    private Context mContext;
    private View.OnClickListener mFastMotionListener = new View.OnClickListener() {
        /* class com.android.camera.fragment.bottom.BottomActionMenu.AnonymousClass2 */

        public void onClick(View view) {
            if (BottomActionMenu.this.mLastSelectedView != null) {
                BottomActionMenu.this.mLastSelectedView.setActivated(false);
                BottomActionMenu.this.mLastSelectedView = (ColorActivateTextView) view;
                BottomActionMenu.this.mLastSelectedView.setActivated(true);
            }
            String str = (String) view.getTag();
            ModeProtocol.FastMotionProtocol fastMotionProtocol = (ModeProtocol.FastMotionProtocol) ModeCoordinatorImpl.getInstance().getAttachProtocol(674);
            if (fastMotionProtocol != null) {
                fastMotionProtocol.switchType(str, true);
            }
        }
    };
    private ColorActivateTextView mLastSelectedView;
    private View.OnClickListener mListener = new View.OnClickListener() {
        /* class com.android.camera.fragment.bottom.BottomActionMenu.AnonymousClass1 */

        public void onClick(View view) {
            if (BottomActionMenu.this.mLastSelectedView != null) {
                BottomActionMenu.this.mLastSelectedView.setActivated(false);
                BottomActionMenu.this.mLastSelectedView = (ColorActivateTextView) view;
                BottomActionMenu.this.mLastSelectedView.setActivated(true);
            }
            String str = (String) view.getTag();
            ModeProtocol.WatermarkProtocol watermarkProtocol = (ModeProtocol.WatermarkProtocol) ModeCoordinatorImpl.getInstance().getAttachProtocol(253);
            if (watermarkProtocol != null) {
                watermarkProtocol.switchType(str, true);
            }
        }
    };

    @Retention(RetentionPolicy.SOURCE)
    public @interface BottomActionMenuAnimType {
    }

    @Retention(RetentionPolicy.SOURCE)
    public @interface BottomActionMode {
    }

    public BottomActionMenu(Context context, FrameLayout frameLayout) {
        this.mContext = context;
        initView(frameLayout);
    }

    private void beautyOperateMenuHandle(int i, boolean z) {
        BeautyMenuGroupManager beautyMenuGroupManager = this.mBeautyOperateMenuViewWrapper;
        if (beautyMenuGroupManager != null) {
            beautyMenuGroupManager.setCurrentBeautyMenuType(i);
            this.mBeautyOperateMenuViewWrapper.switchMenu();
            this.mBeautyOperateMenuViewWrapper.setVisibility(0);
            if (z) {
                enterAnim(this.mBeautyOperateMenuViewWrapper.getView());
            }
        }
    }

    private void cameraOperateMenuHandle(boolean z) {
        BeautyMenuGroupManager beautyMenuGroupManager = this.mBeautyOperateMenuViewWrapper;
        if (beautyMenuGroupManager != null) {
            beautyMenuGroupManager.setVisibility(8);
            if (z) {
                exitAnim(this.mBeautyOperateMenuViewWrapper.getView());
            }
        }
    }

    private void changeMenuView() {
        this.beautyOperateMenuView.setVisibility(0);
        enterAnim(this.beautyOperateMenuView);
    }

    private void enterAnim(@NonNull View view) {
        view.clearAnimation();
        view.setAlpha(0.0f);
        ViewCompat.animate(view).alpha(1.0f).setStartDelay(140).setInterpolator(new QuinticEaseOutInterpolator()).setDuration(300).start();
    }

    private void exitAnim(@NonNull View view) {
        view.clearAnimation();
        ViewCompat.animate(view).alpha(0.0f).setInterpolator(new QuinticEaseInInterpolator()).setDuration(140).start();
    }

    private void hideNearRangeTip() {
        ModeProtocol.BottomPopupTips bottomPopupTips = (ModeProtocol.BottomPopupTips) ModeCoordinatorImpl.getInstance().getAttachProtocol(175);
        if (bottomPopupTips != null) {
            bottomPopupTips.hideNearRangeTip();
        }
    }

    private void hideQrCodeTip() {
        ModeProtocol.BottomPopupTips bottomPopupTips = (ModeProtocol.BottomPopupTips) ModeCoordinatorImpl.getInstance().getAttachProtocol(175);
        if (bottomPopupTips != null) {
            bottomPopupTips.hideQrCodeTip();
        }
    }

    private void initView(FrameLayout frameLayout) {
        this.mContainerView = frameLayout;
        switchMenuMode(0, false);
    }

    /* JADX INFO: Can't fix incorrect switch cases order, some code will duplicate */
    /* JADX WARNING: Removed duplicated region for block: B:30:0x0076  */
    /* JADX WARNING: Removed duplicated region for block: B:43:0x0080 A[SYNTHETIC] */
    public void animateShineBeauty(boolean z) {
        char c2;
        int childCount = this.beautyOperateMenuView.getChildCount();
        boolean z2 = false;
        for (int i = 0; i < childCount; i++) {
            ColorActivateTextView colorActivateTextView = (ColorActivateTextView) this.beautyOperateMenuView.getChildAt(i);
            String str = (String) colorActivateTextView.getTag();
            int hashCode = str.hashCode();
            if (hashCode != 49) {
                if (hashCode != 1572) {
                    switch (hashCode) {
                        case 51:
                            if (str.equals("3")) {
                                c2 = 0;
                                break;
                            }
                            break;
                        case 52:
                            if (str.equals("4")) {
                                c2 = 1;
                                break;
                            }
                            break;
                        case 53:
                            if (str.equals("5")) {
                                c2 = 3;
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
                                c2 = 6;
                                break;
                            }
                            break;
                    }
                    switch (c2) {
                        case 0:
                        case 1:
                        case 2:
                        case 3:
                        case 4:
                            colorActivateTextView.setVisibility(z ? 8 : 0);
                            z2 = true;
                            break;
                    }
                } else if (str.equals("15")) {
                    c2 = 2;
                    switch (c2) {
                    }
                }
            } else if (str.equals("1")) {
                c2 = 5;
                switch (c2) {
                }
            }
            c2 = 65535;
            switch (c2) {
            }
        }
        if (z2) {
            for (int i2 = 0; i2 < childCount; i2++) {
                ColorActivateTextView colorActivateTextView2 = (ColorActivateTextView) this.beautyOperateMenuView.getChildAt(i2);
                if (colorActivateTextView2.getVisibility() == 0) {
                    Completable.create(new AlphaInOnSubscribe(colorActivateTextView2)).subscribe();
                }
            }
        }
    }

    public void bottomMenuAnimate(int i, int i2) {
        if (i == 1) {
            if (160 == i2) {
                this.mBeautyOperateMenuViewWrapper.animateExpanding(true);
            } else if (161 == i2) {
                this.mBeautyOperateMenuViewWrapper.animateExpanding(false);
            }
        }
    }

    public void clearBottomMenu() {
        LinearLayout linearLayout = this.beautyOperateMenuView;
        if (linearLayout != null && linearLayout.getVisibility() == 0) {
            this.beautyOperateMenuView.setVisibility(8);
        }
    }

    public void expandAIWatermark(ComponentRunningAIWatermark componentRunningAIWatermark, int i) {
        List<ComponentDataItem> items = componentRunningAIWatermark.getItems();
        String currentType = componentRunningAIWatermark.getCurrentType();
        this.beautyOperateMenuView.removeAllViews();
        LayoutInflater from = LayoutInflater.from(this.mContext);
        boolean z = items.size() > 1;
        for (int i2 = 0; i2 < items.size(); i2++) {
            ComponentDataItem componentDataItem = items.get(i2);
            ColorActivateTextView colorActivateTextView = (ColorActivateTextView) from.inflate(R.layout.watermark_menu_select_item, (ViewGroup) this.mContainerView, false);
            colorActivateTextView.setNormalCor(this.mContext.getColor(R.color.mode_name_color));
            colorActivateTextView.setActivateColor(ColorConstant.COLOR_COMMON_SELECTED);
            colorActivateTextView.setText(this.mContext.getString(componentDataItem.mDisplayNameRes));
            colorActivateTextView.setTag(componentDataItem.mValue);
            if (z) {
                colorActivateTextView.setOnClickListener(this.mListener);
                if (TextUtils.equals(currentType, componentDataItem.mValue)) {
                    this.mLastSelectedView = colorActivateTextView;
                    colorActivateTextView.setActivated(true);
                }
            }
            this.beautyOperateMenuView.addView(colorActivateTextView);
        }
        changeMenuView();
        hideQrCodeTip();
        hideNearRangeTip();
    }

    /* JADX INFO: Can't fix incorrect switch cases order, some code will duplicate */
    /* JADX WARNING: Removed duplicated region for block: B:39:0x00d9 A[ADDED_TO_REGION] */
    /* JADX WARNING: Removed duplicated region for block: B:57:0x012d  */
    /* JADX WARNING: Removed duplicated region for block: B:58:0x0134  */
    public void expandShine(ComponentRunningShine componentRunningShine, int i) {
        int i2;
        int i3;
        int intValue;
        int intValue2;
        char c2;
        List<ComponentDataItem> items = componentRunningShine.getItems();
        String currentType = componentRunningShine.getCurrentType();
        this.beautyOperateMenuView.removeAllViews();
        LayoutInflater from = LayoutInflater.from(this.mContext);
        boolean z = false;
        boolean z2 = !componentRunningShine.isSmoothDependBeautyVersion() && BeautyConstant.LEVEL_CLOSE.equals(CameraSettings.getFaceBeautifyLevel());
        int size = items.size();
        boolean z3 = size > 1;
        int makeMeasureSpec = View.MeasureSpec.makeMeasureSpec(0, 0);
        ArrayList arrayList = new ArrayList(size);
        int i4 = 0;
        while (i4 < size) {
            ComponentDataItem componentDataItem = items.get(i4);
            ColorActivateTextView colorActivateTextView = (ColorActivateTextView) from.inflate(R.layout.beauty_menu_select_item, this.mContainerView, z);
            colorActivateTextView.setNormalCor(this.mContext.getColor(R.color.mode_name_color));
            colorActivateTextView.setText(this.mContext.getString(componentDataItem.mDisplayNameRes));
            colorActivateTextView.setTag(componentDataItem.mValue);
            if (z3) {
                colorActivateTextView.setOnClickListener(this);
                if (currentType.equals(componentDataItem.mValue)) {
                    this.mLastSelectedView = colorActivateTextView;
                    colorActivateTextView.setActivated(true);
                }
            }
            colorActivateTextView.measure(makeMeasureSpec, makeMeasureSpec);
            arrayList.add(Integer.valueOf(colorActivateTextView.getMeasuredWidth()));
            this.beautyOperateMenuView.addView(colorActivateTextView);
            if (z2) {
                String str = componentDataItem.mValue;
                int hashCode = str.hashCode();
                if (hashCode != 1572) {
                    switch (hashCode) {
                        case 51:
                            if (str.equals("3")) {
                                c2 = 0;
                                break;
                            }
                            break;
                        case 52:
                            if (str.equals("4")) {
                                c2 = 1;
                                break;
                            }
                            break;
                        case 53:
                            if (str.equals("5")) {
                                c2 = 3;
                                break;
                            }
                            break;
                        case 54:
                            if (str.equals("6")) {
                                c2 = 4;
                                break;
                            }
                            break;
                    }
                    if (c2 != 0 || c2 == 1 || c2 == 2 || c2 == 3 || c2 == 4) {
                        colorActivateTextView.setVisibility(8);
                    }
                } else if (str.equals("15")) {
                    c2 = 2;
                    if (c2 != 0) {
                    }
                    colorActivateTextView.setVisibility(8);
                }
                c2 = 65535;
                if (c2 != 0) {
                }
                colorActivateTextView.setVisibility(8);
            }
            i4++;
            z = false;
        }
        if (size == 2) {
            intValue = ((Integer) arrayList.get(1)).intValue();
            i3 = 0;
            intValue2 = ((Integer) arrayList.get(0)).intValue();
        } else {
            i3 = 0;
            if (size == 3) {
                intValue = ((Integer) arrayList.get(2)).intValue();
                intValue2 = ((Integer) arrayList.get(0)).intValue();
            } else {
                i2 = 0;
                ViewGroup.MarginLayoutParams marginLayoutParams = (ViewGroup.MarginLayoutParams) this.beautyOperateMenuView.getLayoutParams();
                if (i2 <= 0) {
                    marginLayoutParams.setMarginStart(i2);
                    marginLayoutParams.setMarginEnd(i3);
                } else {
                    marginLayoutParams.setMarginStart(i3);
                    marginLayoutParams.setMarginEnd(-i2);
                }
                this.beautyOperateMenuView.setLayoutParams(marginLayoutParams);
                changeMenuView();
                hideQrCodeTip();
                hideNearRangeTip();
            }
        }
        i2 = intValue - intValue2;
        ViewGroup.MarginLayoutParams marginLayoutParams2 = (ViewGroup.MarginLayoutParams) this.beautyOperateMenuView.getLayoutParams();
        if (i2 <= 0) {
        }
        this.beautyOperateMenuView.setLayoutParams(marginLayoutParams2);
        changeMenuView();
        hideQrCodeTip();
        hideNearRangeTip();
    }

    public SparseArray<MenuItem> getMenuData() {
        return this.mBeautyOperateMenuViewWrapper.getBottomMenu().getMenuData();
    }

    public View getView() {
        return this.mContainerView;
    }

    public void initBeautyMenuView(int i) {
        if (this.mBeautyOperateMenuViewWrapper == null) {
            this.beautyOperateMenuView = (LinearLayout) this.mContainerView.findViewById(R.id.beauty_operate_menu);
            this.beautyOperateMenuView.setVisibility(8);
            this.mBeautyOperateMenuViewWrapper = new BeautyMenuGroupManager(this.mContext, this.beautyOperateMenuView, i);
        }
    }

    public boolean isBottomMenuVisible() {
        LinearLayout linearLayout = this.beautyOperateMenuView;
        return linearLayout != null && linearLayout.getVisibility() == 0;
    }

    public void onClick(View view) {
        ColorActivateTextView colorActivateTextView = this.mLastSelectedView;
        if (colorActivateTextView != null) {
            if (colorActivateTextView != view) {
                colorActivateTextView.setActivated(false);
                this.mLastSelectedView = (ColorActivateTextView) view;
                this.mLastSelectedView.setActivated(true);
            } else {
                return;
            }
        }
        String str = (String) view.getTag();
        ModeProtocol.MiBeautyProtocol miBeautyProtocol = (ModeProtocol.MiBeautyProtocol) ModeCoordinatorImpl.getInstance().getAttachProtocol(194);
        if (miBeautyProtocol != null) {
            char c2 = 65535;
            int hashCode = str.hashCode();
            if (hashCode != 50) {
                if (hashCode != 1571) {
                    if (hashCode != 54) {
                        if (hashCode != 55) {
                            if (hashCode != 1567) {
                                if (hashCode == 1568 && str.equals("11")) {
                                    c2 = 2;
                                }
                            } else if (str.equals("10")) {
                                c2 = 3;
                            }
                        } else if (str.equals("7")) {
                            c2 = 1;
                        }
                    } else if (str.equals("6")) {
                        c2 = 4;
                    }
                } else if (str.equals("14")) {
                    c2 = 5;
                }
            } else if (str.equals("2")) {
                c2 = 0;
            }
            if (c2 == 0) {
                HashMap hashMap = new HashMap();
                hashMap.put(MistatsConstants.BaseEvent.OPERATE_STATE, MistatsConstants.BeautyAttr.VALUE_BEAUTY_BOTTOM_TAB);
                MistatsWrapper.mistatEvent(MistatsConstants.BeautyAttr.KEY_BEAUTY_CLICK, hashMap);
            } else if (c2 != 1) {
                if (c2 != 2) {
                    if (c2 != 3) {
                        if (c2 == 4) {
                            HashMap hashMap2 = new HashMap();
                            hashMap2.put(MistatsConstants.BaseEvent.OPERATE_STATE, MistatsConstants.BeautyAttr.VALUE_BEAUTY_BOTTOM_TAB);
                            MistatsWrapper.mistatEvent(MistatsConstants.BeautyAttr.KEY_BEAUTY_CLICK, hashMap2);
                        } else if (c2 == 5) {
                            HashMap hashMap3 = new HashMap();
                            hashMap3.put(MistatsConstants.BaseEvent.OPERATE_STATE, MistatsConstants.BeautyAttr.VALUE_BOKEH_BOTTOM_TAB);
                            MistatsWrapper.mistatEvent(MistatsConstants.BeautyAttr.KEY_BEAUTY_CLICK, hashMap3);
                        }
                    } else if (ModuleManager.isLiveModule()) {
                        CameraStatUtils.trackLiveBeautyClick(str);
                    } else if (ModuleManager.isMiLiveModule()) {
                        CameraStatUtils.trackMiLiveClick(MistatsConstants.MiLive.VALUE_MI_LIVE_CLICK_FILTER);
                    }
                } else if (ModuleManager.isLiveModule()) {
                    CameraStatUtils.trackLiveBeautyClick(str);
                } else if (ModuleManager.isMiLiveModule()) {
                    CameraStatUtils.trackMiLiveClick(MistatsConstants.MiLive.VALUE_MI_LIVE_CLICK_BEAUTY);
                }
            } else if (ModuleManager.isMiLiveModule()) {
                CameraStatUtils.trackMiLiveClick(MistatsConstants.MiLive.VALUE_MI_LIVE_CLICK_FILTER);
            } else {
                HashMap hashMap4 = new HashMap();
                hashMap4.put(MistatsConstants.BaseEvent.OPERATE_STATE, MistatsConstants.FilterAttr.VALUE_FILTER_BOTTOM_TAB);
                MistatsWrapper.mistatEvent(MistatsConstants.BeautyAttr.KEY_BEAUTY_CLICK, hashMap4);
            }
            miBeautyProtocol.switchShineType(str, true);
        }
    }

    public void switchFastMotion(ComponentRunningFastMotion componentRunningFastMotion) {
        this.beautyOperateMenuView.removeAllViews();
        List<ComponentDataItem> items = componentRunningFastMotion.getItems();
        LayoutInflater from = LayoutInflater.from(this.mContext);
        boolean z = items.size() > 1;
        for (int i = 0; i < items.size(); i++) {
            ComponentDataItem componentDataItem = items.get(i);
            ColorActivateTextView colorActivateTextView = (ColorActivateTextView) from.inflate(R.layout.beauty_menu_select_item, (ViewGroup) this.mContainerView, false);
            colorActivateTextView.setNormalCor(this.mContext.getColor(R.color.mode_name_color));
            colorActivateTextView.setText(this.mContext.getString(componentDataItem.mDisplayNameRes));
            colorActivateTextView.setTag(componentDataItem.mValue);
            if (z) {
                colorActivateTextView.setOnClickListener(this.mFastMotionListener);
                if (TextUtils.equals(componentRunningFastMotion.getCurrentType(), componentDataItem.mValue)) {
                    this.mLastSelectedView = colorActivateTextView;
                    colorActivateTextView.setActivated(true);
                }
            }
            this.beautyOperateMenuView.addView(colorActivateTextView);
        }
        changeMenuView();
        hideQrCodeTip();
        hideNearRangeTip();
    }

    public void switchMenuMode(int i, int i2, boolean z) {
        if (i == 0) {
            Log.i(TAG, "switch menu mode:camera_operate");
            cameraOperateMenuHandle(z);
        } else if (i == 1) {
            Log.i(TAG, "switch menu mode:beauty_operate");
            beautyOperateMenuHandle(i2, z);
        } else if (i == 2) {
            Log.i(TAG, "switch menu mode:live_operate");
            beautyOperateMenuHandle(i2, z);
        } else if (i == 3) {
            Log.i(TAG, "switch menu mode:kaleidoscope_operate");
            beautyOperateMenuHandle(i2, z);
        } else if (i != 4) {
            Log.i(TAG, "default switch menu mode:camera_operate");
            cameraOperateMenuHandle(z);
        } else {
            Log.i(TAG, "switch menu mode:MIMOJI_operate");
            beautyOperateMenuHandle(i2, z);
        }
    }

    public void switchMenuMode(int i, boolean z) {
        switchMenuMode(i, 161, z);
    }
}
