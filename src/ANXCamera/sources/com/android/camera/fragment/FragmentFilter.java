package com.android.camera.fragment;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.TextureView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import b.c.a.c;
import com.android.camera.ActivityBase;
import com.android.camera.CameraScreenNail;
import com.android.camera.CameraSettings;
import com.android.camera.NoClipChildrenLayout;
import com.android.camera.R;
import com.android.camera.Util;
import com.android.camera.data.DataRepository;
import com.android.camera.data.data.config.ComponentConfigFilter;
import com.android.camera.effect.EffectController;
import com.android.camera.effect.FilterInfo;
import com.android.camera.fragment.EffectItemAdapter;
import com.android.camera.fragment.beauty.BaseBeautyFragment;
import com.android.camera.fragment.beauty.LinearLayoutManagerWrapper;
import com.android.camera.log.Log;
import com.android.camera.protocol.ModeCoordinatorImpl;
import com.android.camera.protocol.ModeProtocol;
import com.android.camera.statistic.CameraStatUtils;
import io.reactivex.Completable;
import java.util.ArrayList;
import java.util.List;
import miui.view.animation.CubicEaseOutInterpolator;

public class FragmentFilter extends BaseBeautyFragment implements View.OnClickListener, CameraScreenNail.RequestRenderListener, NoClipChildrenLayout, EffectItemAdapter.IEffectItemListener {
    public static final int FRAGMENT_INFO = 250;
    private static final String TAG = "FragmentFilter";
    private boolean isAnimation = false;
    private ComponentConfigFilter mComponentConfigFilter;
    private CubicEaseOutInterpolator mCubicEaseOut;
    private int mCurrentIndex = -1;
    private int mCurrentMode;
    private EffectItemAdapter mEffectItemAdapter;
    private EffectItemAdapter.EffectItemPadding mEffectItemPadding;
    private int mHolderHeight;
    private int mHolderWidth;
    private boolean mIgnoreSameItemClick = true;
    private int mIsShowIndex = -1;
    private int mLastIndex = -1;
    private LinearLayoutManager mLayoutManager;
    private RecyclerView mRecyclerView;
    private boolean mSupportRealtimeEffect;
    private boolean mTargetClipChildren;
    private int mTextureHeight;
    private int mTextureOffsetX;
    private int mTextureOffsetY;
    private int mTextureWidth;
    private int mTotalWidth;

    /* JADX WARNING: Code restructure failed: missing block: B:16:0x002e, code lost:
        if (com.android.camera.CameraSettings.isFrontCamera() != false) goto L_0x0030;
     */
    private ArrayList<FilterInfo> getFilterInfo() {
        int currentMode = DataRepository.dataItemGlobal().getCurrentMode();
        int i = 2;
        if (currentMode != 165) {
            if (currentMode != 169) {
                if (currentMode != 171) {
                    if (currentMode != 180) {
                        if (currentMode != 183) {
                            switch (currentMode) {
                                case 161:
                                    i = 3;
                                    break;
                                case 162:
                                    break;
                                case 163:
                                    break;
                                default:
                                    i = 1;
                                    break;
                            }
                        } else {
                            i = 8;
                        }
                    }
                }
                return EffectController.getInstance().getFilterInfo(i);
            }
            i = 7;
            return EffectController.getInstance().getFilterInfo(i);
        }
    }

