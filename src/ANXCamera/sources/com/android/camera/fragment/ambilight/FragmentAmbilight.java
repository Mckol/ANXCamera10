package com.android.camera.fragment.ambilight;

import android.content.Context;
import android.graphics.Rect;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.core.view.ViewCompat;
import androidx.recyclerview.widget.RecyclerView;
import com.android.camera.R;
import com.android.camera.Util;
import com.android.camera.animation.FolmeUtils;
import com.android.camera.data.DataRepository;
import com.android.camera.data.data.ComponentDataItem;
import com.android.camera.data.data.config.ComponentConfigAmbilight;
import com.android.camera.fragment.BaseFragment;
import com.android.camera.fragment.DefaultItemAnimator;
import com.android.camera.fragment.beauty.LinearLayoutManagerWrapper;
import com.android.camera.fragment.top.FragmentTopAlert;
import com.android.camera.module.AmbilightModule;
import com.android.camera.protocol.ModeCoordinatorImpl;
import com.android.camera.protocol.ModeProtocol;
import com.android.camera.ui.ScrollTextview;
import io.reactivex.Completable;
import java.util.ArrayList;
import java.util.List;

public class FragmentAmbilight extends BaseFragment implements View.OnClickListener, ModeProtocol.AmbilightSelector, ModeProtocol.HandleBackTrace, ModeProtocol.TopAlertEvent {
    private static final int FRAGMENT_INFO = 16777200;
    private static final String TAG = "FragmentAmbilight";
    private ComponentConfigAmbilight mComponentConfigAmbilight;
    private int mCurrentIndex;
    private TextView mDebugInfoTextView;
    private EffectItemPadding mEffectItemPadding;
    private boolean mIgnoreSameItemClick = true;
    private ImageView mIndicatorButton;
    private boolean mIsManuallyParentHiding;
    private boolean mIsShooting;
    private int mLastIndex = -1;
    private LinearLayoutManagerWrapper mLayoutManager;
    private int mMode;
    private LinearLayout mRootLayout;
    private RecyclerView mSceneModeList;
    private LinearLayout mSceneModeSelector;
    private SceneModeItemAdapter mSceneModesItemAdapter;

    /* access modifiers changed from: private */
    public static class EffectItemPadding extends RecyclerView.ItemDecoration {
        protected int mEffectListLeft;
        protected int mHorizontalPadding;
        protected int mVerticalPadding;

        public EffectItemPadding(Context context) {
            int dimensionPixelSize = context.getResources().getDimensionPixelSize(R.dimen.vv_list_item_margin);
            this.mEffectListLeft = dimensionPixelSize;
            this.mHorizontalPadding = dimensionPixelSize;
        }

        @Override // androidx.recyclerview.widget.RecyclerView.ItemDecoration
        public void getItemOffsets(Rect rect, View view, RecyclerView recyclerView, RecyclerView.State state) {
            int i = recyclerView.getChildPosition(view) == 0 ? this.mEffectListLeft : 0;
            int i2 = this.mVerticalPadding;
            rect.set(i, i2, this.mHorizontalPadding, i2);
        }
    }

    /* access modifiers changed from: protected */
    public class SceneModeItemAdapter extends RecyclerView.Adapter {
        LayoutInflater mLayoutInflater;
        ComponentConfigAmbilight mSceneModes;

        /* access modifiers changed from: private */
        public class AmbilightModeItemHolder extends RecyclerView.ViewHolder {
            private ImageView mImageView;
            private ImageView mSelectedIndicator;
            protected ScrollTextview mTextView;

            public AmbilightModeItemHolder(View view) {
                super(view);
                this.mTextView = (ScrollTextview) view.findViewById(R.id.effect_item_text);
                this.mImageView = (ImageView) view.findViewById(R.id.effect_item_image);
                this.mSelectedIndicator = (ImageView) view.findViewById(R.id.effect_item_selected_indicator);
            }

