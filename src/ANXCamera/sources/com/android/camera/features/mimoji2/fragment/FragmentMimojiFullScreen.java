package com.android.camera.features.mimoji2.fragment;

import android.app.AlertDialog;
import android.content.ActivityNotFoundException;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ResolveInfo;
import android.graphics.Bitmap;
import android.graphics.Rect;
import android.net.Uri;
import android.os.Handler;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewStub;
import android.view.animation.Animation;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.Space;
import android.widget.TextView;
import androidx.core.view.ViewCompat;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.RecyclerView;
import com.airbnb.lottie.LottieAnimationView;
import com.android.camera.ActivityBase;
import com.android.camera.R;
import com.android.camera.RotateDialogController;
import com.android.camera.Util;
import com.android.camera.animation.FolmeUtils;
import com.android.camera.animation.type.AlphaInOnSubscribe;
import com.android.camera.animation.type.AlphaOutOnSubscribe;
import com.android.camera.animation.type.SlideInOnSubscribe;
import com.android.camera.animation.type.SlideOutOnSubscribe;
import com.android.camera.constant.ColorConstant;
import com.android.camera.constant.ShareConstant;
import com.android.camera.customization.BGTintTextView;
import com.android.camera.customization.TintColor;
import com.android.camera.data.DataRepository;
import com.android.camera.features.mimoji2.fragment.bottomlist.FragmentMimojiBottomList;
import com.android.camera.features.mimoji2.fragment.edit.FragmentMimojiEdit2;
import com.android.camera.features.mimoji2.module.protocol.MimojiModeProtocol;
import com.android.camera.features.mimoji2.utils.MimojiViewUtil;
import com.android.camera.features.mimoji2.widget.helper.MimojiStatusManager2;
import com.android.camera.fragment.BaseFragment;
import com.android.camera.fragment.FragmentUtils;
import com.android.camera.fragment.RecyclerAdapterWrapper;
import com.android.camera.fragment.beauty.LinearLayoutManagerWrapper;
import com.android.camera.fragment.fullscreen.ShareAdapter;
import com.android.camera.fragment.fullscreen.ShareInfo;
import com.android.camera.log.Log;
import com.android.camera.module.impl.component.FileUtils;
import com.android.camera.protocol.ModeCoordinatorImpl;
import com.android.camera.protocol.ModeProtocol;
import com.android.camera.statistic.CameraStatUtils;
import com.android.camera.statistic.MistatsConstants;
import com.android.camera.storage.Storage;
import com.android.camera.ui.CameraSnapView;
import com.android.camera.ui.ColorImageView;
import com.android.camera.ui.TextureVideoView;
import com.android.camera.ui.drawable.snap.PaintConditionReferred;
import io.reactivex.Completable;
import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import miui.view.animation.QuarticEaseInInterpolator;
import miui.view.animation.QuarticEaseOutInterpolator;

public class FragmentMimojiFullScreen extends BaseFragment implements MimojiModeProtocol.MimojiFullScreenProtocol, ModeProtocol.HandleBackTrace, View.OnClickListener {
    public static final int FRAGMENT_INFO = 65523;
    private static final String TAG = "MIMOJIFullScreen";
    private Runnable mAlertTopHintHideRunnable = new a(this);
    private ViewGroup mBottomActionView;
    private ViewGroup mBottomLayout;
    private ViewGroup mBottomTimbreLayout;
    private CameraSnapView mCameraSnapView;
    private ProgressBar mCancelProgress;
    private ImageView mCombineProgress;
    private ProgressBar mConcatProgress;
    private AlertDialog mExitDialog;
    private FragmentMimojiBottomList mFragmentMimojiBottomList;
    private FragmentMimojiEdit2 mFragmentMimojiEdit2;
    private Handler mHandler = new Handler();
    private boolean mIsOnShare;
    private View mMImojiViewLayout;
    private ColorImageView mMimojiChangeTimbreBtn;
    private FrameLayout mMimojiChangeTimbreLayout;
    private ViewStub mMimojiViewStub;
    private ImageView mPreviewBack;
    private LottieAnimationView mPreviewCombine;
    private Bitmap mPreviewCoverBitmap;
    private ImageView mPreviewCoverView;
    private FrameLayout mPreviewLayout;
    private ImageView mPreviewShare;
    private ImageView mPreviewStart;
    private TextureVideoView mPreviewTextureView;
    private BGTintTextView mPreviewTips;
    private String mSavedPath;
    private ShareAdapter mShareAdapter;
    private View mShareCancel;
    private ViewGroup mShareLayout;
    private ProgressBar mShareProgress;
    private RecyclerView mShareRecyclerView;
    private TextView mTimeView;
    private FrameLayout mTopLayout;

    private void adjustViewBackground() {
        int uiStyle = DataRepository.dataItemRunning().getUiStyle();
        int i = (uiStyle == 1 || uiStyle == 3) ? R.drawable.bg_thumbnail_background_full : R.drawable.bg_thumbnail_background_half;
        this.mPreviewBack.setBackgroundResource(i);
        this.mPreviewShare.setBackgroundResource(i);
    }

    private int alertTintColor() {
        return TintColor.isYellowTintColor() ? TintColor.tintColor() : getResources().getColor(R.color.white);
    }

