package com.android.camera.videoplayer.ui;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.AssetFileDescriptor;
import android.graphics.SurfaceTexture;
import android.os.Looper;
import android.preference.PreferenceManager;
import android.util.AttributeSet;
import android.view.TextureView;
import android.view.View;
import com.android.camera.videoplayer.ui.MediaPlayerWrapper;
import com.android.camera.videoplayer.ui.ScalableTextureView;
import com.android.camera.videoplayer.utils.HandlerThreadExtension;
import com.android.camera.videoplayer.utils.Logger;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

public class VideoPlayerView extends ScalableTextureView implements TextureView.SurfaceTextureListener, MediaPlayerWrapper.MainThreadMediaPlayerListener, MediaPlayerWrapper.VideoStateListener {
    private static final String IS_VIDEO_MUTED = "IS_VIDEO_MUTED";
    private static final boolean SHOW_LOGS = false;
    private String TAG;
    private AssetFileDescriptor mAssetFileDescriptor;
    private TextureView.SurfaceTextureListener mLocalSurfaceTextureListener;
    private MediaPlayerWrapper mMediaPlayer;
    private BackgroundThreadMediaPlayerListener mMediaPlayerListenerBackgroundThread;
    private final Set<MediaPlayerWrapper.MainThreadMediaPlayerListener> mMediaPlayerMainThreadListeners = new HashSet();
    private String mPath;
    private final ReadyForPlaybackIndicator mReadyForPlaybackIndicator = new ReadyForPlaybackIndicator();
    private final Runnable mVideoCompletionBackgroundThreadRunnable = new Runnable() {
        /* class com.android.camera.videoplayer.ui.VideoPlayerView.AnonymousClass1 */

        public void run() {
            VideoPlayerView.this.mMediaPlayerListenerBackgroundThread.onVideoSizeChangedBackgroundThread(VideoPlayerView.this.getContentHeight().intValue(), VideoPlayerView.this.getContentWidth().intValue());
        }
    };
    private final Runnable mVideoPreparedBackgroundThreadRunnable = new Runnable() {
        /* class com.android.camera.videoplayer.ui.VideoPlayerView.AnonymousClass2 */

        public void run() {
            VideoPlayerView.this.mMediaPlayerListenerBackgroundThread.onVideoPreparedBackgroundThread();
        }
    };
    private final Runnable mVideoSizeAvailableRunnable = new Runnable() {
        /* class com.android.camera.videoplayer.ui.VideoPlayerView.AnonymousClass4 */

        public void run() {
            synchronized (VideoPlayerView.this.mReadyForPlaybackIndicator) {
                VideoPlayerView.this.mReadyForPlaybackIndicator.setVideoSize(VideoPlayerView.this.getContentHeight(), VideoPlayerView.this.getContentWidth());
                if (VideoPlayerView.this.mReadyForPlaybackIndicator.isReadyForPlayback()) {
                    VideoPlayerView.this.mReadyForPlaybackIndicator.notifyAll();
                }
            }
            if (VideoPlayerView.this.mMediaPlayerListenerBackgroundThread != null) {
                VideoPlayerView.this.mMediaPlayerListenerBackgroundThread.onVideoSizeChangedBackgroundThread(VideoPlayerView.this.getContentHeight().intValue(), VideoPlayerView.this.getContentWidth().intValue());
            }
        }
    };
    private MediaPlayerWrapper.VideoStateListener mVideoStateListener;
    private HandlerThreadExtension mViewHandlerBackgroundThread;

    public interface BackgroundThreadMediaPlayerListener {
        void onErrorBackgroundThread(int i, int i2);

        void onVideoCompletionBackgroundThread();

        void onVideoPreparedBackgroundThread();

        void onVideoSizeChangedBackgroundThread(int i, int i2);
    }

    public interface PlaybackStartedListener {
        void onPlaybackStarted();
    }

    public VideoPlayerView(Context context) {
        super(context);
        initView();
    }

