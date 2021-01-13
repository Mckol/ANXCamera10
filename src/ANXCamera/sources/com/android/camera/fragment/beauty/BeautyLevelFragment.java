package com.android.camera.fragment.beauty;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.android.camera.CameraSettings;
import com.android.camera.R;
import com.android.camera.Util;
import com.android.camera.constant.BeautyConstant;
import com.android.camera.fragment.beauty.SingleCheckAdapter;
import com.android.camera.protocol.ModeCoordinatorImpl;
import com.android.camera.protocol.ModeProtocol;
import com.android.camera.statistic.CameraStatUtils;
import java.util.ArrayList;
import java.util.List;

public class BeautyLevelFragment extends BaseBeautyFragment {
    private MyLayoutManager mLayoutManager;
    private RecyclerView mLevelItemList;
    private SingleCheckAdapter mSingleCheckAdapter;

    public class MyLayoutManager extends LinearLayoutManager {
        public MyLayoutManager(Context context) {
            super(context);
        }
    }

    private void initView(View view) {
        this.mLevelItemList = (RecyclerView) view.findViewById(R.id.beauty_level_item_list);
        this.mLayoutManager = new MyLayoutManager(getActivity());
        this.mLayoutManager.setOrientation(0);
        this.mLevelItemList.setLayoutManager(this.mLayoutManager);
        this.mLevelItemList.setFocusable(false);
        List<SingleCheckAdapter.LevelItem> initBeautyItems = initBeautyItems();
        int provideItemHorizontalMargin = provideItemHorizontalMargin();
        ((ViewGroup.MarginLayoutParams) this.mLevelItemList.getLayoutParams()).setMarginStart((getResources().getDisplayMetrics().widthPixels - ((getResources().getDimensionPixelSize(R.dimen.beautycamera_beauty_level_item_size) + (provideItemHorizontalMargin * 2)) * initBeautyItems.size())) / 2);
        this.mSingleCheckAdapter = new SingleCheckAdapter(getActivity(), initBeautyItems, provideItemHorizontalMargin);
        this.mSingleCheckAdapter.setOnItemClickListener(initOnItemClickListener());
        this.mSingleCheckAdapter.setSelectedPosition(beautyLevelToPosition(CameraSettings.getBeautyShowLevel(), initBeautyItems.size() - 1));
        this.mLevelItemList.setAdapter(this.mSingleCheckAdapter);
        this.mSingleCheckAdapter.notifyDataSetChanged();
    }

    /* access modifiers changed from: private */
    /* access modifiers changed from: public */
    private void onLevelClick(AdapterView<?> adapterView, View view, int i, long j) {
        boolean z = !BeautyConstant.LEVEL_CLOSE.equals(CameraSettings.getFaceBeautifyLevel());
        ModeProtocol.BottomMenuProtocol bottomMenuProtocol = (ModeProtocol.BottomMenuProtocol) ModeCoordinatorImpl.getInstance().getAttachProtocol(197);
        if (bottomMenuProtocol != null) {
            if (!z && i > 0) {
                bottomMenuProtocol.animateShineBeauty(false);
            } else if (z && i == 0) {
                bottomMenuProtocol.animateShineBeauty(true);
            }
        }
        CameraStatUtils.trackBeautySwitchChanged(String.valueOf(i));
        CameraSettings.setFaceBeautyLevel(i);
        ShineHelper.onBeautyChanged();
    }

    /* access modifiers changed from: protected */
    public int beautyLevelToPosition(int i, int i2) {
        return Util.clamp(i, 0, i2);
    }

    /* access modifiers changed from: protected */
    @Override // com.android.camera.fragment.beauty.BaseBeautyFragment
    public View getAnimateView() {
        return this.mLevelItemList;
    }

