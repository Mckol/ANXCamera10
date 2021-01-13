package com.android.camera.fragment.bottom.action;

import android.content.Context;
import android.graphics.Rect;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.view.ViewCompat;
import androidx.recyclerview.widget.RecyclerView;
import com.android.camera.Camera;
import com.android.camera.HybridZoomingSystem;
import com.android.camera.R;
import com.android.camera.Util;
import com.android.camera.animation.FolmeUtils;
import com.android.camera.customization.TintColor;
import com.android.camera.data.DataRepository;
import com.android.camera.data.data.ComponentData;
import com.android.camera.data.data.ComponentDataItem;
import com.android.camera.data.data.config.ComponentConfigRatio;
import com.android.camera.data.data.runing.ComponentRunningLighting;
import com.android.camera.effect.FilterInfo;
import com.android.camera.fragment.BaseFragment;
import com.android.camera.fragment.BaseFragmentDelegate;
import com.android.camera.fragment.DefaultItemAnimator;
import com.android.camera.fragment.EffectItemAdapter;
import com.android.camera.fragment.beauty.LinearLayoutManagerWrapper;
import com.android.camera.protocol.ModeCoordinatorImpl;
import com.android.camera.protocol.ModeProtocol;
import com.android.camera.ui.ColorImageView;
import io.reactivex.Completable;
import java.util.List;

public class FragmentLighting extends BaseFragment implements View.OnClickListener, ModeProtocol.PortraitLighting, ModeProtocol.HandleBackTrace, EffectItemAdapter.IEffectItemListener {
    public static int STATE_HIDDEN = 2;
    public static int STATE_SHOW = 1;
    private ComponentRunningLighting mComponentRunningLighting;
    private int mCurrentIndex = 0;
    private int mHolderWidth;
    private int mLastIndex = 0;
    private LinearLayoutManagerWrapper mLayoutManager;
    private LinearLayout mLightParent;
    private EffectItemAdapter mLightingAdapter;
    private int mPortraitLightingVersion;
    private RecyclerView mRecyclerView;
    protected boolean mRemoveFragment;
    private int mTotalWidth;

    public static class ItemPadding extends RecyclerView.ItemDecoration {
        protected boolean mIsRTL = false;
        protected int padding;

        public ItemPadding(Context context) {
            this.padding = context.getResources().getDimensionPixelSize(R.dimen.beautycamera_makeup_item_margin);
            this.mIsRTL = Util.isLayoutRTL(context);
        }

        @Override // androidx.recyclerview.widget.RecyclerView.ItemDecoration
        public void getItemOffsets(Rect rect, View view, RecyclerView recyclerView, RecyclerView.State state) {
            int childPosition = recyclerView.getChildPosition(view);
            int i = 0;
            if (this.mIsRTL) {
                int i2 = this.padding;
                if (childPosition == 0) {
                    i = i2;
                }
                rect.set(i2, i2, i, this.padding);
                return;
            }
            if (childPosition == 0) {
                i = this.padding;
            }
            int i3 = this.padding;
            rect.set(i, i3, i3, i3);
        }
    }

    private static class LightingAdapter extends EffectItemAdapter {
        private ComponentData mComponentRunningLighting;
        private String[] mContent;
        private int mCount = this.mComponentRunningLighting.getItems().size();
        private int mCurrentMode;
        private int mDegree;
        private View.OnClickListener mOnClickListener;

        public LightingAdapter(Context context, int i, View.OnClickListener onClickListener, ComponentRunningLighting componentRunningLighting) {
            super(context, componentRunningLighting);
            this.mCurrentMode = i;
            this.mOnClickListener = onClickListener;
            this.mComponentRunningLighting = componentRunningLighting;
            updateContent(context);
        }

        private void updateContent(Context context) {
            if (Util.isAccessible() || Util.isSetContentDesc()) {
                this.mContent = new String[this.mCount];
                for (int i = 0; i < this.mCount; i++) {
                    this.mContent[i] = context.getString(this.mComponentRunningLighting.getItems().get(i).mDisplayNameRes);
                }
            }
        }

        @Override // androidx.recyclerview.widget.RecyclerView.Adapter, com.android.camera.fragment.EffectItemAdapter
        public int getItemCount() {
            return this.mCount;
        }

