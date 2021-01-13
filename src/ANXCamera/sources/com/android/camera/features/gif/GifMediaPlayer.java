package com.android.camera.features.gif;

import android.content.Context;
import android.graphics.SurfaceTexture;
import android.os.Handler;
import android.os.HandlerThread;
import android.text.TextUtils;
import android.view.Surface;
import android.view.TextureView;
import android.widget.ImageView;
import android.widget.ProgressBar;
import com.android.camera.constant.DurationConstant;
import com.android.camera.constant.FastMotionConstant;
import com.android.camera.data.DataRepository;
import com.android.camera.features.mimoji2.bean.MimojiInfo2;
import com.android.camera.features.mimoji2.utils.MimojiViewUtil;
import com.android.camera.log.Log;
import com.android.camera.module.impl.component.FileUtils;
import com.android.camera.protocol.ModeCoordinatorImpl;
import com.android.camera.protocol.ModeProtocol;
import com.android.camera.statistic.CameraStatUtils;
import com.android.camera.storage.Storage;
import com.xiaomi.MediaRecord.SystemUtil;
import com.xiaomi.Video2GifEditer.EffectMediaPlayer;
import com.xiaomi.Video2GifEditer.EffectNotifier;
import com.xiaomi.Video2GifEditer.EffectType;
import com.xiaomi.Video2GifEditer.MediaEffect;
import com.xiaomi.Video2GifEditer.MediaEffectGraph;
import com.xiaomi.Video2GifEditer.MediaProcess;
import com.xiaomi.Video2GifEditer.PreViewStatus;
import com.xiaomi.Video2GifPlayer.enums.PlayerWorkingMode;
import java.io.File;
import java.util.HashMap;
import miui.telephony.phonenumber.CountryCodeConverter;

public class GifMediaPlayer implements TextureView.SurfaceTextureListener {
    private static final String TAG = "MimojiGifMediaPlayer";
    private static volatile boolean mIsLibLoaded = false;
    private static final MiLibLoader sLocalLibLoader = new MiLibLoader() {
        /* class com.android.camera.features.gif.GifMediaPlayer.AnonymousClass1 */

        @Override // com.android.camera.features.gif.MiLibLoader
        public void loadLibrary(String str) throws UnsatisfiedLinkError, SecurityException {
            System.loadLibrary(str);
        }
    };
    private int mCheckErrorState = 0;
    private ProgressBar mConcatProgressBar;
    private Context mContext;
    private long mDuration;
    private EffectNotifier mEffectNotifier = new EffectNotifier() {
        /* class com.android.camera.features.gif.GifMediaPlayer.AnonymousClass2 */

        public /* synthetic */ void Fc() {
            if (GifMediaPlayer.this.mIsNeedShowProgress) {
                GifMediaPlayer.this.mIsNeedShowProgress = false;
                GifMediaPlayer.this.setIsComposing(false);
            }
        }

        @Override // com.xiaomi.Video2GifEditer.EffectNotifier
        public void OnHaveFace() {
        }

        @Override // com.xiaomi.Video2GifEditer.EffectNotifier
        public void OnReadyNow() {
            Log.d(GifMediaPlayer.TAG, "mimoji void OnReadyNow[]");
            GifMediaPlayer.this.mMainHandler.post(new f(this));
        }

        @Override // com.xiaomi.Video2GifEditer.EffectNotifier
        public void OnReceiveFailed() {
        }

        @Override // com.xiaomi.Video2GifEditer.EffectNotifier
        public void OnReceiveFinish() {
        }
    };
    private EffectMediaPlayer mEffectPlayer;
    private boolean mEnableReverseFilter;
    private boolean mEnableSpeedFilter;
    private boolean mEnableSubtitleFilter;
    private boolean mEnableVideoSegmentFilter;
    private GifEditLayout mGifEditLayout;
    private HandlerThread mHandlerThread;
    private boolean mIsComposing;
    private boolean mIsHideVideoSegment;
    private boolean mIsNeedShowProgress;
    private boolean mIsSurFaceReady;
    private Handler mLoadHandler;
    private Handler mMainHandler;
    private MediaEffectGraph mMediaEffectGraph;
    public MiMediaProcessCallback mMediaProcessCallback = new MiMediaProcessCallback() {
        /* class com.android.camera.features.gif.GifMediaPlayer.AnonymousClass3 */

        @Override // com.xiaomi.Video2GifEditer.MediaProcess.Callback
        public void OnConvertProgress(int i) {
            if (i == 100) {
                GifMediaPlayer.this.onSuccess(getTarget());
                Log.d(GifMediaPlayer.TAG, "mimoji void OnConvertProgress[progressValue] time : " + (System.currentTimeMillis() - GifMediaPlayer.this.time));
                return;
            }
            Log.d(GifMediaPlayer.TAG, "mimoji void OnConvertProgress[progressValue]  " + i);
        }
    };
    private ImageView mPreviewCoverView;
    private long mReverseFilterID;
    private long mSourceID;
    private long mSpeedFilterID;
    private long mSubtitleFilterID;
    private Surface mSurface;
    private TextureView mTextureView;
    private String mVideoPath;
    private long mVideoSegmentFilterID;
    private long time;