    private void initView(View view) {
        this.mSupportRealtimeEffect = supportsRealtimeEffect();
        String str = TAG;
        Log.v(str, "initView " + this.mTargetClipChildren);
        if (this.mTargetClipChildren) {
            this.mTargetClipChildren = false;
            ((ViewGroup) view).setClipChildren(false);
        }
        this.mRecyclerView = (RecyclerView) view.findViewById(R.id.effect_list);
        this.mRecyclerView.setFocusable(false);
        ArrayList<FilterInfo> filterInfo = getFilterInfo();
        this.mComponentConfigFilter = DataRepository.dataItemRunning().getComponentConfigFilter();
        this.mComponentConfigFilter.mapToItems(filterInfo, this.mCurrentMode);
        Context context = getContext();
        this.mTotalWidth = context.getResources().getDisplayMetrics().widthPixels;
        this.mHolderWidth = context.getResources().getDimensionPixelSize(R.dimen.beautycamera_makeup_item_width);
        this.mHolderHeight = this.mHolderWidth;
        this.mEffectItemAdapter = new EffectItemAdapter(context, this.mComponentConfigFilter, this.mSupportRealtimeEffect);
        this.mEffectItemAdapter.setOnClickListener(this);
        this.mEffectItemAdapter.setOnEffectItemListener(this);
        this.mEffectItemAdapter.setRotation(this.mDegree);
        if (c.Rm()) {
            this.mEffectItemAdapter.setDisplayRotation(Util.getDisplayRotation(getActivity()));
        }
        this.mLayoutManager = new LinearLayoutManagerWrapper(context, "effect_list");
        this.mLayoutManager.setOrientation(0);
        this.mRecyclerView.setLayoutManager(this.mLayoutManager);
        if (this.mEffectItemPadding == null) {
            this.mEffectItemPadding = new EffectItemAdapter.EffectItemPadding(getContext());
            this.mRecyclerView.addItemDecoration(this.mEffectItemPadding);
        }
        this.mRecyclerView.setAdapter(this.mEffectItemAdapter);
        this.mRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            /* class com.android.camera.fragment.FragmentFilter.AnonymousClass1 */

            @Override // androidx.recyclerview.widget.RecyclerView.OnScrollListener
            public void onScrollStateChanged(RecyclerView recyclerView, int i) {
                super.onScrollStateChanged(recyclerView, i);
                FragmentFilter.this.setIsAnimation(false);
            }
        });
        DefaultItemAnimator defaultItemAnimator = new DefaultItemAnimator();
        defaultItemAnimator.setChangeDuration(150);
        defaultItemAnimator.setMoveDuration(150);
        defaultItemAnimator.setAddDuration(150);
        this.mRecyclerView.setItemAnimator(defaultItemAnimator);
        this.mCubicEaseOut = new CubicEaseOutInterpolator();
        measure();
    }

    private void measure() {
        CameraScreenNail cameraScreenNail = ((ActivityBase) getContext()).getCameraScreenNail();
        int width = cameraScreenNail.getWidth();
        int height = cameraScreenNail.getHeight();
        this.mTextureOffsetX = 0;
        this.mTextureOffsetY = 0;
        int i = this.mHolderWidth;
        this.mTextureWidth = i;
        int i2 = this.mHolderHeight;
        this.mTextureHeight = i2;
        if (height * i > width * i2) {
            this.mTextureHeight = (i * height) / width;
            this.mTextureOffsetY = (-(this.mTextureHeight - i2)) / 2;
            return;
        }
        this.mTextureWidth = (i2 * width) / height;
        this.mTextureOffsetX = (-(this.mTextureWidth - i)) / 2;
    }

    private void notifyItemChanged(int i, int i2) {
        EffectItemAdapter.ItemChangeData itemChangeData = new EffectItemAdapter.ItemChangeData(false, i);
        EffectItemAdapter.ItemChangeData itemChangeData2 = new EffectItemAdapter.ItemChangeData(true, i2);
        if (i > -1) {
            this.mEffectItemAdapter.notifyItemChanged(i, itemChangeData);
        }
        if (i2 > -1) {
            this.mEffectItemAdapter.notifyItemChanged(i2, itemChangeData2);
        }
    }

    private void onItemSelected(int i, boolean z) {
        String str = TAG;
        Log.d(str, "onItemSelected: index = " + i + ", fromClick = " + z + ", mCurrentMode = " + this.mCurrentMode + ", DataRepository.dataItemGlobal().getCurrentMode() = " + DataRepository.dataItemGlobal().getCurrentMode());
        ModeProtocol.ConfigChanges configChanges = (ModeProtocol.ConfigChanges) ModeCoordinatorImpl.getInstance().getAttachProtocol(164);
        if (configChanges == null) {
            Log.e(TAG, "onItemSelected: configChanges = null");
            return;
        }
        try {
            String str2 = this.mComponentConfigFilter.getItems().get(i).mValue;
            this.mComponentConfigFilter.setClosed(false, DataRepository.dataItemGlobal().getCurrentMode());
            int intValue = Integer.valueOf(str2).intValue();
            CameraStatUtils.trackFilterChanged(intValue, z);
            selectItem(i);
            if (DataRepository.dataItemRunning().getComponentRunningLighting().getPortraitLightVersion() > 1) {
                DataRepository.dataItemRunning().getComponentRunningLighting().setComponentValue(this.mCurrentMode, "0");
                configChanges.setLighting(false, "0", "0", false);
            }
            configChanges.setFilter(intValue);
        } catch (NumberFormatException e2) {
            String str3 = TAG;
            Log.e(str3, "invalid filter id: " + e2.getMessage());
        }
    }

    private void scrollIfNeed(int i) {
        if (i == this.mLayoutManager.findFirstVisibleItemPosition() || i == this.mLayoutManager.findFirstCompletelyVisibleItemPosition()) {
            int i2 = this.mEffectItemPadding.padding;
            View findViewByPosition = this.mLayoutManager.findViewByPosition(i);
            if (i > 0 && findViewByPosition != null) {
                i2 = (this.mEffectItemPadding.padding * 2) + findViewByPosition.getWidth();
            }
            this.mLayoutManager.scrollToPositionWithOffset(Math.max(0, i), i2);
        } else if (i == this.mLayoutManager.findLastVisibleItemPosition() || i == this.mLayoutManager.findLastCompletelyVisibleItemPosition()) {
            this.mLayoutManager.scrollToPosition(Math.min(i + 1, this.mEffectItemAdapter.getItemCount() - 1));
        }
    }

    private void selectItem(int i) {
        if (i != -1) {
            this.mLastIndex = this.mCurrentIndex;
            this.mCurrentIndex = i;
            scrollIfNeed(i);
            notifyItemChanged(this.mLastIndex, this.mCurrentIndex);
        }
    }

    /* access modifiers changed from: private */
    /* access modifiers changed from: public */
    private void setIsAnimation(boolean z) {
        this.isAnimation = z;
    }

    private void setItemInCenter(int i) {
        this.mCurrentIndex = i;
        this.mIsShowIndex = i;
        int i2 = (this.mTotalWidth / 2) - (this.mHolderWidth / 2);
        this.mEffectItemAdapter.notifyDataSetChanged();
        this.mLayoutManager.scrollToPositionWithOffset(i, i2);
    }

    private void showSelected(ImageView imageView, int i) {
        if (isAdded()) {
            Canvas canvas = new Canvas();
            Bitmap decodeResource = BitmapFactory.decodeResource(getResources(), R.drawable.filter_item_selected_view);
            Bitmap decodeResource2 = BitmapFactory.decodeResource(getResources(), i);
            Bitmap createBitmap = Bitmap.createBitmap(decodeResource.getWidth(), decodeResource.getHeight(), Bitmap.Config.ARGB_8888);
            canvas.setBitmap(createBitmap);
            Paint paint = new Paint();
            paint.setFilterBitmap(false);
            canvas.drawBitmap(decodeResource, 0.0f, 0.0f, paint);
            paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
            canvas.drawBitmap(decodeResource2, 0.0f, 0.0f, paint);
            paint.setXfermode(null);
            imageView.setImageBitmap(createBitmap);
        }
    }

    private boolean supportsRealtimeEffect() {
        int i;
        return (!DataRepository.dataItemFeature()._l() || (i = this.mCurrentMode) == 180 || i == 162 || i == 169) ? false : true;
    }

    private void updateCurrentIndex() {
        CameraSettings.getShaderEffect();
        String componentValue = this.mComponentConfigFilter.getComponentValue(DataRepository.dataItemGlobal().getCurrentMode());
        int findIndexOfValue = this.mComponentConfigFilter.findIndexOfValue(componentValue);
        if (findIndexOfValue == -1) {
            String str = TAG;
            Log.w(str, "invalid filter " + componentValue);
            findIndexOfValue = 0;
        }
        setItemInCenter(findIndexOfValue);
    }

    /* access modifiers changed from: protected */
    @Override // com.android.camera.fragment.beauty.BaseBeautyFragment
    public View getAnimateView() {
        return this.mRecyclerView;
    }

    @Override // com.android.camera.fragment.EffectItemAdapter.IEffectItemListener
    public int getCurrentIndex() {
        return this.mCurrentIndex;
    }

    @Override // com.android.camera.fragment.EffectItemAdapter.IEffectItemListener
    public int getHolderHeight() {
        return this.mHolderHeight;
    }

    @Override // com.android.camera.fragment.EffectItemAdapter.IEffectItemListener
    public int getHolderWidth() {
        return this.mHolderWidth;
    }

    @Override // com.android.camera.fragment.EffectItemAdapter.IEffectItemListener
    public int getLastIndex() {
        return this.mLastIndex;
    }

    @Override // com.android.camera.fragment.EffectItemAdapter.IEffectItemListener
    public int getTextureHeight() {
        return this.mTextureHeight;
    }

    @Override // com.android.camera.fragment.EffectItemAdapter.IEffectItemListener
    public int getTextureOffsetX() {
        return this.mTextureOffsetX;
    }

    @Override // com.android.camera.fragment.EffectItemAdapter.IEffectItemListener
    public int getTextureOffsetY() {
        return this.mTextureOffsetY;
    }

    @Override // com.android.camera.fragment.EffectItemAdapter.IEffectItemListener
    public int getTextureWidth() {
        return this.mTextureWidth;
    }

    @Override // com.android.camera.fragment.EffectItemAdapter.IEffectItemListener
    public int getTotalWidth() {
        return this.mTotalWidth;
    }

    @Override // com.android.camera.fragment.EffectItemAdapter.IEffectItemListener
    public boolean isAnimation() {
        return this.isAnimation;
    }

    public void isShowAnimation(List<Completable> list) {
        if (list == null) {
            setIsAnimation(false);
        } else {
            setIsAnimation(true);
        }
    }

    public void onClick(View view) {
        Log.d(TAG, "onClick: ");
        if (this.mRecyclerView.isEnabled()) {
            ModeProtocol.CameraAction cameraAction = (ModeProtocol.CameraAction) ModeCoordinatorImpl.getInstance().getAttachProtocol(161);
            if (cameraAction == null || !cameraAction.isDoingAction()) {
                int intValue = ((Integer) view.getTag()).intValue();
                if (this.mCurrentIndex != intValue || !this.mIgnoreSameItemClick) {
                    setIsAnimation(false);
                    onItemSelected(intValue, true);
                }
            }
        }
    }

    @Override // androidx.fragment.app.Fragment
    @Nullable
    public View onCreateView(LayoutInflater layoutInflater, @Nullable ViewGroup viewGroup, Bundle bundle) {
        this.mCurrentMode = DataRepository.dataItemGlobal().getCurrentMode();
        View inflate = layoutInflater.inflate(R.layout.fragment_filter, viewGroup, false);
        initView(inflate);
        return inflate;
    }

    @Override // androidx.fragment.app.Fragment
    public void onPause() {
        super.onPause();
        ActivityBase activityBase = (ActivityBase) getActivity();
        if (activityBase != null) {
            activityBase.getCameraScreenNail().removeRequestListener(this);
        }
    }

    @Override // androidx.fragment.app.Fragment
    public void onResume() {
        super.onResume();
        ActivityBase activityBase = (ActivityBase) getActivity();
        if (activityBase != null) {
            activityBase.getCameraScreenNail().addRequestListener(this);
        }
    }

    @Override // com.android.camera.fragment.BaseViewPagerFragment, androidx.fragment.app.Fragment
    public void onViewCreated(View view, @Nullable Bundle bundle) {
        super.onViewCreated(view, bundle);
        updateCurrentIndex();
    }

    @Override // com.android.camera.fragment.beauty.BaseBeautyFragment
    public void provideRotateItem(List<View> list, int i) {
        super.provideRotateItem(list, i);
        if (this.mRecyclerView != null) {
            for (int i2 = 0; i2 < this.mRecyclerView.getChildCount(); i2++) {
                View childAt = this.mRecyclerView.getChildAt(i2);
                if (this.mSupportRealtimeEffect) {
                    if (c.Rm()) {
                        int displayRotation = Util.getDisplayRotation(getActivity());
                        ((TextureView) childAt.findViewById(R.id.effect_item_realtime)).setRotation((float) displayRotation);
                        EffectItemAdapter effectItemAdapter = this.mEffectItemAdapter;
                        if (effectItemAdapter != null) {
                            effectItemAdapter.setDisplayRotation(displayRotation);
                        }
                    }
                    list.add(childAt.findViewById(R.id.effect_item_text));
                } else {
                    list.add(childAt);
                }
            }
        }
        EffectItemAdapter effectItemAdapter2 = this.mEffectItemAdapter;
        if (effectItemAdapter2 != null) {
            effectItemAdapter2.setRotation(i);
            int findFirstVisibleItemPosition = this.mLayoutManager.findFirstVisibleItemPosition();
            int findLastVisibleItemPosition = this.mLayoutManager.findLastVisibleItemPosition();
            for (int i3 = 0; i3 < findFirstVisibleItemPosition; i3++) {
                this.mEffectItemAdapter.notifyItemChanged(i3);
            }
            while (true) {
                findLastVisibleItemPosition++;
                if (findLastVisibleItemPosition < this.mEffectItemAdapter.getItemCount()) {
                    this.mEffectItemAdapter.notifyItemChanged(findLastVisibleItemPosition);
                } else {
                    return;
                }
            }
        }
    }

    public void reInit() {
        setItemInCenter(this.mComponentConfigFilter.findIndexOfValue(this.mComponentConfigFilter.getComponentValue(this.mCurrentMode)));
    }

    @Override // com.android.camera.CameraScreenNail.RequestRenderListener
    public void requestRender() {
        EffectItemAdapter.EffectItemHolder effectItemHolder;
        for (int i = 0; i < this.mLayoutManager.getChildCount(); i++) {
            View childAt = this.mLayoutManager.getChildAt(i);
            if (!(childAt == null || (effectItemHolder = (EffectItemAdapter.EffectItemHolder) this.mRecyclerView.getChildViewHolder(childAt)) == null)) {
                effectItemHolder.requestRender();
            }
        }
    }

    @Override // com.android.camera.fragment.beauty.BaseBeautyFragment
    public void setDegree(int i) {
        super.setDegree(i);
        EffectItemAdapter effectItemAdapter = this.mEffectItemAdapter;
        if (effectItemAdapter != null) {
            effectItemAdapter.setRotation(i);
            if (c.Rm()) {
                this.mEffectItemAdapter.setDisplayRotation(Util.getDisplayRotation(getActivity()));
            }
            this.mEffectItemAdapter.notifyDataSetChanged();
        }
    }

    @Override // com.android.camera.NoClipChildrenLayout
    public void setNoClip(boolean z) {
        if (getView() != null && (getView() instanceof ViewGroup)) {
            ((ViewGroup) getView()).setClipChildren(!z);
        }
        if (getView() == null) {
            this.mTargetClipChildren = z;
        }
    }

    /* JADX WARNING: Removed duplicated region for block: B:13:0x003d  */
    /* JADX WARNING: Removed duplicated region for block: B:15:? A[RETURN, SYNTHETIC] */
    public void switchFilter(int i) {
        int i2;
        if (i == 3) {
            int i3 = this.mCurrentIndex;
            if (i3 > 0) {
                i2 = i3 - 1;
                if (i2 > -1) {
                }
            }
        } else if (i != 5) {
            Log.e(TAG, "unexpected gravity " + i);
        } else if (this.mCurrentIndex < this.mComponentConfigFilter.getItems().size() - 1) {
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

    public void updateFilterData() {
        ArrayList<FilterInfo> filterInfo = getFilterInfo();
        this.mComponentConfigFilter = DataRepository.dataItemRunning().getComponentConfigFilter();
        this.mComponentConfigFilter.mapToItems(filterInfo, this.mCurrentMode);
        this.mEffectItemAdapter.updateData(this.mComponentConfigFilter);
        updateCurrentIndex();
    }
}
