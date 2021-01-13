package miui.preference;

import android.app.Dialog;
import android.content.Context;
import android.content.res.TypedArray;
import android.database.ContentObserver;
import android.media.AudioManager;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Message;
import android.os.Parcel;
import android.os.Parcelable;
import android.preference.Preference;
import android.preference.PreferenceManager;
import android.provider.Settings;
import android.util.AttributeSet;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.SeekBar;
import com.miui.internal.R;
import com.ss.android.vesdk.VEEditor;
import miui.reflect.Field;
import miui.reflect.Method;

public class VolumePreference extends SeekBarDialogPreference implements PreferenceManager.OnActivityStopListener, View.OnKeyListener {
    private static final Method PreferenceManager_registerOnActivityStopListener = Method.of(PreferenceManager.class, "registerOnActivityStopListener", "(Landroid/preference/PreferenceManager$OnActivityStopListener;)V");
    private static final Method PreferenceManager_unregisterOnActivityStopListener = Method.of(PreferenceManager.class, "unregisterOnActivityStopListener", "(Landroid/preference/PreferenceManager$OnActivityStopListener;)V");
    private static final String TAG = "VolumePreference";
    private static final int VolumePreference_streamType = getVolumePreferenceStreamType();
    private SeekBarVolumizer mSeekBarVolumizer;
    private int mStreamType;

    /* access modifiers changed from: private */
    public static class SavedState extends Preference.BaseSavedState {
        public static final Parcelable.Creator<SavedState> CREATOR = new Parcelable.Creator<SavedState>() {
            /* class miui.preference.VolumePreference.SavedState.AnonymousClass1 */

            @Override // android.os.Parcelable.Creator
            public SavedState createFromParcel(Parcel parcel) {
                return new SavedState(parcel);
            }

            @Override // android.os.Parcelable.Creator
            public SavedState[] newArray(int i) {
                return new SavedState[i];
            }
        };
        VolumeStore mVolumeStore = new VolumeStore();

        public SavedState(Parcel parcel) {
            super(parcel);
            this.mVolumeStore.volume = parcel.readInt();
            this.mVolumeStore.originalVolume = parcel.readInt();
        }

        public SavedState(Parcelable parcelable) {
            super(parcelable);
        }

        /* access modifiers changed from: package-private */
        public VolumeStore getVolumeStore() {
            return this.mVolumeStore;
        }

        public void writeToParcel(Parcel parcel, int i) {
            super.writeToParcel(parcel, i);
            parcel.writeInt(this.mVolumeStore.volume);
            parcel.writeInt(this.mVolumeStore.originalVolume);
        }
    }

    public class SeekBarVolumizer implements SeekBar.OnSeekBarChangeListener, Handler.Callback {
        private static final int CHECK_RINGTONE_PLAYBACK_DELAY_MS = 1000;
        private static final int MSG_SET_STREAM_VOLUME = 0;
        private static final int MSG_START_SAMPLE = 1;
        private static final int MSG_STOP_SAMPLE = 2;
        private AudioManager mAudioManager;
        private Context mContext;
        private Handler mHandler;
        private int mLastProgress;
        private int mOriginalStreamVolume;
        private Ringtone mRingtone;
        private SeekBar mSeekBar;
        private int mStreamType;
        private int mVolumeBeforeMute;
        private ContentObserver mVolumeObserver;

        public SeekBarVolumizer(VolumePreference volumePreference, Context context, SeekBar seekBar, int i) {
            this(context, seekBar, i, null);
        }

        public SeekBarVolumizer(Context context, SeekBar seekBar, int i, Uri uri) {
            this.mLastProgress = -1;
            this.mVolumeBeforeMute = -1;
            this.mVolumeObserver = new ContentObserver(this.mHandler) {
                /* class miui.preference.VolumePreference.SeekBarVolumizer.AnonymousClass1 */

                public void onChange(boolean z) {
                    super.onChange(z);
                    if (SeekBarVolumizer.this.mSeekBar != null && SeekBarVolumizer.this.mAudioManager != null) {
                        SeekBarVolumizer.this.mSeekBar.setProgress(SeekBarVolumizer.this.mAudioManager.getStreamVolume(SeekBarVolumizer.this.mStreamType));
                    }
                }
            };
            this.mContext = context;
            this.mAudioManager = (AudioManager) context.getSystemService(VEEditor.MVConsts.TYPE_AUDIO);
            this.mStreamType = i;
            this.mSeekBar = seekBar;
            HandlerThread handlerThread = new HandlerThread("VolumePreference.CallbackHandler");
            handlerThread.start();
            this.mHandler = new Handler(handlerThread.getLooper(), this);
            initSeekBar(seekBar, uri);
        }