    /* access modifiers changed from: protected */
    public List<SingleCheckAdapter.LevelItem> initBeautyItems() {
        String[] stringArray = getResources().getStringArray(R.array.beauty_level_list);
        ArrayList arrayList = new ArrayList();
        arrayList.add(new SingleCheckAdapter.LevelItem((int) R.drawable.ic_vector_beauty_level_none));
        arrayList.add(new SingleCheckAdapter.LevelItem(stringArray[0]));
        arrayList.add(new SingleCheckAdapter.LevelItem(stringArray[1]));
        arrayList.add(new SingleCheckAdapter.LevelItem(stringArray[2]));
        arrayList.add(new SingleCheckAdapter.LevelItem(stringArray[3]));
        arrayList.add(new SingleCheckAdapter.LevelItem(stringArray[4]));
        return arrayList;
    }

    /* access modifiers changed from: protected */
    public AdapterView.OnItemClickListener initOnItemClickListener() {
        return new AdapterView.OnItemClickListener() {
            /* class com.android.camera.fragment.beauty.BeautyLevelFragment.AnonymousClass1 */

            @Override // android.widget.AdapterView.OnItemClickListener
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long j) {
                BeautyLevelFragment.this.onLevelClick(adapterView, view, i, j);
            }
        };
    }

    @Override // androidx.fragment.app.Fragment
    @Nullable
    public View onCreateView(LayoutInflater layoutInflater, @Nullable ViewGroup viewGroup, Bundle bundle) {
        View inflate = layoutInflater.inflate(R.layout.fragment_beauty_level, viewGroup, false);
        initView(inflate);
        return inflate;
    }

    /* access modifiers changed from: protected */
    public int provideItemHorizontalMargin() {
        return getResources().getDimensionPixelSize(R.dimen.beautycamera_beauty_level_item_margin);
    }

    @Override // com.android.camera.fragment.beauty.BaseBeautyFragment
    public void provideRotateItem(List<View> list, int i) {
        super.provideRotateItem(list, i);
        if (this.mLevelItemList != null) {
            for (int i2 = 0; i2 < this.mLevelItemList.getChildCount(); i2++) {
                list.add(this.mLevelItemList.getChildAt(i2));
            }
        }
        SingleCheckAdapter singleCheckAdapter = this.mSingleCheckAdapter;
        if (singleCheckAdapter != null) {
            singleCheckAdapter.setRotation(i);
            int findFirstVisibleItemPosition = this.mLayoutManager.findFirstVisibleItemPosition();
            int findLastVisibleItemPosition = this.mLayoutManager.findLastVisibleItemPosition();
            for (int i3 = 0; i3 < findFirstVisibleItemPosition; i3++) {
                this.mSingleCheckAdapter.notifyItemChanged(i3);
            }
            while (true) {
                findLastVisibleItemPosition++;
                if (findLastVisibleItemPosition < this.mSingleCheckAdapter.getItemCount()) {
                    this.mSingleCheckAdapter.notifyItemChanged(findLastVisibleItemPosition);
                } else {
                    return;
                }
            }
        }
    }

    @Override // com.android.camera.fragment.beauty.BaseBeautyFragment
    public void setDegree(int i) {
        super.setDegree(i);
        SingleCheckAdapter singleCheckAdapter = this.mSingleCheckAdapter;
        if (singleCheckAdapter != null) {
            singleCheckAdapter.setRotation(i);
            this.mSingleCheckAdapter.notifyDataSetChanged();
        }
    }

    public void setEnableClick(boolean z) {
        SingleCheckAdapter singleCheckAdapter = this.mSingleCheckAdapter;
        if (singleCheckAdapter != null) {
            singleCheckAdapter.setEnableClick(z);
        }
    }

    @Override // androidx.fragment.app.Fragment, com.android.camera.fragment.BaseViewPagerFragment
    public void setUserVisibleHint(boolean z) {
        super.setUserVisibleHint(z);
        if (z) {
            SingleCheckAdapter singleCheckAdapter = this.mSingleCheckAdapter;
            if (singleCheckAdapter != null) {
                singleCheckAdapter.setEnableClick(true);
                return;
            }
            return;
        }
        SingleCheckAdapter singleCheckAdapter2 = this.mSingleCheckAdapter;
        if (singleCheckAdapter2 != null) {
            singleCheckAdapter2.setEnableClick(false);
        }
    }
}
