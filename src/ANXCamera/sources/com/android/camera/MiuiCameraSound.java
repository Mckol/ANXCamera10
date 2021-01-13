package com.android.camera;

import android.content.Context;
import android.media.AudioAttributes;
import android.media.AudioManager;
import android.media.SoundPool;
import b.c.a.c;
import com.android.camera.customization.ShutterSound;
import com.android.camera.log.Log;
import com.ss.android.vesdk.VEEditor;
import io.reactivex.BackpressureStrategy;
import io.reactivex.Flowable;
import io.reactivex.FlowableEmitter;
import io.reactivex.FlowableOnSubscribe;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import java.util.ArrayList;

public class MiuiCameraSound implements Consumer<PlayConfig> {
    public static final int AUDIO_CAPTURE = 7;
    public static final int FAST_BURST = 4;
    public static final int FOCUS_COMPLETE = 1;
    public static final int KNOBS_SCROLL = 6;
    private static final int NUM_MEDIA_SOUND_STREAMS = 1;
    public static final int SHUTTER_CLICK = 0;
    public static final int SHUTTER_DELAY = 5;
    public static final int SOUND_NOT_LOADED = -1;
    public static final int START_VIDEO_RECORDING = 2;
    public static final int STOP_VIDEO_RECORDING = 3;
    private static final String TAG = "MiuiCameraSound";
    private final AudioManager mAudioManager;
    private ArrayList mCompleteSampleList;
    private Disposable mDisposable;
    private FlowableEmitter<PlayConfig> mFlowableEmitter;
    private boolean mForceSound;
    private long mLastPlayTime;
    private SoundPool.OnLoadCompleteListener mLoadCompleteListener;
    private int mSoundIdToPlay;
    private SoundPool mSoundPool;

    public static class PlayConfig {
        public int soundId;
        public float volume = 1.0f;
    }

    public MiuiCameraSound(Context context) {
        this(context, false);
    }

    public MiuiCameraSound(Context context, boolean z) {
        this.mLastPlayTime = 0;
        this.mCompleteSampleList = new ArrayList();
        this.mLoadCompleteListener = new SoundPool.OnLoadCompleteListener() {
            /* class com.android.camera.MiuiCameraSound.AnonymousClass3 */

            public void onLoadComplete(SoundPool soundPool, int i, int i2) {
                if (i2 == 0) {
                    if (MiuiCameraSound.this.mSoundIdToPlay == i) {
                        soundPool.play(i, 1.0f, 1.0f, 0, 0, 1.0f);
                        MiuiCameraSound.this.mSoundIdToPlay = -1;
                    }
                    MiuiCameraSound.this.mCompleteSampleList.add(Integer.valueOf(i));
                    return;
                }
                Log.e(MiuiCameraSound.TAG, "Unable to read sound for playback (status: " + i2 + ")");
            }
        };
        this.mAudioManager = (AudioManager) context.getSystemService(VEEditor.MVConsts.TYPE_AUDIO);
        this.mForceSound = z;
        SoundPool.Builder builder = new SoundPool.Builder();
        int i = 1;
        builder.setMaxStreams(1);
        builder.setAudioAttributes(new AudioAttributes.Builder().setInternalLegacyStreamType((!c.On() || this.mForceSound) ? 7 : i).build());
        ShutterSound.release();
        this.mSoundPool = builder.build();
        this.mSoundPool.setOnLoadCompleteListener(this.mLoadCompleteListener);
        this.mSoundIdToPlay = -1;
        this.mDisposable = Flowable.create(new FlowableOnSubscribe<PlayConfig>() {
            /* class com.android.camera.MiuiCameraSound.AnonymousClass2 */

            @Override // io.reactivex.FlowableOnSubscribe
            public void subscribe(FlowableEmitter<PlayConfig> flowableEmitter) throws Exception {
                MiuiCameraSound.this.mFlowableEmitter = flowableEmitter;
            }
        }, BackpressureStrategy.DROP).observeOn(Schedulers.io()).onBackpressureDrop(new Consumer<PlayConfig>() {
            /* class com.android.camera.MiuiCameraSound.AnonymousClass1 */

            public void accept(@NonNull PlayConfig playConfig) throws Exception {
                Log.e(MiuiCameraSound.TAG, "play sound too fast: " + playConfig.soundId);
            }
        }).subscribe(this);
    }

    private synchronized void play(int i, float f, int i2) {
        if (i < 0 || i > 7) {
            throw new RuntimeException("Unknown sound requested: " + i);
        }
        int tryPlaySound = ShutterSound.tryPlaySound(i, this.mSoundPool, f, i2, this.mCompleteSampleList);
        if (tryPlaySound != -1) {
            this.mSoundIdToPlay = tryPlaySound;
        } else {
            this.mLastPlayTime = System.currentTimeMillis();
        }
    }

    private void playSound(int i, float f, int i2) {
        if (!c.On() || this.mForceSound || this.mAudioManager.getRingerMode() == 2) {
            play(i, f, i2);
        }
    }

    public void accept(@NonNull PlayConfig playConfig) throws Exception {
        playSound(playConfig.soundId, playConfig.volume, 1);
    }

    public long getLastSoundPlayTime() {
        return this.mLastPlayTime;
    }

    public synchronized void load(int i) {
        if (i < 0 || i > 7) {
            throw new RuntimeException("Unknown sound requested: " + i);
        } else if (this.mSoundPool == null) {
            Log.d(TAG, "mSoundPool has not been init, skip this time");
        } else {
            ShutterSound.loadSound(this.mSoundPool, i);
        }
    }

    public void playSound(int i) {
        playSound(i, 1.0f);
    }

    public void playSound(int i, float f) {
        if (!this.mFlowableEmitter.isCancelled()) {
            PlayConfig playConfig = new PlayConfig();
            playConfig.soundId = i;
            playConfig.volume = f;
            this.mFlowableEmitter.onNext(playConfig);
        }
    }

    public void release() {
        ShutterSound.release();
        Disposable disposable = this.mDisposable;
        if (disposable != null && !disposable.isDisposed()) {
            this.mDisposable.dispose();
        }
        SoundPool soundPool = this.mSoundPool;
        if (soundPool != null) {
            soundPool.release();
            this.mSoundPool = null;
        }
    }
}
