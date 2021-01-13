package com.android.camera.fragment.bottom.action;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import com.android.camera.R;
import com.android.camera.Util;
import com.android.camera.constant.ColorConstant;
import com.android.camera.data.DataRepository;
import com.android.camera.features.mimoji2.module.protocol.MimojiModeProtocol;
import com.android.camera.features.mimoji2.widget.helper.MimojiStatusManager2;
import com.android.camera.fragment.BaseFragment;
import com.android.camera.fragment.BaseFragmentDelegate;
import com.android.camera.protocol.ModeCoordinatorImpl;
import com.android.camera.protocol.ModeProtocol;
import com.android.camera.ui.ColorActivateTextView;

public class FragmentActionMimoji extends BaseFragment implements View.OnClickListener {
    public static final int MIMOJI_AVATAR = 1;
    public static final int MIMOJI_UNCLICKABLE = 0;
    private static final String TAG = "FragmentActionMimoji";
    private LinearLayout mMenuParent;
    private MimojiStatusManager2 mimojiStatusManager2;

    @Override // com.android.camera.fragment.BaseFragment
    public int getFragmentInto() {
        return BaseFragmentDelegate.FRAGMENT_LIGHTING;
    }

    /* access modifiers changed from: protected */
    @Override // com.android.camera.fragment.BaseFragment
    public int getLayoutResourceId() {
        return R.layout.fragment_bottom_action_mimoji;
    }

    /* access modifiers changed from: protected */
    @Override // com.android.camera.fragment.BaseFragment
    public void initView(View view) {
        this.mMenuParent = (LinearLayout) view.findViewById(R.id.ll_menu_parent);
        FrameLayout.LayoutParams layoutParams = (FrameLayout.LayoutParams) this.mMenuParent.getLayoutParams();
        layoutParams.height = Math.round(((float) Util.sBottomBarHeight) * 0.3f);
        this.mMenuParent.setLayoutParams(layoutParams);
        reInit(this.mCurrentMode);
    }

    public void onClick(View view) {
        if (isEnableClick()) {
            ModeProtocol.CameraAction cameraAction = (ModeProtocol.CameraAction) ModeCoordinatorImpl.getInstance().getAttachProtocol(161);
            if (cameraAction == null || !cameraAction.isDoingAction()) {
                MimojiModeProtocol.MimojiBottomList mimojiBottomList = (MimojiModeProtocol.MimojiBottomList) ModeCoordinatorImpl.getInstance().getAttachProtocol(248);
                int intValue = ((Integer) view.getTag()).intValue();
                if (intValue == 100) {
                    this.mimojiStatusManager2.setAvatarPanelState(intValue);
                    reInit(this.mCurrentMode);
                    if (mimojiBottomList != null) {
                        mimojiBottomList.refreshMimojiList();
                    }
                } else if (intValue == 101) {
                    this.mimojiStatusManager2.setAvatarPanelState(intValue);
                    reInit(this.mCurrentMode);
                    if (mimojiBottomList != null) {
                        mimojiBottomList.refreshMimojiList();
                    }
                }
            }
        }
    }

    public void reInit(int i) {
        if (i == 184) {
            this.mimojiStatusManager2 = DataRepository.dataItemLive().getMimojiStatusManager2();
            int mimojiPanelState = this.mimojiStatusManager2.getMimojiPanelState();
            if (mimojiPanelState == 2) {
                setTitle(0, getString(R.string.background));
            } else if (mimojiPanelState != 3) {
                setTitle(1, getString(R.string.mimoji_tab_human), getString(R.string.mimoji_tab_cartoon));
            } else {
                setTitle(0, getString(R.string.timbre));
            }
        } else {
            setTitle(0, getString(R.string.mimoji_fragment_tab_name));
        }
    }

    public void setTitle(int i, String... strArr) {
        LinearLayout linearLayout = this.mMenuParent;
        if (linearLayout != null && strArr != null && strArr.length != 0) {
            int childCount = linearLayout.getChildCount();
            if (childCount > strArr.length) {
                this.mMenuParent.removeAllViews();
                childCount = 0;
            }
            if (childCount < strArr.length) {
                for (int i2 = 0; i2 < strArr.length - childCount; i2++) {
                    ColorActivateTextView colorActivateTextView = (ColorActivateTextView) LayoutInflater.from(getContext()).inflate(R.layout.beauty_menu_select_item, (ViewGroup) this.mMenuParent, false);
                    colorActivateTextView.setNormalCor(-1711276033);
                    colorActivateTextView.setActivateColor(ColorConstant.COLOR_COMMON_SELECTED);
                    colorActivateTextView.setOnClickListener(this);
                    this.mMenuParent.addView(colorActivateTextView);
                }
            }
            int childCount2 = this.mMenuParent.getChildCount();
            for (int i3 = 0; i3 < childCount2; i3++) {
                ColorActivateTextView colorActivateTextView2 = (ColorActivateTextView) this.mMenuParent.getChildAt(i3);
                colorActivateTextView2.setText(strArr[i3]);
                colorActivateTextView2.setTag(Integer.valueOf(i3));
                colorActivateTextView2.setActivated(false);
                colorActivateTextView2.setOnClickListener(this);
            }
            if (i == 1) {
                ColorActivateTextView colorActivateTextView3 = (ColorActivateTextView) this.mMenuParent.getChildAt(0);
                colorActivateTextView3.setTag(100);
                ColorActivateTextView colorActivateTextView4 = (ColorActivateTextView) this.mMenuParent.getChildAt(1);
                colorActivateTextView4.setTag(101);
                if (this.mimojiStatusManager2.getAvatarPanelState() == 100) {
                    colorActivateTextView3.setActivated(true);
                } else {
                    colorActivateTextView4.setActivated(true);
                }
            }
        }
    }
}