            public void bindEffectIndex(int i, ComponentDataItem componentDataItem) {
                this.mTextView.setText(componentDataItem.mDisplayNameRes);
                this.mImageView.setImageResource(componentDataItem.mIconRes);
                Util.correctionSelectView(this.mImageView, i == FragmentAmbilight.this.mCurrentIndex);
                if (i == FragmentAmbilight.this.mCurrentIndex) {
                    this.itemView.setActivated(true);
                    if (Util.isAccessible() || Util.isSetContentDesc()) {
                        View view = this.itemView;
                        view.setContentDescription(FragmentAmbilight.this.getContext().getString(componentDataItem.mDisplayNameRes) + FragmentAmbilight.this.getString(R.string.accessibility_selected));
                        this.itemView.postDelayed(new Runnable() {
                            /* class com.android.camera.fragment.ambilight.FragmentAmbilight.SceneModeItemAdapter.AmbilightModeItemHolder.AnonymousClass1 */

                            public void run() {
                                if (FragmentAmbilight.this.isAdded()) {
                                    AmbilightModeItemHolder.this.itemView.sendAccessibilityEvent(128);
                                }
                            }
                        }, 100);
                    }
                    this.mSelectedIndicator.setVisibility(0);
                    this.mSelectedIndicator.setAlpha(1.0f);
                    return;
                }
                this.itemView.setActivated(false);
                this.mSelectedIndicator.setVisibility(8);
                this.mSelectedIndicator.setAlpha(0.0f);
            }
        }

        public SceneModeItemAdapter(Context context, ComponentConfigAmbilight componentConfigAmbilight) {
            this.mLayoutInflater = LayoutInflater.from(context);
            this.mSceneModes = componentConfigAmbilight;
        }

        @Override // androidx.recyclerview.widget.RecyclerView.Adapter
        public int getItemCount() {
            return this.mSceneModes.getItems().size();
        }

        @Override // androidx.recyclerview.widget.RecyclerView.Adapter
        public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, int i) {
            AmbilightModeItemHolder ambilightModeItemHolder = (AmbilightModeItemHolder) viewHolder;
            ambilightModeItemHolder.itemView.setTag(Integer.valueOf(i));
            ambilightModeItemHolder.bindEffectIndex(i, this.mSceneModes.getItems().get(i));
        }