    public VideoPlayerView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        initView();
    }

    public VideoPlayerView(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        initView();
    }

    @TargetApi(21)
    public VideoPlayerView(Context context, AttributeSet attributeSet, int i, int i2) {
        super(context, attributeSet, i, i2);
        initView();
    }

    private void checkThread() {
        if (Looper.myLooper() == Looper.getMainLooper()) {
            throw new RuntimeException("cannot be in main thread");
        }
    }

    private void initView() {
        if (!isInEditMode()) {
            this.TAG = "" + this;
            setScaleType(ScalableTextureView.ScaleType.CENTER_CROP);
            super.setSurfaceTextureListener(this);
        }
    }

    private boolean isVideoSizeAvailable() {
        return (getContentHeight() == null || getContentWidth() == null) ? false : true;
    }

    private void notifyOnErrorMainThread(int i, int i2) {
        ArrayList<MediaPlayerWrapper.MainThreadMediaPlayerListener> arrayList;
        synchronized (this.mMediaPlayerMainThreadListeners) {
            arrayList = new ArrayList(this.mMediaPlayerMainThreadListeners);
        }
        for (MediaPlayerWrapper.MainThreadMediaPlayerListener mainThreadMediaPlayerListener : arrayList) {
            mainThreadMediaPlayerListener.onErrorMainThread(i, i2);
        }
    }

    private void notifyOnVideoCompletionMainThread() {
        ArrayList<MediaPlayerWrapper.MainThreadMediaPlayerListener> arrayList;
        synchronized (this.mMediaPlayerMainThreadListeners) {
            arrayList = new ArrayList(this.mMediaPlayerMainThreadListeners);
        }
        for (MediaPlayerWrapper.MainThreadMediaPlayerListener mainThreadMediaPlayerListener : arrayList) {
            mainThreadMediaPlayerListener.onVideoCompletionMainThread();
        }
    }

    private void notifyOnVideoPreparedMainThread() {
        ArrayList<MediaPlayerWrapper.MainThreadMediaPlayerListener> arrayList;
        synchronized (this.mMediaPlayerMainThreadListeners) {
            arrayList = new ArrayList(this.mMediaPlayerMainThreadListeners);
        }
        for (MediaPlayerWrapper.MainThreadMediaPlayerListener mainThreadMediaPlayerListener : arrayList) {
            mainThreadMediaPlayerListener.onVideoPreparedMainThread();
        }
    }

    private void notifyOnVideoSizeChangedMainThread(int i, int i2) {
        ArrayList<MediaPlayerWrapper.MainThreadMediaPlayerListener> arrayList;
        synchronized (this.mMediaPlayerMainThreadListeners) {
            arrayList = new ArrayList(this.mMediaPlayerMainThreadListeners);
        }
        for (MediaPlayerWrapper.MainThreadMediaPlayerListener mainThreadMediaPlayerListener : arrayList) {
            mainThreadMediaPlayerListener.onVideoSizeChangedMainThread(i, i2);
        }
    }

    private void notifyOnVideoStopped() {
        ArrayList<MediaPlayerWrapper.MainThreadMediaPlayerListener> arrayList;
        synchronized (this.mMediaPlayerMainThreadListeners) {
            arrayList = new ArrayList(this.mMediaPlayerMainThreadListeners);
        }
        for (MediaPlayerWrapper.MainThreadMediaPlayerListener mainThreadMediaPlayerListener : arrayList) {
            mainThreadMediaPlayerListener.onVideoStoppedMainThread();
        }
    }

    private void notifyTextureAvailable() {
        this.mViewHandlerBackgroundThread.post(new Runnable() {
            /* class com.android.camera.videoplayer.ui.VideoPlayerView.AnonymousClass5 */

            public void run() {
                synchronized (VideoPlayerView.this.mReadyForPlaybackIndicator) {
                    if (VideoPlayerView.this.mMediaPlayer != null) {
                        VideoPlayerView.this.mMediaPlayer.setSurfaceTexture(VideoPlayerView.this.getSurfaceTexture());
                    } else {
                        VideoPlayerView.this.mReadyForPlaybackIndicator.setVideoSize(null, null);
                    }
                    VideoPlayerView.this.mReadyForPlaybackIndicator.setSurfaceTextureAvailable(true);
                    if (VideoPlayerView.this.mReadyForPlaybackIndicator.isReadyForPlayback()) {
                        VideoPlayerView.this.mReadyForPlaybackIndicator.notifyAll();
                    }
                }
            }
        });
    }

    private void onVideoSizeAvailable() {
        updateTextureViewSize();
        if (isAttachedToWindow()) {
            this.mViewHandlerBackgroundThread.post(this.mVideoSizeAvailableRunnable);
        }
    }

    private void printErrorExtra(int i) {
        if (i != -1010 && i != -1007 && i != -1004) {
        }
    }

    private static String visibilityStr(int i) {
        if (i == 0) {
            return "VISIBLE";
        }
        if (i == 4) {
            return "INVISIBLE";
        }
        if (i == 8) {
            return "GONE";
        }
        throw new RuntimeException("unexpected");
    }

    public void addMediaPlayerListener(MediaPlayerWrapper.MainThreadMediaPlayerListener mainThreadMediaPlayerListener) {
        synchronized (this.mMediaPlayerMainThreadListeners) {
            this.mMediaPlayerMainThreadListeners.add(mainThreadMediaPlayerListener);
        }
    }

    public void clearPlayerInstance() {
        checkThread();
        synchronized (this.mReadyForPlaybackIndicator) {
            this.mReadyForPlaybackIndicator.setVideoSize(null, null);
            this.mMediaPlayer.clearAll();
            this.mMediaPlayer = null;
        }
    }

    public void createNewPlayerInstance() {
        checkThread();
        synchronized (this.mReadyForPlaybackIndicator) {
            this.mMediaPlayer = new MediaPlayerWrapperImpl();
            this.mMediaPlayer.setLooping(true);
            this.mReadyForPlaybackIndicator.setVideoSize(null, null);
            this.mReadyForPlaybackIndicator.setFailedToPrepareUiForPlayback(false);
            if (this.mReadyForPlaybackIndicator.isSurfaceTextureAvailable()) {
                this.mMediaPlayer.setSurfaceTexture(getSurfaceTexture());
            }
            this.mMediaPlayer.setMainThreadMediaPlayerListener(this);
            this.mMediaPlayer.setVideoStateListener(this);
        }
    }

    public AssetFileDescriptor getAssetFileDescriptorDataSource() {
        return this.mAssetFileDescriptor;
    }

    public MediaPlayerWrapper.State getCurrentState() {
        MediaPlayerWrapper.State currentState;
        synchronized (this.mReadyForPlaybackIndicator) {
            currentState = this.mMediaPlayer.getCurrentState();
        }
        return currentState;
    }

    public int getDuration() {
        int duration;
        synchronized (this.mReadyForPlaybackIndicator) {
            duration = this.mMediaPlayer.getDuration();
        }
        return duration;
    }

    public String getVideoUrlDataSource() {
        return this.mPath;
    }

    public boolean isAllVideoMute() {
        return PreferenceManager.getDefaultSharedPreferences(getContext()).getBoolean(IS_VIDEO_MUTED, false);
    }

    public boolean isAttachedToWindow() {
        return this.mViewHandlerBackgroundThread != null;
    }

    public void muteVideo() {
        synchronized (this.mReadyForPlaybackIndicator) {
            PreferenceManager.getDefaultSharedPreferences(getContext()).edit().putBoolean(IS_VIDEO_MUTED, true).commit();
            this.mMediaPlayer.setVolume(0.0f, 0.0f);
        }
    }

    /* access modifiers changed from: protected */
    public void onAttachedToWindow() {
        super.onAttachedToWindow();
        if (!isInEditMode()) {
            this.mViewHandlerBackgroundThread = new HandlerThreadExtension(this.TAG, false);
            this.mViewHandlerBackgroundThread.startThread();
        }
    }

    @Override // com.android.camera.videoplayer.ui.MediaPlayerWrapper.MainThreadMediaPlayerListener
    public void onBufferingUpdateMainThread(int i) {
    }

    /* access modifiers changed from: protected */
    public void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        if (!isInEditMode()) {
            this.mViewHandlerBackgroundThread.postQuit();
            this.mViewHandlerBackgroundThread = null;
        }
    }

    @Override // com.android.camera.videoplayer.ui.MediaPlayerWrapper.MainThreadMediaPlayerListener
    public void onErrorMainThread(final int i, final int i2) {
        if (i == 1) {
            printErrorExtra(i2);
        } else if (i == 100) {
            printErrorExtra(i2);
        }
        notifyOnErrorMainThread(i, i2);
        if (this.mMediaPlayerListenerBackgroundThread != null) {
            this.mViewHandlerBackgroundThread.post(new Runnable() {
                /* class com.android.camera.videoplayer.ui.VideoPlayerView.AnonymousClass3 */

                public void run() {
                    VideoPlayerView.this.mMediaPlayerListenerBackgroundThread.onErrorBackgroundThread(i, i2);
                }
            });
        }
    }

    public void onSurfaceTextureAvailable(SurfaceTexture surfaceTexture, int i, int i2) {
        TextureView.SurfaceTextureListener surfaceTextureListener = this.mLocalSurfaceTextureListener;
        if (surfaceTextureListener != null) {
            surfaceTextureListener.onSurfaceTextureAvailable(surfaceTexture, i, i2);
        }
        notifyTextureAvailable();
    }

    public boolean onSurfaceTextureDestroyed(SurfaceTexture surfaceTexture) {
        TextureView.SurfaceTextureListener surfaceTextureListener = this.mLocalSurfaceTextureListener;
        if (surfaceTextureListener != null) {
            surfaceTextureListener.onSurfaceTextureDestroyed(surfaceTexture);
        }
        if (isAttachedToWindow()) {
            this.mViewHandlerBackgroundThread.post(new Runnable() {
                /* class com.android.camera.videoplayer.ui.VideoPlayerView.AnonymousClass6 */

                public void run() {
                    synchronized (VideoPlayerView.this.mReadyForPlaybackIndicator) {
                        VideoPlayerView.this.mReadyForPlaybackIndicator.setSurfaceTextureAvailable(false);
                        VideoPlayerView.this.mReadyForPlaybackIndicator.notifyAll();
                    }
                }
            });
        }
        surfaceTexture.release();
        return false;
    }

    public void onSurfaceTextureSizeChanged(SurfaceTexture surfaceTexture, int i, int i2) {
        TextureView.SurfaceTextureListener surfaceTextureListener = this.mLocalSurfaceTextureListener;
        if (surfaceTextureListener != null) {
            surfaceTextureListener.onSurfaceTextureSizeChanged(surfaceTexture, i, i2);
        }
    }

    public void onSurfaceTextureUpdated(SurfaceTexture surfaceTexture) {
        TextureView.SurfaceTextureListener surfaceTextureListener = this.mLocalSurfaceTextureListener;
        if (surfaceTextureListener != null) {
            surfaceTextureListener.onSurfaceTextureUpdated(surfaceTexture);
        }
    }

    @Override // com.android.camera.videoplayer.ui.MediaPlayerWrapper.MainThreadMediaPlayerListener
    public void onVideoCompletionMainThread() {
        notifyOnVideoCompletionMainThread();
        if (this.mMediaPlayerListenerBackgroundThread != null) {
            this.mViewHandlerBackgroundThread.post(this.mVideoCompletionBackgroundThreadRunnable);
        }
    }

    @Override // com.android.camera.videoplayer.ui.MediaPlayerWrapper.VideoStateListener
    public void onVideoPlayTimeChanged(int i) {
    }

    @Override // com.android.camera.videoplayer.ui.MediaPlayerWrapper.MainThreadMediaPlayerListener
    public void onVideoPreparedMainThread() {
        notifyOnVideoPreparedMainThread();
        if (this.mMediaPlayerListenerBackgroundThread != null) {
            this.mViewHandlerBackgroundThread.post(this.mVideoPreparedBackgroundThreadRunnable);
        }
    }

    @Override // com.android.camera.videoplayer.ui.MediaPlayerWrapper.MainThreadMediaPlayerListener
    public void onVideoSizeChangedMainThread(int i, int i2) {
        if (i == 0 || i2 == 0) {
            synchronized (this.mReadyForPlaybackIndicator) {
                this.mReadyForPlaybackIndicator.setFailedToPrepareUiForPlayback(true);
                this.mReadyForPlaybackIndicator.notifyAll();
            }
        } else {
            setContentWidth(i);
            setContentHeight(i2);
            onVideoSizeAvailable();
        }
        notifyOnVideoSizeChangedMainThread(i, i2);
    }

    @Override // com.android.camera.videoplayer.ui.MediaPlayerWrapper.MainThreadMediaPlayerListener
    public void onVideoStoppedMainThread() {
        notifyOnVideoStopped();
    }

    /* access modifiers changed from: protected */
    public void onVisibilityChanged(View view, int i) {
        super.onVisibilityChanged(view, i);
        if (!isInEditMode() && i != 0) {
            if (i == 4 || i == 8) {
                synchronized (this.mReadyForPlaybackIndicator) {
                    this.mReadyForPlaybackIndicator.notifyAll();
                }
            }
        }
    }

    public void pause() {
        synchronized (this.mReadyForPlaybackIndicator) {
            this.mMediaPlayer.pause();
        }
    }

    public void prepare() {
        checkThread();
        synchronized (this.mReadyForPlaybackIndicator) {
            this.mMediaPlayer.prepare();
        }
    }

    public void release() {
        checkThread();
        synchronized (this.mReadyForPlaybackIndicator) {
            this.mMediaPlayer.release();
        }
    }

    public void reset() {
        checkThread();
        synchronized (this.mReadyForPlaybackIndicator) {
            this.mMediaPlayer.reset();
        }
    }

    public void setBackgroundThreadMediaPlayerListener(BackgroundThreadMediaPlayerListener backgroundThreadMediaPlayerListener) {
        this.mMediaPlayerListenerBackgroundThread = backgroundThreadMediaPlayerListener;
    }

    public void setDataSource(AssetFileDescriptor assetFileDescriptor) {
        checkThread();
        synchronized (this.mReadyForPlaybackIndicator) {
            try {
                this.mMediaPlayer.setDataSource(assetFileDescriptor);
                this.mAssetFileDescriptor = assetFileDescriptor;
            } catch (IOException e2) {
                Logger.d(this.TAG, e2.getMessage());
                throw new RuntimeException(e2);
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    public void setDataSource(String str) {
        checkThread();
        synchronized (this.mReadyForPlaybackIndicator) {
            try {
                this.mMediaPlayer.setDataSource(str);
                this.mPath = str;
            } catch (IOException e2) {
                Logger.d(this.TAG, e2.getMessage());
                throw new RuntimeException(e2);
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    public void setOnVideoStateChangedListener(MediaPlayerWrapper.VideoStateListener videoStateListener) {
        this.mVideoStateListener = videoStateListener;
        checkThread();
        synchronized (this.mReadyForPlaybackIndicator) {
            this.mMediaPlayer.setVideoStateListener(videoStateListener);
        }
    }

    public final void setSurfaceTextureListener(TextureView.SurfaceTextureListener surfaceTextureListener) {
        this.mLocalSurfaceTextureListener = surfaceTextureListener;
    }

    public void start() {
        synchronized (this.mReadyForPlaybackIndicator) {
            if (this.mReadyForPlaybackIndicator.isReadyForPlayback()) {
                this.mMediaPlayer.start();
            } else if (!this.mReadyForPlaybackIndicator.isFailedToPrepareUiForPlayback()) {
                try {
                    this.mReadyForPlaybackIndicator.wait();
                    if (this.mReadyForPlaybackIndicator.isReadyForPlayback()) {
                        this.mMediaPlayer.start();
                    }
                } catch (InterruptedException e2) {
                    throw new RuntimeException(e2);
                }
            }
        }
    }

    public void stop() {
        checkThread();
        synchronized (this.mReadyForPlaybackIndicator) {
            this.mMediaPlayer.stop();
        }
    }

    public String toString() {
        return VideoPlayerView.class.getSimpleName() + "@" + hashCode();
    }

    public void unMuteVideo() {
        synchronized (this.mReadyForPlaybackIndicator) {
            PreferenceManager.getDefaultSharedPreferences(getContext()).edit().putBoolean(IS_VIDEO_MUTED, false).commit();
            this.mMediaPlayer.setVolume(1.0f, 1.0f);
        }
    }
}