    /* access modifiers changed from: package-private */
    /* renamed from: com.android.camera.features.gif.GifMediaPlayer$4  reason: invalid class name */
    public static /* synthetic */ class AnonymousClass4 {
        static final /* synthetic */ int[] $SwitchMap$com$xiaomi$Video2GifEditer$EffectType = new int[EffectType.values().length];

        /* JADX WARNING: Can't wrap try/catch for region: R(10:0|1|2|3|4|5|6|7|8|10) */
        /* JADX WARNING: Can't wrap try/catch for region: R(8:0|1|2|3|4|5|6|(3:7|8|10)) */
        /* JADX WARNING: Failed to process nested try/catch */
        /* JADX WARNING: Missing exception handler attribute for start block: B:3:0x0014 */
        /* JADX WARNING: Missing exception handler attribute for start block: B:5:0x001f */
        /* JADX WARNING: Missing exception handler attribute for start block: B:7:0x002a */
        static {
            $SwitchMap$com$xiaomi$Video2GifEditer$EffectType[EffectType.ReverseFilter.ordinal()] = 1;
            $SwitchMap$com$xiaomi$Video2GifEditer$EffectType[EffectType.VideoSegmentFilter.ordinal()] = 2;
            $SwitchMap$com$xiaomi$Video2GifEditer$EffectType[EffectType.SubtitleFilter.ordinal()] = 3;
            try {
                $SwitchMap$com$xiaomi$Video2GifEditer$EffectType[EffectType.SetptsExtFilter.ordinal()] = 4;
            } catch (NoSuchFieldError unused) {
            }
        }
    }

    /* access modifiers changed from: private */
    public abstract class MiMediaProcessCallback implements MediaProcess.Callback {
        private String target;

        private MiMediaProcessCallback() {
        }

        public String getTarget() {
            return this.target;
        }

        public void setTarget(String str) {
            this.target = str;
        }
    }

    public GifMediaPlayer(Context context, boolean z) {
        this.mContext = context;
        this.mMainHandler = new Handler();
        this.mHandlerThread = new HandlerThread("gif");
        this.mHandlerThread.start();
        this.mLoadHandler = new Handler(this.mHandlerThread.getLooper());
        this.mIsSurFaceReady = false;
        this.mIsNeedShowProgress = true;
        this.mIsHideVideoSegment = z;
        mIsLibLoaded = false;
        initPlayer(sLocalLibLoader);
    }

    private long CreateFilterByType(int i) {
        int i2 = AnonymousClass4.$SwitchMap$com$xiaomi$Video2GifEditer$EffectType[EffectType.values()[i].ordinal()];
        if (i2 == 1) {
            return MediaEffect.CreateEffect(EffectType.ReverseFilter);
        }
        if (i2 == 2) {
            return MediaEffect.CreateEffect(EffectType.VideoSegmentFilter);
        }
        if (i2 == 3) {
            return MediaEffect.CreateEffect(EffectType.SubtitleFilter);
        }
        if (i2 != 4) {
            return 0;
        }
        HashMap hashMap = new HashMap();
        hashMap.put("speed", "2");
        long CreateEffect = MediaEffect.CreateEffect(EffectType.SetptsExtFilter);
        MediaEffect.SetParamsForEffect(EffectType.SetptsExtFilter, CreateEffect, hashMap);
        return CreateEffect;
    }

