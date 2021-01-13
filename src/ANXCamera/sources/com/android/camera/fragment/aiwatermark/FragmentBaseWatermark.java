package com.android.camera.fragment.aiwatermark;

import android.content.Context;
import android.graphics.Rect;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.android.camera.R;
import com.android.camera.Util;
import com.android.camera.aiwatermark.data.AbstractWatermarkData;
import com.android.camera.aiwatermark.data.WatermarkItem;
import com.android.camera.data.DataRepository;
import com.android.camera.data.data.runing.ComponentRunningAIWatermark;
import com.android.camera.fragment.DefaultItemAnimator;
import com.android.camera.fragment.aiwatermark.adapter.WatermarkAdapter;
import com.android.camera.fragment.beauty.BaseBeautyFragment;
import java.util.List;

public abstract class FragmentBaseWatermark extends BaseBeautyFragment implements View.OnClickListener {
    public static final int FRAGMENT_INFO = 1048566;
    private static final String TAG = "FragmentBaseWatermark";
    private ComponentRunningAIWatermark mComponentRunningAIWatermark = DataRepository.dataItemRunning().getComponentRunningAIWatermark();
    protected AbstractWatermarkData mData = null;
    private WatermarkItemPadding mEffectItemPadding;
    protected boolean mForceUpdateSelected = false;
    private int mHolderWidth;
    private boolean mIsFixedZeroDegree = this.mComponentRunningAIWatermark.isFixedOrientation();
    private LinearLayoutManager mLayoutManager = null;
    private RecyclerView mRecyclerView;
    private int mTotalWidth;
    private WatermarkAdapter mWatermarkAdapter;

    public static class WatermarkItemPadding extends RecyclerView.ItemDecoration {
        protected boolean mIsRTL = false;
        protected int mPadding = 0;
        protected int mTopPadding = 0;

        public WatermarkItemPadding(Context context) {
            this.mPadding = context.getResources().getDimensionPixelSize(R.dimen.beautycamera_makeup_item_margin);
            this.mTopPadding = context.getResources().getDimensionPixelSize(R.dimen.wm_item_top_padding);
            this.mIsRTL = Util.isLayoutRTL(context);
        }

        @Override // androidx.recyclerview.widget.RecyclerView.ItemDecoration
        public void getItemOffsets(Rect rect, View view, RecyclerView recyclerView, RecyclerView.State state) {
            int childPosition = recyclerView.getChildPosition(view);
            int i = 0;
            if (this.mIsRTL) {
                int i2 = this.mPadding;
                int i3 = this.mTopPadding;
                if (childPosition == 0) {
                    i = i2;
                }
                rect.set(i2, i3, i, this.mPadding);
                return;
            }
            if (childPosition == 0) {
                i = this.mPadding;
            }
            int i4 = this.mTopPadding;
            int i5 = this.mPadding;
            rect.set(i, i4, i5, i5);
        }
    }

    FragmentBaseWatermark() {
    }

    private void initView(View view) {
        this.mRecyclerView = (RecyclerView) view.findViewById(R.id.watermark_list);
        List<WatermarkItem> watermarkList = getWatermarkList();
        Context context = getContext();
        this.mLayoutManager = new LinearLayoutManager(context);
        int i = 0;
        this.mLayoutManager.setOrientation(0);
        int size = watermarkList.size();
        String currentKey = this.mComponentRunningAIWatermark.getCurrentKey();
        while (true) {
            if (i >= size) {
                i = -1;
                break;
            } else if (TextUtils.equals(watermarkList.get(i).getKey(), currentKey)) {
                break;
            } else {
                i++;
            }
        }
        this.mWatermarkAdapter = new WatermarkAdapter(context, this.mLayoutManager, i, watermarkList);
        this.mWatermarkAdapter.setRotation(this.mDegree);
        this.mRecyclerView.setLayoutManager(this.mLayoutManager);
        this.mRecyclerView.setAdapter(this.mWatermarkAdapter);
        if (this.mEffectItemPadding == null) {
            this.mEffectItemPadding = new WatermarkItemPadding(getContext());
        }
        this.mRecyclerView.addItemDecoration(this.mEffectItemPadding);
        this.mTotalWidth = context.getResources().getDisplayMetrics().widthPixels;
        this.mHolderWidth = context.getResources().getDimensionPixelSize(R.dimen.wm_item_width);
        if (i >= 0) {
            setItemInCenter(i);
        }
        DefaultItemAnimator defaultItemAnimator = new DefaultItemAnimator();
        defaultItemAnimator.setChangeDuration(150);
        defaultItemAnimator.setMoveDuration(150);
        defaultItemAnimator.setAddDuration(150);
        this.mRecyclerView.setItemAnimator(defaultItemAnimator);
    }

