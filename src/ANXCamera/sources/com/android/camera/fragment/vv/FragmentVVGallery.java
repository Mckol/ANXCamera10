package com.android.camera.fragment.vv;

import android.content.Context;
import android.graphics.Rect;
import android.view.View;
import androidx.core.view.ViewCompat;
import androidx.recyclerview.widget.RecyclerView;
import com.android.camera.R;
import com.android.camera.Util;
import com.android.camera.data.DataRepository;
import com.android.camera.data.observeable.VlogViewModel;
import com.android.camera.fragment.BaseFragment;
import com.android.camera.fragment.BaseFragmentDelegate;
import com.android.camera.fragment.DefaultItemAnimator;
import com.android.camera.fragment.FragmentUtils;
import com.android.camera.fragment.beauty.LinearLayoutManagerWrapper;
import com.android.camera.protocol.ModeCoordinatorImpl;
import com.android.camera.protocol.ModeProtocol;
import com.android.camera.statistic.CameraStatUtils;
import io.reactivex.Completable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;
import java.util.List;

public class FragmentVVGallery extends BaseFragment implements View.OnClickListener, ModeProtocol.LiveVVChooser, ModeProtocol.HandleBackTrace {
    private final CompositeDisposable mDisposable = new CompositeDisposable();
    private VVGalleryAdapter mGalleryAdapter;
    private int mHolderWidth;
    private LinearLayoutManagerWrapper mLayoutManager;
    private int mPreviewIndex = -1;
    private View mProgressView;
    private RecyclerView mRecyclerView;
    private View mRecyclerViewLayout;
    private ResourceSelectedListener mResourceSelectedListener;
    private VVItem mSelectedItem;
    private int mTotalWidth;
    private VVList mVVList;

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

    private void initList() {
        if (isAdded()) {
            VVItem currentVVItem = DataRepository.dataItemLive().getCurrentVVItem();
            if (currentVVItem != null) {
                this.mPreviewIndex = currentVVItem.index;
            }
            DataRepository.dataItemLive().setVVVersion(this.mVVList.version);
            this.mResourceSelectedListener.onResourceReady();
            int i = this.mPreviewIndex;
            if (i < 0 || i >= this.mVVList.getSize()) {
                this.mResourceSelectedListener.onResourceSelected((VVItem) this.mVVList.getItem(0));
            } else {
                this.mResourceSelectedListener.onResourceSelected((VVItem) this.mVVList.getItem(this.mPreviewIndex));
            }
            this.mRecyclerViewLayout.setVisibility(0);
            this.mProgressView.setVisibility(8);
            this.mLayoutManager = new LinearLayoutManagerWrapper(getContext(), "vv_gallery");
            this.mLayoutManager.setOrientation(0);
            EffectItemPadding effectItemPadding = new EffectItemPadding(getContext());
            this.mGalleryAdapter = new VVGalleryAdapter(this.mVVList, this.mLayoutManager, this.mPreviewIndex, this, this.mResourceSelectedListener, effectItemPadding);
            this.mRecyclerView.setLayoutManager(this.mLayoutManager);
            this.mRecyclerView.addItemDecoration(effectItemPadding);
            this.mRecyclerView.setAdapter(this.mGalleryAdapter);
            int i2 = this.mPreviewIndex;
            if (i2 >= 0) {
                setItemInCenter(i2);
            }
            DefaultItemAnimator defaultItemAnimator = new DefaultItemAnimator();
            defaultItemAnimator.setChangeDuration(150);
            defaultItemAnimator.setMoveDuration(150);
            defaultItemAnimator.setAddDuration(150);
            this.mRecyclerView.setItemAnimator(defaultItemAnimator);
        }
    }