        private void initSeekBar(SeekBar seekBar, Uri uri) {
            seekBar.setMax(this.mAudioManager.getStreamMaxVolume(this.mStreamType));
            this.mOriginalStreamVolume = this.mAudioManager.getStreamVolume(this.mStreamType);
            seekBar.setProgress(this.mOriginalStreamVolume);
            seekBar.setOnSeekBarChangeListener(this);
            this.mContext.getContentResolver().registerContentObserver(Settings.System.CONTENT_URI, true, this.mVolumeObserver);
            if (uri == null) {
                int i = this.mStreamType;
                uri = i == 2 ? Settings.System.DEFAULT_RINGTONE_URI : i == 5 ? Settings.System.DEFAULT_NOTIFICATION_URI : Settings.System.DEFAULT_ALARM_ALERT_URI;
            }
            this.mRingtone = RingtoneManager.getRingtone(this.mContext, uri);
            Ringtone ringtone = this.mRingtone;
            if (ringtone != null) {
                ringtone.setStreamType(this.mStreamType);
            }
        }

        private void onStartSample() {
            if (!isSamplePlaying()) {
                VolumePreference.this.onSampleStarting(this);
                Ringtone ringtone = this.mRingtone;
                if (ringtone != null) {
                    ringtone.play();
                }
            }
        }

        private void onStopSample() {
            Ringtone ringtone = this.mRingtone;
            if (ringtone != null) {
                ringtone.stop();
            }
        }

        private void postStartSample() {
            this.mHandler.removeMessages(1);
            Handler handler = this.mHandler;
            handler.sendMessageDelayed(handler.obtainMessage(1), isSamplePlaying() ? 1000 : 0);
        }

        /* access modifiers changed from: private */
        /* access modifiers changed from: public */
        private void postStopSample() {
            this.mHandler.removeMessages(1);
            this.mHandler.removeMessages(2);
            Handler handler = this.mHandler;
            handler.sendMessage(handler.obtainMessage(2));
        }

        public void changeVolumeBy(int i) {
            this.mSeekBar.incrementProgressBy(i);
            postSetVolume(this.mSeekBar.getProgress());
            postStartSample();
            this.mVolumeBeforeMute = -1;
        }

        public SeekBar getSeekBar() {
            return this.mSeekBar;
        }

        public boolean handleMessage(Message message) {
            int i = message.what;
            if (i == 0) {
                this.mAudioManager.setStreamVolume(this.mStreamType, this.mLastProgress, 0);
            } else if (i == 1) {
                onStartSample();
            } else if (i != 2) {
                Log.e(VolumePreference.TAG, "invalid SeekBarVolumizer message: " + message.what);
            } else {
                onStopSample();
            }
            return true;
        }

        public boolean isSamplePlaying() {
            Ringtone ringtone = this.mRingtone;
            return ringtone != null && ringtone.isPlaying();
        }

        public void muteVolume() {
            int i = this.mVolumeBeforeMute;
            if (i != -1) {
                this.mSeekBar.setProgress(i);
                postSetVolume(this.mVolumeBeforeMute);
                postStartSample();
                this.mVolumeBeforeMute = -1;
                return;
            }
            this.mVolumeBeforeMute = this.mSeekBar.getProgress();
            this.mSeekBar.setProgress(0);
            postStopSample();
            postSetVolume(0);
        }

        public void onProgressChanged(SeekBar seekBar, int i, boolean z) {
            if (z) {
                postSetVolume(i);
            }
        }

        public void onRestoreInstanceState(VolumeStore volumeStore) {
            int i = volumeStore.volume;
            if (i != -1) {
                this.mOriginalStreamVolume = volumeStore.originalVolume;
                this.mLastProgress = i;
                postSetVolume(this.mLastProgress);
            }
        }

        public void onSaveInstanceState(VolumeStore volumeStore) {
            int i = this.mLastProgress;
            if (i >= 0) {
                volumeStore.volume = i;
                volumeStore.originalVolume = this.mOriginalStreamVolume;
            }
        }

        public void onStartTrackingTouch(SeekBar seekBar) {
        }

        public void onStopTrackingTouch(SeekBar seekBar) {
            postStartSample();
        }

        /* access modifiers changed from: package-private */
        public void postSetVolume(int i) {
            this.mLastProgress = i;
            this.mHandler.removeMessages(0);
            Handler handler = this.mHandler;
            handler.sendMessage(handler.obtainMessage(0));
        }

        public void revertVolume() {
            this.mAudioManager.setStreamVolume(this.mStreamType, this.mOriginalStreamVolume, 0);
        }

        public void startSample() {
            postStartSample();
        }

        public void stop() {
            postStopSample();
            this.mContext.getContentResolver().unregisterContentObserver(this.mVolumeObserver);
            this.mSeekBar.setOnSeekBarChangeListener(null);
        }

