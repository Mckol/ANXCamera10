package com.android.camera.fragment.dialog;

import android.app.AlertDialog;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.pm.ResolveInfo;
import android.graphics.Bitmap;
import android.graphics.Rect;
import android.graphics.SurfaceTexture;
import android.net.Uri;
import android.view.TextureView;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.LinearInterpolator;
import android.view.animation.RotateAnimation;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.Space;
import android.widget.TextView;
import androidx.annotation.CallSuper;
import androidx.recyclerview.widget.RecyclerView;
import com.airbnb.lottie.LottieAnimationView;
import com.android.camera.ActivityBase;
import com.android.camera.Camera;
import com.android.camera.R;
import com.android.camera.RotateDialogController;
import com.android.camera.Util;
import com.android.camera.animation.FolmeUtils;
import com.android.camera.animation.type.SlideInOnSubscribe;
import com.android.camera.animation.type.SlideOutOnSubscribe;
import com.android.camera.constant.ShareConstant;
import com.android.camera.fragment.BaseFragment;
import com.android.camera.fragment.RecyclerAdapterWrapper;
import com.android.camera.fragment.beauty.LinearLayoutManagerWrapper;
import com.android.camera.fragment.fullscreen.ShareAdapter;
import com.android.camera.fragment.fullscreen.ShareInfo;
import com.android.camera.log.Log;
import com.android.camera.module.impl.component.ILive;
import com.android.camera.module.impl.component.MiLivePlayer;
import com.android.camera.protocol.ModeCoordinatorImpl;
import com.android.camera.protocol.ModeProtocol;
import com.android.camera.statistic.CameraStatUtils;
import com.android.camera.statistic.MistatsConstants;
import com.android.camera.ui.CameraSnapView;
import com.android.camera.ui.drawable.snap.PaintConditionReferred;
import io.reactivex.Completable;
import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import miui.view.animation.QuarticEaseInInterpolator;
import miui.view.animation.QuarticEaseOutInterpolator;

public class FragmentLiveReview extends BaseFragment implements ModeProtocol.MiLivePlayerControl, ModeProtocol.HandleBackTrace, View.OnClickListener {
    public static final int FRAGMENT_INFO = 1048561;
    private static final int STATE_IDLE = 0;
    private static final int STATE_PAUSED = 4;
    private static final int STATE_PAUSE_AFTER_PLAY = 10;
    private static final int STATE_PENDING_PLAY = 2;
    private static final int STATE_PENDING_RESUME = 5;
    private static final int STATE_PENDING_SAVE = 8;
    private static final int STATE_PENDING_SHARE = 6;
    private static final int STATE_PLAYING = 3;
    private static final int STATE_SAVE = 9;
    private static final int STATE_SHARE = 7;
    private static final int STATE_WAIT_SURFACE_CREATE = 1;
    public final String TAG = ("LiveReview@" + hashCode());
    private String mAudioString;
    private ViewGroup mBottomActionView;
    private ViewGroup mBottomLayout;
    private CameraSnapView mCameraSnapView;
    private ProgressBar mCancelProgress;
    private int mComposeState;
    private Consumer<Integer> mComposeStateConsumer = new f(this);
    private Disposable mComposeStateDisposable;
    private ObservableEmitter<Integer> mComposeStateEmitter;
    private AlertDialog mExitDialog;
    private Bitmap mFirstFrame;
    private boolean mPaused;
    private Consumer<Integer> mPlayStateConsumer = new d(this);
    private Disposable mPlayStateDisposable;
    private ObservableEmitter<Integer> mPlayStateEmitter;
    private MiLivePlayer mPlayer;
    private int mPlayerState;
    private ImageView mPreviewBack;
    private LottieAnimationView mPreviewCombine;
    private ImageView mPreviewCover;
    private FrameLayout mPreviewLayout;
    private ImageView mPreviewShare;
    private ImageView mPreviewStart;
    private ProgressBar mPreviewStartProgress;
    private View mRootView;
    private ContentValues mSaveContentValues;
    private String mSavePath;
    private ImageView mSaveProgress;
    private boolean mSaved;
    private Uri mSavedUri;
    private List<ILive.ILiveSegmentData> mSegmentData;
    private ShareAdapter mShareAdapter;
    private View mShareCancel;
    private ViewGroup mShareLayout;
    private ProgressBar mShareProgress;
    private RecyclerView mShareRecyclerView;
    private ILive.ILivePlayerStateListener mStateListener = new ILive.ILivePlayerStateListener() {
        /* class com.android.camera.fragment.dialog.FragmentLiveReview.AnonymousClass1 */

        @Override // com.android.camera.module.impl.component.ILive.ILivePlayerStateListener
        public void onComposeStateChange(int i) {
            if (FragmentLiveReview.this.mComposeState != i) {
                FragmentLiveReview.this.mComposeState = i;
            }
            if (FragmentLiveReview.this.mComposeStateEmitter != null) {
                FragmentLiveReview.this.mComposeStateEmitter.onNext(Integer.valueOf(i));
            }
        }

        @Override // com.android.camera.module.impl.component.ILive.ILivePlayerStateListener
        public void onPlayStateChange(int i) {
            if (FragmentLiveReview.this.mPlayerState != i) {
                FragmentLiveReview.this.mPlayerState = i;
            }
            if (FragmentLiveReview.this.mPlayStateEmitter != null) {
                FragmentLiveReview.this.mPlayStateEmitter.onNext(Integer.valueOf(i));
            }
        }
    };
    private TextureView mTextureView;
    private TextView mTimeView;
    private ViewGroup mTopLayout;
    private Consumer<Integer> mUIStateConsumer = new e(this);
    private Disposable mUIStateDisposable;
    private ObservableEmitter<Integer> mUIStateEmitter;
    private int mUiState = 0;
    private int mVideoHeight;
    private int mVideoWidth;