    private boolean checkAndShare() {
        this.mIsOnShare = true;
        if (FileUtils.checkFileConsist(this.mSavedPath)) {
            try {
                if (FileUtils.checkFileConsist(this.mSavedPath)) {
                    String str = Storage.DIRECTORY + File.separator + FileUtils.createtFileName("MIMOJI_", "mp4");
                    FileUtils.copyFile(new File(this.mSavedPath), new File(str));
                    this.mSavedPath = str;
                    ((ActivityBase) getActivity()).getImageSaver().addVideoSync(this.mSavedPath, Util.genContentValues(2, this.mSavedPath), false);
                    Util.shareMediaToMore(getContext(), this.mSavedPath, getString(R.string.share), true);
                    return true;
                }
            } catch (Exception unused) {
            }
        }
        return false;
    }

    private FragmentMimojiEdit2 getFragmentMiMoji() {
        FragmentMimojiEdit2 fragmentMimojiEdit2 = this.mFragmentMimojiEdit2;
        if (fragmentMimojiEdit2 == null) {
            Log.d(TAG, "getFragmentMiMoji(): fragment is null");
            return null;
        } else if (fragmentMimojiEdit2.isAdded()) {
            return this.mFragmentMimojiEdit2;
        } else {
            Log.d(TAG, "getFragmentMiMoji(): fragment is not added yet");
            return null;
        }
    }

    private Intent getShareIntent() {
        String str = FileUtils.checkFileConsist(this.mSavedPath) ? this.mSavedPath : null;
        if (TextUtils.isEmpty(str)) {
            return null;
        }
        ((ActivityBase) getActivity()).getImageSaver().addVideoSync(str, Util.genContentValues(2, str), false);
        return Util.getShareMediaIntent(getContext(), str, true);
    }

    private void hideShareSheet() {
        if (this.mShareLayout.getVisibility() == 0) {
            Completable.create(new SlideOutOnSubscribe(this.mShareLayout, 80).setInterpolator(new QuarticEaseInInterpolator()).setDurationTime(200)).subscribe();
        }
    }

    private void hideTimbreLayout() {
        if (!MimojiViewUtil.getViewIsVisible(this.mMimojiChangeTimbreBtn)) {
            DataRepository.dataItemLive().getMimojiStatusManager2().setMimojiPanelState(0);
            Completable.create(new AlphaOutOnSubscribe(this.mMimojiChangeTimbreLayout).targetGone()).subscribe();
            MimojiViewUtil.setViewVisible(this.mMimojiChangeTimbreBtn, true);
        }
    }

    private void initMimojiView(View view) {
        this.mPreviewLayout = (FrameLayout) view.findViewById(R.id.live_preview_layout);
        this.mBottomTimbreLayout = (ViewGroup) view.findViewById(R.id.fl_mimoji_change_timbre);
        this.mPreviewTextureView = (TextureVideoView) view.findViewById(R.id.tvv_mimoji_preview);
        this.mMimojiChangeTimbreBtn = (ColorImageView) view.findViewById(R.id.btn_mimoji_change_timbre);
        this.mMimojiChangeTimbreLayout = (FrameLayout) view.findViewById(R.id.fl_bottom_mimoji_change_timbre);
        this.mPreviewCoverView = (ImageView) view.findViewById(R.id.image_mimoji_cover);
        this.mPreviewTips = (BGTintTextView) view.findViewById(R.id.tv_mimoji_top_tips);
        this.mConcatProgress = (ProgressBar) view.findViewById(R.id.live_concat_progress);
        this.mCombineProgress = (ImageView) view.findViewById(R.id.live_save_progress);
        this.mShareProgress = (ProgressBar) view.findViewById(R.id.live_share_progress);
        this.mCancelProgress = (ProgressBar) view.findViewById(R.id.live_back_progress);
        this.mTimeView = (TextView) view.findViewById(R.id.live_preview_recording_time_view);
        this.mCameraSnapView = (CameraSnapView) view.findViewById(R.id.live_preview_save_circle);
        this.mCameraSnapView.setParameters(PaintConditionReferred.create(this.mCurrentMode));
        this.mCameraSnapView.hideCirclePaintItem();
        this.mCameraSnapView.hideRoundPaintItem();
        this.mCameraSnapView.setSnapClickEnable(false);
        this.mPreviewCombine = (LottieAnimationView) view.findViewById(R.id.live_preview_save);
        this.mPreviewBack = (ImageView) view.findViewById(R.id.live_preview_back);
        this.mPreviewShare = (ImageView) view.findViewById(R.id.live_preview_share);
        this.mPreviewStart = (ImageView) view.findViewById(R.id.live_preview_play);
        this.mShareLayout = (ViewGroup) view.findViewById(R.id.live_share_layout);
        this.mShareRecyclerView = (RecyclerView) this.mShareLayout.findViewById(R.id.live_share_list);
        this.mShareRecyclerView.setFocusable(false);
        this.mShareCancel = this.mShareLayout.findViewById(R.id.live_share_cancel);
        this.mShareCancel.setOnClickListener(this);
        this.mPreviewLayout.setOnClickListener(this);
        this.mCameraSnapView.setOnClickListener(this);
        this.mPreviewCombine.setOnClickListener(this);
        this.mPreviewBack.setOnClickListener(this);
        this.mPreviewShare.setOnClickListener(this);
        this.mPreviewStart.setOnClickListener(this);
        this.mMimojiChangeTimbreBtn.setOnClickListener(this);
        this.mTopLayout = (FrameLayout) view.findViewById(R.id.live_preview_top);
        ViewGroup.MarginLayoutParams marginLayoutParams = (ViewGroup.MarginLayoutParams) this.mTopLayout.getLayoutParams();
        marginLayoutParams.topMargin = Util.sTopMargin;
        marginLayoutParams.height = Util.sTopBarHeight;
        this.mTopLayout.setLayoutParams(marginLayoutParams);
        this.mBottomActionView = (FrameLayout) view.findViewById(R.id.live_preview_bottom_action);
        ((ViewGroup.MarginLayoutParams) this.mBottomActionView.getLayoutParams()).height = Util.getBottomHeight();
        this.mBottomActionView.setOnClickListener(this);
        FolmeUtils.touchScaleTint(this.mPreviewBack, this.mPreviewShare, this.mPreviewStart);
        FolmeUtils.touchScale(this.mPreviewCombine);
        this.mBottomLayout = (RelativeLayout) view.findViewById(R.id.live_preview_bottom_parent);
        ViewGroup.MarginLayoutParams marginLayoutParams2 = (ViewGroup.MarginLayoutParams) this.mBottomLayout.getLayoutParams();
        marginLayoutParams2.height = Math.round(((float) Util.sBottomBarHeight) * 0.7f);
        marginLayoutParams2.bottomMargin = Util.sBottomMargin;
        marginLayoutParams2.topMargin = Math.round(((float) Util.sBottomBarHeight) * 0.3f);
        ((ViewGroup.MarginLayoutParams) this.mTimeView.getLayoutParams()).height = Math.round(((float) Util.sBottomBarHeight) * 0.3f);
        this.mBottomActionView.setBackgroundResource(R.color.fullscreen_background);
    }