        @Override // androidx.recyclerview.widget.RecyclerView.Adapter, com.android.camera.fragment.EffectItemAdapter
        public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, int i) {
            ViewCompat.setRotation(viewHolder.itemView.findViewById(R.id.container_wrapper), (float) this.mDegree);
            ColorImageView colorImageView = (ColorImageView) viewHolder.itemView.findViewById(R.id.makeup_item_icon);
            String componentValue = this.mComponentRunningLighting.getComponentValue(this.mCurrentMode);
            ComponentDataItem componentDataItem = this.mComponentRunningLighting.getItems().get(i);
            if (Util.isAccessible() || Util.isSetContentDesc()) {
                colorImageView.setContentDescription(this.mContent[i]);
            }
            TextView textView = (TextView) viewHolder.itemView.findViewById(R.id.makeup_item_name);
            int i2 = componentDataItem.mDisplayNameRes;
            if (i2 > 0) {
                textView.setText(i2);
            } else {
                textView.setText("");
            }
            colorImageView.setImageResource(componentDataItem.mIconRes);
            if (componentValue.equals(componentDataItem.mValue)) {
                colorImageView.setColor(TintColor.tintColor());
                textView.setTextColor(TintColor.tintColor());
            } else {
                colorImageView.setColor(-1);
                textView.setTextColor(textView.getContext().getColor(R.color.beautycamera_beauty_advanced_item_text_normal));
            }
            viewHolder.itemView.setOnClickListener(this.mOnClickListener);
            FolmeUtils.handleListItemTouch(viewHolder.itemView);
            viewHolder.itemView.setTag(Integer.valueOf(i));
        }