    static /* synthetic */ int a(ShareInfo shareInfo, ShareInfo shareInfo2) {
        return Integer.compare(shareInfo.index, shareInfo2.index);
    }

    private boolean checkAndShare() {
        if (this.mSavedUri == null) {
            return false;
        }
        shareMore();
        return true;
    }

    private String getCurStateString() {
        return getStateString(this.mUiState);
    }

    private String getStateString(int i) {
        switch (i) {
            case 0:
                return "STATE_IDLE";
            case 1:
                return "STATE_WAIT_SURFACE_CREATE";
            case 2:
                return "STATE_PENDING_PLAY";
            case 3:
                return "STATE_PLAYING";
            case 4:
                return "STATE_PAUSED";
            case 5:
                return "STATE_PENDING_RESUME";
            case 6:
                return "STATE_PENDING_SHARE";
            case 7:
                return "STATE_SHARE";
            case 8:
                return "STATE_PENDING_SAVE";
            case 9:
                return "STATE_SAVE";
            case 10:
                return "STATE_PAUSE_AFTER_PLAY";
            default:
                return "STATE_UNKNOWN";
        }
    }

    /* access modifiers changed from: private */
    /* access modifiers changed from: public */
    private boolean hasFrame() {
        Bitmap bitmap = this.mFirstFrame;
        return bitmap != null && !bitmap.isRecycled();
    }

    private boolean hideShareSheet() {
        if (this.mShareLayout.getVisibility() != 0) {
            return false;
        }
        Completable.create(new SlideOutOnSubscribe(this.mShareLayout, 80).setInterpolator(new QuarticEaseInInterpolator()).setDurationTime(200)).subscribe();
        return true;
    }

    /* access modifiers changed from: private */
    /* access modifiers changed from: public */
    private void initPlayer() {
        Log.d(this.TAG, "initPlayer");
        if (this.mPlayer == null) {
            this.mSavePath = null;
            this.mUIStateDisposable = Observable.create(new b(this)).observeOn(AndroidSchedulers.mainThread()).subscribe(this.mUIStateConsumer);
            this.mPlayStateDisposable = Observable.create(new h(this)).observeOn(AndroidSchedulers.mainThread()).subscribe(this.mPlayStateConsumer);
            this.mComposeStateDisposable = Observable.create(new g(this)).observeOn(AndroidSchedulers.mainThread()).subscribe(this.mComposeStateConsumer);
            this.mPlayer = new MiLivePlayer(getActivity());
            this.mPlayer.setStateListener(this.mStateListener);
        }
    }