    private void dealAllFilter() {
        this.mIsNeedShowProgress = false;
        if (this.mEnableReverseFilter) {
            this.mReverseFilterID = CreateFilterByType(4);
            long j = this.mReverseFilterID;
            if (j != 0) {
                this.mMediaEffectGraph.AddEffect(j, this.mSourceID, true);
            }
        } else {
            long j2 = this.mReverseFilterID;
            if (j2 != 0) {
                this.mMediaEffectGraph.RemoveEffect(j2, this.mSourceID, true);
                this.mReverseFilterID = 0;
            }
        }
        if (this.mEnableSpeedFilter) {
            this.mSpeedFilterID = CreateFilterByType(7);
            long j3 = this.mSpeedFilterID;
            if (j3 != 0) {
                this.mMediaEffectGraph.AddEffect(j3, this.mSourceID, true);
            }
        } else {
            long j4 = this.mSpeedFilterID;
            if (j4 != 0) {
                this.mMediaEffectGraph.RemoveEffect(j4, this.mSourceID, true);
                this.mSpeedFilterID = 0;
            }
        }
        if (this.mEnableVideoSegmentFilter && this.mVideoSegmentFilterID != 0) {
            this.mIsNeedShowProgress = true;
            HashMap hashMap = new HashMap();
            hashMap.put("show_video_segment", String.valueOf(this.mEnableVideoSegmentFilter ? 1 : 0));
            MediaEffect.SetParamsForEffect(EffectType.VideoSegmentFilter, this.mVideoSegmentFilterID, hashMap);
        }
        this.mMainHandler.post(new m(this));
    }

    private void initPlayer(MiLibLoader miLibLoader) {
        if (!mIsLibLoaded) {
            SystemUtil.Init(this.mContext, 123);
        }
        loadLibrariesOnce(miLibLoader);
        this.mVideoPath = null;
        if (this.mMediaEffectGraph == null) {
            this.mMediaEffectGraph = new MediaEffectGraph();
            this.mMediaEffectGraph.ConstructMediaEffectGraph();
            this.mMediaEffectGraph.SetAudioMute(true);
        }
        if (this.mEffectPlayer == null) {
            this.mEffectPlayer = new EffectMediaPlayer(this.mMediaEffectGraph);
            this.mEffectPlayer.SetPlayLoop(true);
            this.mEffectPlayer.ConstructMediaPlayer(PlayerWorkingMode.PlayerWorkingDisableAudioDeviceMode);
        }
    }

    public static void loadLibrariesOnce(MiLibLoader miLibLoader) {
        synchronized (GifMediaPlayer.class) {
            if (!mIsLibLoaded) {
                if (miLibLoader == null) {
                    miLibLoader = sLocalLibLoader;
                }
                miLibLoader.loadLibrary("vvc++_shared");
                miLibLoader.loadLibrary("mimoji_video2gif");
                mIsLibLoaded = true;
            }
        }
    }

    private void onError() {
        this.mMainHandler.post(new j(this));
    }

    /* access modifiers changed from: private */
    /* access modifiers changed from: public */
    private void onSuccess(String str) {
        this.mMainHandler.post(new n(this, str));
    }

    private void releaseEffectPlayer() {
        EffectMediaPlayer effectMediaPlayer = this.mEffectPlayer;
        if (effectMediaPlayer != null) {
            effectMediaPlayer.StopPreView();
            this.mEffectPlayer.DestructMediaPlayer();
            this.mEffectPlayer.SetViewSurface(null);
            this.mEffectPlayer = null;
        }
        TextureView textureView = this.mTextureView;
        if (textureView != null) {
            textureView.setSurfaceTextureListener(null);
            this.mTextureView.setVisibility(8);
        }
    }

    private void releaseMediaGraph() {
        MediaEffectGraph mediaEffectGraph = this.mMediaEffectGraph;
        if (mediaEffectGraph != null) {
            mediaEffectGraph.DestructMediaEffectGraph();
            this.mMediaEffectGraph = null;
        }
    }

    private void releaseVideoSegmentMediaGraph() {
        long j = this.mVideoSegmentFilterID;
        if (j != 0) {
            MediaEffect.DestoryEffect(j);
            this.mVideoSegmentFilterID = 0;
        }
    }

    public /* synthetic */ void B(boolean z) {
        if (z) {
            this.mReverseFilterID = CreateFilterByType(4);
            long j = this.mReverseFilterID;
            if (j != 0) {
                this.mMediaEffectGraph.AddEffect(j, this.mSourceID, true);
            }
            this.mEnableReverseFilter = true;
        } else {
            long j2 = this.mReverseFilterID;
            if (j2 != 0) {
                this.mMediaEffectGraph.RemoveEffect(j2, this.mSourceID, true);
                this.mReverseFilterID = 0;
            }
            this.mEnableReverseFilter = false;
        }
        this.mMainHandler.post(new e(this));
    }

