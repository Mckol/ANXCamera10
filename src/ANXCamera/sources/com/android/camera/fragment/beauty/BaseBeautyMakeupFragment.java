package com.android.camera.fragment.beauty;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import androidx.annotation.Nullable;
import androidx.core.view.ViewCompat;
import androidx.core.view.ViewPropertyAnimatorListenerAdapter;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.android.camera.R;
import com.android.camera.ToastUtils;
import com.android.camera.Util;
import com.android.camera.data.data.TypeItem;
import com.android.camera.data.data.runing.ComponentRunningShine;
import com.android.camera.fragment.DefaultItemAnimator;
import com.android.camera.fragment.EffectItemAdapter;
import com.android.camera.protocol.ModeCoordinatorImpl;
import com.android.camera.protocol.ModeProtocol;
import java.util.ArrayList;
import java.util.List;

public abstract class BaseBeautyMakeupFragment extends BaseBeautyFragment {
    protected static final int EXTRA_CLEAR = 2;
    protected static final int EXTRA_NULL = -1;
    protected static final int EXTRA_RESET = 1;
    private static final String TAG = "BaseBeautyMakeup";
    protected int mAlphaElement;
    private List<TypeItem> mAugmentItemList;
    protected int mBetaElement;
    protected AdapterView.OnItemClickListener mClickListener;
    protected int mHeaderCustomWidth;
    private LinearLayout mHeaderRecyclerView;
    protected List<TypeItem> mItemList;
    private int mItemMargin;
    private int mItemWidth;
    private int mLastSelectedPosition = -1;
    protected MyLayoutManager mLayoutManager;
    protected MakeupSingleCheckAdapter mMakeupAdapter;
    private RecyclerView mMakeupItemList;
    private boolean mNeedScroll;
    private int mPositionFirstItem = 0;
    private int mPositionLastItem = 0;
    protected int mSelectedParam = 0;
    protected int mSelectedPosition = 0;
    private int mTotalWidth;

    public class MyLayoutManager extends LinearLayoutManager {
        private boolean isScrollEnabled = true;

        public MyLayoutManager(Context context) {
            super(context);
        }

        @Override // androidx.recyclerview.widget.RecyclerView.LayoutManager, androidx.recyclerview.widget.LinearLayoutManager
        public boolean canScrollHorizontally() {
            return this.isScrollEnabled && super.canScrollHorizontally();
        }

        @Override // androidx.recyclerview.widget.RecyclerView.LayoutManager, androidx.recyclerview.widget.LinearLayoutManager
        public void onLayoutChildren(RecyclerView.Recycler recycler, RecyclerView.State state) {
            try {
                super.onLayoutChildren(recycler, state);
            } catch (IndexOutOfBoundsException e2) {
                e2.printStackTrace();
            }
        }

        public void setScrollEnabled(boolean z) {
            this.isScrollEnabled = z;
        }
    }

    private void animateView(View view) {
        view.clearAnimation();
        view.setRotation(0.0f);
        ViewCompat.animate(view).rotation(360.0f).setDuration(500).setListener(new ViewPropertyAnimatorListenerAdapter() {
            /* class com.android.camera.fragment.beauty.BaseBeautyMakeupFragment.AnonymousClass2 */

            @Override // androidx.core.view.ViewPropertyAnimatorListenerAdapter, androidx.core.view.ViewPropertyAnimatorListener
            public void onAnimationEnd(View view) {
                super.onAnimationEnd(view);
                view.setRotation(0.0f);
            }
        }).start();
    }

    private void augmentItemList() {
        this.mAugmentItemList = new ArrayList();
        this.mPositionFirstItem = 0;
        if (hasAlpha()) {
            this.mAugmentItemList.add(initAlphaItem());
            this.mPositionFirstItem++;
        }
        if (hasBeta()) {
            this.mAugmentItemList.add(initBetaItem());
            this.mPositionFirstItem++;
        }
        for (TypeItem typeItem : this.mItemList) {
            this.mAugmentItemList.add(typeItem);
        }
        this.mPositionLastItem = (this.mPositionFirstItem + this.mItemList.size()) - 1;
        this.mSelectedPosition = this.mPositionFirstItem;
    }

    private void calcItemWidthAndNeedScroll() {
        this.mTotalWidth = getResources().getDisplayMetrics().widthPixels;
        int size = this.mAugmentItemList.size();
        if (size != 0) {
            int dimensionPixelSize = getResources().getDimensionPixelSize(R.dimen.beautycamera_makeup_item_width);
            int i = this.mTotalWidth;
            int integer = (int) (((float) (i - dimensionPixelSize)) / (((float) getResources().getInteger(R.integer.beauty_list_max_count)) + 0.5f));
            int i2 = dimensionPixelSize * 2;
            int max = Math.max((i - i2) / size, integer);
            if (max == integer) {
                this.mNeedScroll = true;
            } else {
                this.mNeedScroll = false;
                max = ((this.mTotalWidth - (this.mItemMargin * 2)) - i2) / size;
            }
            this.mItemWidth = max;
        }
    }

