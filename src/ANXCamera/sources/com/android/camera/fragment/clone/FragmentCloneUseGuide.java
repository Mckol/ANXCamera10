package com.android.camera.fragment.clone;

import android.content.res.Resources;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.PagerSnapHelper;
import androidx.recyclerview.widget.RecyclerView;
import com.android.camera.R;
import com.android.camera.Util;
import com.android.camera.fragment.BaseFragment;
import com.android.camera.fragment.BaseFragmentDelegate;
import com.android.camera.log.Log;
import com.android.camera.protocol.ModeCoordinatorImpl;
import com.android.camera.protocol.ModeProtocol;
import com.android.camera.videoplayer.manager.PlayerItemChangeListener;
import com.android.camera.videoplayer.manager.SingleVideoPlayerManager;
import com.android.camera.videoplayer.manager.VideoPlayerManager;
import com.android.camera.videoplayer.meta.MetaData;
import com.android.camera.visibilityutils.calculator.DefaultSingleItemCalculatorCallback;
import com.android.camera.visibilityutils.calculator.ListItemsVisibilityCalculator;
import com.android.camera.visibilityutils.calculator.SingleListViewItemActiveCalculator;
import com.android.camera.visibilityutils.scroll_utils.ItemsPositionGetter;
import com.android.camera.visibilityutils.scroll_utils.RecyclerViewItemPositionGetter;
import com.xiaomi.fenshen.FenShenCam;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class FragmentCloneUseGuide extends BaseFragment implements ModeProtocol.HandleBackTrace {
    public static final String TAG = "CloneUseGuide";
    private ImageView mBackButton;
    private ItemsPositionGetter mItemsPositionGetter;
    private LinearLayoutManager mLayoutManager;
    private final List<BaseVideoItem> mList = new ArrayList();
    private RecyclerView mRecyclerView;
    private int mScrollState = 0;
    private final VideoPlayerManager<MetaData> mVideoPlayerManager = new SingleVideoPlayerManager(new PlayerItemChangeListener() {
        /* class com.android.camera.fragment.clone.FragmentCloneUseGuide.AnonymousClass1 */

        @Override // com.android.camera.videoplayer.manager.PlayerItemChangeListener
        public void onPlayerItemChanged(MetaData metaData) {
        }
    });
    private final ListItemsVisibilityCalculator mVideoVisibilityCalculator = new SingleListViewItemActiveCalculator(new DefaultSingleItemCalculatorCallback(), this.mList);

    private void initAdapter() {
        try {
            Resources resources = getResources();
            if (Config.getCloneMode() == FenShenCam.Mode.PHOTO) {
                this.mList.add(new GuideAssetVideoItem(getContext().getAssets().openFd("cn".equalsIgnoreCase(Locale.getDefault().getCountry()) ? "clone_photo_mode_use_guide.mp4" : "clone_photo_mode_use_guide_en.mp4"), this.mVideoPlayerManager, R.drawable.clone_guide_photo_cover, String.format(resources.getString(R.string.clone_guide_title1), 1), resources.getString(R.string.clone_guide_title2), resources.getString(R.string.clone_guide_tip1), String.format(resources.getQuantityString(R.plurals.clone_guide_tip2, 4), 4), resources.getString(R.string.clone_guide_tip3), resources.getString(R.string.clone_guide_tip4), false));
                this.mList.add(new GuideAssetVideoItem(null, this.mVideoPlayerManager, R.drawable.clone_guide_photo_samples_cover, String.format(resources.getString(R.string.clone_guide_title3), 2), resources.getString(R.string.clone_guide_title4), resources.getString(R.string.clone_guide_tip5), resources.getString(R.string.clone_guide_tip6), resources.getString(R.string.clone_guide_tip7), resources.getString(R.string.clone_guide_tip8), true));
            } else {
                this.mList.add(new GuideAssetVideoItem(getContext().getAssets().openFd("cn".equalsIgnoreCase(Locale.getDefault().getCountry()) ? "clone_video_mode_use_guide.mp4" : "clone_video_mode_use_guide_en.mp4"), this.mVideoPlayerManager, R.drawable.clone_guide_video_cover, String.format(resources.getString(R.string.clone_guide_title1), 1), resources.getString(R.string.clone_guide_video_title2), resources.getString(R.string.clone_guide_tip1), String.format(resources.getQuantityString(R.plurals.clone_guide_tip2, 2), 2), resources.getString(R.string.clone_guide_tip3), resources.getString(R.string.clone_guide_tip4), false));
                this.mList.add(new GuideAssetVideoItem(getContext().getAssets().openFd("clone_video_mode_samples.mp4"), this.mVideoPlayerManager, R.drawable.clone_guide_video_samples_cover, String.format(resources.getString(R.string.clone_guide_title3), 2), resources.getString(R.string.clone_guide_title4), resources.getString(R.string.clone_guide_tip5), resources.getString(R.string.clone_guide_tip6), resources.getString(R.string.clone_guide_tip7), resources.getString(R.string.clone_guide_tip8), true));
            }
        } catch (IOException e2) {
            Log.w(TAG, "openFd failed " + e2.getMessage());
        }
        this.mRecyclerView.setHasFixedSize(true);
        this.mLayoutManager = new LinearLayoutManager(getActivity());
        this.mLayoutManager.setOrientation(1);
        this.mRecyclerView.setLayoutManager(this.mLayoutManager);
        this.mRecyclerView.setAdapter(new VideoRecyclerViewAdapter(this.mVideoPlayerManager, getActivity(), this.mList));
        this.mRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            /* class com.android.camera.fragment.clone.FragmentCloneUseGuide.AnonymousClass3 */

            @Override // androidx.recyclerview.widget.RecyclerView.OnScrollListener
            public void onScrollStateChanged(RecyclerView recyclerView, int i) {
                FragmentCloneUseGuide.this.mScrollState = i;
                if (i == 0 && !FragmentCloneUseGuide.this.mList.isEmpty()) {
                    FragmentCloneUseGuide.this.mVideoVisibilityCalculator.onScrollStateIdle(FragmentCloneUseGuide.this.mItemsPositionGetter, FragmentCloneUseGuide.this.mLayoutManager.findFirstVisibleItemPosition(), FragmentCloneUseGuide.this.mLayoutManager.findLastVisibleItemPosition());
                }
            }

            @Override // androidx.recyclerview.widget.RecyclerView.OnScrollListener
            public void onScrolled(RecyclerView recyclerView, int i, int i2) {
                if (!FragmentCloneUseGuide.this.mList.isEmpty()) {
                    FragmentCloneUseGuide.this.mVideoVisibilityCalculator.onScroll(FragmentCloneUseGuide.this.mItemsPositionGetter, FragmentCloneUseGuide.this.mLayoutManager.findFirstVisibleItemPosition(), (FragmentCloneUseGuide.this.mLayoutManager.findLastVisibleItemPosition() - FragmentCloneUseGuide.this.mLayoutManager.findFirstVisibleItemPosition()) + 1, FragmentCloneUseGuide.this.mScrollState);
                }
            }
        });
        this.mItemsPositionGetter = new RecyclerViewItemPositionGetter(this.mLayoutManager, this.mRecyclerView);
        new PagerSnapHelper().attachToRecyclerView(this.mRecyclerView);
    }

    public /* synthetic */ void e(View view) {
        onBackEvent(6);
    }

    @Override // com.android.camera.fragment.BaseFragment
    public int getFragmentInto() {
        return BaseFragmentDelegate.FRAGMENT_CLONE_USE_GUIDE;
    }

    /* access modifiers changed from: protected */
    @Override // com.android.camera.fragment.BaseFragment
    public int getLayoutResourceId() {
        return R.layout.fragment_clone_use_guide;
    }

    /* access modifiers changed from: protected */
    @Override // com.android.camera.fragment.BaseFragment
    public void initView(View view) {
        Log.d(TAG, "initView");
        this.mRecyclerView = (RecyclerView) view.findViewById(R.id.clone_guide_recyclerView);
        this.mBackButton = (ImageView) view.findViewById(R.id.clone_guide_back);
        this.mBackButton.setRotation(90.0f);
        ViewGroup.MarginLayoutParams marginLayoutParams = (ViewGroup.MarginLayoutParams) this.mBackButton.getLayoutParams();
        marginLayoutParams.topMargin = Util.sTopMargin;
        marginLayoutParams.height = Util.sTopBarHeight;
        this.mBackButton.setOnClickListener(new b(this));
        initAdapter();
    }

    @Override // com.android.camera.protocol.ModeProtocol.HandleBackTrace
    public boolean onBackEvent(int i) {
        ((ModeProtocol.BaseDelegate) ModeCoordinatorImpl.getInstance().getAttachProtocol(160)).delegateEvent(23);
        return true;
    }

    @Override // androidx.fragment.app.Fragment
    public void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "onDestroy");
    }

    @Override // androidx.fragment.app.Fragment
    public void onPause() {
        super.onPause();
        Log.d(TAG, "onPause");
    }

    @Override // com.android.camera.fragment.BaseFragment, androidx.fragment.app.Fragment
    public void onResume() {
        super.onResume();
        Log.d(TAG, "onResume");
        if (!this.mList.isEmpty()) {
            this.mRecyclerView.post(new Runnable() {
                /* class com.android.camera.fragment.clone.FragmentCloneUseGuide.AnonymousClass2 */

                public void run() {
                    FragmentCloneUseGuide.this.mVideoVisibilityCalculator.onScrollStateIdle(FragmentCloneUseGuide.this.mItemsPositionGetter, FragmentCloneUseGuide.this.mLayoutManager.findFirstVisibleItemPosition(), FragmentCloneUseGuide.this.mLayoutManager.findLastVisibleItemPosition());
                }
            });
        }
    }

    @Override // com.android.camera.fragment.BaseFragment, androidx.fragment.app.Fragment
    public void onStart() {
        super.onStart();
        this.mVideoPlayerManager.resumeMediaPlayer();
    }

    @Override // com.android.camera.fragment.BaseFragment, androidx.fragment.app.Fragment
    public void onStop() {
        super.onStop();
        this.mVideoPlayerManager.resetMediaPlayer();
    }

    @Override // com.android.camera.fragment.BaseFragment, com.android.camera.animation.AnimationDelegate.AnimationResource
    public void provideRotateItem(List<View> list, int i) {
        super.provideRotateItem(list, i);
    }

    /* access modifiers changed from: protected */
    @Override // com.android.camera.fragment.BaseFragment
    public void register(ModeProtocol.ModeCoordinator modeCoordinator) {
        super.register(modeCoordinator);
        registerBackStack(modeCoordinator, this);
    }

    /* access modifiers changed from: protected */
    @Override // com.android.camera.fragment.BaseFragment
    public void unRegister(ModeProtocol.ModeCoordinator modeCoordinator) {
        super.unRegister(modeCoordinator);
        unRegisterBackStack(modeCoordinator, this);
    }
}