        public void stopSample() {
            postStopSample();
        }
    }

    public static class VolumeStore {
        public int originalVolume = -1;
        public int volume = -1;
    }

    public VolumePreference(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        TypedArray obtainStyledAttributes = context.obtainStyledAttributes(attributeSet, R.styleable.VolumePreference, 0, 0);
        this.mStreamType = obtainStyledAttributes.getInt(VolumePreference_streamType, 0);
        obtainStyledAttributes.recycle();
    }

    private void cleanup() {
        PreferenceManager_unregisterOnActivityStopListener.invoke(PreferenceManager.class, getPreferenceManager(), this);
        if (this.mSeekBarVolumizer != null) {
            Dialog dialog = getDialog();
            if (dialog != null && dialog.isShowing()) {
                View findViewById = dialog.getWindow().getDecorView().findViewById(R.id.seekbar);
                if (findViewById != null) {
                    findViewById.setOnKeyListener(null);
                }
                this.mSeekBarVolumizer.revertVolume();
            }
            this.mSeekBarVolumizer.stop();
            this.mSeekBarVolumizer = null;
        }
    }

    private static int getVolumePreferenceStreamType() {
        try {
            return Field.of("android.R.styleable", "VolumePreference_streamType", Field.INT_SIGNATURE_PRIMITIVE).getInt(null);
        } catch (Exception e2) {
            e2.printStackTrace();
            return 0;
        }
    }

    public void onActivityStop() {
        SeekBarVolumizer seekBarVolumizer = this.mSeekBarVolumizer;
        if (seekBarVolumizer != null) {
            seekBarVolumizer.postStopSample();
        }
    }

    /* access modifiers changed from: protected */
    @Override // miui.preference.SeekBarDialogPreference
    public void onBindDialogView(View view) {
        super.onBindDialogView(view);
        this.mSeekBarVolumizer = new SeekBarVolumizer(this, getContext(), (SeekBar) view.findViewById(R.id.seekbar), this.mStreamType);
        PreferenceManager_registerOnActivityStopListener.invoke(PreferenceManager.class, getPreferenceManager(), this);
        view.setOnKeyListener(this);
        view.setFocusableInTouchMode(true);
        view.requestFocus();
    }

    /* access modifiers changed from: protected */
    public void onDialogClosed(boolean z) {
        SeekBarVolumizer seekBarVolumizer;
        super.onDialogClosed(z);
        if (!z && (seekBarVolumizer = this.mSeekBarVolumizer) != null) {
            seekBarVolumizer.revertVolume();
        }
        cleanup();
    }

    public boolean onKey(View view, int i, KeyEvent keyEvent) {
        if (this.mSeekBarVolumizer == null) {
            return true;
        }
        boolean z = keyEvent.getAction() == 0;
        if (i == 24) {
            if (z) {
                this.mSeekBarVolumizer.changeVolumeBy(1);
            }
            return true;
        } else if (i == 25) {
            if (z) {
                this.mSeekBarVolumizer.changeVolumeBy(-1);
            }
            return true;
        } else if (i != 164) {
            return false;
        } else {
            if (z) {
                this.mSeekBarVolumizer.muteVolume();
            }
            return true;
        }
    }

    /* access modifiers changed from: protected */
    public void onRestoreInstanceState(Parcelable parcelable) {
        if (parcelable == null || !parcelable.getClass().equals(SavedState.class)) {
            super.onRestoreInstanceState(parcelable);
            return;
        }
        SavedState savedState = (SavedState) parcelable;
        super.onRestoreInstanceState(savedState.getSuperState());
        SeekBarVolumizer seekBarVolumizer = this.mSeekBarVolumizer;
        if (seekBarVolumizer != null) {
            seekBarVolumizer.onRestoreInstanceState(savedState.getVolumeStore());
        }
    }

    /* access modifiers changed from: protected */
    public void onSampleStarting(SeekBarVolumizer seekBarVolumizer) {
        SeekBarVolumizer seekBarVolumizer2 = this.mSeekBarVolumizer;
        if (seekBarVolumizer2 != null && seekBarVolumizer != seekBarVolumizer2) {
            seekBarVolumizer2.stopSample();
        }
    }

    /* access modifiers changed from: protected */
    public Parcelable onSaveInstanceState() {
        Parcelable onSaveInstanceState = super.onSaveInstanceState();
        if (isPersistent()) {
            return onSaveInstanceState;
        }
        SavedState savedState = new SavedState(onSaveInstanceState);
        SeekBarVolumizer seekBarVolumizer = this.mSeekBarVolumizer;
        if (seekBarVolumizer != null) {
            seekBarVolumizer.onSaveInstanceState(savedState.getVolumeStore());
        }
        return savedState;
    }

    public void setStreamType(int i) {
        this.mStreamType = i;
    }
}