    private void onPreviewResume() {
        if (!this.mIsOnShare) {
            onCombineError();
        }
    }

    private void pausePlay() {
        MimojiModeProtocol.MimojiVideoEditor mimojiVideoEditor = (MimojiModeProtocol.MimojiVideoEditor) ModeCoordinatorImpl.getInstance().getAttachProtocol(252);
        if (mimojiVideoEditor != null && mimojiVideoEditor.pausePlay()) {
            MimojiViewUtil.setViewVisible(this.mPreviewStart, true);
        }
    }

    private void removeTimbreLayout() {
        try {
            DataRepository.dataItemLive().getMimojiStatusManager2().setMimojiPanelState(0);
            if (this.mFragmentMimojiBottomList != null) {
                this.mFragmentMimojiBottomList.unRegisterProtocol();
                FragmentManager childFragmentManager = getChildFragmentManager();
                FragmentUtils.removeFragmentByTag(childFragmentManager, getFragmentTag() + this.mFragmentMimojiBottomList.getFragmentTag());
                this.mFragmentMimojiBottomList = null;
            }
        } catch (Exception e2) {
            Log.e(TAG, "mimoji void removeTimbreLayout[] Exception " + e2);
        }
    }

    private void resumePlay() {
        Log.d(TAG, "mimoji void resumePlay[]");
        MimojiModeProtocol.MimojiVideoEditor mimojiVideoEditor = (MimojiModeProtocol.MimojiVideoEditor) ModeCoordinatorImpl.getInstance().getAttachProtocol(252);
        if (mimojiVideoEditor == null) {
            return;
        }
        if (mimojiVideoEditor.resumePlay()) {
            MimojiViewUtil.setViewVisible(this.mPreviewStart, false);
        } else {
            Log.e(TAG, "mimoji void resumePlay fail");
        }
    }

    private void shareMore() {
        try {
            getContext().startActivity(Intent.createChooser(getShareIntent(), getString(R.string.live_edit_share_title)));
        } catch (ActivityNotFoundException e2) {
            Log.e(TAG, "failed to share video shareMore ", e2);
        }
    }