    private void initTextureView() {
        this.mTextureView = new TextureView(getContext());
        this.mPreviewCover = new ImageView(getContext());
        this.mPreviewCover.setBackground(null);
        this.mPreviewCover.setVisibility(8);
        FrameLayout.LayoutParams layoutParams = new FrameLayout.LayoutParams(-1, -1);
        Rect displayRect = Util.getDisplayRect();
        layoutParams.topMargin = displayRect.top;
        layoutParams.height = displayRect.height();
        this.mPreviewLayout.removeAllViews();
        this.mPreviewLayout.setBackground(null);
        this.mPreviewLayout.addView(this.mTextureView, layoutParams);
        this.mPreviewLayout.addView(this.mPreviewCover, layoutParams);
        this.mTextureView.setSurfaceTextureListener(new TextureView.SurfaceTextureListener() {
            /* class com.android.camera.fragment.dialog.FragmentLiveReview.AnonymousClass2 */

            public void onSurfaceTextureAvailable(SurfaceTexture surfaceTexture, int i, int i2) {
                String str = FragmentLiveReview.this.TAG;
                Log.d(str, "onSurfaceTextureAvailable : " + surfaceTexture + ", size = " + i + "x" + i2);
                FragmentLiveReview.this.releaseCover();
                FragmentLiveReview.this.initPlayer();
                if (FragmentLiveReview.this.mUiState == 1) {
                    FragmentLiveReview.this.mPlayer.init(FragmentLiveReview.this.mVideoWidth, FragmentLiveReview.this.mVideoHeight, i, i2, FragmentLiveReview.this.mSegmentData, FragmentLiveReview.this.mAudioString);
                    FragmentLiveReview.this.startPlay(surfaceTexture);
                }
            }

            public boolean onSurfaceTextureDestroyed(SurfaceTexture surfaceTexture) {
                String str = FragmentLiveReview.this.TAG;
                Log.d(str, "onSurfaceTextureDestroyed : " + surfaceTexture);
                FragmentLiveReview.this.release();
                return false;
            }

            public void onSurfaceTextureSizeChanged(SurfaceTexture surfaceTexture, int i, int i2) {
                String str = FragmentLiveReview.this.TAG;
                Log.d(str, "onSurfaceTextureAvailable : " + surfaceTexture + ", size = " + i + "x" + i2);
            }

            public void onSurfaceTextureUpdated(SurfaceTexture surfaceTexture) {
                if (!FragmentLiveReview.this.hasFrame()) {
                    Log.d(FragmentLiveReview.this.TAG, "capture cover.");
                    FragmentLiveReview fragmentLiveReview = FragmentLiveReview.this;
                    fragmentLiveReview.mFirstFrame = fragmentLiveReview.mTextureView.getBitmap();
                    FragmentLiveReview.this.mPreviewCover.setImageBitmap(FragmentLiveReview.this.mFirstFrame);
                }
                if (FragmentLiveReview.this.mUiState == 2 || FragmentLiveReview.this.mUiState == 5) {
                    FragmentLiveReview.this.setUiState(3);
                } else if (FragmentLiveReview.this.mUiState == 10) {
                    FragmentLiveReview.this.pausePlay(false, false);
                }
            }
        });
    }

    private boolean isComposing() {
        int i = this.mUiState;
        return i == 8 || i == 9 || i == 6 || i == 7;
    }

    /* access modifiers changed from: private */
    /* access modifiers changed from: public */
    private void releaseCover() {
        if (hasFrame()) {
            this.mFirstFrame.recycle();
            this.mFirstFrame = null;
        }
    }

    /* access modifiers changed from: private */
    /* access modifiers changed from: public */
    private void setUiState(int i) {
        if (this.mUiState != i) {
            String str = this.TAG;
            Log.d(str, "ui state change from " + getCurStateString() + " to " + getStateString(i));
            this.mUiState = i;
            ObservableEmitter<Integer> observableEmitter = this.mUIStateEmitter;
            if (observableEmitter != null) {
                observableEmitter.onNext(Integer.valueOf(this.mUiState));
            }
        }
    }

    private void shareMore() {
        Util.shareMediaToMore(getContext(), this.mSavePath, getString(R.string.live_edit_share_title), true);
    }

    private boolean showCover() {
        if (this.mPreviewCover == null || !hasFrame()) {
            return false;
        }
        this.mPreviewCover.setVisibility(0);
        return true;
    }