    public /* synthetic */ void C(boolean z) {
        if (z) {
            this.mSpeedFilterID = CreateFilterByType(7);
            long j = this.mSpeedFilterID;
            if (j != 0) {
                this.mMediaEffectGraph.AddEffect(j, this.mSourceID, true);
            }
        } else {
            long j2 = this.mSpeedFilterID;
            if (j2 != 0) {
                this.mMediaEffectGraph.RemoveEffect(j2, this.mSourceID, true);
                this.mSpeedFilterID = 0;
            }
        }
        this.mMainHandler.post(new l(this));
    }

    public /* synthetic */ void D(boolean z) {
        this.mEnableVideoSegmentFilter = z;
        if (this.mVideoSegmentFilterID != 0) {
            this.mIsNeedShowProgress = this.mEnableVideoSegmentFilter;
            HashMap hashMap = new HashMap();
            hashMap.put("show_video_segment", String.valueOf(z ? 1 : 0));
            MediaEffect.SetParamsForEffect(EffectType.VideoSegmentFilter, this.mVideoSegmentFilterID, hashMap);
        }
        this.mMainHandler.post(new p(this));
    }

    public /* synthetic */ void E(boolean z) {
        if (!MimojiViewUtil.getViewIsVisible(this.mGifEditLayout) && !z) {
            MimojiViewUtil.setViewVisible(this.mGifEditLayout, true);
        }
        this.mGifEditLayout.setIsAllowInput(!z);
        if (MimojiViewUtil.getViewIsVisible(this.mConcatProgressBar) && !z) {
            MimojiViewUtil.setViewVisible(this.mConcatProgressBar, false);
        }
    }

    public /* synthetic */ void Gc() {
        EffectMediaPlayer effectMediaPlayer;
        if (this.mIsNeedShowProgress) {
            setIsComposing(true);
            MimojiViewUtil.setViewVisible(this.mConcatProgressBar, true);
            this.mGifEditLayout.setIsAllowInput(false);
        } else {
            setIsComposing(false);
            this.mIsNeedShowProgress = true;
        }
        if (!FileUtils.checkFileConsist(this.mVideoPath) || (effectMediaPlayer = this.mEffectPlayer) == null) {
            onError();
            return;
        }
        effectMediaPlayer.StartPreView();
        this.mEffectPlayer.SetPlayLoop(true);
    }

    public /* synthetic */ void Hc() {
        EffectMediaPlayer effectMediaPlayer;
        if (!FileUtils.checkFileConsist(this.mVideoPath) || (effectMediaPlayer = this.mEffectPlayer) == null) {
            onError();
            return;
        }
        effectMediaPlayer.ResumePreView();
        setIsComposing(false);
    }

    public /* synthetic */ void Ic() {
        EffectMediaPlayer effectMediaPlayer;
        if (!FileUtils.checkFileConsist(this.mVideoPath) || (effectMediaPlayer = this.mEffectPlayer) == null) {
            onError();
            return;
        }
        effectMediaPlayer.ResumePreView();
        setIsComposing(false);
    }

    public /* synthetic */ void Jc() {
        EffectMediaPlayer effectMediaPlayer;
        if (!FileUtils.checkFileConsist(this.mVideoPath) || (effectMediaPlayer = this.mEffectPlayer) == null) {
            onError();
            return;
        }
        effectMediaPlayer.ResumePreView();
        if (!this.mEnableVideoSegmentFilter) {
            setIsComposing(false);
        }
    }

    public /* synthetic */ void Kc() {
        ModeProtocol.MimojiGifEditor mimojiGifEditor = (ModeProtocol.MimojiGifEditor) ModeCoordinatorImpl.getInstance().getAttachProtocol(251);
        if (mimojiGifEditor != null) {
            mimojiGifEditor.coverGifError();
        }
        setIsComposing(false);
    }