        @Override // androidx.recyclerview.widget.RecyclerView.Adapter
        public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int i, @NonNull List list) {
            if (list.isEmpty()) {
                onBindViewHolder(viewHolder, i);
            } else if (viewHolder instanceof AmbilightModeItemHolder) {
                AmbilightModeItemHolder ambilightModeItemHolder = (AmbilightModeItemHolder) viewHolder;
                if (list.get(0) instanceof Boolean) {
                    boolean booleanValue = ((Boolean) list.get(0)).booleanValue();
                    ambilightModeItemHolder.itemView.setActivated(booleanValue);
                    Util.updateSelectIndicator(ambilightModeItemHolder.mSelectedIndicator, booleanValue, true);
                    Util.correctionSelectView(ambilightModeItemHolder.mImageView, booleanValue);
                }
            }
        }

        @Override // androidx.recyclerview.widget.RecyclerView.Adapter
        public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
            View inflate = this.mLayoutInflater.inflate(R.layout.ambilight_scenes_item, viewGroup, false);
            AmbilightModeItemHolder ambilightModeItemHolder = new AmbilightModeItemHolder(inflate);
            inflate.setOnClickListener(FragmentAmbilight.this);
            FolmeUtils.handleListItemTouch(inflate);
            return ambilightModeItemHolder;
        }

        public void updateData(ComponentConfigAmbilight componentConfigAmbilight) {
            this.mSceneModes = componentConfigAmbilight;
            notifyDataSetChanged();
        }
    }

    private void animateAlphaShow(View view, int i, int i2, float f) {
        ViewCompat.setAlpha(view, f);
        ViewCompat.animate(view).setStartDelay((long) i).setDuration((long) i2).alpha(1.0f).start();
    }

    private ArrayList<AmbilightInfo> getAmbilightInfo() {
        ArrayList<AmbilightInfo> arrayList = new ArrayList<>(5);
        arrayList.add(new AmbilightInfo(0, R.string.ambilight_scene_crowd_moving, R.drawable.ambilight_scene_mode_crowd_moving, 0, 4));
        arrayList.add(new AmbilightInfo(1, R.string.ambilight_scene_traffic_light, R.drawable.ambilight_scene_mode_traffic_light, 1, 0));
        arrayList.add(new AmbilightInfo(2, R.string.ambilight_scene_silky_water, R.drawable.ambilight_scene_mode_silky_water, 2, 1));
        arrayList.add(new AmbilightInfo(3, R.string.ambilight_scene_light_track, R.drawable.ambilight_scene_mode_light_track, 3, 2));
        arrayList.add(new AmbilightInfo(4, R.string.ambilight_scene_magic_star, R.drawable.ambilight_scene_mode_magic_star, 4, 5));
        arrayList.add(new AmbilightInfo(5, R.string.ambilight_scene_star_track, R.drawable.ambilight_scene_mode_star_track, 5, 3));
        return arrayList;
    }

    private boolean hideAmbilightLayout(int i) {
        if (this.mIsManuallyParentHiding || 3 == i || getView() == null) {
            return false;
        }
        this.mIsManuallyParentHiding = true;
        FolmeUtils.animateDeparture(this.mSceneModeSelector, new b(this, i));
        if (i != 4) {
            animateAlphaShow(this.mIndicatorButton, 150, 300, 0.0f);
        }
        return true;
    }

    /* access modifiers changed from: private */
    /* renamed from: onHideFinished */
    public void n(int i) {
        this.mIsManuallyParentHiding = false;
        this.mSceneModeSelector.setVisibility(8);
        if (i == 4) {
            this.mRootLayout.setVisibility(8);
        } else {
            this.mIndicatorButton.setVisibility(0);
            updateTips();
        }
        LinearLayout linearLayout = this.mSceneModeSelector;
        if (linearLayout != null) {
            FolmeUtils.clean(linearLayout);
        }
    }

    private void onItemSelected(int i, boolean z) {
        Log.d(TAG, "onItemSelected: index = " + i + ", fromClick = " + z + ", mCurrentMode = " + this.mCurrentMode + ", DataRepository.dataItemGlobal().getCurrentMode() = " + DataRepository.dataItemGlobal().getCurrentMode());
        ModeProtocol.AmbilightProtocol ambilightProtocol = (ModeProtocol.AmbilightProtocol) ModeCoordinatorImpl.getInstance().getAttachProtocol(420);
        if (ambilightProtocol == null) {
            Log.e(TAG, "onItemSelected: configChanges = null");
            return;
        }
        try {
            String str = this.mComponentConfigAmbilight.getItems().get(i).mValue;
            this.mComponentConfigAmbilight.setAmbilightModeValue(i);
            this.mMode = Integer.parseInt(str);
            ambilightProtocol.onSceneModeSelect(this.mMode);
            updateTips();
            selectItem(i);
        } catch (NumberFormatException e2) {
            Log.e(TAG, "invalid filter id: " + e2.getMessage());
        }
    }

    /* access modifiers changed from: private */
    /* renamed from: onShowFinished */
    public void Fa() {
        this.mRootLayout.setVisibility(0);
        int ambilightModeIndex = DataRepository.dataItemRunning().getComponentConfigAmbilight().getAmbilightModeIndex();
        selectItem(ambilightModeIndex);
        onItemSelected(ambilightModeIndex, false);
        LinearLayout linearLayout = this.mSceneModeSelector;
        if (linearLayout != null) {
            FolmeUtils.clean(linearLayout);
        }
    }

    private void scrollIfNeed(int i) {
        if (i == this.mLayoutManager.findFirstVisibleItemPosition() || i == this.mLayoutManager.findFirstCompletelyVisibleItemPosition()) {
            int i2 = this.mEffectItemPadding.mHorizontalPadding;
            View findViewByPosition = this.mLayoutManager.findViewByPosition(i);
            if (i > 0 && findViewByPosition != null) {
                i2 = (this.mEffectItemPadding.mHorizontalPadding * 2) + findViewByPosition.getWidth();
            }
            this.mLayoutManager.scrollToPositionWithOffset(Math.max(0, i), i2);
        } else if (i == this.mLayoutManager.findLastVisibleItemPosition() || i == this.mLayoutManager.findLastCompletelyVisibleItemPosition()) {
            this.mLayoutManager.scrollToPosition(Math.min(i + 1, this.mSceneModesItemAdapter.getItemCount() - 1));
        }
    }

    private void selectItem(int i) {
        int i2;
        if (i != -1 && i != (i2 = this.mCurrentIndex)) {
            this.mLastIndex = i2;
            this.mCurrentIndex = i;
            int i3 = this.mLastIndex;
            if (i3 > -1) {
                this.mSceneModesItemAdapter.notifyItemChanged(i3, false);
            }
            int i4 = this.mCurrentIndex;
            if (i4 > -1) {
                this.mSceneModesItemAdapter.notifyItemChanged(i4, true);
            }
            scrollIfNeed(i);
        }
    }

    private void showSelector() {
        FolmeUtils.animateShow(this.mSceneModeSelector);
        updateTips();
        this.mIndicatorButton.setVisibility(8);
    }

    @Override // com.android.camera.fragment.BaseFragment
    public int getFragmentInto() {
        return 16777200;
    }

    /* access modifiers changed from: protected */
    @Override // com.android.camera.fragment.BaseFragment
    public int getLayoutResourceId() {
        return R.layout.fragment_ambilight;
    }

    /* access modifiers changed from: protected */
    @Override // com.android.camera.fragment.BaseFragment
    public void initView(View view) {
        ((ViewGroup.MarginLayoutParams) view.getLayoutParams()).bottomMargin = Util.getBottomHeight();
        this.mRootLayout = (LinearLayout) view.findViewById(R.id.ambilight_scenes_list_root);
        this.mSceneModeList = (RecyclerView) view.findViewById(R.id.ambilight_scenes_list);
        this.mSceneModeSelector = (LinearLayout) view.findViewById(R.id.ambilight_scenes_selector);
        this.mDebugInfoTextView = (TextView) view.findViewById(R.id.debug_info);
        DefaultItemAnimator defaultItemAnimator = new DefaultItemAnimator();
        defaultItemAnimator.setChangeDuration(150);
        defaultItemAnimator.setMoveDuration(150);
        defaultItemAnimator.setAddDuration(150);
        this.mSceneModeList.setItemAnimator(defaultItemAnimator);
        this.mLayoutManager = new LinearLayoutManagerWrapper(getContext(), AmbilightModule.ALGO_XML_FOLDER_NAME);
        this.mLayoutManager.setOrientation(0);
        this.mSceneModeList.setLayoutManager(this.mLayoutManager);
        Context context = getContext();
        this.mComponentConfigAmbilight = DataRepository.dataItemRunning().getComponentConfigAmbilight();
        this.mComponentConfigAmbilight.mapToItems(getAmbilightInfo(), this.mCurrentMode);
        this.mSceneModesItemAdapter = new SceneModeItemAdapter(context, this.mComponentConfigAmbilight);
        this.mSceneModeList.setAdapter(this.mSceneModesItemAdapter);
        this.mSceneModeList.addOnScrollListener(new RecyclerView.OnScrollListener() {
            /* class com.android.camera.fragment.ambilight.FragmentAmbilight.AnonymousClass1 */

            @Override // androidx.recyclerview.widget.RecyclerView.OnScrollListener
            public void onScrollStateChanged(RecyclerView recyclerView, int i) {
                super.onScrollStateChanged(recyclerView, i);
            }
        });
        if (this.mEffectItemPadding == null) {
            this.mEffectItemPadding = new EffectItemPadding(getContext());
            this.mSceneModeList.addItemDecoration(this.mEffectItemPadding);
        }
        this.mIndicatorButton = (ImageView) view.findViewById(R.id.ambilight_indicator);
        FolmeUtils.touchScaleTint(this.mIndicatorButton);
        this.mIndicatorButton.setOnClickListener(this);
        provideAnimateElement(this.mCurrentMode, null, 2);
    }

    @Override // com.android.camera.fragment.BaseFragment, com.android.camera.animation.AnimationDelegate.AnimationResource
    public void notifyAfterFrameAvailable(int i) {
        super.notifyAfterFrameAvailable(i);
        if (this.mCurrentMode != 187) {
            return;
        }
        if (this.mSceneModeSelector.getVisibility() == 0) {
            updateTips();
            return;
        }
        this.mIndicatorButton.setVisibility(8);
        FolmeUtils.animateShow(this.mSceneModeSelector, new a(this));
    }

    @Override // com.android.camera.protocol.ModeProtocol.HandleBackTrace
    public boolean onBackEvent(int i) {
        LinearLayout linearLayout = this.mSceneModeSelector;
        if (linearLayout == null || linearLayout.getVisibility() != 0 || i == 3 || this.mIsManuallyParentHiding) {
            return false;
        }
        if ((i == 2 || i == 1) && this.mIsShooting) {
            return false;
        }
        return hideAmbilightLayout(i);
    }

    public void onClick(View view) {
        Log.d(TAG, "onClick: ");
        if (this.mSceneModeSelector.isEnabled()) {
            if (view.getId() == R.id.ambilight_indicator) {
                showSelector();
                return;
            }
            ModeProtocol.CameraAction cameraAction = (ModeProtocol.CameraAction) ModeCoordinatorImpl.getInstance().getAttachProtocol(161);
            if (cameraAction == null || !cameraAction.isDoingAction()) {
                int intValue = ((Integer) view.getTag()).intValue();
                if (this.mCurrentIndex != intValue || !this.mIgnoreSameItemClick) {
                    onItemSelected(intValue, true);
                }
            }
        }
    }

    @Override // com.android.camera.protocol.ModeProtocol.TopAlertEvent
    public void onRecommendDescDismiss() {
        if (this.mCurrentMode == 187) {
            updateTips();
        }
    }

    @Override // com.android.camera.protocol.ModeProtocol.AmbilightSelector
    public void onRecordingPrepare() {
        this.mIsShooting = true;
        this.mRootLayout.setVisibility(4);
    }

    @Override // com.android.camera.protocol.ModeProtocol.AmbilightSelector
    public void onRecordingStop() {
        this.mIsShooting = false;
        this.mIndicatorButton.setVisibility(8);
    }

    @Override // com.android.camera.fragment.BaseFragment, com.android.camera.animation.AnimationDelegate.AnimationResource
    public void provideAnimateElement(int i, List<Completable> list, int i2) {
        super.provideAnimateElement(i, list, i2);
        if (i == 187) {
            Fa();
            this.mIndicatorButton.setVisibility(8);
        } else if (this.mRootLayout.getVisibility() == 0) {
            if (i2 != 2) {
                this.mComponentConfigAmbilight.setAmbilightModeValue(0);
            }
            selectItem(0);
            this.mLayoutManager.scrollToPosition(0);
            hideAmbilightLayout(4);
        } else if (this.mSceneModeSelector.getVisibility() == 0) {
            this.mSceneModeSelector.setVisibility(4);
        }
    }

    /* access modifiers changed from: protected */
    @Override // com.android.camera.fragment.BaseFragment
    public void register(ModeProtocol.ModeCoordinator modeCoordinator) {
        super.register(modeCoordinator);
        modeCoordinator.attachProtocol(421, this);
        modeCoordinator.attachProtocol(673, this);
        registerBackStack(modeCoordinator, this);
    }

    @Override // com.android.camera.protocol.ModeProtocol.AmbilightSelector
    public void setSelectorLayoutVisible(boolean z) {
        if (!z) {
            onBackEvent(5);
        } else if (this.mIndicatorButton.getVisibility() != 0) {
            this.mIndicatorButton.setVisibility(0);
        }
    }

    /* access modifiers changed from: protected */
    @Override // com.android.camera.fragment.BaseFragment
    public void unRegister(ModeProtocol.ModeCoordinator modeCoordinator) {
        super.unRegister(modeCoordinator);
        modeCoordinator.detachProtocol(421, this);
        modeCoordinator.detachProtocol(673, this);
        unRegisterBackStack(modeCoordinator, this);
    }

    @Override // com.android.camera.protocol.ModeProtocol.AmbilightSelector
    public void updateDebugInfo(String str) {
        if (this.mDebugInfoTextView.getVisibility() != 0) {
            this.mDebugInfoTextView.setVisibility(0);
        }
        this.mDebugInfoTextView.setText(str);
    }

    @Override // com.android.camera.protocol.ModeProtocol.AmbilightSelector
    public void updateTips() {
        ModeProtocol.TopAlert topAlert;
        if (!this.mIsShooting && (topAlert = (ModeProtocol.TopAlert) ModeCoordinatorImpl.getInstance().getAttachProtocol(172)) != null && !topAlert.getTipsState(FragmentTopAlert.TIP_SPEECH_SHUTTER_DESC)) {
            int i = this.mMode;
            if (i != 2) {
                if (i == 3) {
                    topAlert.alertAiDetectTipHint(0, R.string.ambilight_tips_for_longtime_using_tripod, -1);
                    return;
                } else if (i != 5) {
                    topAlert.alertAiDetectTipHint(8, R.string.ambilight_tips_for_using_tripod, -1);
                    return;
                }
            }
            topAlert.alertAiDetectTipHint(0, R.string.ambilight_tips_for_using_tripod, -1);
        }
    }
}