    private void showExitConfirm() {
        this.mPreviewBack.setVisibility(0);
        this.mCancelProgress.setVisibility(8);
        if (this.mExitDialog == null) {
            CameraStatUtils.trackLiveClick("exit");
            this.mExitDialog = RotateDialogController.showSystemAlertDialog(getContext(), null, getString(R.string.live_edit_exit_message), getString(R.string.live_edit_exit_confirm), new b(this), null, null, getString(R.string.snap_cancel), null);
            this.mExitDialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
                /* class com.android.camera.features.mimoji2.fragment.FragmentMimojiFullScreen.AnonymousClass1 */

                public void onDismiss(DialogInterface dialogInterface) {
                    FragmentMimojiFullScreen.this.mExitDialog = null;
                }
            });
        }
    }

    /* JADX WARNING: Removed duplicated region for block: B:30:0x009a  */
    /* JADX WARNING: Removed duplicated region for block: B:32:0x009e  */
    private void showShareSheet() {
        boolean z;
        Intent shareIntent = getShareIntent();
        if (shareIntent == null) {
            onCombineError();
            return;
        }
        List<ResolveInfo> queryIntentActivities = getContext().getPackageManager().queryIntentActivities(shareIntent, 65536);
        if (queryIntentActivities == null || queryIntentActivities.isEmpty()) {
            Log.d(TAG, "no IntentActivities");
            return;
        }
        ArrayList arrayList = new ArrayList();
        int length = ShareConstant.DEFAULT_SHARE_LIST.length;
        for (ResolveInfo resolveInfo : queryIntentActivities) {
            if (arrayList.size() == length) {
                break;
            }
            int i = 0;
            while (true) {
                if (i >= length) {
                    break;
                } else if (ShareConstant.DEFAULT_SHARE_LIST[i].equals(resolveInfo.activityInfo.name)) {
                    arrayList.add(new ShareInfo(i, resolveInfo.activityInfo.packageName, resolveInfo.activityInfo.name, ShareConstant.DEFAULT_SHARE_LIST_ICON[i], ShareConstant.DEFAULT_SHARE_LIST_NAME[i]));
                    break;
                } else {
                    i++;
                }
            }
        }
        if (arrayList.isEmpty()) {
            Log.d(TAG, "no default share entry");
        } else if (((ShareInfo) arrayList.get(0)).index <= 1 || arrayList.size() >= 2) {
            z = false;
            if (!z) {
                shareMore();
                return;
            }
            arrayList.add(new ShareInfo(length + 1, "com.android.camera", "more", R.drawable.ic_live_share_more, R.string.accessibility_more));
            Collections.sort(arrayList, new Comparator<ShareInfo>() {
                /* class com.android.camera.features.mimoji2.fragment.FragmentMimojiFullScreen.AnonymousClass2 */

                public int compare(ShareInfo shareInfo, ShareInfo shareInfo2) {
                    int i = shareInfo.index;
                    int i2 = shareInfo2.index;
                    if (i == i2) {
                        return 0;
                    }
                    return i > i2 ? 1 : -1;
                }
            });
            int dimensionPixelSize = getResources().getDimensionPixelSize(R.dimen.live_share_item_margin);
            int i2 = Util.sWindowWidth;
            int max = Math.max((i2 - (dimensionPixelSize * 2)) / arrayList.size(), (int) (((float) (i2 - dimensionPixelSize)) / 5.5f));
            ShareAdapter shareAdapter = this.mShareAdapter;
            if (shareAdapter == null || shareAdapter.getItemCount() != arrayList.size()) {
                this.mShareAdapter = new ShareAdapter(arrayList, this, max);
                LinearLayoutManagerWrapper linearLayoutManagerWrapper = new LinearLayoutManagerWrapper(getContext(), "FullScreen");
                linearLayoutManagerWrapper.setOrientation(0);
                this.mShareRecyclerView.setLayoutManager(linearLayoutManagerWrapper);
                RecyclerAdapterWrapper recyclerAdapterWrapper = new RecyclerAdapterWrapper(this.mShareAdapter);
                Space space = new Space(getContext());
                space.setMinimumWidth(dimensionPixelSize);
                recyclerAdapterWrapper.addHeader(space);
                Space space2 = new Space(getContext());
                space2.setMinimumWidth(dimensionPixelSize);
                recyclerAdapterWrapper.addFooter(space2);
                this.mShareRecyclerView.setAdapter(recyclerAdapterWrapper);
            } else {
                this.mShareAdapter.setShareInfoList(arrayList);
                this.mShareAdapter.notifyDataSetChanged();
            }
            if (Util.sNavigationBarHeight > 0) {
                ((ViewGroup.MarginLayoutParams) this.mShareLayout.getLayoutParams()).bottomMargin = Util.sNavigationBarHeight;
            }
            Completable.create(new SlideInOnSubscribe(this.mShareLayout, 80).setInterpolator(new QuarticEaseOutInterpolator()).setDurationTime(200)).subscribe();
            return;
        } else {
            Log.d(TAG, "not match default share strategy");
        }
        z = true;
        if (!z) {
        }
    }

    private void showTimbreLayout() {
        DataRepository.dataItemLive().getMimojiStatusManager2().setMimojiPanelState(3);
        MimojiViewUtil.setViewVisible(this.mMimojiChangeTimbreLayout, true);
        FragmentMimojiBottomList fragmentMimojiBottomList = this.mFragmentMimojiBottomList;
        if (fragmentMimojiBottomList == null) {
            this.mFragmentMimojiBottomList = new FragmentMimojiBottomList();
            this.mFragmentMimojiBottomList.registerProtocol();
            this.mFragmentMimojiBottomList.setDegree(this.mDegree);
            FragmentManager childFragmentManager = getChildFragmentManager();
            FragmentMimojiBottomList fragmentMimojiBottomList2 = this.mFragmentMimojiBottomList;
            FragmentUtils.addFragmentWithTag(childFragmentManager, (int) R.id.fl_bottom_mimoji_change_timbre, fragmentMimojiBottomList2, getFragmentTag() + this.mFragmentMimojiBottomList.getFragmentTag());
        } else {
            fragmentMimojiBottomList.refreshMimojiList();
            Completable.create(new AlphaInOnSubscribe(this.mFragmentMimojiBottomList.getView())).subscribe();
        }
        MimojiViewUtil.setViewVisible(this.mMimojiChangeTimbreBtn, false);
    }

    private void startCombine() {
        if (FileUtils.checkFileConsist(this.mSavedPath)) {
            try {
                if (this.mSavedPath.contains("mimoji_normal") || this.mSavedPath.contains("mimoji_deal")) {
                    String str = Storage.DIRECTORY + File.separator + FileUtils.createtFileName("MIMOJI_", "mp4");
                    FileUtils.copyFile(new File(this.mSavedPath), new File(str));
                    this.mSavedPath = str;
                }
                onCombineSuccess(new String[0]);
            } catch (Exception unused) {
                onCombineError();
            }
        } else {
            onCombineError();
        }
    }

    private void startPlay() {
        MimojiViewUtil.setViewVisible(this.mConcatProgress, false);
        MimojiViewUtil.setViewVisible(this.mPreviewStart, false);
        MimojiModeProtocol.MimojiVideoEditor mimojiVideoEditor = (MimojiModeProtocol.MimojiVideoEditor) ModeCoordinatorImpl.getInstance().getAttachProtocol(252);
        if (mimojiVideoEditor != null) {
            mimojiVideoEditor.startPlay();
        }
    }

    @Override // com.android.camera.features.mimoji2.module.protocol.MimojiModeProtocol.MimojiFullScreenProtocol
    public void alertTop(int i, int i2, long j) {
        Handler handler = this.mHandler;
        if (handler != null && this.mPreviewTips != null) {
            handler.removeCallbacks(this.mAlertTopHintHideRunnable);
            if (i == 0) {
                this.mPreviewTips.setText(i2);
                this.mPreviewTips.setBGColor(alertTintColor());
                this.mPreviewTips.setVisibility(0);
                if (j > 0) {
                    this.mHandler.postDelayed(this.mAlertTopHintHideRunnable, j);
                    return;
                }
                return;
            }
            this.mPreviewTips.setText("");
            this.mPreviewTips.setVisibility(8);
        }
    }

    public /* synthetic */ void b(int i, String str) {
        if (!canProvide()) {
            onCombineError();
        } else if (this.mPreviewLayout == null || i != 0 || DataRepository.dataItemLive().getMimojiStatusManager2().isInMimojiPreview()) {
            onCombineError();
        } else {
            Rect previewRect = Util.getPreviewRect(getActivity());
            FrameLayout.LayoutParams layoutParams = (FrameLayout.LayoutParams) this.mPreviewLayout.getLayoutParams();
            layoutParams.topMargin = previewRect.top;
            layoutParams.height = previewRect.height();
            this.mPreviewLayout.setLayoutParams(layoutParams);
            boolean z = true;
            MimojiViewUtil.setViewVisible(this.mPreviewLayout, true);
            MimojiViewUtil.setViewVisible(this.mBottomTimbreLayout, true);
            MimojiViewUtil.setViewVisible(this.mPreviewTextureView, true);
            boolean z2 = false;
            if (DataRepository.dataItemLive().getMimojiStatusManager2().getMimojiPanelState() == 3) {
                MimojiViewUtil.setViewVisible(this.mMimojiChangeTimbreBtn, false);
            } else {
                MimojiViewUtil.setViewVisible(this.mMimojiChangeTimbreBtn, true);
            }
            if (this.mMimojiChangeTimbreBtn != null) {
                if (DataRepository.dataItemLive().getMimojiStatusManager2().getCurrentMimojiTimbreInfo() == null) {
                    z = false;
                }
                this.mMimojiChangeTimbreBtn.setColor(z ? TintColor.tintColor() : ColorConstant.IMAGE_NORMAL_COLOR);
            }
            MimojiModeProtocol.MimojiVideoEditor mimojiVideoEditor = (MimojiModeProtocol.MimojiVideoEditor) ModeCoordinatorImpl.getInstance().getAttachProtocol(252);
            if (mimojiVideoEditor != null) {
                z2 = mimojiVideoEditor.init(this.mPreviewTextureView, str);
            }
            if (z2) {
                this.mSavedPath = str;
                startPlay();
                return;
            }
            onCombineError();
        }
    }

    @Override // com.android.camera.features.mimoji2.module.protocol.MimojiModeProtocol.MimojiFullScreenProtocol
    public void concatResult(String str, int i) {
        Log.d(TAG, "mimoji void concatResult[]");
        if (getActivity() != null) {
            getActivity().runOnUiThread(new e(this, i, str));
        }
    }

    @Override // com.android.camera.fragment.BaseFragment
    public int getFragmentInto() {
        return 65523;
    }

    /* access modifiers changed from: protected */
    @Override // com.android.camera.fragment.BaseFragment
    public int getLayoutResourceId() {
        return R.layout.fragment_mimoji_full_screen;
    }

    @Override // com.android.camera.features.mimoji2.module.protocol.MimojiModeProtocol.MimojiFullScreenProtocol
    public String getMimojiVideoSavePath() {
        return this.mSavedPath;
    }

    /* access modifiers changed from: protected */
    @Override // com.android.camera.fragment.BaseFragment
    public void initView(View view) {
        this.mMimojiViewStub = (ViewStub) view.findViewById(R.id.mimoji_record_preview);
    }

    @Override // com.android.camera.features.mimoji2.module.protocol.MimojiModeProtocol.MimojiFullScreenProtocol
    public boolean isMimojiRecordPreviewShowing() {
        return MimojiViewUtil.getViewIsVisible(this.mMImojiViewLayout);
    }

    @Override // com.android.camera.fragment.BaseFragment, com.android.camera.animation.AnimationDelegate.AnimationResource
    public void notifyAfterFrameAvailable(int i) {
        super.notifyAfterFrameAvailable(i);
        if (this.mFragmentMimojiEdit2 == null && DataRepository.dataItemFeature().Sj()) {
            this.mFragmentMimojiEdit2 = new FragmentMimojiEdit2();
            this.mFragmentMimojiEdit2.registerProtocol();
            this.mFragmentMimojiEdit2.setDegree(this.mDegree);
            FragmentManager childFragmentManager = getChildFragmentManager();
            FragmentMimojiEdit2 fragmentMimojiEdit2 = this.mFragmentMimojiEdit2;
            FragmentUtils.addFragmentWithTag(childFragmentManager, (int) R.id.fragment_full_screen_mimoji_edit, fragmentMimojiEdit2, fragmentMimojiEdit2.getFragmentTag());
        }
    }

    @Override // com.android.camera.fragment.BaseFragment, com.android.camera.animation.AnimationDelegate.AnimationResource
    public void notifyDataChanged(int i, int i2) {
        super.notifyDataChanged(i, i2);
        if (this.mFragmentMimojiEdit2 == null && DataRepository.dataItemFeature().Sj()) {
            this.mFragmentMimojiEdit2 = new FragmentMimojiEdit2();
            this.mFragmentMimojiEdit2.registerProtocol();
            this.mFragmentMimojiEdit2.setDegree(this.mDegree);
            FragmentManager childFragmentManager = getChildFragmentManager();
            FragmentMimojiEdit2 fragmentMimojiEdit2 = this.mFragmentMimojiEdit2;
            FragmentUtils.addFragmentWithTag(childFragmentManager, (int) R.id.fragment_full_screen_mimoji_edit, fragmentMimojiEdit2, fragmentMimojiEdit2.getFragmentTag());
        }
    }

    @Override // com.android.camera.protocol.ModeProtocol.HandleBackTrace
    public boolean onBackEvent(int i) {
        if (i != 1) {
            return false;
        }
        if (MimojiViewUtil.getViewIsVisible(this.mMImojiViewLayout)) {
            if (MimojiViewUtil.getViewIsVisible(this.mShareLayout)) {
                hideShareSheet();
            } else {
                showExitConfirm();
            }
            return true;
        }
        FragmentMimojiEdit2 fragmentMiMoji = getFragmentMiMoji();
        if (fragmentMiMoji != null) {
            return fragmentMiMoji.onBackEvent(i);
        }
        return false;
    }

    public void onClick(View view) {
        MimojiModeProtocol.MimojiVideoEditor mimojiVideoEditor = (MimojiModeProtocol.MimojiVideoEditor) ModeCoordinatorImpl.getInstance().getAttachProtocol(252);
        if (this.mConcatProgress.getVisibility() == 0) {
            return;
        }
        if ((mimojiVideoEditor == null || mimojiVideoEditor.isAvaliable()) && this.mCombineProgress.getVisibility() != 0 && this.mShareProgress.getVisibility() != 0 && this.mCancelProgress.getVisibility() != 0) {
            switch (view.getId()) {
                case R.id.btn_mimoji_change_timbre:
                    showTimbreLayout();
                    return;
                case R.id.live_preview_back:
                    this.mCancelProgress.setVisibility(0);
                    this.mPreviewBack.setVisibility(8);
                    showExitConfirm();
                    return;
                case R.id.live_preview_bottom_action:
                default:
                    return;
                case R.id.live_preview_layout:
                    FragmentMimojiBottomList fragmentMimojiBottomList = this.mFragmentMimojiBottomList;
                    if (fragmentMimojiBottomList != null && MimojiViewUtil.getViewIsVisible(fragmentMimojiBottomList.getView())) {
                        hideTimbreLayout();
                        return;
                    }
                    return;
                case R.id.live_preview_play:
                    hideShareSheet();
                    startPlay();
                    return;
                case R.id.live_preview_save:
                case R.id.live_preview_save_circle:
                    CameraStatUtils.trackLiveClick(MistatsConstants.Live.VALUE_LIVE_CLICK_PLAY_SAVE);
                    pausePlay();
                    this.mPreviewStart.setVisibility(8);
                    this.mPreviewCombine.setVisibility(8);
                    MimojiViewUtil.setViewVisible(this.mCombineProgress, true);
                    startCombine();
                    return;
                case R.id.live_preview_share:
                    if (this.mConcatProgress.getVisibility() == 0) {
                        Log.d(TAG, "concat not finished, skip share~");
                        return;
                    }
                    CameraStatUtils.trackLiveClick(MistatsConstants.Live.VALUE_LIVE_CLICK_PLAY_SHARE);
                    if (!checkAndShare()) {
                        Log.d(TAG, "uri null");
                        onCombineError();
                        return;
                    }
                    return;
                case R.id.live_share_cancel:
                    hideShareSheet();
                    return;
                case R.id.live_share_item:
                    ShareInfo shareInfo = (ShareInfo) view.getTag();
                    hideShareSheet();
                    CameraStatUtils.trackLiveClick(MistatsConstants.Live.VALUE_LIVE_CLICK_PLAY_SHARE_SHEET + shareInfo.index);
                    if (shareInfo.className.equals("more")) {
                        shareMore();
                        return;
                    }
                    String str = null;
                    if (FileUtils.checkFileConsist(this.mSavedPath)) {
                        str = this.mSavedPath;
                    }
                    if (!TextUtils.isEmpty(str)) {
                        try {
                            ((ActivityBase) getActivity()).getImageSaver().addVideoSync(str, Util.genContentValues(2, str), false);
                            Util.startShareMedia(shareInfo.packageName, shareInfo.className, getContext(), str, true);
                            return;
                        } catch (Exception unused) {
                            onCombineError();
                            return;
                        }
                    } else {
                        onCombineError();
                        return;
                    }
            }
        }
    }

    @Override // com.android.camera.features.mimoji2.module.protocol.MimojiModeProtocol.MimojiFullScreenProtocol
    public void onCombineError() {
        if (this.mCurrentMode == 184 && getActivity() != null && !DataRepository.dataItemLive().getMimojiStatusManager2().isInMimojiCreate()) {
            getActivity().runOnUiThread(new c(this));
        }
    }

    @Override // com.android.camera.features.mimoji2.module.protocol.MimojiModeProtocol.MimojiFullScreenProtocol
    public void onCombineSuccess(String... strArr) {
        if (this.mCurrentMode == 184 && !DataRepository.dataItemLive().getMimojiStatusManager2().isInMimojiCreate()) {
            Log.d(TAG, "combineSuccess");
            if (strArr != null && strArr.length > 0) {
                this.mSavedPath = strArr[0];
            }
            if (getActivity() != null) {
                getActivity().runOnUiThread(new d(this));
            }
        }
    }

    @Override // com.android.camera.features.mimoji2.module.protocol.MimojiModeProtocol.MimojiFullScreenProtocol
    public void onMimojiSaveToLocalFinished(Uri uri) {
        Log.d(TAG, "MIMOJI onMimojiSaveToLocalFinished" + uri);
    }

    @Override // androidx.fragment.app.Fragment
    public void onPause() {
        super.onPause();
        pausePlay();
    }

    @Override // com.android.camera.fragment.BaseFragment, androidx.fragment.app.Fragment
    public void onResume() {
        super.onResume();
        onPreviewResume();
    }

    @Override // com.android.camera.fragment.BaseFragment, androidx.fragment.app.Fragment
    public void onStop() {
        onCombineError();
        super.onStop();
    }

    @Override // com.android.camera.fragment.BaseFragment, com.android.camera.animation.AnimationDelegate.AnimationResource
    public void provideAnimateElement(int i, List<Completable> list, int i2) {
        super.provideAnimateElement(i, list, i2);
        if (i2 == 3) {
            AlertDialog alertDialog = this.mExitDialog;
            if (alertDialog != null) {
                alertDialog.dismiss();
                this.mExitDialog = null;
            }
            onCombineError();
        }
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
        View view = this.mMImojiViewLayout;
        if (view != null && view.getVisibility() == 0) {
            list.add(this.mPreviewStart);
            list.add(this.mCameraSnapView);
            list.add(this.mPreviewCombine);
            list.add(this.mPreviewBack);
            list.add(this.mPreviewShare);
            ColorImageView colorImageView = this.mMimojiChangeTimbreBtn;
            if (colorImageView != null) {
                list.add(colorImageView);
            }
            ColorImageView colorImageView2 = this.mMimojiChangeTimbreBtn;
            if (colorImageView2 != null) {
                list.add(colorImageView2);
            }
        }
        FragmentMimojiBottomList fragmentMimojiBottomList = this.mFragmentMimojiBottomList;
        if (fragmentMimojiBottomList != null) {
            fragmentMimojiBottomList.provideRotateItem(list, i);
        }
    }

    public void quitMimojiRecordPreview() {
        MimojiStatusManager2 mimojiStatusManager2 = DataRepository.dataItemLive().getMimojiStatusManager2();
        if (mimojiStatusManager2.isInMimojiPreviewPlay()) {
            mimojiStatusManager2.setMode(2);
        }
        this.mIsOnShare = false;
        setPreviewCover(null);
        showPreviewCover(false);
        MimojiViewUtil.setViewVisible(this.mConcatProgress, false);
        MimojiViewUtil.setViewVisible(this.mCombineProgress, false);
        MimojiViewUtil.setViewVisible(this.mShareProgress, false);
        MimojiViewUtil.setViewVisible(this.mMImojiViewLayout, false);
        removeTimbreLayout();
        MimojiModeProtocol.MimojiVideoEditor mimojiVideoEditor = (MimojiModeProtocol.MimojiVideoEditor) ModeCoordinatorImpl.getInstance().getAttachProtocol(252);
        if (mimojiVideoEditor != null) {
            mimojiVideoEditor.onDestory();
        }
    }

    /* access modifiers changed from: protected */
    @Override // com.android.camera.fragment.BaseFragment
    public void register(ModeProtocol.ModeCoordinator modeCoordinator) {
        super.register(modeCoordinator);
        modeCoordinator.attachProtocol(249, this);
        registerBackStack(modeCoordinator, this);
    }

    public /* synthetic */ void sa() {
        alertTop(8, 0, 0);
    }

    @Override // com.android.camera.features.mimoji2.module.protocol.MimojiModeProtocol.MimojiFullScreenProtocol
    public void setPreviewCover(Bitmap bitmap) {
        Bitmap bitmap2 = this.mPreviewCoverBitmap;
        if (bitmap2 != null && !bitmap2.isRecycled()) {
            this.mPreviewCoverBitmap.recycle();
        }
        this.mPreviewCoverBitmap = bitmap;
    }

    @Override // com.android.camera.features.mimoji2.module.protocol.MimojiModeProtocol.MimojiFullScreenProtocol
    public void showPreviewCover(boolean z) {
        Bitmap bitmap;
        ImageView imageView;
        if (!z || (bitmap = this.mPreviewCoverBitmap) == null || bitmap.isRecycled() || (imageView = this.mPreviewCoverView) == null) {
            MimojiViewUtil.setViewVisible(this.mPreviewCoverView, false);
            return;
        }
        imageView.setImageBitmap(this.mPreviewCoverBitmap);
        MimojiViewUtil.setViewVisible(this.mPreviewCoverView, true);
    }

    @Override // com.android.camera.features.mimoji2.module.protocol.MimojiModeProtocol.MimojiFullScreenProtocol
    public void startMimojiRecordPreview() {
        this.mSavedPath = null;
        if (this.mMImojiViewLayout == null) {
            this.mMImojiViewLayout = this.mMimojiViewStub.inflate();
            initMimojiView(this.mMImojiViewLayout);
        }
        MimojiViewUtil.setViewVisible(this.mPreviewLayout, false);
        MimojiViewUtil.setViewVisible(this.mCombineProgress, false);
        MimojiViewUtil.setViewVisible(this.mShareProgress, false);
        MimojiViewUtil.setViewVisible(this.mCancelProgress, false);
        MimojiViewUtil.setViewVisible(this.mShareLayout, false);
        MimojiViewUtil.setViewVisible(this.mMImojiViewLayout, true);
        showPreviewCover(true);
        adjustViewBackground();
        if (DataRepository.dataItemLive().getMimojiStatusManager2().getMimojiPanelState() == 3) {
            MimojiViewUtil.setViewVisible(this.mBottomTimbreLayout, true);
            MimojiViewUtil.setViewVisible(this.mMimojiChangeTimbreLayout, true);
            MimojiViewUtil.setViewVisible(this.mMimojiChangeTimbreBtn, false);
        } else {
            MimojiViewUtil.setViewVisible(this.mBottomTimbreLayout, false);
            MimojiViewUtil.setViewVisible(this.mMimojiChangeTimbreLayout, false);
            MimojiViewUtil.setViewVisible(this.mMimojiChangeTimbreBtn, true);
        }
        ViewCompat.setRotation(this.mPreviewStart, (float) this.mDegree);
        ViewCompat.setRotation(this.mCameraSnapView, (float) this.mDegree);
        ViewCompat.setRotation(this.mPreviewCombine, (float) this.mDegree);
        ViewCompat.setRotation(this.mPreviewBack, (float) this.mDegree);
        ViewCompat.setRotation(this.mPreviewShare, (float) this.mDegree);
        Completable.create(new AlphaInOnSubscribe(this.mCameraSnapView)).subscribe();
        Completable.create(new AlphaInOnSubscribe(this.mPreviewCombine)).subscribe();
        Completable.create(new AlphaInOnSubscribe(this.mPreviewBack)).subscribe();
        Completable.create(new AlphaInOnSubscribe(this.mPreviewStart)).subscribe();
        this.mPreviewCombine.setScale(0.38f);
        this.mPreviewCombine.k(R.raw.vv_save);
        this.mPreviewCombine.T();
        if (((ActivityBase) getContext()).startFromKeyguard()) {
            this.mPreviewShare.setVisibility(8);
        } else {
            Completable.create(new AlphaInOnSubscribe(this.mPreviewShare)).subscribe();
        }
        MimojiModeProtocol.MimojiAvatarEngine2 mimojiAvatarEngine2 = (MimojiModeProtocol.MimojiAvatarEngine2) ModeCoordinatorImpl.getInstance().getAttachProtocol(246);
        if (mimojiAvatarEngine2 != null) {
            this.mTimeView.setText(mimojiAvatarEngine2.getTimeValue());
            this.mTimeView.setVisibility(0);
        }
        MimojiViewUtil.setViewVisible(this.mPreviewStart, false);
        MimojiViewUtil.setViewVisible(this.mConcatProgress, true);
    }

    @Override // com.android.camera.features.mimoji2.module.protocol.MimojiModeProtocol.MimojiFullScreenProtocol
    public void startMimojiRecordSaving() {
        this.mCameraSnapView.performClick();
    }

    public /* synthetic */ void ta() {
        quitMimojiRecordPreview();
        ModeProtocol.CameraAction cameraAction = (ModeProtocol.CameraAction) ModeCoordinatorImpl.getInstance().getAttachProtocol(161);
        if (cameraAction != null) {
            cameraAction.onReviewCancelClicked();
            return;
        }
        ModeProtocol.RecordState recordState = (ModeProtocol.RecordState) ModeCoordinatorImpl.getInstance().getAttachProtocol(212);
        if (recordState != null) {
            recordState.onFinish();
        }
    }

    public /* synthetic */ void ua() {
        quitMimojiRecordPreview();
        ModeProtocol.CameraAction cameraAction = (ModeProtocol.CameraAction) ModeCoordinatorImpl.getInstance().getAttachProtocol(161);
        if (cameraAction != null) {
            cameraAction.onReviewDoneClicked();
        }
    }

    /* access modifiers changed from: protected */
    @Override // com.android.camera.fragment.BaseFragment
    public void unRegister(ModeProtocol.ModeCoordinator modeCoordinator) {
        super.unRegister(modeCoordinator);
        modeCoordinator.detachProtocol(249, this);
        removeTimbreLayout();
        unRegisterBackStack(modeCoordinator, this);
    }

    public /* synthetic */ void va() {
        CameraStatUtils.trackLiveClick(MistatsConstants.Live.VALUE_LIVE_CLICK_PLAY_EXIT_CONFIRM);
        MimojiModeProtocol.MimojiAvatarEngine2 mimojiAvatarEngine2 = (MimojiModeProtocol.MimojiAvatarEngine2) ModeCoordinatorImpl.getInstance().getAttachProtocol(246);
        if (mimojiAvatarEngine2 != null) {
            mimojiAvatarEngine2.deleteMimojiCache(1);
        }
        onCombineError();
    }
}