    private boolean hasAlpha() {
        return this.mAlphaElement != -1;
    }

    private boolean hasBeta() {
        return this.mBetaElement != -1;
    }

    private TypeItem<Integer> initAlphaItem() {
        TypeItem<Integer> typeItem = new TypeItem<>(-1, -1, (String) null, (String) null);
        int i = this.mAlphaElement;
        if (i == 1) {
            typeItem.mIconRes = R.drawable.icon_beauty_reset;
            typeItem.mDisplayNameRes = R.string.beauty_reset;
        } else if (i == 2) {
            typeItem.mIconRes = R.drawable.icon_beauty_reset;
            typeItem.mDisplayNameRes = R.string.face_beauty_close;
        }
        return typeItem;
    }

    private TypeItem<Integer> initBetaItem() {
        TypeItem<Integer> typeItem = new TypeItem<>(-1, -1, (String) null, (String) null);
        int i = this.mBetaElement;
        if (i == 1) {
            typeItem.mIconRes = R.drawable.icon_beauty_reset;
            typeItem.mDisplayNameRes = R.string.beauty_reset;
        } else if (i == 2) {
            typeItem.mIconRes = R.drawable.icon_beauty_clear;
            typeItem.mDisplayNameRes = R.string.beauty_clear;
        }
        return typeItem;
    }

    private final List<TypeItem> initItems() {
        ModeProtocol.MiBeautyProtocol miBeautyProtocol = (ModeProtocol.MiBeautyProtocol) ModeCoordinatorImpl.getInstance().getAttachProtocol(194);
        if (miBeautyProtocol != null) {
            return miBeautyProtocol.getSupportedBeautyItems(getShineType());
        }
        return null;
    }

    /* access modifiers changed from: private */
    /* access modifiers changed from: public */
    private void notifyItemChanged(int i, int i2) {
        if (i > -1) {
            this.mMakeupAdapter.notifyItemChanged(i, new Object());
        }
        if (i2 > -1) {
            this.mMakeupAdapter.notifyItemChanged(i2, new Object());
        }
    }

    /* access modifiers changed from: private */
    /* access modifiers changed from: public */
    private void onExtraClick(View view, int i) {
        int i2 = (!hasAlpha() || i != 0) ? (!hasBeta() || i != 1) ? -1 : this.mBetaElement : this.mAlphaElement;
        if (i2 == 1) {
            onResetClick();
        } else if (i2 == 2) {
            onClearClick();
        }
    }

    /* access modifiers changed from: private */
    /* access modifiers changed from: public */
    private boolean scrollIfNeed(int i) {
        int max = (i == this.mLayoutManager.findFirstVisibleItemPosition() || i == this.mLayoutManager.findFirstCompletelyVisibleItemPosition()) ? Math.max(0, i - 1) : (i == this.mLayoutManager.findLastVisibleItemPosition() || i == this.mLayoutManager.findLastCompletelyVisibleItemPosition()) ? Math.min(i + 1, this.mLayoutManager.getItemCount() - 1) : i;
        if (max == i) {
            return false;
        }
        this.mLayoutManager.scrollToPosition(max);
        return true;
    }

    private void setItemInCenter(int i) {
        this.mLayoutManager.scrollToPositionWithOffset(i, (this.mTotalWidth / 2) - (this.mItemWidth / 2));
    }

    /* access modifiers changed from: protected */
    @Override // com.android.camera.fragment.beauty.BaseBeautyFragment
    public View getAnimateView() {
        return this.mHeaderRecyclerView;
    }

    /* access modifiers changed from: protected */
    public abstract String getClassString();

    /* access modifiers changed from: protected */
    public int getListItemMargin() {
        if (!isNeedScroll()) {
            return getResources().getDimensionPixelSize(R.dimen.beauty_item_margin);
        }
        return 0;
    }

    /* access modifiers changed from: protected */
    @ComponentRunningShine.ShineType
    public abstract String getShineType();

    /* access modifiers changed from: protected */
    public abstract void initExtraType();