    private void loadItemList() {
        this.mResourceSelectedListener = new ResourceSelectedListener() {
            /* class com.android.camera.fragment.vv.FragmentVVGallery.AnonymousClass1 */

            @Override // com.android.camera.fragment.vv.ResourceSelectedListener
            public void onResourceReady() {
            }

            @Override // com.android.camera.fragment.vv.ResourceSelectedListener
            public void onResourceSelected(VVItem vVItem) {
                FragmentVVGallery.this.mSelectedItem = vVItem;
            }
        };
        this.mVVList = ((VlogViewModel) DataRepository.dataItemObservable().get(VlogViewModel.class)).getVVList();
        if (this.mVVList != null) {
            initList();
            return;
        }
        this.mRecyclerViewLayout.setVisibility(8);
        this.mProgressView.setVisibility(0);
        this.mDisposable.add(((VlogViewModel) DataRepository.dataItemObservable().get(VlogViewModel.class)).getVVListObservable().subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new b(this), a.INSTANCE));
    }

    private void setItemInCenter(int i) {
        this.mLayoutManager.scrollToPositionWithOffset(i, (this.mTotalWidth / 2) - (this.mHolderWidth / 2));
    }

    private void transformToPreview(int i, View view) {
        FragmentVVPreview fragmentVVPreview = new FragmentVVPreview();
        fragmentVVPreview.setPreviewData(i, this.mVVList);
        fragmentVVPreview.setResourceSelectedListener(this.mResourceSelectedListener);
        fragmentVVPreview.registerProtocol();
        getFragmentManager().beginTransaction().addSharedElement(view, ViewCompat.getTransitionName(view)).add(R.id.bottom_beauty, fragmentVVPreview, fragmentVVPreview.getFragmentTag()).hide(this).commitAllowingStateLoss();
        ModeProtocol.BaseDelegate baseDelegate = (ModeProtocol.BaseDelegate) ModeCoordinatorImpl.getInstance().getAttachProtocol(160);
        if (baseDelegate != null) {
            baseDelegate.updateCurrentFragments(R.id.bottom_beauty, fragmentVVPreview.getFragmentInto(), 4);
        }
    }

    public /* synthetic */ void a(VVList vVList) throws Exception {
        this.mVVList = vVList;
        initList();
    }

    @Override // com.android.camera.fragment.BaseFragment
    public int getFragmentInto() {
        return BaseFragmentDelegate.FRAGMENT_VV_GALLERY;
    }

    /* access modifiers changed from: protected */
    @Override // com.android.camera.fragment.BaseFragment
    public int getLayoutResourceId() {
        return R.layout.fragment_vv_gallery;
    }

    @Override // com.android.camera.protocol.ModeProtocol.LiveVVChooser
    public void hide() {
        FragmentUtils.removeFragmentByTag(getFragmentManager(), String.valueOf((int) BaseFragmentDelegate.FRAGMENT_VV_GALLERY));
        FragmentUtils.removeFragmentByTag(getFragmentManager(), String.valueOf((int) BaseFragmentDelegate.FRAGMENT_VV_PREVIEW));
    }

    /* access modifiers changed from: protected */
    @Override // com.android.camera.fragment.BaseFragment
    public void initView(View view) {
        Util.alignPopupBottom(view);
        this.mProgressView = view.findViewById(R.id.vv_updating);
        this.mRecyclerViewLayout = view.findViewById(R.id.vv_list_layout);
        this.mRecyclerView = (RecyclerView) view.findViewById(R.id.vv_list);
        this.mRecyclerView.setFocusable(false);
        Context context = getContext();
        this.mTotalWidth = context.getResources().getDisplayMetrics().widthPixels;
        this.mHolderWidth = context.getResources().getDimensionPixelSize(R.dimen.vv_list_item_image_width);
        loadItemList();
    }

    @Override // com.android.camera.protocol.ModeProtocol.LiveVVChooser
    public boolean isPreviewShow() {
        FragmentVVPreview fragmentVVPreview;
        return isShow() && (fragmentVVPreview = (FragmentVVPreview) FragmentUtils.getFragmentByTag(getFragmentManager(), String.valueOf(BaseFragmentDelegate.FRAGMENT_VV_PREVIEW))) != null && fragmentVVPreview.isVisible();
    }

    @Override // com.android.camera.protocol.ModeProtocol.LiveVVChooser
    public boolean isShow() {
        return isAdded() && getView().getVisibility() == 0;
    }

    @Override // com.android.camera.protocol.ModeProtocol.HandleBackTrace
    public boolean onBackEvent(int i) {
        if (!isVisible()) {
        }
        return false;
    }

    public void onClick(View view) {
        transformToPreview(((Integer) view.getTag()).intValue(), view.findViewById(R.id.vv_gallery_item_image));
    }

    @Override // androidx.fragment.app.Fragment
    public void onHiddenChanged(boolean z) {
        int i;
        VVGalleryAdapter vVGalleryAdapter;
        super.onHiddenChanged(z);
        if (!z && (i = this.mPreviewIndex) != -1 && (vVGalleryAdapter = this.mGalleryAdapter) != null) {
            vVGalleryAdapter.onSelected(i, null, false);
            setItemInCenter(this.mPreviewIndex);
            this.mPreviewIndex = -1;
        }
    }

    @Override // com.android.camera.fragment.BaseFragment, androidx.fragment.app.Fragment
    public void onStop() {
        super.onStop();
        this.mDisposable.clear();
    }

    @Override // com.android.camera.fragment.BaseFragment, com.android.camera.animation.AnimationDelegate.AnimationResource
    public void provideAnimateElement(int i, List<Completable> list, int i2) {
        super.provideAnimateElement(i, list, i2);
        if (i != 209) {
            hide();
        }
    }

    /* access modifiers changed from: protected */
    @Override // com.android.camera.fragment.BaseFragment
    public void register(ModeProtocol.ModeCoordinator modeCoordinator) {
        super.register(modeCoordinator);
        modeCoordinator.attachProtocol(229, this);
        registerBackStack(modeCoordinator, this);
    }

    public void setPreviewData(int i) {
        this.mPreviewIndex = i;
    }

    @Override // com.android.camera.protocol.ModeProtocol.LiveVVChooser
    public void show(int i) {
    }

    @Override // com.android.camera.protocol.ModeProtocol.LiveVVChooser
    public void startShot() {
        ModeProtocol.ConfigChanges configChanges;
        if (this.mSelectedItem != null && (configChanges = (ModeProtocol.ConfigChanges) ModeCoordinatorImpl.getInstance().getAttachProtocol(164)) != null) {
            CameraStatUtils.trackVVStartClick(this.mSelectedItem.name, isPreviewShow());
            configChanges.configLiveVV(this.mSelectedItem, true, false);
        }
    }

    /* access modifiers changed from: protected */
    @Override // com.android.camera.fragment.BaseFragment
    public void unRegister(ModeProtocol.ModeCoordinator modeCoordinator) {
        super.unRegister(modeCoordinator);
        modeCoordinator.detachProtocol(229, this);
        unRegisterBackStack(modeCoordinator, this);
    }
}