    public /* synthetic */ void Lc() {
        if (this.mEnableVideoSegmentFilter) {
            HashMap hashMap = new HashMap();
            hashMap.put("show_video_segment", String.valueOf(1));
            MediaProcess.AddVideoSegmentFilter(this.mVideoSegmentFilterID, hashMap);
        }
        if (this.mEnableReverseFilter) {
            MediaProcess.AddReverseFilter();
        }
        String str = null;
        GifEditLayout gifEditLayout = this.mGifEditLayout;
        if (gifEditLayout != null && !TextUtils.isEmpty(gifEditLayout.getResult())) {
            HashMap hashMap2 = new HashMap();
            Log.i(TAG, "subtile:" + this.mGifEditLayout.getResult());
            hashMap2.put("textname", this.mGifEditLayout.getResult());
            hashMap2.put("posx", "200");
            hashMap2.put("posy", CountryCodeConverter.LT);
            hashMap2.put("subtitle_width", FastMotionConstant.FAST_MOTION_DURATION_100);
            hashMap2.put("subtitle_height", FastMotionConstant.FAST_MOTION_DURATION_100);
            hashMap2.put("font_size", CountryCodeConverter.HU);
            MediaProcess.AddSubtitleFilter(hashMap2);
            str = this.mGifEditLayout.getResult();
        }
        float f = 1.0f;
        if (this.mEnableSpeedFilter) {
            f = 2.0f;
        }
        String str2 = this.mVideoPath;
        String str3 = Storage.DIRECTORY + File.separator + FileUtils.createtFileName("MIMOJI_GIF_", "gif");
        this.mMediaProcessCallback.setTarget(str3);
        MimojiInfo2 currentMimojiInfo = DataRepository.dataItemLive().getMimojiStatusManager2().getCurrentMimojiInfo();
        CameraStatUtils.trackMimojiSaveGif(str, currentMimojiInfo == null ? "null" : currentMimojiInfo.mAvatarTemplatePath, this.mDuration + "");
        MediaProcess.ConvertGif(str2, str3, 20, DurationConstant.DURATION_RESET_FALLBACK, 0, 5000, f, this.mMediaProcessCallback);
    }

    public /* synthetic */ void Q(String str) {
        ModeProtocol.MimojiGifEditor mimojiGifEditor = (ModeProtocol.MimojiGifEditor) ModeCoordinatorImpl.getInstance().getAttachProtocol(251);
        if (mimojiGifEditor != null) {
            mimojiGifEditor.coverGifSuccess(str);
        }
        setIsComposing(false);
    }

    public /* synthetic */ void R(String str) {
        initPlayer(sLocalLibLoader);
        this.mVideoPath = str;
        if (!FileUtils.checkFileConsist(this.mVideoPath) || this.mEffectPlayer == null) {
            Log.d(TAG, "mimoji void playCameraRecord[] null");
            onError();
        } else if (this.mSurface == null) {
            Log.e(TAG, "mimoji void playCameraRecord[]  mSurface == nul");
        } else {
            this.mSourceID = this.mMediaEffectGraph.AddVideoSource(this.mVideoPath, true);
            if (!this.mIsHideVideoSegment) {
                this.mVideoSegmentFilterID = CreateFilterByType(20);
                long j = this.mVideoSegmentFilterID;
                if (j != 0) {
                    this.mMediaEffectGraph.AddEffect(j, this.mSourceID, true);
                    MediaEffect.SetFilterCallback(this.mVideoSegmentFilterID, this.mEffectNotifier);
                }
            } else {
                this.mVideoSegmentFilterID = 0;
            }
            dealAllFilter();
        }
    }

    public void cancelVideo2gif() {
        if (FileUtils.checkFileConsist(this.mVideoPath) && this.mIsComposing) {
            MediaProcess.CancelGifConvert(this.mVideoPath);
        }
    }

    public void enableReverseFilter(boolean z) {
        this.mEnableReverseFilter = z;
        setIsComposing(true);
        this.mEffectPlayer.PausePreView();
        this.mLoadHandler.post(new k(this, z));
    }

    public void enableSpeedFilter(boolean z) {
        this.mEnableSpeedFilter = z;
        setIsComposing(true);
        this.mEffectPlayer.PausePreView();
        this.mLoadHandler.post(new i(this, z));
    }

    public void enableVideoSegmentFilter(boolean z) {
        if (isEnable()) {
            if (z && this.mIsNeedShowProgress) {
                MimojiViewUtil.setViewVisible(this.mConcatProgressBar, true);
                this.mGifEditLayout.setIsAllowInput(false);
            }
            setIsComposing(true);
            this.mEffectPlayer.PausePreView();
            this.mLoadHandler.post(new h(this, z));
        }
    }

    public Surface getSurface() {
        return this.mSurface;
    }

    public boolean isComposing() {
        return this.mIsComposing;
    }

    public boolean isEnable() {
        return !TextUtils.isEmpty(this.mVideoPath) && this.mEffectPlayer != null && this.mMediaEffectGraph != null && this.mIsSurFaceReady;
    }

    public boolean isPlaying() {
        EffectMediaPlayer effectMediaPlayer = this.mEffectPlayer;
        return effectMediaPlayer != null && effectMediaPlayer.GetPreViewStatus() == PreViewStatus.PreViewPlaying;
    }