    /* access modifiers changed from: protected */
    public AdapterView.OnItemClickListener initOnItemClickListener() {
        return new AdapterView.OnItemClickListener() {
            /* class com.android.camera.fragment.beauty.BaseBeautyMakeupFragment.AnonymousClass3 */

            @Override // android.widget.AdapterView.OnItemClickListener
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long j) {
                Object tag = view.getTag();
                if (tag != null && (tag instanceof TypeItem)) {
                    BaseBeautyMakeupFragment.this.onAdapterItemClick((TypeItem) tag);
                }
            }
        };
    }

    /* access modifiers changed from: protected */
    public void initView(View view) {
        this.mHeaderRecyclerView = (LinearLayout) view.findViewById(R.id.header_recyclerView);
        this.mMakeupItemList = (RecyclerView) view.findViewById(R.id.makeup_item_list);
        this.mLayoutManager = new MyLayoutManager(getActivity());
        this.mLayoutManager.setOrientation(0);
        this.mLayoutManager.setScrollEnabled(true);
        this.mMakeupItemList.setLayoutManager(this.mLayoutManager);
        this.mMakeupItemList.setFocusable(false);
        this.mItemList = initItems();
        initExtraType();
        augmentItemList();
        calcItemWidthAndNeedScroll();
        if (!isNeedScroll()) {
            this.mLayoutManager.setScrollEnabled(false);
        }
        this.mMakeupAdapter = new MakeupSingleCheckAdapter(getActivity(), this.mAugmentItemList, this.mPositionFirstItem, this.mPositionLastItem);
        this.mMakeupAdapter.setRotation(this.mDegree);
        this.mClickListener = initOnItemClickListener();
        this.mMakeupAdapter.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            /* class com.android.camera.fragment.beauty.BaseBeautyMakeupFragment.AnonymousClass1 */

            @Override // android.widget.AdapterView.OnItemClickListener
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long j) {
                BaseBeautyMakeupFragment baseBeautyMakeupFragment = BaseBeautyMakeupFragment.this;
                baseBeautyMakeupFragment.mLastSelectedPosition = baseBeautyMakeupFragment.mSelectedPosition;
                BaseBeautyMakeupFragment baseBeautyMakeupFragment2 = BaseBeautyMakeupFragment.this;
                baseBeautyMakeupFragment2.mSelectedParam = i - baseBeautyMakeupFragment2.mPositionFirstItem;
                BaseBeautyMakeupFragment baseBeautyMakeupFragment3 = BaseBeautyMakeupFragment.this;
                baseBeautyMakeupFragment3.mSelectedPosition = i;
                if (i < baseBeautyMakeupFragment3.mPositionFirstItem || i > BaseBeautyMakeupFragment.this.mPositionLastItem) {
                    BaseBeautyMakeupFragment.this.onExtraClick(view, i);
                } else {
                    BaseBeautyMakeupFragment baseBeautyMakeupFragment4 = BaseBeautyMakeupFragment.this;
                    baseBeautyMakeupFragment4.mClickListener.onItemClick(adapterView, view, baseBeautyMakeupFragment4.mSelectedParam, j);
                }
                if (BaseBeautyMakeupFragment.this.isNeedScroll()) {
                    BaseBeautyMakeupFragment baseBeautyMakeupFragment5 = BaseBeautyMakeupFragment.this;
                    if (baseBeautyMakeupFragment5.scrollIfNeed(baseBeautyMakeupFragment5.mSelectedPosition)) {
                        BaseBeautyMakeupFragment baseBeautyMakeupFragment6 = BaseBeautyMakeupFragment.this;
                        baseBeautyMakeupFragment6.notifyItemChanged(baseBeautyMakeupFragment6.mLastSelectedPosition, BaseBeautyMakeupFragment.this.mSelectedPosition);
                    }
                }
            }
        });
        this.mMakeupAdapter.setSelectedPosition(this.mSelectedPosition);
        this.mMakeupItemList.setAdapter(this.mMakeupAdapter);
        this.mMakeupItemList.addItemDecoration(new EffectItemAdapter.EffectItemPadding(getContext()));
        DefaultItemAnimator defaultItemAnimator = new DefaultItemAnimator();
        defaultItemAnimator.setChangeDuration(150);
        defaultItemAnimator.setMoveDuration(150);
        defaultItemAnimator.setAddDuration(150);
        this.mMakeupItemList.setItemAnimator(defaultItemAnimator);
        this.mMakeupAdapter.notifyDataSetChanged();
        setItemInCenter(this.mSelectedPosition);
    }

    /* access modifiers changed from: protected */
    public boolean isNeedScroll() {
        return this.mNeedScroll;
    }

    /* access modifiers changed from: protected */
    public abstract void onAdapterItemClick(TypeItem typeItem);

    /* access modifiers changed from: protected */
    public abstract void onClearClick();

    @Override // androidx.fragment.app.Fragment
    @Nullable
    public View onCreateView(LayoutInflater layoutInflater, @Nullable ViewGroup viewGroup, Bundle bundle) {
        View inflate = layoutInflater.inflate(R.layout.fragment_beauty_makeup, viewGroup, false);
        initView(inflate);
        return inflate;
    }

    /* access modifiers changed from: protected */
    public abstract void onResetClick();

    /* access modifiers changed from: protected */
    @Override // com.android.camera.fragment.beauty.BaseBeautyFragment, com.android.camera.fragment.BaseViewPagerFragment
    public void onViewCreatedAndVisibleToUser(boolean z) {
        super.onViewCreatedAndVisibleToUser(z);
        List<TypeItem> list = this.mItemList;
        if (list != null && !list.isEmpty()) {
            TypeItem typeItem = this.mItemList.get(this.mSelectedParam);
            ModeProtocol.MakeupProtocol makeupProtocol = (ModeProtocol.MakeupProtocol) ModeCoordinatorImpl.getInstance().getAttachProtocol(180);
            if (makeupProtocol != null) {
                makeupProtocol.onMakeupItemSelected(typeItem.mKeyOrType, false);
            }
        }
    }

    @Override // com.android.camera.fragment.beauty.BaseBeautyFragment
    public void provideRotateItem(List<View> list, int i) {
        super.provideRotateItem(list, i);
        if (this.mMakeupItemList != null) {
            for (int i2 = 0; i2 < this.mMakeupItemList.getChildCount(); i2++) {
                list.add(this.mMakeupItemList.getChildAt(i2).findViewById(R.id.container_wrapper));
            }
        }
        MakeupSingleCheckAdapter makeupSingleCheckAdapter = this.mMakeupAdapter;
        if (makeupSingleCheckAdapter != null) {
            makeupSingleCheckAdapter.setRotation(i);
            int findFirstVisibleItemPosition = this.mLayoutManager.findFirstVisibleItemPosition();
            int findLastVisibleItemPosition = this.mLayoutManager.findLastVisibleItemPosition();
            for (int i3 = 0; i3 < findFirstVisibleItemPosition; i3++) {
                this.mMakeupAdapter.notifyItemChanged(i3);
            }
            while (true) {
                findLastVisibleItemPosition++;
                if (findLastVisibleItemPosition < this.mMakeupAdapter.getItemCount()) {
                    this.mMakeupAdapter.notifyItemChanged(findLastVisibleItemPosition);
                } else {
                    return;
                }
            }
        }
    }

    /* access modifiers changed from: protected */
    public void selectFirstItem() {
        this.mSelectedPosition = this.mPositionFirstItem;
        this.mSelectedParam = 0;
        this.mMakeupAdapter.setSelectedPosition(this.mSelectedPosition);
        this.mLayoutManager.scrollToPosition(this.mSelectedPosition);
        ModeProtocol.MakeupProtocol makeupProtocol = (ModeProtocol.MakeupProtocol) ModeCoordinatorImpl.getInstance().getAttachProtocol(180);
        if (makeupProtocol != null) {
            makeupProtocol.onMakeupItemSelected(this.mItemList.get(this.mSelectedParam).mKeyOrType, true);
        }
        int i = this.mLastSelectedPosition;
        int i2 = this.mSelectedPosition;
        if (i != i2) {
            notifyItemChanged(i, i2);
        }
    }

    @Override // com.android.camera.fragment.beauty.BaseBeautyFragment
    public void setDegree(int i) {
        super.setDegree(i);
        MakeupSingleCheckAdapter makeupSingleCheckAdapter = this.mMakeupAdapter;
        if (makeupSingleCheckAdapter != null) {
            makeupSingleCheckAdapter.setRotation(i);
            this.mMakeupAdapter.notifyDataSetChanged();
        }
    }

    /* access modifiers changed from: protected */
    public void setListPadding(RecyclerView recyclerView) {
        int i;
        if (recyclerView != null) {
            int dimensionPixelSize = getResources().getDimensionPixelSize(R.dimen.beauty_model_recycler_padding_left);
            if (!isNeedScroll()) {
                dimensionPixelSize = getResources().getDimensionPixelSize(R.dimen.beauty_padding_left);
                i = getResources().getDimensionPixelSize(R.dimen.beauty_padding_right);
            } else {
                i = 0;
            }
            recyclerView.setPadding(dimensionPixelSize, 0, i, 0);
        }
    }

    /* access modifiers changed from: protected */
    public void toast(String str) {
        int dimensionPixelSize = getResources().getDimensionPixelSize(R.dimen.beauty_reset_toast_margin_bottom);
        if (!Util.sIsFullScreenNavBarHidden) {
            dimensionPixelSize -= Util.sNavigationBarHeight;
        }
        ToastUtils.showToast(getActivity(), str, 80, 0, dimensionPixelSize - (getResources().getDimensionPixelSize(R.dimen.beauty_reset_toast_height) / 2));
    }
}