    private void setItemInCenter(int i) {
        this.mLayoutManager.scrollToPositionWithOffset(i, (this.mTotalWidth / 2) - (this.mHolderWidth / 2));
    }

    /* access modifiers changed from: protected */
    @Override // com.android.camera.fragment.beauty.BaseBeautyFragment
    public View getAnimateView() {
        return this.mRecyclerView;
    }

    public abstract List<WatermarkItem> getWatermarkList();

    public void onClick(View view) {
    }

    @Override // androidx.fragment.app.Fragment
    @Nullable
    public View onCreateView(@NonNull LayoutInflater layoutInflater, @Nullable ViewGroup viewGroup, @Nullable Bundle bundle) {
        View inflate = layoutInflater.inflate(R.layout.fragment_watermark_rv, viewGroup, false);
        initView(inflate);
        return inflate;
    }

    /* access modifiers changed from: protected */
    @Override // com.android.camera.fragment.beauty.BaseBeautyFragment, com.android.camera.fragment.BaseViewPagerFragment
    public void onViewCreatedAndVisibleToUser(boolean z) {
        WatermarkAdapter watermarkAdapter;
        super.onViewCreatedAndVisibleToUser(z);
        if (!z && (watermarkAdapter = this.mWatermarkAdapter) != null) {
            if (this.mForceUpdateSelected || watermarkAdapter.getSelectedIndex() >= 0) {
                List<WatermarkItem> items = this.mWatermarkAdapter.getItems();
                int size = items.size();
                int i = -1;
                int i2 = 0;
                while (true) {
                    if (i2 >= size) {
                        break;
                    } else if (TextUtils.equals(items.get(i2).getKey(), this.mComponentRunningAIWatermark.getCurrentKey())) {
                        i = i2;
                        break;
                    } else {
                        i2++;
                    }
                }
                this.mWatermarkAdapter.onSelected(i, false);
            }
        }
    }

    @Override // com.android.camera.fragment.beauty.BaseBeautyFragment
    public void provideRotateItem(List<View> list, int i) {
        if (this.mIsFixedZeroDegree) {
            i = 0;
        }
        super.provideRotateItem(list, i);
        if (this.mRecyclerView != null && !this.mIsFixedZeroDegree) {
            for (int i2 = 0; i2 < this.mRecyclerView.getChildCount(); i2++) {
                list.add(this.mRecyclerView.getChildAt(i2));
            }
        }
        WatermarkAdapter watermarkAdapter = this.mWatermarkAdapter;
        if (watermarkAdapter != null) {
            watermarkAdapter.setRotation(i);
            int findFirstVisibleItemPosition = this.mLayoutManager.findFirstVisibleItemPosition();
            int findLastVisibleItemPosition = this.mLayoutManager.findLastVisibleItemPosition();
            for (int i3 = 0; i3 < findFirstVisibleItemPosition; i3++) {
                this.mWatermarkAdapter.notifyItemChanged(i3);
            }
            while (true) {
                findLastVisibleItemPosition++;
                if (findLastVisibleItemPosition < this.mWatermarkAdapter.getItemCount()) {
                    this.mWatermarkAdapter.notifyItemChanged(findLastVisibleItemPosition);
                } else {
                    return;
                }
            }
        }
    }

    @Override // com.android.camera.fragment.beauty.BaseBeautyFragment
    public void setDegree(int i) {
        if (this.mIsFixedZeroDegree) {
            i = 0;
        }
        super.setDegree(i);
        WatermarkAdapter watermarkAdapter = this.mWatermarkAdapter;
        if (watermarkAdapter != null) {
            watermarkAdapter.setRotation(i);
            this.mWatermarkAdapter.notifyDataSetChanged();
        }
    }
}