    private void showExitConfirm() {
        if (this.mExitDialog == null) {
            int i = this.mUiState;
            if (i == 3 || i == 4) {
                this.mExitDialog = RotateDialogController.showSystemAlertDialog(getContext(), null, getString(R.string.live_edit_exit_message), getString(R.string.live_edit_exit_confirm), new Runnable() {
                    /* class com.android.camera.fragment.dialog.FragmentLiveReview.AnonymousClass3 */

                    public void run() {
                        FragmentLiveReview.this.mExitDialog.dismiss();
                        CameraStatUtils.trackMiLiveClick(MistatsConstants.MiLive.VALUE_MI_LIVE_CLICK_CANCEL);
                        FragmentLiveReview.this.quitLiveRecordPreview(false);
                    }
                }, null, null, getString(R.string.snap_cancel), null);
                this.mExitDialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
                    /* class com.android.camera.fragment.dialog.FragmentLiveReview.AnonymousClass4 */

                    public void onDismiss(DialogInterface dialogInterface) {
                        FragmentLiveReview.this.mExitDialog = null;
                    }
                });
            }
        }
    }

    /* JADX WARNING: Removed duplicated region for block: B:29:0x00ac  */
    /* JADX WARNING: Removed duplicated region for block: B:31:0x00b0  */
    private void showShareSheet() {
        boolean z;
        if (!this.mPaused) {
            pausePlay(false, false);
            setUiState(4);
            List<ResolveInfo> queryIntentActivities = getContext().getPackageManager().queryIntentActivities(Util.getShareMediaIntent(getContext(), this.mSavePath, true), 65536);
            if (queryIntentActivities.isEmpty()) {
                Log.d(this.TAG, "no IntentActivities");
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
                Log.d(this.TAG, "no default share entry");
            } else if (((ShareInfo) arrayList.get(0)).index <= 1 || arrayList.size() >= 2) {
                z = false;
                if (!z) {
                    shareMore();
                    return;
                }
                arrayList.add(new ShareInfo(length + 1, "com.android.camera", "more", R.drawable.ic_live_share_more, R.string.accessibility_more));
                Collections.sort(arrayList, c.INSTANCE);
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
                Log.d(this.TAG, "not match default share strategy");
            }
            z = true;
            if (!z) {
            }
        }
    }

    private void showTime(List<ILive.ILiveSegmentData> list) {
        if (list != null && !list.isEmpty()) {
            long totalDuration = ILive.getTotalDuration(list);
            String str = this.TAG;
            Log.d(str, "showTime = " + totalDuration);
            this.mTimeView.setText(Util.millisecondToTimeString(Util.clamp(totalDuration, 1000, 15000), false, false));
            this.mTimeView.setVisibility(0);
        }
    }

    private void startSave(boolean z) {
        int i = this.mUiState;
        if (i != 3 && i != 4) {
            String str = this.TAG;
            Log.d(str, "skip save, caz ui state is " + getCurStateString());
        } else if (this.mComposeState != 1) {
            String str2 = this.TAG;
            Log.d(str2, "skip save, caz composer state is " + this.mComposeState);
        } else {
            if (z) {
                setUiState(6);
            } else {
                setUiState(8);
            }
            Log.d(this.TAG, "startSave");
            combineVideoAudio();
        }
    }

    private void startSaveToLocal() {
        if (!this.mSaved && this.mSavePath != null) {
            this.mSaved = true;
            this.mSaveContentValues.put("datetaken", Long.valueOf(System.currentTimeMillis()));
            if (getActivity() instanceof Camera) {
                ((Camera) getActivity()).getImageSaver().addVideo(this.mSavePath, this.mSaveContentValues, true);
            } else {
                Log.e(this.TAG, "can't add to db.");
            }
        }
    }

    public /* synthetic */ void a(ObservableEmitter observableEmitter) throws Exception {
        this.mUIStateEmitter = observableEmitter;
    }

    public /* synthetic */ void a(Integer num) throws Exception {
        Disposable disposable = this.mUIStateDisposable;
        if (disposable != null && !disposable.isDisposed()) {
            String str = this.TAG;
            Log.d(str, "UI state consumer get state : " + num);
            ActivityBase activityBase = (ActivityBase) getContext();
            int intValue = num.intValue();
            if (intValue == 0) {
                return;
            }
            if (intValue == 1 || intValue == 2) {
                if (activityBase.startFromKeyguard()) {
                    this.mPreviewShare.setVisibility(8);
                } else {
                    this.mPreviewShare.setVisibility(0);
                }
                this.mCameraSnapView.setVisibility(0);
                this.mPreviewCombine.setVisibility(0);
                this.mPreviewBack.setVisibility(0);
                showTime(this.mSegmentData);
                this.mPreviewStart.setVisibility(8);
                this.mSaveProgress.clearAnimation();
                this.mSaveProgress.setVisibility(8);
                this.mShareProgress.setVisibility(8);
                this.mCancelProgress.setVisibility(8);
                this.mShareLayout.setVisibility(4);
                this.mPreviewStartProgress.setVisibility(0);
            } else if (intValue == 3) {
                this.mPreviewCover.setVisibility(8);
                this.mPreviewStart.setVisibility(8);
                this.mPreviewStartProgress.setVisibility(8);
            } else if (intValue == 4) {
                this.mPreviewStart.setVisibility(0);
                this.mPreviewStartProgress.setVisibility(8);
                this.mPreviewCombine.setVisibility(0);
                this.mSaveProgress.clearAnimation();
                this.mSaveProgress.setVisibility(8);
                if (activityBase.startFromKeyguard()) {
                    this.mPreviewShare.setVisibility(8);
                } else {
                    this.mPreviewShare.setVisibility(0);
                }
                this.mShareProgress.setVisibility(8);
            } else if (intValue == 7) {
                this.mPreviewStart.setVisibility(0);
                this.mPreviewStartProgress.setVisibility(8);
                this.mPreviewShare.setVisibility(8);
                this.mShareProgress.setVisibility(0);
            } else if (intValue == 9) {
                this.mPreviewStart.setVisibility(8);
                this.mPreviewStartProgress.setVisibility(8);
                this.mPreviewCombine.setVisibility(8);
                RotateAnimation rotateAnimation = new RotateAnimation(0.0f, 360.0f, (float) (this.mSaveProgress.getLayoutParams().width / 2), (float) (this.mSaveProgress.getLayoutParams().height / 2));
                rotateAnimation.setDuration((long) getResources().getInteger(R.integer.post_process_duration));
                rotateAnimation.setInterpolator(new LinearInterpolator());
                rotateAnimation.setRepeatMode(1);
                rotateAnimation.setRepeatCount(-1);
                this.mSaveProgress.setAnimation(rotateAnimation);
                this.mSaveProgress.setVisibility(0);
            }
        }
    }

    public void attemptStartPlay() {
        if (this.mPlayerState == 1) {
            if (this.mTextureView.getSurfaceTexture() == null || this.mPlayer == null) {
                initTextureView();
            } else {
                startPlay(this.mTextureView.getSurfaceTexture());
            }
        }
    }

    public /* synthetic */ void b(ObservableEmitter observableEmitter) throws Exception {
        this.mPlayStateEmitter = observableEmitter;
    }

    public /* synthetic */ void b(Integer num) throws Exception {
        Disposable disposable = this.mPlayStateDisposable;
        if (disposable == null || disposable.isDisposed()) {
        }
    }

    public /* synthetic */ void c(ObservableEmitter observableEmitter) throws Exception {
        this.mComposeStateEmitter = observableEmitter;
    }

    public /* synthetic */ void c(Integer num) throws Exception {
        Disposable disposable = this.mComposeStateDisposable;
        if (disposable != null && !disposable.isDisposed()) {
            int intValue = num.intValue();
            if (intValue == -1) {
                setUiState(4);
            } else if (intValue == 2) {
                int i = this.mUiState;
                if (i == 6) {
                    setUiState(7);
                } else if (i == 8) {
                    setUiState(9);
                }
            } else if (intValue == 3) {
                this.mSavePath = this.mSaveContentValues.getAsString("_data");
                int i2 = this.mUiState;
                if (i2 == 9) {
                    startSaveToLocal();
                    quitLiveRecordPreview(true);
                } else if (i2 == 7) {
                    startSaveToLocal();
                } else {
                    String str = this.TAG;
                    Log.d(str, "onComposed error, state : " + getCurStateString());
                    setUiState(4);
                }
            }
        }
    }

    public void combineVideoAudio() {
        ContentValues contentValues = this.mSaveContentValues;
        if (contentValues != null && this.mSavePath == null) {
            String asString = contentValues.getAsString("_data");
            MiLivePlayer miLivePlayer = this.mPlayer;
            if (miLivePlayer != null) {
                miLivePlayer.stopPlayer();
                this.mPlayer.startCompose(asString);
            }
        }
    }

    @Override // com.android.camera.fragment.BaseFragment
    public int getFragmentInto() {
        return 1048561;
    }

    /* access modifiers changed from: protected */
    @Override // com.android.camera.fragment.BaseFragment
    public int getLayoutResourceId() {
        return R.layout.fragment_live_record_preview;
    }

    @Override // com.android.camera.protocol.ModeProtocol.MiLivePlayerControl
    public ContentValues getSaveContentValues() {
        return this.mSaveContentValues;
    }

    /* access modifiers changed from: protected */
    @Override // com.android.camera.fragment.BaseFragment
    public void initView(View view) {
        Log.d(this.TAG, "initView");
        this.mRootView = view;
        this.mTopLayout = (ViewGroup) view.findViewById(R.id.live_preview_top);
        ViewGroup.MarginLayoutParams marginLayoutParams = (ViewGroup.MarginLayoutParams) this.mTopLayout.getLayoutParams();
        marginLayoutParams.topMargin = Util.sTopMargin;
        marginLayoutParams.height = Util.sTopBarHeight;
        this.mPreviewLayout = (FrameLayout) view.findViewById(R.id.live_preview_layout);
        this.mPreviewStartProgress = (ProgressBar) view.findViewById(R.id.live_concat_progress);
        this.mPreviewStartProgress.setVisibility(0);
        this.mSaveProgress = (ImageView) view.findViewById(R.id.live_save_progress);
        this.mSaveProgress.setVisibility(8);
        this.mShareProgress = (ProgressBar) view.findViewById(R.id.live_share_progress);
        this.mShareProgress.setVisibility(8);
        this.mCancelProgress = (ProgressBar) view.findViewById(R.id.live_back_progress);
        this.mCancelProgress.setVisibility(8);
        this.mTimeView = (TextView) view.findViewById(R.id.live_preview_recording_time_view);
        this.mCameraSnapView = (CameraSnapView) view.findViewById(R.id.live_preview_save_circle);
        this.mCameraSnapView.setParameters(PaintConditionReferred.create(this.mCurrentMode));
        this.mCameraSnapView.hideCirclePaintItem();
        this.mCameraSnapView.hideRoundPaintItem();
        this.mCameraSnapView.setSnapClickEnable(false);
        this.mPreviewCombine = (LottieAnimationView) view.findViewById(R.id.live_preview_save);
        this.mPreviewCombine.setScale(0.38f);
        this.mPreviewCombine.k(R.raw.vv_save);
        this.mPreviewCombine.T();
        this.mPreviewBack = (ImageView) view.findViewById(R.id.live_preview_back);
        this.mPreviewShare = (ImageView) view.findViewById(R.id.live_preview_share);
        this.mPreviewStart = (ImageView) view.findViewById(R.id.live_preview_play);
        this.mPreviewStart.setVisibility(8);
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
        FolmeUtils.touchScaleTint(this.mPreviewBack, this.mPreviewShare, this.mPreviewStart);
        FolmeUtils.touchScale(this.mPreviewCombine);
        if (((Camera) getActivity()).startFromKeyguard()) {
            this.mPreviewShare.setVisibility(8);
        } else {
            this.mPreviewShare.setVisibility(0);
        }
        this.mBottomActionView = (FrameLayout) view.findViewById(R.id.live_preview_bottom_action);
        ((ViewGroup.MarginLayoutParams) this.mBottomActionView.getLayoutParams()).height = Util.getBottomHeight();
        this.mBottomActionView.setOnClickListener(this);
        this.mBottomLayout = (RelativeLayout) view.findViewById(R.id.live_preview_bottom_parent);
        ViewGroup.MarginLayoutParams marginLayoutParams2 = (ViewGroup.MarginLayoutParams) this.mBottomLayout.getLayoutParams();
        marginLayoutParams2.height = Math.round(((float) Util.sBottomBarHeight) * 0.7f);
        marginLayoutParams2.bottomMargin = Util.sBottomMargin;
        marginLayoutParams2.topMargin = Math.round(((float) Util.sBottomBarHeight) * 0.3f);
        ((ViewGroup.MarginLayoutParams) this.mTimeView.getLayoutParams()).height = Math.round(((float) Util.sBottomBarHeight) * 0.3f);
        this.mBottomActionView.setBackgroundResource(R.color.fullscreen_background);
        this.mRootView.setVisibility(8);
    }

    @Override // com.android.camera.protocol.ModeProtocol.MiLivePlayerControl
    public boolean isShowing() {
        return this.mUiState != 0;
    }

    @Override // com.android.camera.fragment.BaseFragment, com.android.camera.animation.AnimationDelegate.AnimationResource
    public void notifyAfterFrameAvailable(int i) {
        super.notifyAfterFrameAvailable(i);
        String str = this.TAG;
        Log.d(str, "notifyAfterFrameAvailable ui state : " + getCurStateString());
        if (!showCover() && this.mPlayerState == 1) {
            attemptStartPlay();
            setUiState(10);
        }
    }

    @Override // com.android.camera.protocol.ModeProtocol.HandleBackTrace
    public boolean onBackEvent(int i) {
        if (this.mUiState == 0) {
            return false;
        }
        if (hideShareSheet()) {
            return true;
        }
        showExitConfirm();
        return true;
    }

    public void onClick(View view) {
        String str = this.TAG;
        Log.d(str, "onClick view" + view + ", ui state = " + getCurStateString());
        switch (view.getId()) {
            case R.id.live_preview_back:
                showExitConfirm();
                return;
            case R.id.live_preview_bottom_action:
            case R.id.live_preview_bottom_parent:
            case R.id.live_preview_recording_time_view:
            case R.id.live_preview_top:
            case R.id.live_record_preview:
            case R.id.live_save_progress:
            case R.id.live_share_icon:
            default:
                return;
            case R.id.live_preview_layout:
                if (this.mUiState == 3) {
                    pausePlay(false, true);
                    return;
                }
                return;
            case R.id.live_preview_play:
                if (this.mUiState == 4) {
                    hideShareSheet();
                    int i = this.mPlayerState;
                    if (i == 1) {
                        attemptStartPlay();
                        return;
                    } else if (i == 4) {
                        resumePlay();
                        return;
                    } else {
                        Log.d(this.TAG, "skip start play~");
                        return;
                    }
                } else {
                    return;
                }
            case R.id.live_preview_save:
            case R.id.live_preview_save_circle:
                int i2 = this.mUiState;
                if (i2 == 4 || i2 == 3) {
                    CameraStatUtils.trackMiLiveClick(MistatsConstants.MiLive.VALUE_MI_LIVE_CLICK_DONE);
                    if (this.mSavedUri != null) {
                        quitLiveRecordPreview(true);
                        return;
                    } else if (this.mSavePath != null) {
                        startSaveToLocal();
                        quitLiveRecordPreview(true);
                        return;
                    } else {
                        startSave(false);
                        return;
                    }
                } else {
                    return;
                }
            case R.id.live_preview_share:
                int i3 = this.mUiState;
                if (i3 == 4 || i3 == 3) {
                    CameraStatUtils.trackMiLiveClick(MistatsConstants.MiLive.VALUE_MI_LIVE_CLICK_SHARE);
                    if (this.mSavePath != null && this.mSavedUri == null) {
                        setUiState(7);
                        startSaveToLocal();
                        return;
                    } else if (!checkAndShare()) {
                        startSave(true);
                        return;
                    } else {
                        return;
                    }
                } else {
                    return;
                }
            case R.id.live_share_cancel:
                hideShareSheet();
                return;
            case R.id.live_share_item:
                ShareInfo shareInfo = (ShareInfo) view.getTag();
                if (shareInfo.className.equals("more")) {
                    shareMore();
                } else {
                    Util.startShareMedia(shareInfo.packageName, shareInfo.className, getContext(), this.mSavePath, true);
                }
                hideShareSheet();
                return;
        }
    }

    @Override // androidx.fragment.app.Fragment
    public void onDestroy() {
        super.onDestroy();
        ImageView imageView = this.mSaveProgress;
        if (imageView != null) {
            imageView.clearAnimation();
        }
    }

    @Override // com.android.camera.protocol.ModeProtocol.MiLivePlayerControl
    public void onLiveSaveToLocalFinished(Uri uri, String str) {
        this.mSavedUri = uri;
        if (this.mUiState == 7) {
            shareMore();
        }
    }

    @Override // androidx.fragment.app.Fragment
    public void onPause() {
        super.onPause();
        String str = this.TAG;
        Log.d(str, "onPause ui state :" + getCurStateString());
        pausePlay(true, false);
        hideShareSheet();
        this.mPaused = true;
    }

    @Override // com.android.camera.fragment.BaseFragment, androidx.fragment.app.Fragment
    public void onResume() {
        super.onResume();
        String str = this.TAG;
        Log.d(str, "onResume ui state :" + getCurStateString());
        this.mPaused = false;
        if (!showCover() && this.mPlayerState == 1) {
            attemptStartPlay();
            setUiState(10);
        }
    }

    public void pausePlay(boolean z, boolean z2) {
        int i = this.mUiState;
        if (i == 3 || i == 2) {
            setUiState(4);
            MiLivePlayer miLivePlayer = this.mPlayer;
            if (miLivePlayer == null) {
                return;
            }
            if (z) {
                miLivePlayer.stopPlayer();
            } else {
                miLivePlayer.pausePlayer();
            }
        } else if (isComposing()) {
            setUiState(4);
        } else if (this.mUiState == 10) {
            setUiState(4);
            MiLivePlayer miLivePlayer2 = this.mPlayer;
            if (miLivePlayer2 != null) {
                miLivePlayer2.pausePlayer();
            }
        } else {
            String str = this.TAG;
            Log.d(str, "skip pause play, caz state is " + getCurStateString());
        }
    }

    @Override // com.android.camera.protocol.ModeProtocol.MiLivePlayerControl
    public void prepare(ContentValues contentValues, List<ILive.ILiveSegmentData> list, String str) {
        if (this.mUiState != 0) {
            String str2 = this.TAG;
            Log.d(str2, "prepare fail, state is " + getCurStateString());
            return;
        }
        this.mSaved = false;
        this.mSavedUri = null;
        this.mSaveContentValues = contentValues;
        this.mSegmentData = new ArrayList(list);
        this.mAudioString = str;
        String[] split = contentValues.getAsString("resolution").split("x");
        this.mVideoWidth = Integer.valueOf(split[0]).intValue();
        this.mVideoHeight = Integer.valueOf(split[1]).intValue();
        MiLivePlayer miLivePlayer = this.mPlayer;
        if (miLivePlayer != null) {
            miLivePlayer.init(this.mVideoWidth, this.mVideoHeight, this.mTextureView.getWidth(), this.mTextureView.getHeight(), this.mSegmentData, this.mAudioString);
            startPlay(this.mTextureView.getSurfaceTexture());
            return;
        }
        setUiState(1);
    }

    public void quitLiveRecordPreview(boolean z) {
        String str = this.TAG;
        Log.d(str, "quitLiveRecordPreview " + z);
        ModeProtocol.CameraAction cameraAction = (ModeProtocol.CameraAction) ModeCoordinatorImpl.getInstance().getAttachProtocol(161);
        if (cameraAction == null) {
            Log.d(this.TAG, "concat error, action null");
            return;
        }
        if (z) {
            cameraAction.onReviewDoneClicked();
        } else {
            cameraAction.onReviewCancelClicked();
        }
        if (getActivity().getVolumeControlStream() != 1) {
            getActivity().setVolumeControlStream(1);
        }
    }

    /* access modifiers changed from: protected */
    @Override // com.android.camera.fragment.BaseFragment
    @CallSuper
    public void register(ModeProtocol.ModeCoordinator modeCoordinator) {
        super.register(modeCoordinator);
        Log.d(this.TAG, "register");
        modeCoordinator.attachProtocol(242, this);
        registerBackStack(modeCoordinator, this);
    }

    @Override // com.android.camera.protocol.ModeProtocol.MiLivePlayerControl
    public void release() {
        Log.d(this.TAG, "release");
        Disposable disposable = this.mUIStateDisposable;
        if (disposable != null) {
            disposable.dispose();
            this.mUIStateDisposable = null;
        }
        Disposable disposable2 = this.mPlayStateDisposable;
        if (disposable2 != null) {
            disposable2.dispose();
            this.mPlayStateDisposable = null;
        }
        Disposable disposable3 = this.mComposeStateDisposable;
        if (disposable3 != null) {
            disposable3.dispose();
            this.mComposeStateDisposable = null;
        }
        MiLivePlayer miLivePlayer = this.mPlayer;
        if (miLivePlayer != null) {
            miLivePlayer.release();
            this.mPlayer = null;
        }
        List<ILive.ILiveSegmentData> list = this.mSegmentData;
        if (list != null) {
            list.clear();
        }
        setUiState(0);
    }

    public void resumePlay() {
        if (this.mUiState != 4) {
            String str = this.TAG;
            Log.d(str, "skip resume play, caz state is " + getCurStateString());
            return;
        }
        MiLivePlayer miLivePlayer = this.mPlayer;
        if (miLivePlayer != null) {
            miLivePlayer.resumePlayer();
        }
        setUiState(5);
    }

    @Override // com.android.camera.protocol.ModeProtocol.MiLivePlayerControl
    public void show() {
        this.mRootView.setVisibility(0);
        initTextureView();
    }

    @Override // com.android.camera.protocol.ModeProtocol.MiLivePlayerControl
    public void startLiveRecordSaving() {
        if (isAdded() && this.mCameraSnapView.getVisibility() == 0) {
            this.mCameraSnapView.performClick();
        }
    }

    public void startPlay(SurfaceTexture surfaceTexture) {
        if (this.mPlayer != null && !this.mPaused && this.mPlayerState == 1) {
            setUiState(2);
            this.mPlayer.cancelCompose();
            this.mPlayer.startPlayer(surfaceTexture);
        }
    }

    /* access modifiers changed from: protected */
    @Override // com.android.camera.fragment.BaseFragment
    @CallSuper
    public void unRegister(ModeProtocol.ModeCoordinator modeCoordinator) {
        super.unRegister(modeCoordinator);
        Log.d(this.TAG, "unRegister");
        modeCoordinator.detachProtocol(242, this);
        unRegisterBackStack(modeCoordinator, this);
    }
}
