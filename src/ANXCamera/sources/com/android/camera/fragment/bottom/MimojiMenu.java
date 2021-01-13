package com.android.camera.fragment.bottom;

import android.content.Context;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import com.android.camera.CameraAppImpl;
import com.android.camera.R;
import com.android.camera.data.DataRepository;
import com.android.camera.data.data.global.DataItemGlobal;
import com.android.camera.features.mimoji2.module.protocol.MimojiModeProtocol;
import com.android.camera.features.mimoji2.widget.helper.MimojiStatusManager2;
import com.android.camera.fragment.beauty.MenuItem;
import com.android.camera.protocol.ModeCoordinatorImpl;
import com.android.camera.protocol.ModeProtocol;
import com.android.camera.ui.ColorActivateTextView;

public class MimojiMenu extends AbBottomMenu implements View.OnClickListener {
    public static final int MIMOJI_CARTOON = 1;
    public static final int MIMOJI_HUMEN = 0;
    public static final int MIMOJI_NULL = -1;
    private int currentModule;
    private boolean mIsMimoji = DataRepository.dataItemFeature().Ej();
    private boolean mIsMimoji2 = DataRepository.dataItemFeature().Sj();
    private SparseArray<ColorActivateTextView> mMenuTextViewList;
    private SparseArray<MenuItem> mMimojiMenuTabList;
    private MimojiStatusManager2 mMimojiStatusManager2 = DataRepository.dataItemLive().getMimojiStatusManager2();

    public MimojiMenu(Context context, LinearLayout linearLayout, BeautyMenuAnimator beautyMenuAnimator) {
        super(context, linearLayout, beautyMenuAnimator);
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
            if (this.mMimojiStatusManager2.getAvatarPanelState() == 101 && valueAt.type == 1) {
                this.mCurrentBeautyTextView = colorActivateTextView;
                colorActivateTextView.setActivated(true);
            } else if (this.mMimojiStatusManager2.getAvatarPanelState() == 100 && valueAt.type == 0) {
                this.mCurrentBeautyTextView = colorActivateTextView;
                colorActivateTextView.setActivated(true);
            } else {
                colorActivateTextView.setActivated(false);
            }
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
        return 8;
    }

    /* access modifiers changed from: package-private */
    @Override // com.android.camera.fragment.bottom.AbBottomMenu
    public SparseArray<MenuItem> getMenuData() {
        SparseArray<MenuItem> sparseArray = this.mMimojiMenuTabList;
        if (sparseArray != null) {
            sparseArray.clear();
        } else {
            this.mMimojiMenuTabList = new SparseArray<>();
        }
        this.currentModule = ((DataItemGlobal) DataRepository.provider().dataGlobal()).getCurrentMode();
        if (this.mIsMimoji && this.currentModule == 177) {
            MenuItem menuItem = new MenuItem();
            menuItem.type = -1;
            menuItem.text = CameraAppImpl.getAndroidContext().getString(R.string.mimoji_fragment_tab_name);
            menuItem.number = 0;
            this.mMimojiMenuTabList.put(-1, menuItem);
        }
        if (this.mIsMimoji2 && this.currentModule == 184) {
            int mimojiPanelState = this.mMimojiStatusManager2.getMimojiPanelState();
            if (mimojiPanelState == 2) {
                MenuItem menuItem2 = new MenuItem();
                menuItem2.type = -1;
                menuItem2.text = this.mContext.getString(R.string.background);
                menuItem2.number = 0;
                this.mMimojiMenuTabList.put(menuItem2.type, menuItem2);
            } else if (mimojiPanelState != 3) {
                MenuItem menuItem3 = new MenuItem();
                menuItem3.type = 0;
                menuItem3.text = this.mContext.getString(R.string.mimoji_tab_human);
                menuItem3.number = 0;
                this.mMimojiMenuTabList.put(menuItem3.type, menuItem3);
                MenuItem menuItem4 = new MenuItem();
                menuItem4.type = 1;
                menuItem4.text = this.mContext.getString(R.string.mimoji_tab_cartoon);
                menuItem4.number = 1;
                this.mMimojiMenuTabList.put(menuItem4.type, menuItem4);
            } else {
                MenuItem menuItem5 = new MenuItem();
                menuItem5.type = -1;
                menuItem5.text = this.mContext.getString(R.string.timbre);
                menuItem5.number = 0;
                this.mMimojiMenuTabList.put(menuItem5.type, menuItem5);
            }
        }
        return this.mMimojiMenuTabList;
    }

    /* access modifiers changed from: protected */
    @Override // com.android.camera.fragment.bottom.AbBottomMenu
    public boolean isClickEnable() {
        return super.isClickEnable() && this.mIsMimoji2 && this.mMimojiStatusManager2.getMimojiPanelState() == 1;
    }

    /* access modifiers changed from: package-private */
    @Override // com.android.camera.fragment.bottom.AbBottomMenu
    public boolean isRefreshUI() {
        return true;
    }

    public void onClick(View view) {
        if (isClickEnable()) {
            ModeProtocol.CameraAction cameraAction = (ModeProtocol.CameraAction) ModeCoordinatorImpl.getInstance().getAttachProtocol(161);
            if (cameraAction == null || !cameraAction.isDoingAction()) {
                MimojiModeProtocol.MimojiBottomList mimojiBottomList = (MimojiModeProtocol.MimojiBottomList) ModeCoordinatorImpl.getInstance().getAttachProtocol(248);
                int intValue = ((Integer) view.getTag()).intValue();
                if (intValue != 0) {
                    if (intValue == 1 && this.mMimojiStatusManager2.getAvatarPanelState() != 101) {
                        this.mMimojiStatusManager2.setAvatarPanelState(101);
                        switchMenu();
                        if (mimojiBottomList != null) {
                            mimojiBottomList.switchMimojiList();
                        }
                    }
                } else if (this.mMimojiStatusManager2.getAvatarPanelState() != 100) {
                    this.mMimojiStatusManager2.setAvatarPanelState(100);
                    switchMenu();
                    if (mimojiBottomList != null) {
                        mimojiBottomList.switchMimojiList();
                    }
                }
            }
        }
    }

    /* access modifiers changed from: package-private */
    @Override // com.android.camera.fragment.bottom.AbBottomMenu
    public void switchMenu() {
        this.mContainerView.removeAllViews();
        addAllView();
    }
}
