package com.android.camera.features.mimoji2.module.impl;

import android.content.Context;
import android.media.MediaPlayer;
import android.text.TextUtils;
import android.view.Surface;
import com.android.camera.ActivityBase;
import com.android.camera.constant.DurationConstant;
import com.android.camera.data.DataRepository;
import com.android.camera.features.mimoji2.module.protocol.MimojiModeProtocol;
import com.android.camera.features.mimoji2.utils.MimojiViewUtil;
import com.android.camera.features.mimoji2.widget.helper.MimojiHelper2;
import com.android.camera.features.mimoji2.widget.helper.MimojiStatusManager2;
import com.android.camera.log.Log;
import com.android.camera.module.impl.component.FileUtils;
import com.android.camera.protocol.ModeCoordinatorImpl;
import com.android.camera.storage.Storage;
import com.android.camera.ui.TextureVideoView;
import com.arcsoft.avatar2.emoticon.EmoInfo;
import com.xiaomi.Video2GifEditer.MediaProcess;
import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MimojiVideoEditorImpl implements MimojiModeProtocol.MimojiVideoEditor {
    private static final String TAG = "MimojiVideoEditorImpl";
    private long composeTime;
    private Context mContext;
    private int mEncodeHeight = 1080;
    private int mEncodeWidth = 1920;
    private String mGifSourcePath;
    private boolean mIsComposing = false;
    private boolean mIsRelease = false;
    private MimojiChangeTimbreCallback mMimojiChangeTimbreCallback;
    private MimojiMediaPlayerCallback mMimojiMediaPlayerCallback;
    private MimojiVideo2GifCallback mMimojiVideo2GifCallback;
    private int mOrientation;
    private TextureVideoView mTextureVideoView;
    private String mVideoSavePath;
    private Map<String, String> mVoiceMap;
    private boolean mWaitingResultSurfaceTexture;

    /* access modifiers changed from: private */
    public class MimojiChangeTimbreCallback implements MediaProcess.Callback {
        private int stopRecordType;

        private MimojiChangeTimbreCallback() {
        }

        @Override // com.xiaomi.Video2GifEditer.MediaProcess.Callback
        public void OnConvertProgress(int i) {
            if (i == 100) {
                MimojiVideoEditorImpl.this.onSuccess(MimojiHelper2.VIDEO_DEAL_CACHE_FILE, this.stopRecordType);
                long currentTimeMillis = System.currentTimeMillis() - MimojiVideoEditorImpl.this.composeTime;
                String str = MimojiVideoEditorImpl.TAG;
                Log.d(str, "mimoji void combineVideoAudio[savePath] time  " + currentTimeMillis);
            }
            String str2 = MimojiVideoEditorImpl.TAG;
            Log.d(str2, "progress value: " + i);
        }

        public int getStopRecordType() {
            return this.stopRecordType;
        }

        public void setStopRecordType(int i) {
            this.stopRecordType = i;
        }
    }

    /* access modifiers changed from: private */
    public class MimojiMediaPlayerCallback implements TextureVideoView.MediaPlayerCallback {
        private MimojiMediaPlayerCallback() {
        }

        public /* synthetic */ void Xc() {
            MimojiModeProtocol.MimojiFullScreenProtocol mimojiFullScreenProtocol = (MimojiModeProtocol.MimojiFullScreenProtocol) ModeCoordinatorImpl.getInstance().getAttachProtocol(249);
            if (mimojiFullScreenProtocol != null) {
                mimojiFullScreenProtocol.showPreviewCover(false);
                Log.d(MimojiVideoEditorImpl.TAG, "mimoji void onPreviewPixelsRead[pixels, width, height] bitmap mPreviewCover null");
            }
            MimojiModeProtocol.MimojiBottomList mimojiBottomList = (MimojiModeProtocol.MimojiBottomList) ModeCoordinatorImpl.getInstance().getAttachProtocol(248);
            if (mimojiBottomList != null) {
                mimojiBottomList.hideTimbreProgress();
            }
            MimojiVideoEditorImpl.this.mIsComposing = false;
        }

        @Override // com.android.camera.ui.TextureVideoView.MediaPlayerCallback
        public void onBufferingUpdate(MediaPlayer mediaPlayer, int i) {
            String str = MimojiVideoEditorImpl.TAG;
            Log.d(str, "mimoji void onBufferingUpdate[mp, percent]" + i);
        }

        @Override // com.android.camera.ui.TextureVideoView.MediaPlayerCallback
        public void onCompletion(MediaPlayer mediaPlayer) {
            Log.d(MimojiVideoEditorImpl.TAG, "mimoji void onCompletion[mp]");
        }

        @Override // com.android.camera.ui.TextureVideoView.MediaPlayerCallback
        public boolean onError(MediaPlayer mediaPlayer, int i, int i2) {
            String str = MimojiVideoEditorImpl.TAG;
            Log.e(str, "mimoji boolean onError[mp, what, extra]" + i + "  " + i2);
            return false;
        }

        @Override // com.android.camera.ui.TextureVideoView.MediaPlayerCallback
        public boolean onInfo(MediaPlayer mediaPlayer, int i, int i2) {
            if (MimojiVideoEditorImpl.this.mTextureVideoView != null) {
                String str = MimojiVideoEditorImpl.TAG;
                Log.d(str, "mimoji boolean onInfo[mp, what, extra] delay " + i + "  " + i2);
                if (MimojiViewUtil.getViewIsVisible(MimojiVideoEditorImpl.this.mTextureVideoView)) {
                    MimojiVideoEditorImpl.this.mTextureVideoView.postDelayed(new r(this), 200);
                } else {
                    MimojiVideoEditorImpl.this.onFail();
                }
            } else {
                String str2 = MimojiVideoEditorImpl.TAG;
                Log.d(str2, "mimoji boolean onInfo[mp, what, extra] " + i + "  " + i2);
                MimojiVideoEditorImpl.this.mIsComposing = false;
                MimojiVideoEditorImpl.this.onFail();
            }
            return false;
        }

        @Override // com.android.camera.ui.TextureVideoView.MediaPlayerCallback
        public void onPrepared(MediaPlayer mediaPlayer) {
            Log.d(MimojiVideoEditorImpl.TAG, "mimoji void onPrepared[mp]");
            MimojiViewUtil.setViewVisible(MimojiVideoEditorImpl.this.mTextureVideoView, true);
        }

        @Override // com.android.camera.ui.TextureVideoView.MediaPlayerCallback
        public void onSurfaceReady(Surface surface) {
            Log.d(MimojiVideoEditorImpl.TAG, "mimoji void onSurfaceReady[surface]");
            if (MimojiVideoEditorImpl.this.mWaitingResultSurfaceTexture) {
                MimojiVideoEditorImpl.this.startPlay(surface);
            }
        }

        @Override // com.android.camera.ui.TextureVideoView.MediaPlayerCallback
        public void onVideoSizeChanged(MediaPlayer mediaPlayer, int i, int i2) {
            Log.d(MimojiVideoEditorImpl.TAG, "mimoji void onVideoSizeChanged[mp, width, height]");
        }
    }

    /* access modifiers changed from: private */
    public class MimojiVideo2GifCallback implements MediaProcess.Callback {
        private int count;
        private int index;

        private MimojiVideo2GifCallback() {
        }

        @Override // com.xiaomi.Video2GifEditer.MediaProcess.Callback
        public void OnConvertProgress(int i) {
            MimojiModeProtocol.MimojiEditor2.MimojiEmoticon mimojiEmoticon;
            if (i != 100) {
                String str = MimojiVideoEditorImpl.TAG;
                Log.d(str, "mimoji void video2gif[]  Video  " + this.index + " convert GIF progress : " + i + "%");
            } else if (this.index == this.count - 1 && (mimojiEmoticon = (MimojiModeProtocol.MimojiEditor2.MimojiEmoticon) ModeCoordinatorImpl.getInstance().getAttachProtocol(250)) != null) {
                MimojiVideoEditorImpl.this.mIsComposing = false;
                mimojiEmoticon.coverEmoticonSuccess();
            }
        }

        public int getCount() {
            return this.count;
        }

        public int getIndex() {
            return this.index;
        }

        public void setCount(int i) {
            this.count = i;
        }

        public void setIndex(int i) {
            this.index = i;
        }
    }

    static {
        System.loadLibrary("vvc++_shared");
        System.loadLibrary("mimoji_video2gif");
    }

    private MimojiVideoEditorImpl(ActivityBase activityBase) {
        this.mContext = activityBase.getCameraAppImpl().getApplicationContext();
    }

    public static MimojiVideoEditorImpl create(ActivityBase activityBase) {
        return new MimojiVideoEditorImpl(activityBase);
    }

    /* access modifiers changed from: private */
    /* access modifiers changed from: public */
    private synchronized void onFail() {
        Log.d(TAG, "mimoji void onFail[]");
        FileUtils.deleteFile(MimojiHelper2.VIDEO_DEAL_CACHE_FILE);
        MimojiModeProtocol.MimojiFullScreenProtocol mimojiFullScreenProtocol = (MimojiModeProtocol.MimojiFullScreenProtocol) ModeCoordinatorImpl.getInstance().getAttachProtocol(249);
        if (mimojiFullScreenProtocol != null) {
            mimojiFullScreenProtocol.onCombineError();
        } else {
            Log.e(TAG, "mimoji void onFail null");
        }
        this.mIsComposing = false;
    }

    /* access modifiers changed from: private */
    /* access modifiers changed from: public */
    private synchronized void onSuccess(String str, int i) {
        MimojiModeProtocol.MimojiFullScreenProtocol mimojiFullScreenProtocol = (MimojiModeProtocol.MimojiFullScreenProtocol) ModeCoordinatorImpl.getInstance().getAttachProtocol(249);
        if (mimojiFullScreenProtocol != null) {
            if (i == 0) {
                mimojiFullScreenProtocol.concatResult(str, i);
            } else {
                String str2 = null;
                try {
                    if (str.contains("mimoji_normal") || str.contains("mimoji_deal")) {
                        str2 = Storage.DIRECTORY + File.separator + FileUtils.createtFileName("MIMOJI_", "mp4");
                        FileUtils.copyFile(new File(str), new File(str2));
                    }
                    if (TextUtils.isEmpty(str2)) {
                        onFail();
                    } else {
                        mimojiFullScreenProtocol.onCombineSuccess(str2);
                    }
                } catch (Exception e2) {
                    Log.e(TAG, "mimoji void cnSuccess[savePath] Exception " + e2.getMessage());
                    onFail();
                }
            }
            this.mIsComposing = false;
        } else {
            Log.e(TAG, "mimoji void cnSuccess[savePath] null");
            onFail();
        }
    }

    private void release() {
        this.mIsRelease = true;
        cancelVideo2gif();
        this.mGifSourcePath = null;
        if (this.mMimojiVideo2GifCallback != null) {
            this.mMimojiVideo2GifCallback = null;
        }
        if (this.mMimojiChangeTimbreCallback != null) {
            this.mMimojiChangeTimbreCallback = null;
        }
        TextureVideoView textureVideoView = this.mTextureVideoView;
        if (textureVideoView != null) {
            textureVideoView.stop();
            this.mMimojiMediaPlayerCallback = null;
            this.mTextureVideoView.setMediaPlayerCallback(null);
            this.mTextureVideoView = null;
        }
    }

    /* access modifiers changed from: private */
    /* access modifiers changed from: public */
    private void startPlay(Surface surface) {
        Log.d(TAG, "mimoji void startPlay[surface]");
        if (!MimojiViewUtil.getViewIsVisible(this.mTextureVideoView) || surface == null || this.mIsRelease) {
            release();
            return;
        }
        this.mWaitingResultSurfaceTexture = false;
        int i = this.mOrientation;
        if (i == 0 || i == 180) {
            this.mTextureVideoView.setScaleType(3);
            this.mTextureVideoView.setRotation((float) this.mOrientation);
        } else {
            this.mTextureVideoView.setScaleType(7);
            this.mTextureVideoView.setRotation(this.mOrientation == 270 ? 0.0f : 180.0f);
        }
        this.mTextureVideoView.setLoop(true);
        this.mTextureVideoView.setClearSurface(false);
        this.mTextureVideoView.setVideoPath(this.mVideoSavePath);
        this.mTextureVideoView.setVideoSpecifiedSize(this.mEncodeWidth, this.mEncodeHeight);
        this.mTextureVideoView.start();
    }

    public /* synthetic */ void Yc() {
        if (FileUtils.checkFileConsist(MimojiHelper2.VIDEO_NORMAL_CACHE_FILE)) {
            pausePlay();
            FileUtils.deleteFile(MimojiHelper2.VIDEO_DEAL_CACHE_FILE);
            FileUtils.makeNoMediaDir(MimojiHelper2.VIDEO_CACHE_DIR);
            combineVideoAudio(MimojiHelper2.VIDEO_NORMAL_CACHE_FILE, 0);
        }
    }

    @Override // com.android.camera.features.mimoji2.module.protocol.MimojiModeProtocol.MimojiVideoEditor
    public void cancelVideo2gif() {
        if (FileUtils.checkFileConsist(this.mGifSourcePath) && isComposing()) {
            MediaProcess.CancelGifConvert(this.mGifSourcePath);
        }
    }

    @Override // com.android.camera.features.mimoji2.module.protocol.MimojiModeProtocol.MimojiVideoEditor
    public void changeTimbre() {
        new Thread(new s(this)).start();
    }

    @Override // com.android.camera.features.mimoji2.module.protocol.MimojiModeProtocol.MimojiVideoEditor
    public synchronized void combineVideoAudio(String str, int i) {
        String str2 = TAG;
        Log.d(str2, "mimoji void combineVideoAudio[savePath]" + str);
        this.composeTime = System.currentTimeMillis();
        this.mIsComposing = true;
        if (i == 0) {
            DataRepository.dataItemLive().getMimojiStatusManager2().setMode(10);
            this.mIsRelease = false;
        }
        if (this.mMimojiChangeTimbreCallback == null) {
            this.mMimojiChangeTimbreCallback = new MimojiChangeTimbreCallback();
        }
        MimojiStatusManager2 mimojiStatusManager2 = DataRepository.dataItemLive().getMimojiStatusManager2();
        if (mimojiStatusManager2.getCurrentMimojiTimbreInfo() != null) {
            Log.d(TAG, "mimoji void startPlay[surface]  timbre start");
            if (this.mVoiceMap == null) {
                this.mVoiceMap = new HashMap();
            }
            Map<String, String> map = this.mVoiceMap;
            map.put("mode", mimojiStatusManager2.getCurrentMimojiTimbreInfo().getTimbreId() + "");
            MediaProcess.AddVoiceChangeFilter(this.mVoiceMap);
            this.mMimojiChangeTimbreCallback.setStopRecordType(i);
            MediaProcess.Convert(str, 2000, MimojiHelper2.VIDEO_DEAL_CACHE_FILE, true, 30, DurationConstant.DURATION_RESET_FALLBACK, 0, 0, 1.0f, this.mMimojiChangeTimbreCallback);
        } else {
            onSuccess(MimojiHelper2.VIDEO_NORMAL_CACHE_FILE, i);
        }
    }

    @Override // com.android.camera.features.mimoji2.module.protocol.MimojiModeProtocol.MimojiVideoEditor
    public boolean init(TextureVideoView textureVideoView, String str) {
        String str2 = TAG;
        Log.v(str2, "mimoji videoeditor init videoUri " + str);
        this.mWaitingResultSurfaceTexture = true;
        this.mTextureVideoView = textureVideoView;
        this.mVideoSavePath = str;
        this.mGifSourcePath = null;
        if (this.mMimojiChangeTimbreCallback == null) {
            this.mMimojiChangeTimbreCallback = new MimojiChangeTimbreCallback();
        }
        if (this.mMimojiVideo2GifCallback == null) {
            this.mMimojiVideo2GifCallback = new MimojiVideo2GifCallback();
        }
        if (this.mMimojiMediaPlayerCallback == null) {
            this.mMimojiMediaPlayerCallback = new MimojiMediaPlayerCallback();
        }
        this.mTextureVideoView.setMediaPlayerCallback(this.mMimojiMediaPlayerCallback);
        this.mTextureVideoView.setIsNeedAudio(false);
        return true;
    }

    @Override // com.android.camera.features.mimoji2.module.protocol.MimojiModeProtocol.MimojiVideoEditor
    public boolean isAvaliable() {
        return MimojiViewUtil.getViewIsVisible(this.mTextureVideoView) && !isComposing();
    }

    @Override // com.android.camera.features.mimoji2.module.protocol.MimojiModeProtocol.MimojiVideoEditor
    public boolean isComposing() {
        return this.mIsComposing;
    }

    @Override // com.android.camera.features.mimoji2.module.protocol.MimojiModeProtocol.MimojiVideoEditor
    public boolean isPlaying() {
        TextureVideoView textureVideoView = this.mTextureVideoView;
        if (textureVideoView != null) {
            return textureVideoView.isPlaying();
        }
        return false;
    }

    @Override // com.android.camera.features.mimoji2.module.protocol.MimojiModeProtocol.MimojiVideoEditor
    public void onDestory() {
        if (DataRepository.dataItemLive().getMimojiStatusManager2().isInMimojiPreviewPlay()) {
            DataRepository.dataItemLive().getMimojiStatusManager2().setMode(2);
            Log.d(TAG, "mimoji void onDestory[]");
        }
        this.mIsComposing = false;
        release();
    }

    @Override // com.android.camera.features.mimoji2.module.protocol.MimojiModeProtocol.MimojiVideoEditor
    public boolean pausePlay() {
        if (this.mTextureVideoView == null) {
            return false;
        }
        Log.d(TAG, "mimoji void pausePlay[]");
        this.mTextureVideoView.stop();
        return true;
    }

    @Override // com.android.camera.protocol.ModeProtocol.BaseProtocol
    public void registerProtocol() {
        ModeCoordinatorImpl.getInstance().attachProtocol(252, this);
    }

    @Override // com.android.camera.features.mimoji2.module.protocol.MimojiModeProtocol.MimojiVideoEditor
    public boolean resumePlay() {
        if (this.mTextureVideoView == null) {
            return false;
        }
        Log.d(TAG, "mimoji void resumePlay[]");
        this.mTextureVideoView.resume();
        return false;
    }

    @Override // com.android.camera.features.mimoji2.module.protocol.MimojiModeProtocol.MimojiVideoEditor
    public void setRecordParameter(int i, int i2, int i3) {
        this.mOrientation = Math.max(0, i3);
        String str = TAG;
        Log.d(str, "setRecordParameter:  " + i + " | " + i2 + " | " + this.mOrientation);
        this.mEncodeWidth = i;
        this.mEncodeHeight = i2;
    }

    @Override // com.android.camera.features.mimoji2.module.protocol.MimojiModeProtocol.MimojiVideoEditor
    public void startPlay() {
        if (this.mVideoSavePath != null) {
            if (this.mTextureVideoView.getPreviewSurface() == null) {
                this.mWaitingResultSurfaceTexture = true;
                this.mTextureVideoView.start();
                return;
            }
            startPlay(this.mTextureVideoView.getPreviewSurface());
        }
    }

    @Override // com.android.camera.protocol.ModeProtocol.BaseProtocol
    public void unRegisterProtocol() {
        ModeCoordinatorImpl.getInstance().detachProtocol(252, this);
        release();
    }

    @Override // com.android.camera.features.mimoji2.module.protocol.MimojiModeProtocol.MimojiVideoEditor
    public void video2gif(List<EmoInfo> list) {
        this.mGifSourcePath = null;
        if (list == null || list.size() == 0) {
            MimojiModeProtocol.MimojiEditor2.MimojiEmoticon mimojiEmoticon = (MimojiModeProtocol.MimojiEditor2.MimojiEmoticon) ModeCoordinatorImpl.getInstance().getAttachProtocol(250);
            if (mimojiEmoticon != null) {
                mimojiEmoticon.coverEmoticonSuccess();
                return;
            }
            return;
        }
        if (this.mMimojiVideo2GifCallback == null) {
            this.mMimojiVideo2GifCallback = new MimojiVideo2GifCallback();
        }
        this.mMimojiVideo2GifCallback.setCount(list.size());
        this.mIsComposing = true;
        for (int i = 0; i < list.size(); i++) {
            String str = MimojiHelper2.EMOTICON_MP4_CACHE_DIR + list.get(i).getEmoName() + ".mp4";
            String str2 = MimojiHelper2.EMOTICON_GIF_CACHE_DIR + list.get(i).getEmoName() + ".gif";
            FileUtils.makeDir(MimojiHelper2.EMOTICON_GIF_CACHE_DIR);
            Log.d(TAG, " source :" + str + "\n target : " + str2);
            this.mGifSourcePath = str;
            this.mMimojiVideo2GifCallback.setIndex(i);
            if (MediaProcess.ConvertGif(str, str2, 20, 100000000, 0, 5000, 1.0f, this.mMimojiVideo2GifCallback) != 0) {
                Log.d(TAG, "mimoji void video2gif[] cover fail");
                MimojiModeProtocol.MimojiEditor2.MimojiEmoticon mimojiEmoticon2 = (MimojiModeProtocol.MimojiEditor2.MimojiEmoticon) ModeCoordinatorImpl.getInstance().getAttachProtocol(250);
                if (mimojiEmoticon2 != null) {
                    this.mIsComposing = false;
                    mimojiEmoticon2.coverEmoticonError();
                }
            }
        }
    }
}