        @Override // androidx.recyclerview.widget.RecyclerView.Adapter, com.android.camera.fragment.EffectItemAdapter
        public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int i, @NonNull List list) {
            onBindViewHolder(viewHolder, i);
        }

        @Override // androidx.recyclerview.widget.RecyclerView.Adapter, com.android.camera.fragment.EffectItemAdapter
        public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
            return new EffectItemAdapter.EffectStillItemHolder(LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.makeup_item, viewGroup, false));
        }

        @Override // com.android.camera.fragment.EffectItemAdapter
        public void setRotation(int i) {
            this.mDegree = i;
        }

        @Override // com.android.camera.fragment.EffectItemAdapter
        public void updateData(ComponentData componentData) {
            this.mComponentRunningLighting = componentData;
            this.mCount = this.mComponentRunningLighting.getItems().size();
            updateContent(this.mContext);
            notifyDataSetChanged();
        }
    }

    private void notifyItemChanged(int i, int i2) {
        EffectItemAdapter.ItemChangeData itemChangeData = new EffectItemAdapter.ItemChangeData(false, i);
        EffectItemAdapter.ItemChangeData itemChangeData2 = new EffectItemAdapter.ItemChangeData(true, i2);
        if (i > -1) {
            this.mLightingAdapter.notifyItemChanged(i, itemChangeData);
        }
        if (i2 > -1) {
            this.mLightingAdapter.notifyItemChanged(i2, itemChangeData2);
        }
    }

    private void onDismissFinished() {
        ModeProtocol.BottomPopupTips bottomPopupTips = (ModeProtocol.BottomPopupTips) ModeCoordinatorImpl.getInstance().getAttachProtocol(175);
        if (bottomPopupTips != null) {
            bottomPopupTips.reInitTipImage();
        }
        ModeProtocol.DualController dualController = (ModeProtocol.DualController) ModeCoordinatorImpl.getInstance().getAttachProtocol(182);
        int i = this.mCurrentMode;
        if (i == 171) {
            if (dualController != null) {
                dualController.showBokehButton();
            }
        } else if (HybridZoomingSystem.IS_3_OR_MORE_SAT && ((i == 174 || i == 161 || i == 183) && DataRepository.dataItemGlobal().getCurrentCameraId() == 0 && dualController != null)) {
            dualController.showZoomButton();
            ModeProtocol.TopAlert topAlert = (ModeProtocol.TopAlert) ModeCoordinatorImpl.getInstance().getAttachProtocol(172);
            if (topAlert != null) {
                topAlert.clearAlertStatus();
            }
        }
        if (getView() != null) {
            FolmeUtils.clean(getView());
        }
    }

    private void onItemSelected(int i, boolean z) {
        ModeProtocol.ConfigChanges configChanges = (ModeProtocol.ConfigChanges) ModeCoordinatorImpl.getInstance().getAttachProtocol(164);
        if (configChanges != null) {
            String componentValue = this.mComponentRunningLighting.getComponentValue(this.mCurrentMode);
            String str = this.mComponentRunningLighting.getItems().get(i).mValue;
            if (!componentValue.equals(str)) {
                if (DataRepository.dataItemRunning().getComponentRunningLighting().getPortraitLightVersion() > 1) {
                    configChanges.setFilter(FilterInfo.FILTER_ID_NONE);
                }
                this.mComponentRunningLighting.setComponentValue(this.mCurrentMode, str);
                configChanges.setLighting(false, componentValue, str, true);
                this.mLastIndex = this.mCurrentIndex;
                this.mCurrentIndex = i;
                scrollIfNeed(i);
                notifyItemChanged(this.mLastIndex, this.mCurrentIndex);
            }
        }
    }

    private void scrollIfNeed(int i) {
        if (i == this.mLayoutManager.findFirstVisibleItemPosition() || i == this.mLayoutManager.findFirstCompletelyVisibleItemPosition()) {
            this.mLayoutManager.scrollToPosition(Math.max(0, i - 1));
        } else if (i == this.mLayoutManager.findLastVisibleItemPosition() || i == this.mLayoutManager.findLastCompletelyVisibleItemPosition()) {
            this.mLayoutManager.scrollToPosition(Math.min(i + 1, this.mLightingAdapter.getItemCount() - 1));
        }
    }

    private void setItemInCenter(int i) {
        this.mCurrentIndex = i;
        this.mLastIndex = i;
        int i2 = (this.mTotalWidth / 2) - (this.mHolderWidth / 2);
        this.mLightingAdapter.notifyDataSetChanged();
        this.mLayoutManager.scrollToPositionWithOffset(i, i2);
    }

    @Override // com.android.camera.protocol.ModeProtocol.PortraitLighting
    public void dismiss(int i) {
        onBackEvent(6);
    }

    @Override // com.android.camera.fragment.EffectItemAdapter.IEffectItemListener
    public int getCurrentIndex() {
        return this.mCurrentIndex;
    }

    @Override // com.android.camera.fragment.BaseFragment
    public int getFragmentInto() {
        return BaseFragmentDelegate.FRAGMENT_LIGHTING;
    }

    @Override // com.android.camera.fragment.EffectItemAdapter.IEffectItemListener
    public int getHolderHeight() {
        return 0;
    }

    @Override // com.android.camera.fragment.EffectItemAdapter.IEffectItemListener
    public int getHolderWidth() {
        return 0;
    }

    @Override // com.android.camera.fragment.EffectItemAdapter.IEffectItemListener
    public int getLastIndex() {
        return this.mLastIndex;
    }

    /* access modifiers changed from: protected */
    @Override // com.android.camera.fragment.BaseFragment
    public int getLayoutResourceId() {
        return R.layout.fragment_bottom_action_lighting;
    }

    @Override // com.android.camera.fragment.EffectItemAdapter.IEffectItemListener
    public int getTextureHeight() {
        return 0;
    }

    @Override // com.android.camera.fragment.EffectItemAdapter.IEffectItemListener
    public int getTextureOffsetX() {
        return 0;
    }

    @Override // com.android.camera.fragment.EffectItemAdapter.IEffectItemListener
    public int getTextureOffsetY() {
        return 0;
    }

    @Override // com.android.camera.fragment.EffectItemAdapter.IEffectItemListener
    public int getTextureWidth() {
        return 0;
    }

    @Override // com.android.camera.fragment.EffectItemAdapter.IEffectItemListener
    public int getTotalWidth() {
        return 0;
    }

    /* access modifiers changed from: protected */
    @Override // com.android.camera.fragment.BaseFragment
    public void initView(View view) {
        Util.alignPopupBottom(view);
        this.mRecyclerView = (RecyclerView) view.findViewById(R.id.lighting_list);
        this.mRecyclerView.setFocusable(false);
        this.mLightParent = (LinearLayout) view.findViewById(R.id.lighting_parent);
        if (ComponentConfigRatio.RATIO_4X3.equals(DataRepository.dataItemConfig().getComponentConfigRatio().getComponentValue(this.mCurrentMode))) {
            this.mLightParent.setClipChildren(true);
        } else {
            this.mLightParent.setClipChildren(false);
        }
        this.mComponentRunningLighting = DataRepository.dataItemRunning().getComponentRunningLighting();
        if (this.mComponentRunningLighting.getPortraitLightVersion() >= 2) {
            this.mLightingAdapter = new EffectItemAdapter(getContext(), this.mComponentRunningLighting, false);
        } else {
            this.mLightingAdapter = new LightingAdapter(getContext(), this.mCurrentMode, this, this.mComponentRunningLighting);
        }
        this.mLightingAdapter.setOnClickListener(this);
        this.mLightingAdapter.setOnEffectItemListener(this);
        this.mLightingAdapter.setRotation(this.mDegree);
        this.mLayoutManager = new LinearLayoutManagerWrapper(getContext(), 0, false, "lighting_list");
        this.mRecyclerView.setLayoutManager(this.mLayoutManager);
        this.mRecyclerView.setAdapter(this.mLightingAdapter);
        DefaultItemAnimator defaultItemAnimator = new DefaultItemAnimator();
        defaultItemAnimator.setChangeDuration(150);
        defaultItemAnimator.setMoveDuration(150);
        defaultItemAnimator.setAddDuration(150);
        this.mRecyclerView.setItemAnimator(defaultItemAnimator);
        this.mRecyclerView.addItemDecoration(new ItemPadding(getContext()));
        this.mTotalWidth = getResources().getDisplayMetrics().widthPixels;
        if (this.mPortraitLightingVersion > 1) {
            this.mHolderWidth = getResources().getDimensionPixelSize(R.dimen.lighting_item_height);
        } else {
            this.mHolderWidth = getResources().getDimensionPixelSize(R.dimen.lighting_2_item_height);
        }
        String componentValue = this.mComponentRunningLighting.getComponentValue(this.mCurrentMode);
        List<ComponentDataItem> items = this.mComponentRunningLighting.getItems();
        int i = 0;
        while (true) {
            if (i >= items.size()) {
                break;
            } else if (items.get(i).mValue.equals(componentValue)) {
                this.mCurrentIndex = i;
                break;
            } else {
                i++;
            }
        }
        reInitAdapterBgMode(false);
        this.mPortraitLightingVersion = 1;
        if (getActivity() != null && (getActivity() instanceof Camera)) {
            Camera camera = (Camera) getActivity();
            if (camera.getCurrentModule() != null) {
                this.mPortraitLightingVersion = camera.getCurrentModule().getPortraitLightingVersion();
            }
        }
    }

    @Override // com.android.camera.fragment.EffectItemAdapter.IEffectItemListener
    public boolean isAnimation() {
        return true;
    }

    @Override // com.android.camera.protocol.ModeProtocol.HandleBackTrace
    public boolean onBackEvent(int i) {
        ModeProtocol.BaseDelegate baseDelegate;
        ModeProtocol.BaseDelegate baseDelegate2;
        if (i != 1) {
            if (i != 2) {
                if (i == 4 && (baseDelegate2 = (ModeProtocol.BaseDelegate) ModeCoordinatorImpl.getInstance().getAttachProtocol(160)) != null) {
                    baseDelegate2.delegateEvent(33);
                }
                return false;
            }
            String componentValue = DataRepository.dataItemRunning().getComponentRunningLighting().getComponentValue(171);
            if (this.mPortraitLightingVersion <= 1 && !componentValue.equals("0")) {
                return false;
            }
        }
        ModeProtocol.CameraAction cameraAction = (ModeProtocol.CameraAction) ModeCoordinatorImpl.getInstance().getAttachProtocol(161);
        if ((cameraAction != null && cameraAction.isDoingAction()) || (baseDelegate = (ModeProtocol.BaseDelegate) ModeCoordinatorImpl.getInstance().getAttachProtocol(160)) == null || baseDelegate.getActiveFragment(R.id.bottom_beauty) != getFragmentInto()) {
            return false;
        }
        ModeProtocol.ConfigChanges configChanges = (ModeProtocol.ConfigChanges) ModeCoordinatorImpl.getInstance().getAttachProtocol(164);
        if (configChanges != null) {
            this.mRemoveFragment = true;
            configChanges.showOrHideLighting(false);
        }
        FolmeUtils.animateDeparture(getView(), null);
        return true;
    }

    public void onClick(View view) {
        if (isEnableClick()) {
            ModeProtocol.CameraAction cameraAction = (ModeProtocol.CameraAction) ModeCoordinatorImpl.getInstance().getAttachProtocol(161);
            if (cameraAction == null || !cameraAction.isDoingAction()) {
                onItemSelected(((Integer) view.getTag()).intValue(), true);
            }
        }
    }

    @Override // com.android.camera.fragment.BaseFragment, androidx.fragment.app.Fragment
    public void onDestroyView() {
        super.onDestroyView();
        if (this.mRemoveFragment) {
            this.mRemoveFragment = false;
            onDismissFinished();
        }
    }

    @Override // com.android.camera.fragment.BaseFragment, androidx.fragment.app.Fragment
    public void onViewCreated(View view, @Nullable Bundle bundle) {
        super.onViewCreated(view, bundle);
        FolmeUtils.animateEntrance(view);
        if (this.mPortraitLightingVersion > 1) {
            reInit();
        }
    }

    @Override // com.android.camera.fragment.BaseFragment, com.android.camera.animation.AnimationDelegate.AnimationResource
    public void provideAnimateElement(int i, List<Completable> list, int i2) {
        super.provideAnimateElement(i, list, i2);
        onBackEvent(4);
    }

    /* access modifiers changed from: protected */
    @Override // com.android.camera.fragment.BaseFragment
    public Animation provideEnterAnimation(int i) {
        return null;
    }

    /* access modifiers changed from: protected */
    @Override // com.android.camera.fragment.BaseFragment
    public Animation provideExitAnimation(int i) {
        return null;
    }

    @Override // com.android.camera.fragment.BaseFragment, com.android.camera.animation.AnimationDelegate.AnimationResource
    public void provideRotateItem(List<View> list, int i) {
        super.provideRotateItem(list, i);
        if (this.mRecyclerView != null) {
            for (int i2 = 0; i2 < this.mRecyclerView.getChildCount(); i2++) {
                View childAt = this.mRecyclerView.getChildAt(i2);
                if (this.mPortraitLightingVersion >= 2) {
                    list.add(childAt);
                } else {
                    list.add(childAt.findViewById(R.id.container_wrapper));
                }
            }
        }
        EffectItemAdapter effectItemAdapter = this.mLightingAdapter;
        if (effectItemAdapter != null) {
            effectItemAdapter.setRotation(i);
            int findFirstVisibleItemPosition = this.mLayoutManager.findFirstVisibleItemPosition();
            int findLastVisibleItemPosition = this.mLayoutManager.findLastVisibleItemPosition();
            for (int i3 = 0; i3 < findFirstVisibleItemPosition; i3++) {
                this.mLightingAdapter.notifyItemChanged(i3);
            }
            while (true) {
                findLastVisibleItemPosition++;
                if (findLastVisibleItemPosition < this.mLightingAdapter.getItemCount()) {
                    this.mLightingAdapter.notifyItemChanged(findLastVisibleItemPosition);
                } else {
                    return;
                }
            }
        }
    }

    public void reInit() {
        reInitAdapterBgMode(false);
        setItemInCenter(this.mComponentRunningLighting.findIndexOfValue(this.mComponentRunningLighting.getComponentValue(this.mCurrentMode)));
    }

    public void reInitAdapterBgMode(boolean z) {
        if (z) {
            this.mComponentRunningLighting = DataRepository.dataItemRunning().getComponentRunningLighting();
            this.mComponentRunningLighting.initItems();
            this.mLightingAdapter.updateData(this.mComponentRunningLighting);
        }
    }

    /* access modifiers changed from: protected */
    @Override // com.android.camera.fragment.BaseFragment
    public void register(ModeProtocol.ModeCoordinator modeCoordinator) {
        super.register(modeCoordinator);
        modeCoordinator.attachProtocol(672, this);
        registerBackStack(modeCoordinator, this);
    }

    /* JADX WARNING: Removed duplicated region for block: B:12:0x0027  */
    /* JADX WARNING: Removed duplicated region for block: B:14:? A[RETURN, SYNTHETIC] */
    public void switchLighting(int i) {
        int i2;
        if (i == 3) {
            int i3 = this.mCurrentIndex;
            if (i3 > 0) {
                i2 = i3 - 1;
                if (i2 > -1) {
                }
            }
        } else if (i == 5 && this.mCurrentIndex < this.mComponentRunningLighting.getItems().size() - 1) {
            i2 = this.mCurrentIndex + 1;
            if (i2 > -1) {
                onItemSelected(i2, false);
                return;
            }
            return;
        }
        i2 = -1;
        if (i2 > -1) {
        }
    }

    /* access modifiers changed from: protected */
    @Override // com.android.camera.fragment.BaseFragment
    public void unRegister(ModeProtocol.ModeCoordinator modeCoordinator) {
        super.unRegister(modeCoordinator);
        modeCoordinator.detachProtocol(672, this);
        unRegisterBackStack(modeCoordinator, this);
    }
}