    public void onSurfaceTextureAvailable(SurfaceTexture surfaceTexture, int i, int i2) {
        Log.d(TAG, "mimoji void onSurfaceTextureAvailable[surface, width, height]");
        if (this.mSurface == null) {
            this.mSurface = new Surface(surfaceTexture);
            EffectMediaPlayer effectMediaPlayer = this.mEffectPlayer;
            if (effectMediaPlayer != null) {
                effectMediaPlayer.SetViewSurface(this.mSurface);
            }
            if (!TextUtils.isEmpty(this.mVideoPath)) {
                openVideoUrl(this.mVideoPath, this.mDuration);
            }
        }
    }

    public boolean onSurfaceTextureDestroyed(SurfaceTexture surfaceTexture) {
        Log.d(TAG, "mimoji boolean onSurfaceTextureDestroyed[surface]");
        return false;
    }

    public void onSurfaceTextureSizeChanged(SurfaceTexture surfaceTexture, int i, int i2) {
        Log.d(TAG, "mimoji void onSurfaceTextureSizeChanged[surface, width, height]");
    }

    public void onSurfaceTextureUpdated(SurfaceTexture surfaceTexture) {
        if (!this.mIsSurFaceReady) {
            this.mIsSurFaceReady = true;
            MimojiViewUtil.setViewVisible(this.mPreviewCoverView, false);
            MimojiViewUtil.setViewVisible(this.mConcatProgressBar, false);
            MimojiViewUtil.setViewVisible(this.mGifEditLayout, true);
            this.mGifEditLayout.setIsAllowInput(true);
        }
    }

    public void openVideoUrl(String str, long j) {
        this.mDuration = j;
        this.mLoadHandler.post(new g(this, str));
    }

    public void pausePlay() {
        EffectMediaPlayer effectMediaPlayer = this.mEffectPlayer;
        if (effectMediaPlayer != null && this.mMediaEffectGraph != null) {
            effectMediaPlayer.PausePreView();
        }
    }

    public void releaseMedia(boolean z) {
        long currentTimeMillis = System.currentTimeMillis();
        cancelVideo2gif();
        releaseEffectPlayer();
        releaseMediaGraph();
        if (!z) {
            setIsComposing(false);
            releaseVideoSegmentMediaGraph();
            Surface surface = this.mSurface;
            if (surface != null) {
                surface.release();
                this.mSurface = null;
            }
            if (mIsLibLoaded) {
                SystemUtil.UnInit();
                mIsLibLoaded = false;
            }
        }
        Log.e(TAG, "mimoji void releaseMedia[fromInit] " + z + "   time:  " + (System.currentTimeMillis() - currentTimeMillis));
    }

    public void resumePlay() {
        EffectMediaPlayer effectMediaPlayer;
        if (!isEnable()) {
            Log.e(TAG, " resumePlay unenable");
        } else if (!FileUtils.checkFileConsist(this.mVideoPath) || (effectMediaPlayer = this.mEffectPlayer) == null) {
            onError();
        } else {
            effectMediaPlayer.ResumePreView();
        }
    }

    public void setConcatProgressBar(ProgressBar progressBar) {
        this.mConcatProgressBar = progressBar;
    }

    public void setGifEditLayout(GifEditLayout gifEditLayout) {
        this.mGifEditLayout = gifEditLayout;
    }

    public void setIsComposing(boolean z) {
        this.mIsComposing = z;
        this.mCheckErrorState = 0;
        this.mMainHandler.post(new d(this, z));
    }

    public void setPreviewCoverView(ImageView imageView) {
        this.mPreviewCoverView = imageView;
    }

    public void setTextureView(TextureView textureView) {
        this.mTextureView = textureView;
        this.mTextureView.setSurfaceTextureListener(this);
    }

    public void setVideoUrl(String str, long j) {
        this.mDuration = j;
        this.mVideoPath = str;
    }

    public void startVideo2Gif() {
        if (!FileUtils.checkFileConsist(this.mVideoPath) || this.mEffectPlayer == null) {
            Log.e(TAG, "mimoji void startVideo2Gif[] videpath null");
            return;
        }
        this.time = System.currentTimeMillis();
        setIsComposing(true);
        this.mEffectPlayer.PausePreView();
        MimojiViewUtil.setViewVisible(this.mConcatProgressBar, true);
        this.mGifEditLayout.setIsAllowInput(false);
        this.mLoadHandler.post(new o(this));
    }
}
